package com.doommes.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

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
        String[] items = {"Bitmap", "DiskLruCache", "PhotoWall"};
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRvList.setLayoutManager(manager);
        mRvList.setAdapter(new ItemAdapter(items, this));
    }
}
