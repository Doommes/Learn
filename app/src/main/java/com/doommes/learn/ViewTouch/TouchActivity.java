package com.doommes.learn.ViewTouch;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;

import com.doommes.learn.R;

public class TouchActivity extends AppCompatActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        initView();
    }

    private void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(manager);
        mList.setAdapter(adapter);
    }
}
