package com.doommes.learn.View;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;

public class LikeView extends android.support.v7.widget.AppCompatCheckBox {
    private static final String TAG = "LikeView";
    private Paint mPaint;
    private Paint mPaintNum;
    //數字翻动角度
    private int numberDegrees;
    private static final int INIT_DEGREES = 45;
    private static final int REVERSE_FLAG = -1;
    private  int isLike = -1;
    private  int isFirst;

    private boolean isInit = true;

    //设置的数字
    private int number;
    //计算可变与不可变数字的flag
    private int numberFlag;
    //下一个或上一个数字
    private int numberFlip;

    public int getNumber() {
        return number;

    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberDegrees() {
        return numberDegrees;

    }

    public void setNumberDegrees(int numberDegrees) {
        this.numberDegrees = numberDegrees;
        invalidate();
    }
    public LikeView(Context context) {
        super(context);
        init();
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG, "init: ");
        mPaint = new Paint();
        mPaintNum = new Paint();
        mPaint.setTextSize(40);
        mPaint.setColor(Color.BLACK);
        mPaintNum.setTextSize(40);
        mPaintNum.setColor(Color.BLACK);
        mPaintNum.setTextAlign(Paint.Align.RIGHT);
        number = getNumber();
        numberDegrees = 0;
        isFirst = -2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        judgeChecked();
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = widthSpecSize;
        int height = heightSpecSize;
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            width = 100;
            height = 100;
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            width = (int) mPaint.measureText(String.valueOf(number));
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            height = (int) (mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top);
        }
        setMeasuredDimension(width+getPaddingLeft()+getPaddingRight()
                , height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
        Camera camera = new Camera();

        int count = 1;
        int countTemp = numberFlag;
        while (countTemp%10==0 && countTemp != 0){
            countTemp/=10;
            count++;
        }
        int constantNum = (int) (number/Math.pow(10, count));
        int changeNum = (int) (number%Math.pow(10, count));


        String constant = String.valueOf(constantNum);
        Rect rect = new Rect();
        mPaintNum.getTextBounds(constant, 0, constant.length(), rect);
        if (constantNum != 0) canvas.drawText(constant, 0,mPaint.getFontSpacing(), mPaint);
        drawNumber(canvas, camera, changeNum, rect.right + mPaintNum.measureText(String.valueOf(changeNum)), mPaint.getFontSpacing());

        Log.d(TAG, "onDraw: count=" + count
                + " constantNum=" + constantNum
                + " changeNum=" + changeNum
                + " " +numberDegrees + " "
                +isChecked());
    }

    private void judgeChecked() {
        if (this.isChecked()){
            isLike = -1;
            numberFlag = number;
            numberFlip = number - 1;
        }else {
            isLike = 1;
            numberFlag = number + 1;
            numberFlip = number + 1;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isFirst != 0){
            isLike *= REVERSE_FLAG;
            Log.d(TAG, "onTouchEvent: After"+isChecked());
        }else {
            isInit = false;
            isFirst++;
            Log.d(TAG, "onTouchEvent: First"+isChecked());
        }
        return super.onTouchEvent(event);
    }

    private void drawNumber(Canvas canvas, Camera camera, int num, float x, float y){
        int flag = isLike;

        float width = mPaintNum.measureText(String.valueOf(num));
        float height = mPaintNum.getFontSpacing() * isLike;
        float diff = (numberDegrees/(float)INIT_DEGREES) * height * REVERSE_FLAG;
                ;
        int degrees = 45;
        int alpha = 255 - (int) (numberDegrees/(float)INIT_DEGREES * 255);

        numberDegrees *= isLike;

        canvas.save();
        mPaintNum.setAlpha(alpha);
        camera.save();
        camera.rotateX(numberDegrees);
        canvas.translate((x+width/2), y);
        camera.applyToCanvas(canvas);
        canvas.translate(-(x+width/2), -y);
        camera.restore();
        canvas.drawText(String.valueOf(num), x, y + diff, mPaintNum);
        canvas.restore();

        int degreesHide = INIT_DEGREES * isLike * REVERSE_FLAG + numberDegrees;
        canvas.save();
        mPaintNum.setAlpha(255 - alpha);
        camera.save();
        camera.rotateX(degreesHide);
        canvas.translate((x+width/2), y);
        camera.applyToCanvas(canvas);
        canvas.translate(-(x+width/2), -y);
        canvas.drawText(String.valueOf(numberFlip), x, y + height + diff, mPaintNum);
        camera.restore();
        canvas.restore();
        Log.d(TAG, "drawNumber: y1 = " +(y + diff)
                + " y2 = " + (y + height + diff)
                + " degrees1 = " + numberDegrees
                + " degrees2 = " + (degreesHide));
    }
}
