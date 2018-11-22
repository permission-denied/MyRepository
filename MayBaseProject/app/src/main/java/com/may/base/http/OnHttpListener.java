package com.may.base.http;


public interface OnHttpListener<T> {
    void onError(ExceptionHandle.ResponeThrowable e);

    void onSuccess(T t);
}
