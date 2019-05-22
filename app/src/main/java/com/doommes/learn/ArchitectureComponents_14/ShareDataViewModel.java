package com.doommes.learn.ArchitectureComponents_14;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ShareDataViewModel extends ViewModel {
    public MutableLiveData<Integer> mMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getMutableLiveData() {
        return mMutableLiveData;
    }
}
