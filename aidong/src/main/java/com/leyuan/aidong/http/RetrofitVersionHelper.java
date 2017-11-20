package com.leyuan.aidong.http;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.leyuan.aidong.config.UrlConfig;
import com.leyuan.aidong.ui.App;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitVersionHelper {
    private static final int DEFAULT_TIMEOUT = 30;
    private static Retrofit singleton;

    public static <T> T createVersionApi(Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitVersionHelper.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(UrlConfig.BASE_URL_VERSION)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

                    singleton = builder.client(createClient()).build();
                }
            }
        }
        return singleton.create(clazz);
    }

    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();


                        if (App.getInstance().isLogin()) {
                            if (App.getInstance().getToken() != null)
                                builder.addHeader("token", App.getInstance().getToken());
                            if (App.getInstance().getUser().getMobile() != null)
                                builder.addHeader("mobile", App.getInstance().getUser().getMobile());
                        }
                        builder.addHeader("version", App.getInstance().getVersionName());
                        builder.addHeader("city", URLEncoder.encode(App.getInstance().getSelectedCity(), "UTF-8"));
//                        builder.addHeader("lat", String.valueOf(App.lat));
//                        builder.addHeader("lng", String.valueOf(App.lon));
//                        builder.addHeader("deviceName", DeviceManager.getPhoneBrand());
//                        builder.addHeader("Content-Type","application/x-www-form-urlencoded");

                        Request authorised = builder.build();
                        return chain.proceed(authorised);
                    }
                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

}
