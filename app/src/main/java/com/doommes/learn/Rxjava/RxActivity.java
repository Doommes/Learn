package com.doommes.learn.Rxjava;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.doommes.learn.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class RxActivity extends AppCompatActivity {
    private static final String TAG = "RxActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
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
}
