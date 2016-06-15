package com.example.aidong;

import android.app.Application;
import android.content.Context;

import com.example.aidong.model.UserCoach;
import com.example.aidong.utils.SharePrefUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApp extends Application{

    public static BaseApp mInstance;
    public static Context context;
    private UserCoach user;
    private String token;
    public static double lat;
    public static double lon;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        initConfig();
    }

    private void initConfig() {
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public boolean isLogin() {
        if (SharePrefUtils.getBoolean(this, "islogin", false)
                && SharePrefUtils.getUser(this) != null) {
            return true;
        }
        return false;
    }

    public UserCoach getUser() {

        if (SharePrefUtils.getBoolean(this, "islogin", false)
                && SharePrefUtils.getUser(this) != null) {
            if (user == null) {
                user = SharePrefUtils.getUser(this);
            }
            return user;
        }
        return null;
    }
    public void setUser(UserCoach user){
        this.user = user;
        SharePrefUtils.setUser(context, user);
    }

    public String getToken(){
        if(token == null){
            token = SharePrefUtils.getString(context, "token", null);
        }
        return token;
    }
    public void setToken(String token){
        this.token =token;
        SharePrefUtils.putString(context, "token", token);
    }

}
