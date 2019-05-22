package com.doommes.learn.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

public class TestView extends View implements GestureDetector.OnGestureListener {

    GestureDetector mGestureDetector;
    Scroller mScroller;

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(getContext(), this);
        mGestureDetector.setIsLongpressEnabled(false);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        velocityTracker.computeCurrentVelocity(1000);
        int velocityX = (int) velocityTracker.getXVelocity();
        int velocityY = (int) velocityTracker.getYVelocity();

        velocityTracker.clear();
        velocityTracker.recycle();

        //GestureDetector 接管onTouchEvent
        Boolean consume = mGestureDetector.onTouchEvent(event);
        return consume;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private void smoothScroller(int destX, int destY){
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 10000ms 内滑动destX， 效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
         if (mScroller.computeScrollOffset()){
             scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
             postInvalidate();
         }
    }
}
