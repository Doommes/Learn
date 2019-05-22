package com.doommes.learn.View;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.doommes.learn.R;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ViewActivity";
    private PracticeDraw4View mView;
    private LikeView mLike;

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
        mLike.setNumber(100);
        mLike.setChecked(true);
        Log.d(TAG, "initView: ");
        mLike.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.like:
                Log.d(TAG, "onClick: ");
                if (mLike.isChecked()){
                    ObjectAnimator.ofInt(mLike, "numberDegrees", 45)
                            .setDuration(500)
                            .start();
                }else {
                    ObjectAnimator.ofInt(mLike, "numberDegrees", 45)
                            .setDuration(500)
                            .start();
                }
                break;
        }
    }
}
