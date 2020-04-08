// IOnNewBookArrivedListener.aidl
package com.doommes.learn.AIDL;

// Declare any non-default types here with import statements
import com.doommes.learn.AIDL.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
