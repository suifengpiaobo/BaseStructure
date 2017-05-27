package com.zxl.base.client;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zxl.base.utils.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description Retrofit 和 HttpClient 初始化配置类
 * Created by zxl on 2017/5/27 下午4:05.
 * Email:444288256@qq.com
 */
public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private static final int TIMEOUT_CONNECTION = 25;

    private OkHttpClient.Builder sBuilder;

    private static RetrofitClient instance;

    private Retrofit retrofit;

    private RetrofitClient() {
        initOkHttpClient();
    }

    private void initOkHttpClient() {
        if (sBuilder == null) {
            sBuilder = new OkHttpClient.Builder();
        }
        if (BuildConfig.IS_DEBUG) {
            sBuilder.interceptors().add(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            // sBuilder.networkInterceptors().add(new StethoInterceptor());
        }
        sBuilder.retryOnConnectionFailure(true)//失败重连
                //time out
                .readTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
    }

    //封装 GuokrService 请求
    public Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(sBuilder.build())
//                    .baseUrl((IPUtils.BASE_URL))
                    .baseUrl("")//随意输入的baseURL，后续通过ApiService传入真实url
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava2支持
                    //如果网络访问返回的字符串，而不是json数据格式，要使用下面的转换器
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    //如果网络访问返回的是json字符串，使用gson转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static synchronized RetrofitClient getInstanse() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }
}
