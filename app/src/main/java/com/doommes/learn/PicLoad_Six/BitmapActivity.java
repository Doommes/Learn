package com.doommes.learn.PicLoad_Six;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doommes.learn.R;

public class BitmapActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImg;
    /**
     * density%1$d
     */
    private TextView mDensity;
    private TextView mWidth;
    private TextView mHeight;
    private TextView mConfig;
    private TextView mSize;
    private ImageView mImg2;
    private ImageView mImg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        initView();
    }

    private void initView() {

        mImg = (ImageView) findViewById(R.id.img);
        mImg2 = (ImageView) findViewById(R.id.img2);
        mImg3 = (ImageView) findViewById(R.id.img3);



        Bitmap bitmap = decodeBitmap(100, 200);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);

        mImg.setImageBitmap(bitmap);
        mImg2.setImageBitmap(bitmap1);
        Bitmap bitmap2 = ((BitmapDrawable)mImg3.getDrawable()).getBitmap();


        mWidth = (TextView) findViewById(R.id.width);
        mWidth.setText(String.format(getResources().getString(R.string.Width), bitmap.getWidth() + " " + bitmap1.getWidth()+ " " + bitmap2.getWidth()));
        mHeight = (TextView) findViewById(R.id.height);
        mHeight.setText(String.format(getResources().getString(R.string.Height), bitmap.getHeight() + " " + bitmap1.getHeight() + " " + bitmap2.getHeight()));
        mConfig = (TextView) findViewById(R.id.config);
        mConfig.setText(String.format(getResources().getString(R.string.Config), bitmap.getConfig().toString() + " " + bitmap1.getConfig().toString()+ " " + bitmap2.getConfig().toString()));
        mSize = (TextView) findViewById(R.id.size);
        mSize.setText(String.format(getResources().getString(R.string.Size), bitmap.getRowBytes() + " " + bitmap1.getByteCount() + " " + bitmap2.getByteCount()));

        mDensity = (TextView) findViewById(R.id.density);
        mDensity.setText(String.format(getResources().getString(R.string.density), bitmap.getDensity()));
    }

    private Bitmap decodeBitmap(int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return   BitmapFactory.decodeResource(getResources(), R.drawable.bitmap, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (height > reqHeight || width > reqHeight) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.density:
                break;
        }
    }
}
