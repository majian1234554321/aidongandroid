package com.leyuan.aidong.http;

import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.Constant;

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

import static com.leyuan.aidong.ui.App.mInstance;


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
                       Request.Builder builder= chain.request().newBuilder();
                        if(mInstance.getUser() !=null && mInstance.getUser().getToken() != null){
                            builder.addHeader("token", mInstance.getUser().getToken());
//                            authorised = originalRequest.newBuilder()
//                                    .header("token", App.mInstance.getUser().getToken())
//                                    .build();
                        }
                        builder.addHeader("city", URLEncoder.encode(App.city, "UTF-8"));

                        Request authorised = builder.build();
                        return chain.proceed(authorised);
                    }
                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

}