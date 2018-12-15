package com.example.chiragpc.starspace.base;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T view;

    public T getView() {
        return this.view;
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }
}
