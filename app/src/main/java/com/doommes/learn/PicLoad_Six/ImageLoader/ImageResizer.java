package com.doommes.learn.PicLoad_Six.ImageLoader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.doommes.learn.R;

import java.io.FileDescriptor;

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public ImageResizer() {
    }

    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.bitmap, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return   BitmapFactory.decodeResource(res, R.drawable.bitmap, options);
    }

    public Bitmap decodeSampleBitmapFromFileDescriotor(FileDescriptor fd, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, 150, 100);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
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
