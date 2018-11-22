package com.may.base.http.bean;


public interface BaseResponse {

    boolean isSuccessful();

    String getErrorMsg();

    String getErrorCode();
}
