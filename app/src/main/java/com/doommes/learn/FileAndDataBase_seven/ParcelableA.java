package com.doommes.learn.FileAndDataBase_seven;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableA implements Parcelable {
    public int userId;
    public String userName;

    public ParcelableA(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    protected ParcelableA(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
    }

    public static final Creator<ParcelableA> CREATOR = new Creator<ParcelableA>() {
        @Override
        public ParcelableA createFromParcel(Parcel in) {
            return new ParcelableA(in);
        }

        @Override
        public ParcelableA[] newArray(int size) {
            return new ParcelableA[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
    }
}
