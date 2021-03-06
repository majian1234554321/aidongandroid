package com.example.aidong.ui;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.stetho.Stetho;

import com.example.aidong .config.UrlConfig;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .entity.user.MineInfoBean;
import com.example.aidong .module.chat.manager.EmConfigManager;
import com.example.aidong .module.photopicker.BoxingGlideLoader;
import com.example.aidong .module.photopicker.BoxingUCrop;
import com.example.aidong .module.photopicker.boxing.BoxingCrop;
import com.example.aidong .module.photopicker.boxing.BoxingMediaLoader;
import com.example.aidong .module.photopicker.boxing.loader.IBoxingMediaLoader;
import com.example.aidong .utils.ForegroundCallbacks;
import com.example.aidong .utils.LogAidong;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.SharePrefUtils;
import com.example.aidong .utils.VersionManager;


import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import iknow.android.utils.BaseUtils;
import io.realm.Realm;

import static com.example.aidong .utils.Constant.DEFAULT_CITY;



public class App extends MultiDexApplication {

    @Deprecated
    public static App mInstance;
    public static Context context;
    private UserCoach user;
    private String token;

    public static double lat = 31.214241;
    public static double lon = 121.4141;
    public String citySelected;
    public String cityLocation;
    public static String addressStr;
    private String versionName;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String parseString;
    private String jPushId;
    public boolean isForeground;
    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        context = getApplicationContext();
        initConfig();
    }

    private void initConfig() {

        ForegroundCallbacks foregroundCallbacks = ForegroundCallbacks.init(this);
        foregroundCallbacks.addListener(foregroundListener);

        JPushInterface.setDebugMode(!UrlConfig.debug);
        JPushInterface.init(this);

        SDKInitializer.initialize(this);
        initBaiduLoc();
        initImagePicker();

        EmConfigManager.getInstance().initializeEaseUi(this);
        Realm.init(context);
//        if (UrlConfig.debug) {
        Stetho.initializeWithDefaults(this);
//        }

        RichText.initCacheDir(this);

        BaseUtils.init(this);

        initFFmpegBinary(this);



//        Logger.i("md5", Md5Utils.createMd("ae2c037cd273f69bfb5c96902d95b151"));
    }

    private ForegroundCallbacks.Listener foregroundListener = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
            isForeground = true;
        }

        @Override
        public void onBecameBackground() {
            isForeground = false;
        }
    };

    private void initImagePicker() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUCrop());
    }


    private void initBaiduLoc() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();
        LogAidong.i("mLocationClient.start();");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 60 * 1000;
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



    public String getjPushId() {
//        if (jPushId == null) {
//            jPushId = SharePrefUtils.getString(context, "jPushId", null);
//        }
//
//        Logger.i("getjPushId = " + jPushId);

        if (TextUtils.isEmpty(jPushId))
            jPushId =   JPushInterface.getRegistrationID(getApplicationContext());

        return jPushId;
    }

    public List<BaseActivity> getActivityStack() {
        return mActivities;
    }

    private ArrayList<CircleDynamicBean> cmdCircleDynamicBeans;


    public ArrayList<CircleDynamicBean> getCMDCirleDynamicBean() {
        if (cmdCircleDynamicBeans == null) {
//            cmdCircleDynamicBeans = new DynamicDb(this).queryAll();
            if (isLogin())
            cmdCircleDynamicBeans = SharePrefUtils.getCmdMessage(this,getUser().getId()+"");
        }
        return cmdCircleDynamicBeans;
    }

    public void saveDynamicCmdMessage(CircleDynamicBean bean) {
        if (isLogin())
        SharePrefUtils.addCmdMessage(this, bean,getUser().getId()+"");

//        new DynamicDb(this).insertInto(bean);
    }

    public void refreshDynamicCmdMessage() {
//        cmdCircleDynamicBeans = new DynamicDb(this).queryAll();
        if (isLogin())
        cmdCircleDynamicBeans = SharePrefUtils.getCmdMessage(this,getUser().getId()+"");
    }

    public void clearCMDMessage() {
        cmdCircleDynamicBeans = null;
        if (isLogin())
        SharePrefUtils.saveCmdMessage(this, new ArrayList<CircleDynamicBean>(),getUser().getId()+"");
//        new DynamicDb(this).clear();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getCity() != null) {
                App.lat = location.getLatitude();
                App.lon = location.getLongitude();
                cityLocation = location.getCity().replace("市", "");
            }

            if (location.getAddrStr() != null)
                addressStr = location.getAddrStr();
            if (cityLocation != null) {
                setLocationCity(cityLocation);
                mLocationClient.stop();
//                LocatinCityManager.checkLocationCityFirstly(cityLocation);
            }
            LogAidong.i(" LocationClient ok : lat = " + lat + ",   lon = " + lon + ", cityLocation = " + location.getCity());
        }

    }

    public boolean isLogin() {
        return getUser() != null;
    }

    public void exitLogin() {
        setUser(null);
        setToken(null);
        saveMineInfoBean(null);
        parseString = null;
    }

    public UserCoach getUser() {
        if (user == null) {
            user = SharePrefUtils.getUser(this);
        }
        return user;
    }

    public void setUser(UserCoach user) {
        this.user = user;
        if (user != null && user.getToken() != null) {
            setToken(user.getToken());
            Logger.i("User", "name = " + user.getName());
        }
        SharePrefUtils.setUser(context, user);
    }

    public String getToken() {
        if (token == null) {
            token = SharePrefUtils.getToken(context);
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharePrefUtils.setToken(context, token);
    }

    public static App getInstance() {
        return mInstance;
    }

    public String getVersionName() {
        if (versionName == null) {
            versionName = VersionManager.getVersionName(context);
        }
        return versionName;
    }

    public void StopLocation() {
        mLocationClient.stop();

    }

    public void startLocation() {
        mLocationClient.start();
    }


    public void setSelectedCity(String city) {
        this.citySelected = city;
        SharePrefUtils.putString(this, "citySelected", city);
        setJPushTags();
    }


    public String getSelectedCity() {
        if (citySelected == null) {
            citySelected = SharePrefUtils.getString(this, "citySelected", DEFAULT_CITY);
        }
        return citySelected;
    }

    public void setLocationCity(String city) {
        this.cityLocation = city;
        SharePrefUtils.putString(this, "cityLocation", city);
        setJPushTags();
    }

    private void setJPushTags() {
        Set<String> sets = new HashSet<>();
        String locationCity = getLocationCity() == null ? getSelectedCity() : getLocationCity();
        String selectCity = getSelectedCity() == null ? getLocationCity() : getSelectedCity();
        sets.add(locationCity + 0);
        sets.add(selectCity + 1);
        JPushInterface.setTags(this, sets, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Logger.i("setJPushTags", "result : code  = " + i + ", set.isEmpty  = " + set.isEmpty());
            }
        });
    }


    public String getLocationCity() {
        if (cityLocation == null) {
            cityLocation = SharePrefUtils.getString(this, "cityLocation", null);
        }
        return cityLocation;
    }

    private List<VenuesBean> sportsHistoryList;

    public List<VenuesBean> getSportsHistory() {
        if (sportsHistoryList == null) {
            sportsHistoryList = SharePrefUtils.getSportHistory(this);
        }
        return sportsHistoryList;
    }

    public void saveMineInfoBean(MineInfoBean mineInfoBean) {
        sportsHistoryList = mineInfoBean == null ? null : mineInfoBean.getGyms();
        SharePrefUtils.saveSportsHistory(this, mineInfoBean);
    }

    public String getParseString() {
        if (getUser() != null) {
            if (parseString == null) {
                String user = getUser().getId() + "";
                parseString = SharePrefUtils.getString(this, user, null);
            }
        } else {
            parseString = null;
        }
        return parseString;
    }

    public void setParseString(String parseString) {
        if (getUser() != null) {
            this.parseString = parseString;
            String user = getUser().getId() + "";
            SharePrefUtils.putString(this, user, parseString);
        }
    }

    public void addParseId(String parseId) {
        StringBuffer p = new StringBuffer();
        if (getParseString() != null)
            p.append(getParseString());
        p.append(parseId).append(" ");
        setParseString(p.toString());
    }

    public void deleteParseId(String parseId) {
        parseString = getParseString();
        if (parseString != null) {
            Logger.i("parse", parseString);
            parseString = parseString.replace(parseId, "").trim();

            Logger.i("parse", parseString);
        }
        setParseString(parseString);

    }




    private void initFFmpegBinary(Context context) {

        try {
            FFmpeg.getInstance(context).loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                }
            });

        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
