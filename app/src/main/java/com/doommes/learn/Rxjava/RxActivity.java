package com.doommes.learn.Rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.doommes.learn.BaseActivity;
import com.doommes.learn.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

@SuppressLint("CheckResult")
public class RxActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RxActivity";
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private FrameLayout mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initView();
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1); // skip
            Thread.sleep(400);
            emitter.onNext(2); // deliver
            Thread.sleep(405);
            emitter.onNext(3); // skip
            Thread.sleep(100);
            emitter.onNext(4); // deliver
            Thread.sleep(105);
            emitter.onNext(5); // deliver
            emitter.onComplete();
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(integer -> Log.d(TAG, "onCreate: " + integer));

    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
        mFragment = (FrameLayout) findViewById(R.id.fragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn1:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_rx,new BlankFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
        }
    }
}
