package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.aidong.R;
import com.example.aidong .entity.BannerBean;
import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.DistrictBean;
import com.example.aidong .entity.DistrictDescBean;
import com.example.aidong.entity.MarketPartsBean;
import com.example.aidong .entity.SystemBean;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .ui.mvp.model.SystemModel;
import com.example.aidong .ui.mvp.model.impl.SystemModelImpl;
import com.example.aidong .ui.mvp.presenter.SystemPresent;
import com.example.aidong .ui.mvp.view.SplashView;
import com.example.aidong .ui.mvp.view.SystemView;
import com.example.aidong .utils.LogAidong;
import com.example.aidong .utils.RequestResponseCount;
import com.example.aidong .utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.example.aidong .utils.Constant.systemInfoBean;


/**
 * 系统配置
 */
public class SystemPresentImpl implements SystemPresent {
    private Context context;
    private SystemModel systemModel;
    private SystemView systemView;
    private SplashView splashView;
    private RequestResponseCount requestResponse;

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

                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
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
                    if (landmark != null && !landmark.isEmpty()) {
                        for (DistrictBean districtBean : landmark) {
                            List<DistrictDescBean> districtValues = districtBean.getDistrictValues();

                            DistrictDescBean descBean = new DistrictDescBean();
                            descBean.setArea(context.getString(R.string.all_circle));
                            districtValues.add(0, descBean);
//                            landmark.get(0).setDistrict_values(districtValues);

                        }

//
//                        List<DistrictDescBean> districtValues = landmark.get(0).getDistrictValues();
//
//                        DistrictDescBean descBean = new DistrictDescBean();
//                        descBean.setArea(context.getString(R.string.all_circle));
//                        districtValues.add(0, descBean);
//                        landmark.get(0).setDistrict_values(districtValues);
                    }




//                    List<MarketPartsBean>  market_parts =  systemBean.market_parts;
//
//                    if (market_parts!=null&&!market_parts.isEmpty()){
//                        for (int i = 0; i < market_parts.size(); i++) {
//                            List<DistrictDescBean> districtValues = districtBean.getDistrictValues();
//
//                            DistrictDescBean descBean = new DistrictDescBean();
//                            descBean.setArea(context.getString(R.string.all_circle));
//                            districtValues.add(0, descBean);
//                        }
//                    }


                    ArrayList<CategoryBean> equipments = systemBean.getEquipment();
                    if (equipments != null) {
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        equipments.add(0, allBean);
                    } else {
                        equipments = new ArrayList<>();
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        equipments.add(0, allBean);
                        systemBean.setEquipment(equipments);
                    }

                    ArrayList<CategoryBean> nutrition = systemBean.getNutrition();
                    if (nutrition != null) {
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        nutrition.add(0, allBean);
                    } else {
                        nutrition = new ArrayList<CategoryBean>();
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        nutrition.add(0, allBean);
                        systemBean.setNutrition(nutrition);
                    }

                    ArrayList<CategoryBean> foods = systemBean.getFoods();
                    if (foods != null) {
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        foods.add(0, allBean);
                    } else {
                        foods = new ArrayList<CategoryBean>();
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        foods.add(0, allBean);
                        systemBean.setFoods(foods);
                    }

                    ArrayList<CategoryBean> tickets = systemBean.getTicket();
                    if (tickets != null) {
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        tickets.add(0, allBean);
                    } else {
                        tickets = new ArrayList<CategoryBean>();
                        CategoryBean allBean = new CategoryBean();
                        allBean.setName("全部");
                        allBean.setId("0");
                        tickets.add(0, allBean);
                        systemBean.setTicket(tickets);
                    }

                    ArrayList<String> types = systemBean.getGymTypes();
                    if (types != null) {
                        types.add(0, "全部类型");
                    }

                    systemInfoBean = systemBean;

                    SystemInfoUtils.putSystemInfoBean(context, systemBean, SystemInfoUtils.KEY_SYSTEM);  //保存到本地
                    if (systemView != null) {
                        systemView.onGetSystemConfiguration(true);
                    }

                    BannerBean startingUpBanner = null;
                    if (splashView != null && systemBean.getBanner() != null) {
                        for (BannerBean bannerBean : systemBean.getBanner()) {
                            if (TextUtils.equals(bannerBean.getPosition(), "0")) {
                                startingUpBanner = bannerBean;
                                break;
                            }
                        }
                    }
                    if (splashView != null) {
                        splashView.onGetStartingBanner(startingUpBanner);
                    }

                    LogAidong.i("mLocationClient   SystemInfoUtils.putSystemInfoBean");

                    if (requestResponse != null) {
                        requestResponse.onRequestResponse();
                    }

                }
            }
        }, os);
    }

    @Override
    public void getSystemInfoSelected(String os) {
        systemModel.getSystem(new BaseSubscriber<SystemBean>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //网络获取配置发生错误需要从本地读取配置信息
                if (systemView != null) {
                    systemView.onGetSystemConfiguration(false);
                }

                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
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
                if (landmark != null && !landmark.isEmpty()) {
                    for (DistrictBean districtBean : landmark) {
                        List<DistrictDescBean> districtValues = districtBean.getDistrictValues();

                        DistrictDescBean descBean = new DistrictDescBean();
                        descBean.setArea(context.getString(R.string.all_circle));
                        districtValues.add(0, descBean);
//                        landmark.get(0).setDistrict_values(districtValues);

                    }

//
                }





                ArrayList<CategoryBean> equipments = systemBean.getEquipment();
                if (equipments != null) {
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    equipments.add(0, allBean);
                } else {
                    equipments = new ArrayList<>();
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    equipments.add(0, allBean);
                    systemBean.setEquipment(equipments);
                }

                ArrayList<CategoryBean> nutrition = systemBean.getNutrition();
                if (nutrition != null) {
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    nutrition.add(0, allBean);
                } else {
                    nutrition = new ArrayList<CategoryBean>();
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    nutrition.add(0, allBean);
                    systemBean.setNutrition(nutrition);
                }

                ArrayList<CategoryBean> foods = systemBean.getFoods();
                if (foods != null) {
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    foods.add(0, allBean);
                } else {
                    foods = new ArrayList<CategoryBean>();
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    foods.add(0, allBean);
                    systemBean.setFoods(foods);
                }

                ArrayList<CategoryBean> tickets = systemBean.getTicket();
                if (tickets != null) {
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    tickets.add(0, allBean);
                } else {
                    tickets = new ArrayList<CategoryBean>();
                    CategoryBean allBean = new CategoryBean();
                    allBean.setName("全部");
                    allBean.setId("0");
                    tickets.add(0, allBean);
                    systemBean.setTicket(tickets);
                }

                ArrayList<String> types = systemBean.getGymTypes();
                if (types != null) {
                    types.add(0, "全部类型");
                }

                systemInfoBean = systemBean;

                SystemInfoUtils.putSystemInfoBean(context, systemBean, SystemInfoUtils.KEY_SYSTEM);  //保存到本地 为NULL时不会保存 需修改

                if (systemView != null) {
                    systemView.onGetSystemConfiguration(true);
                }

                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }

            }
        }, os);
    }

    public void setOnRequestResponse(RequestResponseCount requestResponse) {
        this.requestResponse = requestResponse;
    }
}
