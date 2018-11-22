package com.may.base.http.transformer;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.may.base.http.ExceptionHandle;
import com.may.base.http.bean.BaseResponse;
import com.may.base.http.bean.TestResponseBean;
import com.orhanobut.logger.Logger;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;



public class BaseTransformer<R extends TestResponseBean> implements ObservableTransformer<String, R> {

    private Type type;

    public BaseTransformer(Type type) {
        this.type = type;
    }


    @Override
    public ObservableSource<R> apply(io.reactivex.Observable<String> upstream) {
        return upstream.map(new HandleFuc()).onErrorResumeNext(new HttpResponseFunc<R>());
    }

    private class HandleFuc implements Function<String, R> {
        @Override
        public R apply(String response) throws Exception {
            R r;
            try {
                r = JSON.parseObject(response, getType());
            } catch (Exception e) {
                try {
                    r = retryParseObject(response, e.getMessage());
                } catch (Exception e1) {
                    Logger.d("parseError:=====" + response);
                    throw new ExceptionHandle.ServerException("0", e.getMessage());
                }
            }
            if (r == null) {
                throw new ExceptionHandle.ServerException("0", "");
            }

            if (!r.isSuccessful()) {
                String msg = TextUtils.isEmpty(r.getErrorMsg()) ? "" : r.getErrorMsg();
                String code = TextUtils.isEmpty(r.getErrorCode()) ? "0" : r.getErrorCode();
                throw new ExceptionHandle.ServerException(code, msg);
            }
            return r;
        }
    }

    public static Type getType(Class<?> clazz){
        Type type = clazz.getGenericSuperclass();
        if (type != null && type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * 试图重新转换对象，默认为抛出异常
     *
     * @param response
     * @param errorMessage
     * @return
     */
    public R retryParseObject(String response, String errorMessage) {
        throw new ExceptionHandle.ServerException("0", errorMessage);
    }

    private static class HttpResponseFunc<T> implements Function<Throwable,Observable<T>> {

        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }


    public Type getType() {
        return type;
    }
}
