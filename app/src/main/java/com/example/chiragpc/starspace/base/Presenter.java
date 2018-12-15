package com.example.chiragpc.starspace.base;

public interface Presenter<T extends MvpView> {
    void attachView(T view);
}
