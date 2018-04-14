package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.data.FollowCampaignData;
import com.leyuan.aidong.entity.data.FollowCourseData;
import com.leyuan.aidong.entity.data.FollowData;
import com.leyuan.aidong.entity.data.FollowUserData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.view.AppointmentUserActivityView;
import com.leyuan.aidong.ui.mvp.view.CircleView;
import com.leyuan.aidong.ui.mvp.view.CourseBeanNewDataView;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.ui.mvp.view.FollowCacheView;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.ui.mvp.view.UserInfoView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.RequestResponseCount;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 * Created by song on 2016/8/19.
 */
public class FollowPresentImpl implements FollowPresent {
    private Context context;
    private FollowModelImpl followModel;
    private FollowFragmentView followFragment;
    private List<UserBean> userBeanList = new ArrayList<>();
    private AppointmentUserActivityView appointmentUserActivityView;
    private RequestResponseCount requestResponse;
    private UserInfoView userInfoView;
    private FollowView followView;
    private CourseBeanNewDataView courseListListener;
    private CourserFragmentView courserFragmentView;
    private ArrayList<CourseBeanNew> courseBeanList;
    private FollowCacheView followCacheView;

    private CircleView circleView;

    public FollowPresentImpl(Context context) {
        this.context = context;
        followModel = new FollowModelImpl();
    }

    public FollowPresentImpl(Context context, AppointmentUserActivityView view) {
        this.context = context;
        this.appointmentUserActivityView = view;
        followModel = new FollowModelImpl();
    }

    public FollowPresentImpl(Context context, FollowFragmentView followFragment) {
        this.context = context;
        this.followFragment = followFragment;
        followModel = new FollowModelImpl();
    }


    public FollowPresentImpl(Context context, CircleView circleView) {
        this.context = context;
        this.circleView = circleView;
        followModel = new FollowModelImpl();
    }


    public void setUserViewListener(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
    }

    public void setFollowListener(FollowView followView) {
        this.followView = followView;
    }

    public void setFollowCacheView(FollowCacheView followCacheView) {
        this.followCacheView = followCacheView;
    }

    public void getUserFollow(String type, int page) {
        followModel.getUserFollow(new BaseSubscriber<FollowUserData>(context) {
            @Override
            public void onNext(FollowUserData followUserData) {
                if (userInfoView != null) {
                    userInfoView.onGetUserData(followUserData.getFollowings());
                }
            }

            @Override
            public void onError(Throwable e) {
//                super.onError(e);

                if (userInfoView != null) {
                    userInfoView.onGetUserData(null);
                }
            }
        }, type, page);
    }

    public void getCampaignFollow(int page) {
        followModel.getCampaignFollow(new ProgressSubscriber<FollowCampaignData>(context) {
            @Override
            public void onNext(FollowCampaignData followUserData) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, page);
    }

    public void getCourseFollowFirst() {
        followModel.getCourseFollow(new ProgressSubscriber<FollowCourseData>(context) {
            @Override
            public void onNext(FollowCourseData courseData) {
                if (courseData != null && courseData.getFollowings() != null && !courseData.getFollowings().isEmpty()) {
                    courserFragmentView.refreshRecyclerViewData(courseData.getFollowings());
                } else {
                    courserFragmentView.showEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }
        }, Constant.PAGE_FIRST);
    }

    public void getCourseFollowMore(int page) {
        followModel.getCourseFollow(new ProgressSubscriber<FollowCourseData>(context) {
            @Override
            public void onNext(FollowCourseData courseData) {
                if (courseData != null && !courseData.getFollowings().isEmpty()) {
                    courseBeanList = courseData.getFollowings();
                }
                if (courseBeanList != null && !courseBeanList.isEmpty()) {
                    courserFragmentView.loadMoreRecyclerViewData(courseBeanList);
                }
                //没有更多数据了显示到底提示
//                if (courseBeanList != null && courseBeanList.size() < pageSize) {
//                    courserFragmentView.showEndFooterView();
//                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, page);
    }


    public void getRecommendCoachList(int page) {
        followModel.getRecommendCoachList(new ProgressSubscriber<FollowUserData>(context) {
            @Override
            public void onNext(FollowUserData followUserData) {
                if (userInfoView != null) {
                    userInfoView.onGetUserData(followUserData.getCoach());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, page);
    }




    public void requestMoreDataFollow(RecyclerView recyclerView,int page,int size) {
        followModel.getRecommendCoachList(new RequestMoreSubscriber<FollowUserData>(context, recyclerView,size) {
            @Override
            public void onNext(FollowUserData followUserData) {
                if (followUserData!=null){
                    circleView.loadMoreData(followUserData.getCoach());
                }
            }
        }, page);
    }








    @Override
    public void getFollowList() {
        //旧的获取关注的人列表
        followModel.getFollowCache(new RefreshSubscriber<FollowData>(context) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null) {
                    followData = followData;
                    SystemInfoUtils.putSystemInfoBean(context, followData, SystemInfoUtils.KEY_FOLLOW);
                }
                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }

                if (followCacheView != null) {
                    followCacheView.onGetFollowCacheList(followData.following_ids);
                }


            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }

                if (followCacheView != null) {
                    followCacheView.onGetFollowCacheList(null);
                }
            }
        });
    }

    @Override
    public void getFollowCahceList() {
        //旧的获取关注的人列表
        followModel.getFollowCache(new RefreshSubscriber<FollowData>(context) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null) {
                    followData = followData;
                    SystemInfoUtils.putSystemInfoBean(context, followData, SystemInfoUtils.KEY_FOLLOW);
                }
                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }

                if (followCacheView != null) {
                    followCacheView.onGetFollowCacheList(followData.following_ids);
                }


            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }

                if (followCacheView != null) {
                    followCacheView.onGetFollowCacheList(null);
                }
            }
        });
    }


    @Override
    public void getFollowers(final int pageSize) {
        followModel.getFollowers(new RefreshSubscriber<FollowData>(context) {
            @Override
            public void onNext(FollowData followData) {
                if (followData == null) {
                    followFragment.onRefreshData(new ArrayList<UserBean>());
                    return;
                }
                if (pageSize == 1) {
                    followFragment.onRefreshData(followData.getUser());
                } else {
                    followFragment.onLoadMoreData(followData.getUser());
                }
            }
        }, pageSize);
    }


    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, final String type) {
        followModel.getFollow(new CommonSubscriber<FollowData>(context, switcherLayout) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null && !followData.getFollow().isEmpty()) {
                    userBeanList = followData.getFollow();
                }
                if (userBeanList != null && !userBeanList.isEmpty()) {
                    if ("followings".equals(type)) {
                        followData = followData;
                        SystemInfoUtils.putSystemInfoBean(context, followData, SystemInfoUtils.KEY_FOLLOW);
                    }
                    switcherLayout.showContentLayout();
                    followFragment.onRefreshData(userBeanList);
                } else {
                    followFragment.showEmptyView();
                }
            }
        }, type, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(final String type) {
        followModel.getFollow(new RefreshSubscriber<FollowData>(context) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null && !followData.getFollow().isEmpty()) {
//                    if ("followings".equals(type)) {
//                        followData = followData;
//                        SystemInfoUtils.putSystemInfoBean(context, followData, SystemInfoUtils.KEY_FOLLOW);
//                    }
                    followFragment.onRefreshData(followData.getFollow());
                }else {
                    followFragment.showEmptyView();
                }
            }
        }, type, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, String type, int page) {
        followModel.getFollow(new RequestMoreSubscriber<FollowData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(FollowData followData) {
                if (followData != null) {
                    userBeanList = followData.getFollow();
                }
                if (!userBeanList.isEmpty()) {
                    followFragment.onLoadMoreData(userBeanList);
                }
                //没有更多数据了显示到底提示
                if (userBeanList.size() < pageSize) {
                    followFragment.showEndFooterView();

                }
            }
        }, type, page);
    }

    @Override
    public void addFollow(String id) {
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (appointmentUserActivityView != null) {
                    appointmentUserActivityView.addFollowResult(baseBean);
                }
                if (followFragment != null) {
                    followFragment.addFollowResult(baseBean);
                }
            }
        }, id, Constant.USER);
    }

    @Override
    public void addFollow(String id, String type) {
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {

                Logger.i("Appointuseractivity follow"," addFollow  onNext");

                if (followView != null) {
                    followView.addFollowResult(baseBean);
                }

                if (followFragment != null) {
                    followFragment.addFollowResult(baseBean);
                }

                if (appointmentUserActivityView != null) {
                    appointmentUserActivityView.addFollowResult(baseBean);
                }
            }
        }, id, type);
    }

    @Override
    public void cancelFollow(String id) {
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (appointmentUserActivityView != null) {
                    appointmentUserActivityView.cancelFollowResult(baseBean);
                }
                if (followFragment != null) {
                    followFragment.cancelFollowResult(baseBean);
                }
            }
        }, id, Constant.USER);
    }

    @Override
    public void cancelFollow(String id, String type) {
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                Logger.i("Appointuseractivity follow"," cancelFollow  onNext");


                if (followView != null) {
                    followView.cancelFollowResult(baseBean);
                }

                if (followFragment != null) {
                    followFragment.cancelFollowResult(baseBean);
                }

                if (appointmentUserActivityView != null) {
                    appointmentUserActivityView.cancelFollowResult(baseBean);
                }
            }
        }, id, type);
    }

    public void setOnRequestResponse(RequestResponseCount requestResponse) {
        this.requestResponse = requestResponse;
    }

    public void setCourseBeanNewDataView(CourseBeanNewDataView courseListListener) {
        this.courseListListener = courseListListener;
    }

    public void setCourserFragmentView(CourserFragmentView courseFragmentView) {
        this.courserFragmentView = courseFragmentView;
    }
}
