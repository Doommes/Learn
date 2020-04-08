package com.doommes.learn;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
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

import com.doommes.learn.AIDL.Book;
import com.doommes.learn.AIDL.BookManagerServices;
import com.doommes.learn.AIDL.IBookManager;
import com.doommes.learn.AIDL.IOnNewBookArrivedListener;
import com.doommes.learn.Jni.TestJni;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private RecyclerView mRvList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");

        Log.d(TAG, "onCreate: " + TestJni.sayHello());
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

        Intent intent = new Intent(MainActivity.this, BookManagerServices.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private IBookManager mRemoteBookManager;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            mRemoteBookManager = bookManager;
            try {
                bookManager.registerListener(onNewBookArrivedListener);

                List<Book> books = bookManager.getBookList();
                Log.d(TAG, "onServiceConnected: " + books.getClass().getCanonicalName());
                Log.d(TAG, "onServiceConnected: " + books.toString());
                bookManager.addBook(new Book(2, "three"));
                List<Book> newBooks = bookManager.getBookList();
                Log.d(TAG, "onServiceConnected: " +newBooks);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.d(TAG, "onNewBookArrived: new Book" + book.toString());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mRemoteBookManager && mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                mRemoteBookManager.unregisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
    }
}
