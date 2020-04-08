package com.doommes.learn.AIDL;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerServices extends Service {
    public static final String TAG = "Services";
    public BookManagerServices() {
    }


    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
                mListenerList.unregister(listener);
            Log.d(TAG, "unregisterListener: " + mListenerList.beginBroadcast());
            mListenerList.finishBroadcast();
        }
    };

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean mIsServicesDestoryed = new AtomicBoolean(false);



    private void addNewBook(Book book) throws RemoteException {
        final int N = mListenerList.beginBroadcast();
        for (int i = 0 ; i< N; i++){
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            listener.onNewBookArrived(book);
        }
        mListenerList.finishBroadcast();
    }

    private class ServicesWork implements Runnable {

        @Override
        public void run() {
            while (!mIsServicesDestoryed.get()){
                try {
                    Thread.sleep(5000);
                    int bookId  = mBookList.size() + 1;
                    Book newBook = new Book(bookId, "new Book");
                    mBookList.add(newBook);
                    addNewBook(newBook);
                    Log.d(TAG, "run: add");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(0, "one"));
        mBookList.add(new Book(1, "two"));
        new Thread(new ServicesWork()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServicesDestoryed.set(true);
    }
}
