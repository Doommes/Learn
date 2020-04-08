package com.doommes.learn.AIDL;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

public interface IMyBookManager extends IInterface {
    static final String DESCRIPTOR = "com.deeoomes.learn.AIDL.IMyBookManager";

    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION;
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    public List<Book> getBookList() throws RemoteException;

    public void addBook(Book book) throws RemoteException;

    public class BookManagerImpl extends Binder implements IMyBookManager {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return null;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
