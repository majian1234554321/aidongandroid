package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.UserInfoData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.UserInfoModel;
import com.leyuan.aidong.ui.mvp.model.impl.UserInfoModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.view.UpdateUserInfoActivityView;
import com.leyuan.aidong.ui.mvp.view.UserDynamicFragmentView;
import com.leyuan.aidong.ui.mvp.view.UserInfoActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class UserInfoPresentImpl implements UserInfoPresent{
    private Context context;
    private UserInfoModel userInfoModel;
    private UserInfoActivityView userInfoActivityView;
    private UpdateUserInfoActivityView updateUserInfoActivityView;
    private UserDynamicFragmentView dynamicFragmentView;

    public UserInfoPresentImpl(Context context, UserInfoActivityView view) {
        this.context = context;
        this.userInfoActivityView = view;
        if(userInfoModel == null){
            this.userInfoModel = new UserInfoModelImpl(context);
        }
    }

    public UserInfoPresentImpl(Context context, UpdateUserInfoActivityView view) {
        this.context = context;
        this.updateUserInfoActivityView = view;
        if(userInfoModel == null){
            this.userInfoModel = new UserInfoModelImpl(context);
        }
    }

    public UserInfoPresentImpl(Context context, UserDynamicFragmentView view) {
        this.context = context;
        this.dynamicFragmentView = view;
        if(userInfoModel == null){
            this.userInfoModel = new UserInfoModelImpl(context);
        }
    }

    @Override
    public void getUserInfo(final SwitcherLayout switcherLayout, String id) {
        userInfoModel.getUserInfo(new CommonSubscriber<UserInfoData>(switcherLayout) {
            @Override
            public void onNext(UserInfoData userInfoData) {
                if(userInfoData != null && userInfoData.getProfile() != null){
                    switcherLayout.showContentLayout();
                    userInfoActivityView.updateUserInfo(userInfoData);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },id);
    }

    @Override
    public void commonLoadDynamic(String id) {
        userInfoModel.getUserDynamic(new BaseSubscriber<DynamicsData>(context) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if(dynamicsData != null && dynamicsData.getDynamic() != null &&
                        !dynamicsData.getDynamic().isEmpty()){
                    dynamicFragmentView.updateDynamic(dynamicsData.getDynamic());
                }else {
                    dynamicFragmentView.showEmptyLayout();
                }
            }
        },id, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreDynamic(String id, RecyclerView recyclerView, final int pageSize, int page) {
        userInfoModel.getUserDynamic(new RequestMoreSubscriber<DynamicsData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if(dynamicsData != null && dynamicsData.getDynamic() != null &&
                        !dynamicsData.getDynamic().isEmpty()){
                    dynamicFragmentView.updateDynamic(dynamicsData.getDynamic());
                }

                if(dynamicsData != null && (dynamicsData.getDynamic() == null ||
                        dynamicsData.getDynamic().size() < pageSize)){
                    dynamicFragmentView.showEndFooterView();
                }
            }
        },id,page);
    }

    @Override
    public void updateAvatar(String avatar) {

    }

    @Override
    public void updateGender(String gender) {

    }

    @Override
    public void updateBirthday(String birthday) {

    }

    @Override
    public void updateSignature(String signature) {

    }

    @Override
    public void updateAddress(String province, String city, String area) {

    }

    @Override
    public void updateHeight(String height) {

    }

    @Override
    public void updateWeight(String weight) {

    }

    @Override
    public void updateFrequency(String frequency) {

    }

    @Override
    public void updateUserInfo(String avatar,String gender, String birthday, String signature, String province,
                               String city, String area, String height, String weight, String frequency) {
        userInfoModel.updateUserInfo(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                updateUserInfoActivityView.updateResult(baseBean);
            }
        },null,null,gender,birthday,signature,null,null,province,city,area,height,weight,null, null,null,null,frequency);
    }
}
