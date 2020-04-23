package com.doommes.learn;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private boolean flag = false;
    private float downX;
    private float downY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (ev.getX() <= 50){
                    flag = true;
                    downX = ev.getX();
                    downY = ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (flag && ev.getX() - downX >= 150){
                    onBackPressed();
                    flag = false;
                    Log.d(TAG, "dispatchTouchEvent: back");
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
