package com.may.base.http;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by may on 2018/8/29.
 */

public class HttpUtils {
    /**
     * 获取首页banner
     */
    public static Observable<String> getBannerData() {
        return HttpManager.getInstance().getApiService().getBannerData();
    }
}
