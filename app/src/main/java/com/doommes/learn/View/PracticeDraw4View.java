package com.doommes.learn.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.doommes.learn.R;

public class PracticeDraw4View extends View {
    private static final String TAG = "PracticeDraw4View";
    private Paint mPaint;
    private Paint mPaintNum;
    private int progress;
    private int degrees;
    private int rotates;
    private int degreess;

    public int getDegreess() {
        return degreess;

    }

    public void setDegreess(int degreess) {
        this.degreess = degreess;
        invalidate();
    }

    public int getRotates() {
        return rotates;

    }

    public void setRotates(int rotates) {
        this.rotates = rotates;
        invalidate();
    }

    public int getDegrees() {
        return degrees;

    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
        invalidate();
    }

    public int getProgress() {
        return progress;

    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public PracticeDraw4View(Context context) {
        super(context);
        init();
    }

    public PracticeDraw4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PracticeDraw4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaintNum = new Paint();
        mPaintNum.setTextAlign(Paint.Align.CENTER);
        mPaintNum.setTextSize(40);
        mPaintNum.setColor(Color.BLACK);
        progress = 10;
        degrees = 0;
        rotates = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);

        int width = getWidth();
        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        int start = (width / 2 - picWidth) / 2;
        int top = 50;
        int end = start + picWidth;
        int bottom = top + picHeight;

        Camera camera = new Camera();


        //1
        Path path1 = new Path();
        path1.addOval(start + picWidth / 5, top + picHeight / 5, end + 100, bottom + 100, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path1);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();

        //2
        start += width / 2;
        end += width / 2;
        Path path2 = new Path();
        path2.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        path2.addOval(start + picWidth / 5, top + picHeight / 5, end + 100, bottom + 100, Path.Direction.CCW);
        canvas.save();
        canvas.clipPath(path2);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();

        //3
        top += picHeight + 100;
        bottom += picHeight + 100;

        canvas.save();
        canvas.rotate(45, start + picWidth / 2, top + picHeight / 2);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();

        start -= width / 2;
        end -= width / 2;

        int centerX = start + picWidth / 2;
        int centerY = end + picHeight / 2;

        RectF rectF1 = new RectF(centerX, top - 100, end + 100, bottom + 100);
        RectF rectF2 = new RectF(start - 100, top - 100, centerX, bottom + 100);
        canvas.save();
        canvas.rotate(-rotates, centerX, centerY);
        camera.save();
        camera.rotateY(-degrees);
        camera.setLocation(0, 0, -20);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.clipRect(rectF1);
        canvas.rotate(rotates, centerX, centerY);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(-rotates, centerX, centerY);
        camera.save();
        camera.rotateY(degreess);
        camera.setLocation(0, 0, -20);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.clipRect(rectF2);
        canvas.rotate(rotates, centerX, centerY);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();


        //4
        top += picHeight + 100;
        bottom += picHeight + 100;

        canvas.save();
        camera.save();
        camera.rotateX(-30);
        //camera.setLocation(0, 0, -30);
        canvas.translate(start + picWidth / 2, top + picHeight / 2);
        camera.applyToCanvas(canvas);
        canvas.translate(-(start + picWidth / 2), -(top + picHeight / 2));
        camera.restore();
        canvas.clipRect(start, top, end, bottom - picHeight / 2);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(start, top + picHeight / 2, end, bottom);
        canvas.drawBitmap(bitmap, start, top, mPaint);
        canvas.restore();

        start += width / 2;
        end += width / 2;


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        String progressText = progress + "%";
        float textWidth = mPaint.measureText(progressText);
        canvas.drawText(progressText, start + picWidth / 2 - textWidth / 2, top + picHeight / 2, mPaint);

        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(start, top, end, bottom, 0, progress * 3.6f, false, mPaint);


    }
}
