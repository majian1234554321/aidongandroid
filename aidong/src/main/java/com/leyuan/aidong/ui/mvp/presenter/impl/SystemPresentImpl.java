package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.DistrictDescBean;
import com.leyuan.aidong.entity.SystemBean;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.SystemModel;
import com.leyuan.aidong.ui.mvp.model.impl.SystemModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.SystemPresent;
import com.leyuan.aidong.ui.mvp.view.SplashView;
import com.leyuan.aidong.ui.mvp.view.SystemView;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.leyuan.aidong.utils.Constant.systemInfoBean;


/**
 * 系统配置
 */
public class SystemPresentImpl implements SystemPresent {
    private Context context;
    private SystemModel systemModel;
    private SystemView systemView;
    private SplashView splashView;

    public SystemPresentImpl(Context context) {
        this.context = context;
        systemModel = new SystemModelImpl();
    }

    @Override
    public void setSystemView(SystemView systemView) {
        this.systemView = systemView;
    }

    @Override
    public void setSplashView(SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void getSystemInfo(String os) {
        systemModel.getSystem(new Subscriber<SystemBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //网络获取配置发生错误需要从本地读取配置信息
                if (systemView != null) {
                    systemView.onGetSystemConfiguration(false);
                }

                if (splashView != null) {
                    splashView.onGetStartingBanner(null);
                }
            }

            @Override
            public void onNext(SystemBean systemBean) {
                if (systemBean != null) {

                    List<CategoryBean> gymList = systemBean.getGymBrand();
                    if (gymList != null) {
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部品牌");
                        gymList.add(0, allBean);
                    }

                    ArrayList<DistrictBean> landmark = systemBean.getLandmark();
                    if(landmark != null && !landmark.isEmpty()) {
                        List<DistrictDescBean> districtValues = landmark.get(0).getDistrictValues();
                        DistrictDescBean descBean = new DistrictDescBean();
                        descBean.setArea(context.getString(R.string.all_circle));
                        districtValues.add(0,descBean);
                        landmark.get(0).setDistrict_values(districtValues);
                    }

                    ArrayList<CategoryBean> equipments = systemBean.getEquipment();
                    if(equipments != null){
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        equipments.add(0, allBean);
                    }

                    ArrayList<CategoryBean> nutrition = systemBean.getNutrition();
                    if(nutrition != null){
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        nutrition.add(0, allBean);
                    }

                    ArrayList<String> types = systemBean.getGymTypes();
                    if(types != null){
                        types.add(0, "全部类型");
                    }

                    systemInfoBean = systemBean;

                    SystemInfoUtils.putSystemInfoBean(context, systemBean, SystemInfoUtils.KEY_SYSTEM);  //保存到本地
                    if (systemView != null) {
                        systemView.onGetSystemConfiguration(true);
                    }

                    String image = null;
                    if (splashView != null && systemBean.getBanner() != null) {
                        for (BannerBean bannerBean : systemBean.getBanner()) {
                            if (TextUtils.equals(bannerBean.getPosition(), "0")) {
                                image = bannerBean.getImage();
                                break;
                            }
                        }
                    }

                    splashView.onGetStartingBanner(image);
                    LogAidong.i("mLocationClient   SystemInfoUtils.putSystemInfoBean");
                }
            }
        }, os);
    }

    @Override
    public void getSystemInfoSelected(String os) {
        systemModel.getSystem(new ProgressSubscriber<SystemBean>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //网络获取配置发生错误需要从本地读取配置信息
                if (systemView != null) {
                    systemView.onGetSystemConfiguration(false);
                }
            }

            @Override
            public void onNext(SystemBean systemBean) {

                List<CategoryBean> gymList = systemBean.getGymBrand();
                if (gymList != null) {
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部品牌");
                    gymList.add(0, allBean);
                }

                ArrayList<DistrictBean> landmark = systemBean.getLandmark();
                if(landmark != null && !landmark.isEmpty()) {
                    List<DistrictDescBean> districtValues = landmark.get(0).getDistrictValues();
                    DistrictDescBean descBean = new DistrictDescBean();
                    descBean.setArea(context.getString(R.string.all_circle));
                    districtValues.add(0,descBean);
                    landmark.get(0).setDistrict_values(districtValues);
                }

                ArrayList<CategoryBean> equipments = systemBean.getEquipment();
                if(equipments != null){
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    equipments.add(0, allBean);
                }

                ArrayList<CategoryBean> nutrition = systemBean.getNutrition();
                if(nutrition != null){
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    nutrition.add(0, allBean);
                }

                ArrayList<String> types = systemBean.getGymTypes();
                if(types != null){
                    types.add(0, "全部类型");
                }

                systemInfoBean = systemBean;

                SystemInfoUtils.putSystemInfoBean(context, systemBean, SystemInfoUtils.KEY_SYSTEM);  //保存到本地 为NULL时不会保存 需修改

                if (systemView != null) {
                    systemView.onGetSystemConfiguration(true);
                }

//                UserInfoModel model = new UserInfoModelImpl();
//                model.getMineInfo(new ProgressSubscriber<MineInfoBean>(context) {
//                    @Override
//                    public void onNext(MineInfoBean mineInfoBean) {
//
//
//                        if (systemView != null) {
//                            Constant.gyms = mineInfoBean.getGyms();
//                            Constant.activity = mineInfoBean.getActivity();
//                            systemView.onGetSystemConfiguration(true);
//                        }
//                    }
//                });
//                LogAidong.i("mLocationClient   SystemInfoUtils.putSystemInfoBean");
            }
        }, os);
    }
}
