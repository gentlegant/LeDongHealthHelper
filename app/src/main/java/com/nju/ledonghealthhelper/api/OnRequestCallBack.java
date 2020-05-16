package com.nju.ledonghealthhelper.api;

public interface OnRequestCallBack<T> {
    void onSuccess(T t);
    void  onFailure();
}
