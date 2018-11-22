package com.may.base.http.transformer;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.may.base.http.bean.BaseResponse;
import com.may.base.http.bean.TestResponseBean;

import java.lang.reflect.Type;



public class SecondTransformer<T extends TestResponseBean> extends BaseTransformer<T> {

    public SecondTransformer(Type type) {
        super(type);
    }

    @Override
    public T retryParseObject(String response, String errorMessage) {
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.containsKey("data")) {
            String data = jsonObject.getString("data");
            if (TextUtils.isEmpty(data)) {
                jsonObject.put("data", null);
                return JSON.parseObject(jsonObject.toJSONString(), getType());
            }
        }
        return super.retryParseObject(response, errorMessage);
    }
}
