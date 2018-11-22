package com.may.base.http.bean;

import java.io.Serializable;

/**
 * Created by may on 2018/8/29.
 */

public class TestResponseBean<T> implements BaseResponse, Serializable {

    private int code;

    private String msg;

    private T data;

    @Override
    public boolean isSuccessful() {
        return code == 200;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    @Override
    public String getErrorCode() {
        return String.valueOf(code);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
