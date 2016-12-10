package com.leyuan.aidong.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.SystemBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取系统配置信息帮助类
 * app启动时发送获取最新配置的网络请求，将配置信息保存到本地
 * 当需要使用到配置信息时，先从内存读取，如果没有再从本地获取
 * Created by song on 2016/11/10.
 */
public class SystemInfoUtils {
    // 0-开机广告 1-首页广告位 2-弹出广告位
    private static final String BANNER_SPLASH = "0";
    private static final String BANNER_HOME = "1";
    private static final String BOUNCED_AD = "2";

    /**
     * 获取首页广告
     */
    public static List<BannerBean> getHomeBanner(Context context){
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> homeBannerBeanList = new ArrayList<>();
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getBanner() != null){
            bannerBeanList =  Constant.systemInfoBean.getBanner();
        }else {
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }

        for (BannerBean bannerBean :bannerBeanList) {
            if(BANNER_HOME.equals(bannerBean.getPosition())){
                homeBannerBeanList.add(bannerBean);
            }
        }
        return homeBannerBeanList;
    }

    /**
     *获取闪屏页广告
     */
    public static List<BannerBean> getSplashBanner(Context context){
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> splashBannerBean = new ArrayList<>();
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getBanner() != null){
            bannerBeanList =  Constant.systemInfoBean.getBanner();
        }else {
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }
        for (BannerBean bannerBean : bannerBeanList) {
            if(BANNER_SPLASH.equals(bannerBean.getPosition())){
                splashBannerBean.add(bannerBean);
                break;
            }
        }
        return splashBannerBean;
    }

    /**
     * 获取首页弹框广告 支持多张图片
     */
    public static List<BannerBean> getHomePopupBanner(Context context){
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> popupBannerList = new ArrayList<>();
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getBanner() != null){
            bannerBeanList =  Constant.systemInfoBean.getBanner();
        }else {
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }
        for (BannerBean bannerBean : bannerBeanList) {
            if(BOUNCED_AD.equals(bannerBean.getPosition())){
                popupBannerList.add(bannerBean);
                break;
            }
        }
        return popupBannerList;
    }

    /**
     * 获取开通城市
     */
    public static List<String> getOpenCity(Context context){
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getOpen_city() != null){ //内存有直接从内存读取返回
            return Constant.systemInfoBean.getOpen_city();
        }else{          // 从本地读取
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                return ((SystemBean) bean).getOpen_city();
            }
            return null;
        }
    }

    /**
     * 获取课程分类信息
     */
    public static List<CategoryBean> getCourseCategory(Context context){
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getCourse() != null){ //内存有直接从内存读取返回
            return Constant.systemInfoBean.getCourse();
        }else{          // 从本地读取
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                return ((SystemBean) bean).getCourse();
            }
            return null;
        }
    }

    /**
     * 获取营养品分类信息
     */
    public static ArrayList<CategoryBean> getNurtureCategory(Context context){
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getNutrition() != null){ //内存有直接从内存读取返回
            return Constant.systemInfoBean.getNutrition();
        }else{          // 从本地读取
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                return ((SystemBean) bean).getNutrition();
            }
            return null;
        }
    }

    /**
     * 获取装备分类信息
     */
    public static ArrayList<CategoryBean> getEquipmentCategory(Context context){
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getEquipment() != null){ //内存有直接从内存读取返回
            return Constant.systemInfoBean.getEquipment();
        }else{          // 从本地读取
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                return ((SystemBean) bean).getEquipment();
            }
            return null;
        }
    }

    /**
     * 获取商圈信息
     */
    public static List<DistrictBean> getLandmark(Context context){
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getLandmark() != null){ //内存有直接从内存读取返回
            return Constant.systemInfoBean.getLandmark();
        }else{          // 从本地读取
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                return ((SystemBean) bean).getLandmark();
            }
            return null;
        }
    }

    /**
     * 获取场馆品牌分类
     */
    public static List<CategoryBean> getGymBrand(Context context){
        if(Constant.systemInfoBean != null && Constant.systemInfoBean.getGymBrand() != null){ //内存有直接从内存读取返回
            return Constant.systemInfoBean.getGymBrand();
        }else {          // 从本地读取
            Object bean = getSystemInfoBean(context);
            if(bean instanceof SystemBean){
                return ((SystemBean) bean).getGymBrand();
            }
            return null;
        }
    }

    /**
     * 存放实体类以及任意类型
     * @param context 上下文对象
     * @param obj
     */
    public static void putSystemInfoBean(Context context, Object obj) {
        if (obj instanceof Serializable) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(), 0));
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("system", string64).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("the obj must implement Serializable");
        }
    }

    public static Object getSystemInfoBean(Context context) {
        Object obj = null;
        try {
            String base64 =PreferenceManager.getDefaultSharedPreferences(context).getString("system", "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
