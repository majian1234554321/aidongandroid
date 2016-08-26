package com.leyuan.support.http;

import com.leyuan.support.util.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static Retrofit singleton;

    public static <T> T createApi( Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitHelper.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(Constant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }

}
