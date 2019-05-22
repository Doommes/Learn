// IBookManager.aidl
package com.doommes.learn;

// Declare any non-default types here with import statements
import com.doommes.learn.AIDL.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

}
