package com.leyuan.aidong.ui;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.stetho.Stetho;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.photopicker.BoxingGlideLoader;
import com.leyuan.aidong.module.photopicker.BoxingUcrop;
import com.leyuan.aidong.module.photopicker.boxing.BoxingCrop;
import com.leyuan.aidong.module.photopicker.boxing.BoxingMediaLoader;
import com.leyuan.aidong.module.photopicker.boxing.loader.IBoxingMediaLoader;
import com.leyuan.aidong.utils.AppUtil;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.SharePrefUtils;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;

public class App extends Application {

    public static App mInstance;
    public static Context context;
    private UserCoach user;
    private String token;

    public static double lat;
    public static double lon;
    public static String city = "上海";
    public static String addressStr;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        initConfig();
        Stetho.initializeWithDefaults(this);
    }

    private void initConfig() {
        LeakCanary.install(this);
        SDKInitializer.initialize(this);
        initBaiduLoc();

        //initDbUtils();
        initEMchat();
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
     //   initImagePicker();


        Realm.init(context);
//        WXAPIFactory.createWXAPI(this, "wx365ab323b9269d30", false).registerApp("wx365ab323b9269d30");
//        new WXShare(this);
    }


    /**
     * 初始化仿微信控件ImagePicker
     */
    /*private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new UILImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }*/

    private void initEMchat() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        // options.setAcceptInvitationAlways(false);
        //自动登录属性默认是 true 打开的，如果不需要自动登录,设置为 false 关闭。
        //  options.setAutoLogin(false);

        int pid = android.os.Process.myPid();
        String processAppName = AppUtil.getAppName(pid,this);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Logger.e("","enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private void initDbUtils() {
        try {
            //db = DbUtils.create(this, "mxing.db");
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



    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5 * 60 * 1000;
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

            App.lat = location.getLatitude();
            App.lon = location.getLongitude();
            if (location.getCity() != null)
                city = location.getCity().replace("市", "");
            if (location.getAddrStr() != null)
                addressStr = location.getAddrStr();
            if (city != null) {
                mLocationClient.stop();
            }
            LogAidong.i("lat = " + lat + ",   lon = " + lon);

        }
    }

    public boolean isLogin() {
        if (getUser() == null) {
            return false;
        }
        return true;
    }

    public void exitLogin() {
        setUser(null);
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
        }
        SharePrefUtils.setUser(context, user);
    }

    public String getToken() {
        if (token == null) {
            token = SharePrefUtils.getString(context, "token", null);
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharePrefUtils.putString(context, "token", token);
    }


}
