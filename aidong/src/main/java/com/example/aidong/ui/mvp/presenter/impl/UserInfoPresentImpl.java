package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.DynamicsData;
import com.example.aidong .entity.data.UserInfoData;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong.http.subscriber.Progress2Subscriber;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.DynamicModel;
import com.example.aidong .ui.mvp.model.FollowModel;
import com.example.aidong .ui.mvp.model.UserInfoModel;
import com.example.aidong .ui.mvp.model.impl.DynamicModelImpl;
import com.example.aidong .ui.mvp.model.impl.FollowModelImpl;
import com.example.aidong .ui.mvp.model.impl.UserInfoModelImpl;
import com.example.aidong .ui.mvp.presenter.UserInfoPresent;
import com.example.aidong .ui.mvp.view.UpdateUserInfoActivityView;
import com.example.aidong .ui.mvp.view.UserDynamicFragmentView;
import com.example.aidong .ui.mvp.view.UserInfoActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;
import com.example.aidong .widget.SwitcherLayout;

/**
 * 用户资料
 * Created by song on 2017/1/16.
 */
public class UserInfoPresentImpl implements UserInfoPresent {
    private Context context;
    private UserInfoModel userInfoModel;
    private FollowModel followModel;
    private DynamicModel dynamicModel;
    private UserInfoActivityView userInfoActivityView;
    private UpdateUserInfoActivityView updateUserInfoActivityView;
    private UserDynamicFragmentView dynamicFragmentView;

    public UserInfoPresentImpl(Context context, UserInfoActivityView view) {
        this.context = context;
        this.userInfoActivityView = view;
        if (userInfoModel == null) {
            this.userInfoModel = new UserInfoModelImpl();
        }

    }

    public UserInfoPresentImpl(Context context, UpdateUserInfoActivityView view) {
        this.context = context;
        this.updateUserInfoActivityView = view;
        if (userInfoModel == null) {
            this.userInfoModel = new UserInfoModelImpl();
        }
    }

    public UserInfoPresentImpl(Context context, UserDynamicFragmentView view) {
        this.context = context;
        this.dynamicFragmentView = view;
        if (userInfoModel == null) {
            this.userInfoModel = new UserInfoModelImpl();
        }
    }

    @Override
    public void getUserInfo(String id) {
        userInfoModel.getUserInfo(new IsLoginSubscriber<UserInfoData>(context) {
            @Override
            public void onNext(UserInfoData userInfoData) {
                if (userInfoData != null && userInfoData.getProfile() != null) {
                    userInfoActivityView.updateUserInfo(userInfoData);
                }
            }
        }, id);
    }

    @Override
    public void getUserInfo(final SwitcherLayout switcherLayout, String id) {
        userInfoModel.getUserInfo(new CommonSubscriber<UserInfoData>(context,switcherLayout) {
            @Override
            public void onNext(UserInfoData userInfoData) {
                if (userInfoData != null && userInfoData.getProfile() != null) {
                    switcherLayout.showContentLayout();
                    userInfoActivityView.updateUserInfo(userInfoData);
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, id);
    }

    @Override
    public void pullToRefreshDynamic(String id) {
        userInfoModel.getUserDynamic(new IsLoginSubscriber<DynamicsData>(context) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null && dynamicsData.getDynamic() != null &&
                        !dynamicsData.getDynamic().isEmpty()) {
                    dynamicFragmentView.updateDynamic(dynamicsData.getDynamic());
                } else {
                    dynamicFragmentView.showEmptyLayout();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dynamicFragmentView.showEmptyLayout();
            }
        }, id, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreDynamic(String id, RecyclerView recyclerView, final int pageSize, int page) {
        userInfoModel.getUserDynamic(new RequestMoreSubscriber<DynamicsData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null && dynamicsData.getDynamic() != null &&
                        !dynamicsData.getDynamic().isEmpty()) {
                    dynamicFragmentView.updateDynamic(dynamicsData.getDynamic());
                }

                if (dynamicsData != null && (dynamicsData.getDynamic() == null ||
                        dynamicsData.getDynamic().size() < pageSize)) {
                    dynamicFragmentView.showEndFooterView();
                }
            }


        }, id, page);
    }

    @Override
    public void updateUserInfo(String name, String avatar, String gender, String birthday, String signature, String province,
                               String city, String area, String height, String weight, String frequency) {
        userInfoModel.updateUserInfo(new ProgressSubscriber<UserInfoData>(context) {
            @Override
            public void onNext(UserInfoData userInfoData) {
                if (App.mInstance.isLogin() && userInfoData.getProfile() != null) {
                    UserCoach userCoach = App.mInstance.getUser();
                    userCoach.setName(userInfoData.getProfile().getName());
                    userCoach.setAvatar(userInfoData.getProfile().getAvatar());
                    App.mInstance.setUser(userCoach);
                    Logger.i("updateuserinfo", "name = " + userInfoData.getProfile().getName() + ", avatar = "
                            + userInfoData.getProfile().getAvatar());
                }
                updateUserInfoActivityView.updateResult(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                updateUserInfoActivityView.updateResult(false);
            }
        }, name, avatar, gender, birthday, signature, null, null, province, city, area, height, weight, null, null, null, null, frequency);
    }

    @Override
    public void addFollow(String userId) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }

        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                userInfoActivityView.addFollowResult(baseBean);
            }
        }, userId,Constant.USER);

    }

    @Override
    public void cancelFollow(String userId) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }

        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                userInfoActivityView.cancelFollowResult(baseBean);
            }
        }, userId,Constant.USER);
    }


    @Override
    public void addFollow(String userId,String type) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }

        followModel.addFollow(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                userInfoActivityView.addFollowResult(baseBean);
            }
        }, userId,type);

    }

    @Override
    public void cancelFollow(String userId,String type) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }

        followModel.cancelFollow(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                userInfoActivityView.cancelFollowResult(baseBean);
            }
        }, userId,type);
    }



    @Override
    public void addLike(String id, final int position) {
        if(dynamicModel == null){
            dynamicModel = new DynamicModelImpl();
        }
        dynamicModel.addLike(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(dynamicFragmentView != null){
                    dynamicFragmentView.addLikeResult(position,baseBean);
                }
            }
        },id);
    }

    @Override
    public void cancelLike(String id,final int position) {
        if(dynamicModel == null){
            dynamicModel = new DynamicModelImpl();
        }
        dynamicModel.cancelLike(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(dynamicFragmentView != null){
                    dynamicFragmentView.canLikeResult(position,baseBean);
                }
            }
        },id);
    }

    @Override
    public void release() {

    }
}
