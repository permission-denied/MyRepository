package com.may.base.http.subscriber;


import com.may.base.http.ExceptionHandle;
import com.may.base.http.OnHttpListener;
import com.may.base.utils.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class BaseObserver<T> implements Observer<T> {

    private OnHttpListener<T> onHttpListener;

    public BaseObserver(OnHttpListener<T> onHttpListener) {
        this.onHttpListener = onHttpListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!NetworkUtils.isConnected()) {
            if (!d.isDisposed()) {
                d.dispose();
                onError(new ExceptionHandle.ServerException(ExceptionHandle.ERROR.NETWORD_ERROR, "网络错误"));
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (onHttpListener != null) {
            onHttpListener.onSuccess(t);
        }
        onHttpListener = null;
    }

    @Override
    public void onError(Throwable e) {
        // DialogUtil.cancelProgressDialog();
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
        //DialogUtil.cancelProgressDialog();
        if (onHttpListener != null) {
            onHttpListener = null;
        }
    }

    private void onError(ExceptionHandle.ResponeThrowable e) {
        if (onHttpListener != null) {
            onHttpListener.onError(e);
        }
        onHttpListener = null;

//        LogReportManager.onEventUpload();
    }


}
