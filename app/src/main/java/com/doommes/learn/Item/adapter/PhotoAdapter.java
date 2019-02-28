package com.doommes.learn.Item.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doommes.learn.R;
import com.doommes.learn.Uitl;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private static final String TAG = "PhotoAdapter";
    private String[] imgs;
    private LruCache<String, Bitmap> mLruCache = null;
    private DiskLruCache mDiskLruCache = null;
    private View mView;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageView view = mView.findViewWithTag(Uitl.hashKeyForDisk((String) msg.obj));
            view.setImageBitmap(loadImage((String) msg.obj));
        }
    };

    public PhotoAdapter(Context context, String[] imageThumbUrls) {
        imgs = imageThumbUrls;
        int size = (int) (Runtime.getRuntime().maxMemory()/1024);
        mLruCache = new LruCache<String, Bitmap>(size){
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
        try {
            File cacheDir = Uitl.getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, Uitl.getAppVersion(context), 1, 10*1024*1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder viewHolder, int i) {

        viewHolder.mImageView.setTag(Uitl.hashKeyForDisk(imgs[i]));
        Bitmap bitmap = loadImage(imgs[i]);
        if (bitmap != null){
            viewHolder.mImageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public int getItemCount() {
        return imgs.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImageView = mView.findViewById(R.id.img);
        }
    }

    private Bitmap loadImage(String img) {
        Bitmap bitmap = getBitmapFromLruCache(img);
        if (bitmap != null){
            return bitmap;
        }else {
            return getBitmapFromDiskLruCache(img);
        }
    }



    public Bitmap getBitmapFromLruCache(String key){
        Bitmap bitmap = mLruCache.get(Uitl.hashKeyForDisk(key));
        if (bitmap != null){
            Log.d(TAG, "getBitmapFromLruCache: LurCache");
            return bitmap;
        }
        return null;
    }

    private Bitmap getBitmapFromDiskLruCache(String img){
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(Uitl.hashKeyForDisk(img));
            if (snapshot != null){
                Bitmap bitmap = Uitl.loadBitmap(snapshot, 150, 150);
                Log.d(TAG, "getBitmapFromDiskLruCache: DiskLruCache");
                return bitmap;
            }else {
                writeToDiskLruCache(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeToDiskLruCache(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String key = Uitl.hashKeyForDisk(url);
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null){
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(url, outputStream)){
                            editor.commit();
                        }else {
                            editor.abort();
                        }
                    }
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(Uitl.hashKeyForDisk(url));
                    if (snapshot != null){
                        Bitmap bitmap = Uitl.loadBitmap(snapshot, 150, 150);
                        if (bitmap != null){
                            mLruCache.put(Uitl.hashKeyForDisk(url), bitmap);
                            Log.d(TAG, "run: write to lruCache");
//                            Message message = new Message();
////                            message.obj = url;
////                            mHandler.sendMessage(message);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 1 * 1024);
            out = new BufferedOutputStream(outputStream, 8*1024);
            int b;
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
        }
        return false;
    }


}
