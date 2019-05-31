package com.doommes.learn.ViewTouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class OutSideScroller extends ScrollView {
    private static final String TAG = "OutSideScroller";
    private int mlastX;
    private int mlastY;
    public OutSideScroller(Context context) {
        super(context);
    }

    public OutSideScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutSideScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = Math.abs(x - mlastX);
                int deltaY = Math.abs(y - mlastY);
                if (deltaX > deltaY){
                    intercepted = false;
                }else {
                    intercepted = true;
                }
                Log.d(TAG, "onInterceptTouchEvent: " + deltaX + " " + deltaY);
                break;
            case MotionEvent.ACTION_UP:
                 intercepted = false;
                 break;
            default:
                break;
        }
        Log.d(TAG, "onInterceptTouchEvent: " + intercepted);
        mlastX = x;
        mlastY = y;
        return intercepted;
    }
}
