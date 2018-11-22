package com.may.base.http;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface ApiService {

    /**
     * 查询收货地址
     *
     * @param map
     * @return
     */
    @GET(value = "/gateway/act/address/find")
    Observable<String> recieveAddressQuery(@QueryMap HashMap<String, String> map);

    /**
     * 获取个人信息
     *
     * @param map
     * @return
     */
    @POST(value = "/query/qapi/user/index.do")
    Observable<String> userDetail(@QueryMap HashMap<String, String> map, @HeaderMap HashMap<String, String> headers);


    @GET(value = HttpConstants.Path.banner_data)
    Observable<String> getBannerData();
}

