package com.leyuan.aidong.http;

import com.leyuan.aidong.utils.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit singleton;

    public static <T> T createApi( Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitHelper.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(Constant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    singleton = builder.client(createClient()).build();
                }
            }
        }
        return singleton.create(clazz);
    }

    public static OkHttpClient createClient() {
        return  new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                       /* if ("sToken" == null || alreadyHasTokenHeader(originalRequest)) {
                            return chain.proceed(originalRequest);
                        }*/
                        Request authorised = originalRequest.newBuilder()
                                .header("token", "sToken")
                                .build();
                        return chain.proceed(authorised);
                    }
                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

}
