package com.doommes.learn.ImageLoader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class ImageLoader {
    private static final int DISK_CACHE_MAXSIZE = 10 * 1024 * 1024;

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private Context mContext;

    public ImageLoader(Context context) {
        mContext = context.getApplicationContext();

        int memorySize = (int) (Runtime.getRuntime().maxMemory()/8);
        mMemoryCache = new LruCache<String, Bitmap>(memorySize){
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        // TODO 判断当前储存空间是否足够
        try {
            mDiskLruCache = DiskLruCache.open(diskCacheDir, getAppVersion(mContext), 1, DISK_CACHE_MAXSIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getAppVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public static File getDiskCacheDir(Context context, String uniqueName){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private void putBitmapToMemoryCache(String key, Bitmap bitmap){
        if (getBitmapToMemoryCache(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapToMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight){
        return null;
    }
}
