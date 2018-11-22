package com.may.base.http;

import com.google.gson.Gson;
import com.may.base.http.bean.TestResponseBean;
import com.may.base.http.subscriber.BaseObserver;
import com.may.base.http.transformer.BaseTransformer;
import com.may.base.http.transformer.SecondTransformer;
import com.may.base.module.base.activity.BaseActivity;
import com.may.base.module.base.fragment.BaseFragment;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class HttpManager {

    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit mRetrofit;
    private ApiService apiService;

    private HttpManager() {

//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
                .addInterceptor(new HttpCommonInterceptor())
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookiesManager())
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpConstants.BASE_URL)
                .build();

        //Glide.get(App.getInstance()).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
        apiService = mRetrofit.create(ApiService.class);
    }


    public static <T extends TestResponseBean,C> void post(C context, Observable<String> observable, OnHttpListener<T> onHttpListener) {
        observable.compose(HttpManager.<String>schedulerTransformer())
                .compose(new SecondTransformer<T>(BaseTransformer.getType(onHttpListener.getClass())))
                .compose(HttpManager.<T,C>lifeCycleTransformer(context))
                .subscribe(new BaseObserver<>(onHttpListener));

    }

    /**
     * 返回不带范型的json任意结构
     *
     * @param observable
     * @param onHttpListener
     * @param <T>
     */
    /*public static <T extends BaseResponse, C> void postForJSON(C context, Observable<String> observable, OnHttpListener<T> onHttpListener) {
        observable.compose(HttpManager.<String>schedulerTransformer())
                .compose(new BaseTransformer<T>(BaseTransformer.getType(onHttpListener.getClass())))
                .compose(HttpManager.<T,C>lifeCycleTransformer(context))
                .subscribe(new BaseObserver<T>(onHttpListener));

    }*/

    /**
     * 直接返回接受到的string
     *
     * @param observable
     * @param onHttpListener
     */
    public static <C> void postForStr(C context,Observable<String> observable, OnHttpListener<String> onHttpListener) {
        observable.compose(HttpManager.<String>schedulerTransformer())
                .compose(HttpManager.<String,C>lifeCycleTransformer(context))
                .subscribe(new BaseObserver<>(onHttpListener));

    }

    public ApiService getApiService() {
        return apiService;
    }

    private class CookiesManager implements CookieJar {

        //保存每个url的cookie
        private HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

        //上一个请求url
        private HttpUrl url;

        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            //保存链接的cookie
            cookieStore.put(httpUrl, list);
            //保存上一次的url，供给下一次c  ookie的提取。
            url = httpUrl;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            //加载上一个链接的cookie
            List<Cookie> cookies = cookieStore.get(url);
            return cookies != null ? cookies : new ArrayList<Cookie>();

        }
    }

    public static <T> RequestBody convertToBody(T t) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(t));
    }

    public static RequestBody convertToBody(String json) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }


    private static <T> ObservableTransformer<T, T> schedulerTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T, C> ObservableTransformer<T, T> lifeCycleTransformer(final C context) {
        if (context instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) context;
            return baseActivity.bindUntilEvent(ActivityEvent.DESTROY);
        } else if (context instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) context;
            return baseFragment.bindToLifecycle();
        }
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream;
            }
        };
    }

    public static HttpManager getInstance() {
        return SingleInstance.INSTANCE;
    }

    private static class SingleInstance {
        private static final HttpManager INSTANCE = new HttpManager();
    }
}
