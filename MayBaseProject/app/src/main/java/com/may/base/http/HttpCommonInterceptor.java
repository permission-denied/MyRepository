package com.may.base.http;

import android.os.Handler;
import android.os.HandlerThread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.may.base.http.bean.BaseResponse;
import com.may.base.http.bean.ResponseBean;
import com.may.base.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static okhttp3.internal.http.StatusLine.HTTP_CONTINUE;


class HttpCommonInterceptor implements Interceptor {

    private static final Type[] dests = new Type[]{
            new TypeReference<ResponseBean<String>>() {
            }.getType()
    };

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final String PREFIX_REQUEST_LOG = "request >>> ";
    private static final String PREFIX_RESPONSE_LOG = "response >>> ";

    private static HandlerThread handlerThread;
    private static Handler handler;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request(); // 添加新的参数，添加到url 中
        /*// 新的请求
        Request.Builder requestBuilder = oldRequest.newBuilder();

        requestBuilder.method(oldRequest.method(), oldRequest.body());

        HttpUrl newHttpUrl = HttpUrl.parse(HttpUtils.getBaseUrl());


        HttpUrl oldHttpUrl = oldRequest.url();
        HttpUrl newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newHttpUrl.scheme())
                .host(newHttpUrl.host())
                .port(newHttpUrl.port())
                .build();

        Request newRequest = requestBuilder.url(newFullUrl).build();*/

        Response response = chain.proceed(oldRequest);
        try {
            HttpInfo httpInfo = new HttpInfo(oldRequest, response);
            handleData(httpInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 处理数据
     *
     * @param httpInfo 新的request
     */
    private void handleData(HttpInfo httpInfo) {
        if (handlerThread == null || !handlerThread.isAlive()) {
            handlerThread = new HandlerThread(HttpCommonInterceptor.class.getSimpleName());
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }

        if (!"image".equals(httpInfo.response.contentType)) {
            //handler.post(new TransformerAndUploadTask(httpInfo));
            handler.post(new LogPrintlnTask(httpInfo));
        }
    }

    /**
     * 日志打印任务类
     */
    private static class LogPrintlnTask implements Runnable {

        private HttpInfo httpInfo;

        LogPrintlnTask(HttpInfo httpInfo) {
            this.httpInfo = httpInfo;
        }

        @Override
        public void run() {
            try {
                printlnLocalLog(httpInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 打印日志
         *
         * @param httpInfo 返回的封装的http请求以及返回的信息
         */
        private void printlnLocalLog(HttpInfo httpInfo) {
            String log = PREFIX_REQUEST_LOG + "\n";
            String url = httpInfo.getRequest().url;
            String params = httpInfo.getRequest().body;
            String result = httpInfo.getResponse().body;
            log += "\t\t" + "url：" + url + "\n";
            log += "\t\t" + "method：" + httpInfo.getRequest().method + "\n";
            log += "\t\t" + "header：" + httpInfo.getRequest().headers + "\n";
            log += "\t\t" + "body：" + params + "\n";
            log += PREFIX_RESPONSE_LOG + "\n";
            log += "\t\t" + "httpCode：" + httpInfo.getResponse().code + "\n";
            log += "\t\t" + "header：" + httpInfo.getResponse().headers + "\n";
            log += "\t\t" + "body：" + result + "\n";
            LogUtils.d(log);
        }
    }

    /**
     * 转换并上报错误日志
     */
    private static class TransformerAndUploadTask implements Runnable {

        private HttpInfo httpInfo;

        TransformerAndUploadTask(HttpInfo httpInfo) {
            this.httpInfo = httpInfo;
        }

        @Override
        public void run() {
            try {
                transformer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void transformer() throws IOException {

            String url = httpInfo.getRequest().url;
            String params = httpInfo.getRequest().body;
            String result = httpInfo.getResponse().body;

            if (!httpInfo.getResponse().isSuccessful) {
                //logReport(params, url, httpInfo.getResponse().code, result);
                return;
            }

            for (Type item : dests) {
                try {
                    Object json = JSON.parseObject(result, item);
                    if (json == null) continue;
                    if (json instanceof BaseResponse) {
                        BaseResponse data = (BaseResponse) json;
                        if (!data.isSuccessful()) {
                            //错误日志上传
                            //logReport(params, url, data.getErrorCode(), data.getErrorMsg());
                        }
                        break;
                    }
                } catch (Exception e) {
                    // do nothing
                }
            }
        }


    }

    private static class HttpInfo {

        private Request request;
        private Response response;

        HttpInfo(okhttp3.Request newRequest, okhttp3.Response response) throws IOException {
            request = new Request();
            this.response = new Response();
            request.url = newRequest.url() != null ? newRequest.url().toString() : "";
            request.body = getRequestString(newRequest);
            request.method = newRequest.method();
            request.headers = newRequest.headers() != null ? newRequest.headers().toString() : "";
            this.response.headers = response.headers() != null ? response.headers().toString() : "";
            this.response.code = String.valueOf(response.code());
            this.response.isSuccessful = response.isSuccessful();
            this.response.body = getResponseBody(response);
            this.response.contentType = getContentType(response);
        }

        private String getContentType(okhttp3.Response response) {
            String contentType = "";
            ResponseBody responseBody = response.body();
            if (responseBody != null && responseBody.contentType() != null) {
                if (responseBody.contentType().type() != null) {
                    contentType = responseBody.contentType().type();
                }
            }
            return contentType;
        }

        private String getRequestString(okhttp3.Request newRequest) throws IOException {
            String requestBodyStr = "";
            RequestBody requestBody = newRequest.body();
            if (newRequest.body() != null && !bodyEncoded(newRequest.headers())) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                requestBodyStr = buffer.readString(charset);
            }
            return requestBodyStr;
        }

        private String getResponseBody(okhttp3.Response response) throws IOException {
            String responseBodyStr = "";
            if (!bodyEncoded(response.headers())) {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    return responseBodyStr;
                }
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {

                    }
                }
                long contentLength = responseBody.contentLength();
                if (contentLength != 0) {
                    responseBodyStr = buffer.clone().readString(charset);
                }
            }
            return responseBodyStr;
        }

        private boolean bodyEncoded(Headers headers) {
            String contentEncoding = headers.get("Content-Encoding");
            return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
        }

        static class Request {
            private String url;
            private String method;
            private String headers;
            private String body;
        }

        static class Response {
            private String headers;
            private String body;
            private String code;
            private String contentType;
            private boolean isSuccessful;
        }

        public Request getRequest() {
            return request;
        }

        public void setRequest(Request request) {
            this.request = request;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

}
