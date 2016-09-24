package com.example.aidong.ui;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.aidong.utils.common.MXLog;
import com.example.aidong.entity.model.UserCoach;
import com.example.aidong.utils.SharePrefUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lidroid.xutils.DbUtils;
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
    public static String city;
    public static String addressStr;

    public DbUtils db;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        initConfig();
        Fresco.initialize(this);
    }

    private void initConfig() {
        initBaiduLoc();
        SDKInitializer.initialize(this);
        initImageLoader(getApplicationContext());
        initDbUtils();
    }

    private void initDbUtils() {
        try {
            db = DbUtils.create(this, "mxing.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBaiduLoc() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();
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

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=5*60*1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设隔需要大于等于1000ms才是置发起定位请求的间有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            MXLog.out("GPS onReceiveLocation success");
            BaseApp.lat = location.getLatitude();
            BaseApp.lon = location.getLongitude();
            if(location.getCity()!= null)
                city = location.getCity().replace("市", "");
            if(location.getAddrStr()!=null)
                addressStr = location.getAddrStr();
            if(city!=null){
                mLocationClient.stop();
            }

        }
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

    @Deprecated
    public String getToken(){
        if(token == null){
            token = SharePrefUtils.getString(context, "token", null);
        }
        return token;
    }

    @Deprecated
    public void setToken(String token){
        this.token =token;
        SharePrefUtils.putString(context, "token", token);
    }

}
