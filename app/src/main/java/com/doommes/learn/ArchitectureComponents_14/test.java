package com.doommes.learn.ArchitectureComponents_14;

import android.arch.lifecycle.ViewModel;

public class test extends ViewModel {

    public String userId;

    public String name;

    public test(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId == null ? "" : userId;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name == null ? "" : name;

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "test{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
