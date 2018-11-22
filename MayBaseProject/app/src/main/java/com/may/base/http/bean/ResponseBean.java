package com.may.base.http.bean;

import java.io.Serializable;


public class ResponseBean<T> implements Serializable,BaseResponse{

    private String code;

    private String message;

    private T data;

    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean isSuccessful() {
        return isSuccess();
    }

    @Override
    public String getErrorMsg() {
        return getMessage();
    }

    @Override
    public String getErrorCode() {
        return getCode();
    }
}
