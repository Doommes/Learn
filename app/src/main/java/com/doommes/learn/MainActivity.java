package com.doommes.learn;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.doommes.learn.permission.FloatWindowManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRvList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");

    }



    private void initView() {
        String[] items = {"Bitmap", "DiskLruCache",
                "PhotoWall", "Handler", "Thread",
                "LiveData","Rxjava", "View", "Touch",
                "remote"};
        mRvList = (RecyclerView) findViewById(R.id.rv_list);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRvList.setLayoutManager(manager);
        mRvList.setAdapter(new ItemAdapter(items, this));

        //FloatWindowManager.getInstance().applyOrShowFloatWindow(this);

    }

}
