package com.doommes.learn.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.doommes.learn.R;

public class LoveView extends android.support.v7.widget.AppCompatCheckBox {
    private static final String TAG = "LoveView";
    private Paint mPaint;
    private Paint mPaintNum;
    private Paint mPaintLove;
    private Camera mCamera;
    private Rect mRect;

    // 动画终点
    private int value;
    //修正是否点击的value值
    private int mFixValue;
    // 散开效果的动画
    private float speed;


    // 初始化数字
    private int mNumber;
    // 不变部分的数字
    private int mNumberOfConstant;
    // 可变部分的数字
    private int mNumberOfChange;
    private int mNumberOfChangeNext;

    // 是否点赞
    private int isLove;

    private Bitmap mBitmapLove;
    private Bitmap mBitmapUnLove;
    private Bitmap mBitmapDecoration;

    private int mTextHeight;
    private static final int REVERSE_FLAG = -1;
    public static final int ANIMATE_END = 100;
    public int getValue() {
        return value;

    }

    public void setValue(int value) {
        this.value = value;
        invalidate();
    }

    public int getNumber() {
        return mNumber;

    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public float getSpeed() {
        return speed;

    }

    public void setSpeed(float speed) {
        this.speed = speed;
        invalidate();
    }

    public LoveView(Context context) {
        super(context);
        init();
    }

    public LoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始化画笔
        //不可移动数字
        mPaint = new Paint();
        mPaint.setTextSize(40);
        mPaint.setColor(Color.BLACK);
        // 可移动数字
        mPaintNum = new Paint();
        mPaintNum.setTextSize(40);
        mPaintNum.setColor(Color.BLACK);
        mPaintNum.setTextAlign(Paint.Align.RIGHT);
        // 点赞图片
        mPaintLove = new Paint();
        // 存放数字高度信息
        mRect = new Rect();
        mCamera = new Camera();

        //文字高度
        mTextHeight = (int) (mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent);

        mBitmapLove = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_selected);
        mBitmapUnLove = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_unselected);
        mBitmapDecoration = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_messages_like_selected_shining);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        IsChecked();
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = widthSpecSize;
        int height = heightSpecSize;
        int textWidth = (int) mPaintNum.measureText(String.valueOf(mNumberOfConstant) + String.valueOf(mNumberOfChange));
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            width = textWidth + mBitmapLove.getWidth();
            height = mTextHeight * 3;
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            width = textWidth + mBitmapLove.getWidth();
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            height = mTextHeight * 3;
        }
        setMeasuredDimension(width+getPaddingLeft()+getPaddingRight()
                , height + getPaddingTop() + getPaddingBottom());
    }
    private void IsChecked() {
        int countTemp;

        if (this.isChecked()){
            isLove = -1;
            countTemp = mNumber;
            value = 100;
            speed = 1f;
        }else {
            isLove = 1;
            countTemp = mNumber + isLove;
            value = 0;
            speed = 0f;
        }
        mFixValue = isLove == -1 ? 1 : 0;
        int count = 1;
        while (countTemp%10==0 && countTemp != 0){
            countTemp/=10;
            count++;
        }
        // 分别计算不变数字、变化数字、变化后的数字
        mNumberOfConstant = (int) (mNumber/Math.pow(10, count));
        mNumberOfChange = (int) (mNumber%Math.pow(10, count));
        mNumberOfChangeNext = mNumberOfChange + isLove;

        measureNumber(mNumberOfConstant);
        Log.d(TAG, "IsChecked: number= " + mNumber + " countTemp = " + countTemp + " count = " + count + " " + isChecked());
        Log.d(TAG, "IsChecked: change = "+ mNumberOfChange + " change_next = "+ mNumberOfChangeNext +
                " constant = "+ mNumberOfConstant);
    }

    // 计算文字高度等息息
    private void measureNumber(int num) {
        String constant = String.valueOf(num);
        mPaintNum.getTextBounds(constant, 0, constant.length(), mRect);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算文字坐标
        int constantX = getPaddingStart() + mBitmapLove.getWidth();
        int changeX = (int) (constantX + mPaintNum.measureText(String.valueOf(mNumberOfConstant) + String.valueOf(mNumberOfChange))) ;
        float y = mTextHeight * 2 + getPaddingTop();
        //计算图片坐标
        int bitmapTop = (int) y  - mBitmapLove.getHeight() + (int)(Math.abs(mBitmapLove.getHeight() - mRect.bottom + mRect.top)/2f);
        int bitmapStart = getPaddingStart();

        //绘制图片
        if (isChecked()){
            canvas.save();
            canvas.scale(speed, speed, bitmapStart + mBitmapLove.getHeight()/2, bitmapTop);
            canvas.drawBitmap(mBitmapDecoration, bitmapStart + 5, bitmapTop - mBitmapLove.getHeight()/5*2, mPaintLove);
            canvas.drawBitmap(mBitmapLove, bitmapStart, bitmapTop, mPaintLove);
            canvas.restore();
            canvas.save();
            canvas.scale(1- speed, 1 - speed, bitmapStart + mBitmapLove.getHeight()/2, bitmapTop);
            canvas.drawBitmap(mBitmapUnLove, bitmapStart, bitmapTop, mPaintLove);
            canvas.restore();
        }else {
            canvas.save();
            canvas.scale(speed, speed, bitmapStart + mBitmapLove.getHeight()/2, bitmapTop);
            canvas.drawBitmap(mBitmapDecoration, bitmapStart + 5, bitmapTop - mBitmapLove.getHeight()/5*2, mPaintLove);
            canvas.drawBitmap(mBitmapLove, bitmapStart, bitmapTop, mPaintLove);
            canvas.restore();
            canvas.save();
            canvas.scale(1- speed, 1 - speed, bitmapStart + mBitmapLove.getHeight()/2, bitmapTop);
            canvas.drawBitmap(mBitmapUnLove, bitmapStart, bitmapTop, mPaintLove);
            canvas.restore();
        }
        // 绘制文字
        if (mNumberOfConstant != 0) canvas.drawText(String.valueOf(mNumberOfConstant), constantX, y, mPaint);
        drawNumber(canvas, mCamera,changeX , y);

    }

    private void drawNumber(Canvas canvas, Camera camera, float x, float y){
        //view 的width和height
        float widthCanvas = getWidth();
        float heightCanvas = getHeight();
        // 带显示数字的高度增量
        float height = mPaintNum.getFontSpacing() * isLove;
        // 动画增量
        float diff = Math.abs(((value - mFixValue * ANIMATE_END) / (float)ANIMATE_END));
        // rotate 的初始值
        int degrees = 45 * isLove;

        float diffHeight = diff * height * REVERSE_FLAG;
        float diffDegrees = diff * degrees;
        int alpha = (int) (255 - (diff * 255));

        canvas.save();
        mPaintNum.setAlpha(alpha);
        camera.save();
        camera.rotateX(diffDegrees);
        canvas.translate(widthCanvas/2, heightCanvas/2);
        camera.applyToCanvas(canvas);
        canvas.translate(-widthCanvas/2, -heightCanvas/2);
        canvas.drawText(String.valueOf(mNumberOfChange), x, y + diffHeight, mPaintNum);

        camera.restore();
        canvas.restore();
//
        canvas.save();
        mPaintNum.setAlpha(255 - alpha);
        camera.save();
        camera.rotateX(degrees*REVERSE_FLAG + diffDegrees);
        canvas.translate(widthCanvas/2, heightCanvas/2);
        camera.applyToCanvas(canvas);
        canvas.translate(-widthCanvas/2, -heightCanvas/2);
        canvas.drawText(String.valueOf(mNumberOfChangeNext), x, y + height + diffHeight, mPaintNum);
        camera.restore();
        canvas.restore();
    }

}
