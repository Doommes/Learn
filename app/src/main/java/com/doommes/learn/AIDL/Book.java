package com.doommes.learn.AIDL;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int bookId;
    private String  boolName;

    public Book(int bookId, String boolName) {
        this.bookId = bookId;
        this.boolName = boolName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", boolName='" + boolName + '\'' +
                '}';
    }

    private Book(Parcel in) {
        bookId = in.readInt();
        boolName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(boolName);
    }
}
