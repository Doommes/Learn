package com.doommes.learn.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.doommes.learn.R;

/**
 * 继承View重写onDraw
 * 1.考虑wrap_content和padding
 * 1.自定义属性
 */
public class CircleView extends View {

    private int mColor = Color.RED;
    private Paint mPaint;
    private Path mPath;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.nvshenshenqingye_toutu);
        BitmapShader bitmapShader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.shouyefujin_shipinsupei);
        BitmapShader bitmapShader2 = new BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        ComposeShader composeShader = new ComposeShader(bitmapShader1, bitmapShader2, PorterDuff.Mode.SRC_IN);

        mPaint.setShader(bitmapShader1);
        mPaint.setStrokeWidth(20);
        mPath= new Path();
        RectF rectF = new RectF();
//        画爱心
        mPath.addArc(50, 50, 350, 360, -230, 230);
        mPath.arcTo(350, 50, 650, 360, -180, 230, false);
        mPath.lineTo(350, 600);
        mPath.close();

//        mPath.addCircle(100,100, 100, Path.Direction.CW);
//        mPath.addCircle(100,100, 150, Path.Direction.CW);
//        mPath.setFillType(Path.FillType.EVEN_ODD);


//        mPath.addArc(200, 200, 400, 400, -225, 225);
//        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
//        mPath.lineTo(400, 542);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
            width = 100;
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            height = 100;
        }
        setMeasuredDimension(width+getPaddingLeft()+getPaddingRight()
                , height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom= getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.min(width, height)/2;
        //canvas.drawCircle(paddingLeft + width/2, paddingTop + height/2, radius, mPaint);

        canvas.drawPath(mPath, mPaint);
//        int saved = canvas.saveLayer(null,null, Canvas.ALL_SAVE_FLAG);
//
//        //canvas.drawPath(mPath, mPaint);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.shouyefujin_shipinsupei);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap2, 300, 400, mPaint);
//        mPaint.setXfermode(null);
//
//        canvas.restoreToCount(saved);
//        RectF rectF = new RectF(100, 100, 600, 600);
//        canvas.drawArc(rectF, -55, 55, true, mPaint);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(rectF, 5, 20, true, mPaint);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawArc(rectF, 30, 20, true, mPaint);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawArc(rectF, 55, 40, true, mPaint);
//        mPaint.setColor(Color.DKGRAY);
//        canvas.drawArc(rectF, 100, 75, true, mPaint);
//        rectF.set(90,90,590,590);
//        canvas.drawArc(rectF, -180, 120, true, mPaint);
//        mPaint.setColor(Color.RED);
//        Shader shader = new LinearGradient(1000,1000, 1500, 1500 ,Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
//        mPaint.setShader(shader);
//        canvas.drawText("etdfadfag", 100,100, mPaint);
    }
}
