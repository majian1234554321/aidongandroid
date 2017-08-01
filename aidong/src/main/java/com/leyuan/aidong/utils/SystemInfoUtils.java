package com.leyuan.aidong.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.SystemBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.data.FollowData;
import com.leyuan.aidong.ui.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.systemInfoBean;

/**
 * 获取系统配置信息帮助类
 * app启动时发送获取最新配置的网络请求，将配置信息赋值给常亮并保存到本地
 * 当需要使用到配置信息时，先从内存读取，如果没有再从本地获取
 * Created by song on 2016/11/10.
 */
public class SystemInfoUtils {
    public static final String KEY_SYSTEM = "system";
    public static final String KEY_FOLLOW = "follow";
    // 0-开机广告 1-首页广告位 2-弹出广告位 3-发现广告位 4-商城广告位
    private static final String BANNER_SPLASH = "0";
    private static final String BANNER_HOME = "1";
    private static final String BANNER_HOME_BOUNCED = "2";
    private static final String BANNER_DISCOVER = "3";
    private static final String BANNER_STORE = "4";


    public static double getExpressPrice(Context context) {
        double express = 0d;
        if (systemInfoBean != null && systemInfoBean.getAcivity() != null) {
            express = Constant.systemInfoBean.getExpressPrice();
        } else {
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                express = ((SystemBean) bean).getExpressPrice();
            }
        }
        return express;
    }

    public static String getCourseVideoTipOnLogout() {
        String activityTip = "";
        if (systemInfoBean != null && systemInfoBean.getAcivity() != null) {
            activityTip = Constant.systemInfoBean.getAcivity();
        }
        return activityTip;
    }

    /**
     * 获取闪屏页广告
     */
    public static List<BannerBean> getSplashBanner(Context context) {
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> splashBannerBean = new ArrayList<>();
        if (systemInfoBean != null && systemInfoBean.getBanner() != null) {
            bannerBeanList = systemInfoBean.getBanner();
        } else {
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }
        for (BannerBean bannerBean : bannerBeanList) {
            if (BANNER_SPLASH.equals(bannerBean.getPosition())) {
                splashBannerBean.add(bannerBean);
                break;
            }
        }
        return splashBannerBean;
    }

    /**
     * 获取首页广告
     */
    public static List<BannerBean> getHomeBanner(Context context) {
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> homeBannerBeanList = new ArrayList<>();
        if (systemInfoBean != null && systemInfoBean.getBanner() != null) {
            bannerBeanList = systemInfoBean.getBanner();
        } else {
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }

        for (BannerBean bannerBean : bannerBeanList) {
            if (BANNER_HOME.equals(bannerBean.getPosition())) {
                homeBannerBeanList.add(bannerBean);
            }
        }
        return homeBannerBeanList;
    }

    /**
     * 获取首页弹框广告 支持多张图片
     */
    public static List<BannerBean> getHomePopupBanner(Context context) {
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> popupBannerList = new ArrayList<>();
        if (systemInfoBean != null && systemInfoBean.getBanner() != null) {
            bannerBeanList = systemInfoBean.getBanner();
        } else {
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }
        for (BannerBean bannerBean : bannerBeanList) {
            if (BANNER_HOME_BOUNCED.equals(bannerBean.getPosition())) {
                popupBannerList.add(bannerBean);
                break;
            }
        }
        return popupBannerList;
    }


    /**
     * 获取商城广告
     */
    public static List<BannerBean> getStoreBanner(Context context) {
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> discoverBannerBeanList = new ArrayList<>();
        if (systemInfoBean != null && systemInfoBean.getBanner() != null) {
            bannerBeanList = systemInfoBean.getBanner();
        } else {
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }

        for (BannerBean bannerBean : bannerBeanList) {
            if (BANNER_STORE.equals(bannerBean.getPosition())) {
                discoverBannerBeanList.add(bannerBean);
            }
        }
        return discoverBannerBeanList;
    }

    /**
     * 获取发现广告
     */
    public static List<BannerBean> getDiscoverBanner(Context context) {
        List<BannerBean> bannerBeanList = new ArrayList<>();
        List<BannerBean> discoverBannerBeanList = new ArrayList<>();
        if (systemInfoBean != null && systemInfoBean.getBanner() != null) {
            bannerBeanList = systemInfoBean.getBanner();
        } else {
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                bannerBeanList = ((SystemBean) bean).getBanner();
            }
        }

        for (BannerBean bannerBean : bannerBeanList) {
            if (BANNER_DISCOVER.equals(bannerBean.getPosition())) {
                discoverBannerBeanList.add(bannerBean);
            }
        }
        return discoverBannerBeanList;
    }

    /**
     * 获取开通城市
     */
    public static List<String> getOpenCity(Context context) {
        if (systemInfoBean != null && systemInfoBean.getOpen_city() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getOpen_city();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getOpen_city();
            }
            return null;
        }
    }


    /**
     * 获取预约倒计时
     */
    public static int getAppointmentCountdown(Context context) {
        if (systemInfoBean != null && systemInfoBean.getAppointment_countdown() != 0) { //内存有直接从内存读取返回
            return systemInfoBean.getAppointment_countdown();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                systemInfoBean = ((SystemBean) bean);//缓存到内存
                return systemInfoBean.getAppointment_countdown();
            }
            return 120;
        }
    }

    /**
     * 获取订单倒计时
     */
    public static int getOrderCountdown(Context context) {
        if (systemInfoBean != null && systemInfoBean.getOrder_countdown() != 0) { //内存有直接从内存读取返回
            return systemInfoBean.getOrder_countdown();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                systemInfoBean = ((SystemBean) bean);//缓存到内存
                return systemInfoBean.getOrder_countdown();
            }
            return 30;
        }
    }

    /**
     * 获取课程分类信息
     */
    public static List<CategoryBean> getCourseCategory(Context context) {
        if (systemInfoBean != null && systemInfoBean.getCourse() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getCourse();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getCourse();
            }
            return null;
        }
    }

    /**
     * 获取营养品分类信息
     */
    public static ArrayList<CategoryBean> getNurtureCategory(Context context) {
        if (systemInfoBean != null && systemInfoBean.getNutrition() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getNutrition();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getNutrition();
            }
            return null;
        }
    }

    /**
     * 获取餐饮分类信息
     */
    public static ArrayList<CategoryBean> getFoodsCategory(Context context) {
        if (systemInfoBean != null && systemInfoBean.getFoods() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getFoods();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                //把本地数据做进内存缓存
                systemInfoBean = (SystemBean) bean;
                return systemInfoBean.getFoods();
            }
            return null;
        }
    }

    /**
     * 获取装备分类信息
     */
    public static ArrayList<CategoryBean> getEquipmentCategory(Context context) {
        if (systemInfoBean != null && systemInfoBean.getEquipment() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getEquipment();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getEquipment();
            }
            return null;
        }
    }


    /**
     * 获取场馆类型信息
     */
    public static ArrayList<String> getVenuesCategory(Context context) {
        if (systemInfoBean != null && systemInfoBean.getGymTypes() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getGymTypes();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getGymTypes();
            }
            return null;
        }
    }

    /**
     * 获取商圈信息
     */
    public static List<DistrictBean> getLandmark(Context context) {
        if (systemInfoBean != null && systemInfoBean.getLandmark() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getLandmark();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getLandmark();
            }
            return null;
        }
    }

    /**
     * 获取场馆品牌分类
     */
    public static List<CategoryBean> getGymBrand(Context context) {
        if (systemInfoBean != null && systemInfoBean.getGymBrand() != null) { //内存有直接从内存读取返回
            return systemInfoBean.getGymBrand();
        } else {          // 从本地读取
            Object bean = getSystemInfoBean(context, KEY_SYSTEM);
            if (bean instanceof SystemBean) {
                return ((SystemBean) bean).getGymBrand();
            }
            return null;
        }
    }


    /**
     * 获取关注列表
     *
     * @param context
     * @return
     */
    public static List<UserBean> getFollowList(Context context) {
        List<UserBean> followList = new ArrayList<>();
        if (Constant.followData != null && Constant.followData.getFollow() != null) {
            followList = Constant.followData.getFollow();
        } else {
            Object bean = getSystemInfoBean(context, KEY_FOLLOW);
            if (bean instanceof FollowData) {
                followList = ((FollowData) bean).getFollow();
            }
        }
        return followList;
    }

    /**
     * 判断是否关注
     *
     * @param context
     * @param uid
     * @return
     */
    public static boolean isFollow(Context context, String uid) {
        boolean isFollow = false;
        List<UserBean> followList = getFollowList(context);
        for (UserBean userBean : followList) {
            if (userBean.getId().equals(uid)) {
                isFollow = true;
                break;
            }
        }
        return isFollow;
    }

    /**
     * 判断是否关注
     *
     * @param context
     * @param bean
     * @return
     */
    public static boolean isFollow(Context context, UserBean bean) {

        boolean isFollow = false;
        List<UserBean> followList = getFollowList(context);
        for (UserBean userBean : followList) {
            if (bean == null) {
                return false;
            }
            if (userBean.getId().equals(bean.getId())) {
                isFollow = true;
                break;
            }
        }
        return isFollow;
    }

    /**
     * 添加关注
     *
     * @param bean
     */
    public static void addFollow(UserBean bean) {
        if (bean != null) {
            getFollowList(App.context).add(bean);
//            Constant.followData.getFollow().add(bean);
        }
    }

    /**
     * 删除关注
     *
     * @param bean
     */
    public static void removeFollow(UserBean bean) {
        if (Constant.followData == null || Constant.followData.getFollow() == null
                || bean == null || TextUtils.isEmpty(bean.getId())) {
            return;
        }
        int index = -1;
        List<UserBean> followList = Constant.followData.getFollow();

        for (int i = 0; i < followList.size(); i++) {
            if (bean.getId().equals(followList.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            followList.remove(index);
        }
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param obj
     */
    public static void putSystemInfoBean(Context context, Object obj, String key) {
        if (obj instanceof Serializable) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(), 0));
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString(key, string64).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("the obj must implement Serializable");
        }
    }

    public static Object getSystemInfoBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
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
