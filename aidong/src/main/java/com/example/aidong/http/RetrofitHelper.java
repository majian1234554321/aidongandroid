package com.example.aidong.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.example.aidong .config.UrlConfig;
import com.example.aidong .ui.App;
import com.example.aidong .utils.DeviceManager;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT = 30;
    private static Retrofit singleton;

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    public static <T> T createApi(Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitHelper.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(UrlConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

                    singleton = builder.client(createClient()).build();
                }
            }
        }
        return singleton.create(clazz);
    }

    private static OkHttpClient createClient() {


        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
//                        if (App.getInstance().isLogin() && App.getInstance().getToken() != null) {
//                            builder.addHeader("token", App.getInstance().getToken());
//
//                            if (App.getInstance().getUser().getMobile() != null)
//                                builder.addHeader("mobile", App.getInstance().getUser().getMobile());
//                        }

                        if (App.getInstance().isLogin()) {
                            if (App.getInstance().getToken() != null)
                                builder.addHeader("token", App.getInstance().getToken());
                            if (App.getInstance().getUser().getMobile() != null)
                                builder.addHeader("mobile", App.getInstance().getUser().getMobile());
                        }
                        builder.addHeader("city", URLEncoder.encode(App.getInstance().getSelectedCity(), "UTF-8"));
                        builder.addHeader("lat", String.valueOf(App.lat));
                        builder.addHeader("lng", String.valueOf(App.lon));
                        builder.addHeader("device", "android");
                        builder.addHeader("ver", App.getInstance().getVersionName());
                        builder.addHeader("os", DeviceManager.getPhoneBrand());

                        Request authorised = builder.build();
                        return chain.proceed(authorised);
                    }
                })

                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new ChuckInterceptor(App.context))
                .build();
    }



    public static void setSingleton(Retrofit singleton) {
        RetrofitHelper.singleton = singleton;
    }
}
