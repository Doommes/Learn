// IBookManager.aidl
package com.doommes.learn.AIDL;

// Declare any non-default types here with import statements
import com.doommes.learn.AIDL.Book;
import com.doommes.learn.AIDL.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener lintener);
    void unregisterListener(IOnNewBookArrivedListener lintener);
}
