package com.example.aidong;

import android.app.Application;
import android.content.Context;

import com.example.aidong.model.UserCoach;
import com.example.aidong.utils.SharePrefUtils;

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
