package com.doommes.learn.PicLoad_Six;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.doommes.learn.R;
import com.doommes.learn.Util;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.doommes.learn.Util.hashKeyForDisk;

public class DiskLruCacheActivity extends AppCompatActivity {
    private static final String TAG = "DiskLruCacheActivity";
    DiskLruCache mDiskLruCache = null;
    private ImageView mImg;
    private TextView mDensity;
    private TextView mWidth;
    private TextView mHeight;
    private TextView mConfig;
    private TextView mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache);
        initView();



    }



    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mWidth = (TextView) findViewById(R.id.width);
        mHeight = (TextView) findViewById(R.id.height);
        mConfig = (TextView) findViewById(R.id.config);
        mSize = (TextView) findViewById(R.id.size);
        mDensity = (TextView) findViewById(R.id.density);


        OpenDiskLruCache();
        try {
            if (!isDisLruCache()){
                writeToDisk();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadDisk();
    }

    private void OpenDiskLruCache() {
        File cacheDir = Util.getDiskCacheDir(this, "bitmap");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, Util.getAppVersion(this), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDisLruCache() throws IOException {
        String imageUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        String key = hashKeyForDisk(imageUrl);
        DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        if (snapShot != null){
            Log.d(TAG, "isDisLruCache: true");
            return true;
        }
        return false;
    }

    private void loadDisk() {
        try {
            String imageUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
            String key = hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                FileInputStream is = (FileInputStream) snapShot.getInputStream(0);
                FileDescriptor fileDescriptor = is.getFD();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                options.inSampleSize = calculateInSampleSize(options, 150, 100);
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

                mImg.setImageBitmap(bitmap);
                mWidth.setText(String.format(getResources().getString(R.string.Width), bitmap.getWidth() + " " + bitmap.getWidth()+ " " + bitmap.getWidth()));
                mHeight.setText(String.format(getResources().getString(R.string.Height), bitmap.getHeight() + " " + bitmap.getHeight() + " " + bitmap.getHeight()));
                mConfig.setText(String.format(getResources().getString(R.string.Config), bitmap.getConfig().toString() + " " + bitmap.getConfig().toString()+ " " + bitmap.getConfig().toString()));
                mSize.setText(String.format(getResources().getString(R.string.Size), bitmap.getByteCount() + " " + bitmap.getByteCount() + " " + bitmap.getByteCount()));
                mDensity.setText(String.format(getResources().getString(R.string.density), bitmap.getDensity()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSimpleSize = 1;
        if (width > reqWidth || height > reqHeight){
            int widthRatio = Math.round(width/reqWidth);
            int heightRatio = Math.round(height/reqHeight);
            inSimpleSize = widthRatio > heightRatio ? heightRatio : widthRatio;
        }
        return inSimpleSize;
    }



    private void writeToDisk() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUrl = "https://img-my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
                String key = hashKeyForDisk(imageUrl);
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null){
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (dowmloadUrlToStream(imageUrl, outputStream)){
                            editor.commit();
                        }else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean dowmloadUrlToStream(String urlString, OutputStream outputStream) throws IOException {
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
