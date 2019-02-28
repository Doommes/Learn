package com.doommes.learn;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Uitl {
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

    // MD5 加密
    public static String hashKeyForDisk(String key){
        String cacheKey;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            cacheKey = byteToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    private static String byteToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < digest.length ; i++){
            String hex = Integer.toHexString(0xFF & digest[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    // 优化Bitmap加载
    public static Bitmap loadBitmap(DiskLruCache.Snapshot snapshot, int reqWidth, int reqHeight) {
        try {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(0);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileInputStream.getFD(), null, options);
            options.inSampleSize = Uitl.calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //计算采样率
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
}
