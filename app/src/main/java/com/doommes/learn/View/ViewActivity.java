package com.doommes.learn.View;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

import com.doommes.learn.R;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ViewActivity";
    private PracticeDraw4View mView;
    private LikeView mLike;
    private LoveView mLove;
    private LoveView mLove2;
    private LoveView mLove3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initView();
    }

    private void initView() {
        mView = (PracticeDraw4View) findViewById(R.id.view);
        mView.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override
            public void onClick(View v) {
//                mView.animate().y(100) ;
//                ObjectAnimator animator = ObjectAnimator.ofInt(mView, "progress", 30,100,80)
//                        .setDuration(2000);
//                animator.setInterpolator(new AnticipateOvershootInterpolator());
//                animator.start();

                ObjectAnimator animator1 = ObjectAnimator.ofInt(mView, "degrees", 0, 45)
                        .setDuration(1000);
                ObjectAnimator animator2 = ObjectAnimator.ofInt(mView, "rotates", 0, 270)
                        .setDuration(5000);

                ObjectAnimator animator3 = ObjectAnimator.ofInt(mView, "degreess", 0, 30)
                        .setDuration(500);

                AnimatorSet set = new AnimatorSet();
                set.playSequentially(animator1, animator2, animator3);
            }
        });
        mLike = (LikeView) findViewById(R.id.like);
        mLike.setNumber(1199);
        mLike.setChecked(false);
        Log.d(TAG, "initView: ");
        mLike.setOnClickListener(this);


        mLove = (LoveView) findViewById(R.id.love1);
        mLove.setNumber(11199);
        mLove.setChecked(false);
        mLove.setOnClickListener(this);

        mLove2 = (LoveView) findViewById(R.id.love2);
        mLove2.setNumber(99);
        mLove2.setChecked(false);
        mLove2.setOnClickListener(this);

        mLove3 = (LoveView) findViewById(R.id.love3);
        mLove3.setNumber(12200);
        mLove3.setChecked(true);
        mLove3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.like:
                Log.d(TAG, "onClick: ");
                if (mLike.isChecked()) {
                    ObjectAnimator.ofInt(mLike, "numberDegrees", 45)
                            .setDuration(500)
                            .start();
                } else {
                    ObjectAnimator.ofInt(mLike, "numberDegrees", 45)
                            .setDuration(500)
                            .start();
                }
                break;
            case R.id.love1:
                Log.d(TAG, "onClick: " + mLove.isChecked());
                if (mLove.isChecked()) {
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator animator1 = ObjectAnimator.ofInt(mLove, "value", LoveView.ANIMATE_END)
                            .setDuration(300);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLove, "speed", 1f)
                            .setDuration(200);
                    animator2.setInterpolator(new AnticipateOvershootInterpolator());
                    set.playTogether(animator1, animator2);
                    set.start();

                } else {
                    AnimatorSet set = new AnimatorSet();
                    ObjectAnimator animator1 = ObjectAnimator.ofInt(mLove, "value", 0)
                            .setDuration(300);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLove, "speed", 0f)
                            .setDuration(200);
                    animator2.setInterpolator(new AnticipateOvershootInterpolator());
                    set.playTogether(animator1, animator2);
                    set.start();
                }
                break;
            case R.id.love2:
                Log.d(TAG, "onClick: " + mLove2.isChecked());
                if (mLove2.isChecked()) {
                    ObjectAnimator.ofInt(mLove2, "value", LoveView.ANIMATE_END)
                            .setDuration(300)
                            .start();
                } else {
                    ObjectAnimator.ofInt(mLove2, "value", 0)
                            .setDuration(300)
                            .start();
                }
                break;
            case R.id.love3:
                Log.d(TAG, "onClick: " + mLove3.isChecked());
                if (mLove3.isChecked()) {
                    AnimatorSet set = new AnimatorSet();

                    ObjectAnimator animator1 = ObjectAnimator.ofInt(mLove3, "value", LoveView.ANIMATE_END)
                            .setDuration(300);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLove3, "speed", 1f)
                            .setDuration(200);
                    animator2.setInterpolator(new AnticipateOvershootInterpolator());
                    set.playTogether(animator1, animator2);
                    set.start();
                } else {
                    AnimatorSet set = new AnimatorSet();

                    ObjectAnimator animator1 = ObjectAnimator.ofInt(mLove3, "value", 0)
                            .setDuration(300);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mLove3, "speed", 0f)
                            .setDuration(200);
                    animator2.setInterpolator(new AnticipateOvershootInterpolator ());
                    set.playTogether(animator1, animator2);
                    set.start();
                }
                break;
        }
    }
}
