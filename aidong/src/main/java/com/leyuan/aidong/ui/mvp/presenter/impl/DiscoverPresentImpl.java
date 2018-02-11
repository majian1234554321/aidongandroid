package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.data.DiscoverData;
import com.leyuan.aidong.entity.data.DiscoverNewsData;
import com.leyuan.aidong.entity.data.DiscoverUserData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.DiscoverModel;
import com.leyuan.aidong.ui.mvp.model.FollowModel;
import com.leyuan.aidong.ui.mvp.model.impl.DiscoverModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.DiscoverPresent;
import com.leyuan.aidong.ui.mvp.view.DiscoverFragmentView;
import com.leyuan.aidong.ui.mvp.view.DiscoverUserActivityView;
import com.leyuan.aidong.ui.mvp.view.SportNewsActivityView;
import com.leyuan.aidong.ui.mvp.view.UserInfoView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 * Created by song on 2016/8/29.
 */
public class DiscoverPresentImpl implements DiscoverPresent {
    private Context context;
    private DiscoverModel discoverModel;
    private FollowModel followModel;
    private DiscoverUserActivityView discoverUserActivityView;
    private SportNewsActivityView sportNewsActivityView;
    private DiscoverFragmentView discoverFragmentView;
    private List<UserBean> userBeanList = new ArrayList<>();
    private List<NewsBean> newsBeanList = new ArrayList<>();

    public DiscoverPresentImpl(Context context, DiscoverUserActivityView discoverUserActivityView) {
        this.context = context;
        this.discoverUserActivityView = discoverUserActivityView;
        if (discoverModel == null) {
            discoverModel = new DiscoverModelImpl();
        }
    }

    public DiscoverPresentImpl(Context context, SportNewsActivityView sportNewsActivityView) {
        this.context = context;
        this.sportNewsActivityView = sportNewsActivityView;
        if (discoverModel == null) {
            discoverModel = new DiscoverModelImpl();
        }
    }

    public DiscoverPresentImpl(Context context, DiscoverFragmentView discoverFragmentView) {
        this.context = context;
        this.discoverFragmentView = discoverFragmentView;
        if (discoverModel == null) {
            discoverModel = new DiscoverModelImpl();
        }
    }

    public DiscoverPresentImpl(Context context) {
        this.context = context;
        if (discoverModel == null) {
            discoverModel = new DiscoverModelImpl();
        }
    }

    @Override
    public void commonLoadDiscoverData(final SwitcherLayout switcherLayout) {
        discoverModel.getDiscover(new CommonSubscriber<DiscoverData>(context, switcherLayout) {
            @Override
            public void onNext(DiscoverData discoverData) {
                if (discoverData != null) {
                    switcherLayout.showContentLayout();
                    discoverFragmentView.setDiscoverData(discoverData);
                }
            }
        });
    }


    public void pullToRefreshDiscoverData() {
        discoverModel.getDiscover(new IsLoginSubscriber<DiscoverData>(context) {
            @Override
            public void onNext(DiscoverData discoverData) {
                if (discoverData != null) {
                    discoverFragmentView.setDiscoverData(discoverData);
                }
            }
        });
    }

    @Override
    public void commonLoadUserData(final SwitcherLayout switcherLayout, double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new CommonSubscriber<DiscoverUserData>(context, switcherLayout) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if (discoverUserData != null && discoverUserData.getUser() != null) {
                    userBeanList = discoverUserData.getUser();
                }
                if (!userBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                } else {
                    discoverUserActivityView.showEmptyView();
                }
            }
        }, lat, lng, Constant.PAGE_FIRST, gender, type);
    }

    @Override
    public void pullToRefreshUserData(double lat, double lng, String gender, String type) {
        discoverModel.getUsers(new RefreshSubscriber<DiscoverUserData>(context) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if (discoverUserData != null && discoverUserData.getUser() != null) {
                    userBeanList = discoverUserData.getUser();
                }
                if (!userBeanList.isEmpty()) {
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                } else {
                    discoverUserActivityView.showEmptyView();
                }
            }
        }, lat, lng, Constant.PAGE_FIRST, gender, type);
    }

    public void getNearlyUserData(final UserInfoView listener) {
        discoverModel.getUsers(new RefreshSubscriber<DiscoverUserData>(context) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                listener.onGetUserData(discoverUserData.getUser());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onGetUserData(null);
            }
        }, App.lat, App.lon, Constant.PAGE_FIRST, null, null);
    }

    @Override
    public void requestMoreUserData(RecyclerView recyclerView, double lat, double lng, String gender, String type, final int pageSize, int page) {
        discoverModel.getUsers(new RequestMoreSubscriber<DiscoverUserData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(DiscoverUserData discoverUserData) {
                if (discoverUserData != null && discoverUserData.getUser() != null) {
                    userBeanList = discoverUserData.getUser();
                }
                if (!userBeanList.isEmpty()) {
                    discoverUserActivityView.updateRecyclerView(userBeanList);
                }
                //没有更多数据了显示到底提示
                if (userBeanList.size() < pageSize) {
                    discoverUserActivityView.showEndFooterView();
                }
            }
        }, lat, lng, page, gender, type);
    }

    @Override
    public void commonLoadNewsData(final SwitcherLayout switcherLayout) {
        discoverModel.getNews(new CommonSubscriber<DiscoverNewsData>(context, switcherLayout) {
            @Override
            public void onNext(DiscoverNewsData discoverNewsData) {
                if (discoverNewsData != null && discoverNewsData.getNews() != null) {
                    newsBeanList = discoverNewsData.getNews();
                }
                if (!newsBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    sportNewsActivityView.updateRecyclerView(newsBeanList);
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshNewsData() {
        discoverModel.getNews(new RefreshSubscriber<DiscoverNewsData>(context) {
            @Override
            public void onNext(DiscoverNewsData discoverNewsData) {
                if (discoverNewsData != null && discoverNewsData.getNews() != null) {
                    newsBeanList = discoverNewsData.getNews();
                }
                if (!newsBeanList.isEmpty()) {
                    sportNewsActivityView.updateRecyclerView(newsBeanList);
                }
            }
        }, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreNewsData(RecyclerView recyclerView, final int pageSize, int page) {
        discoverModel.getNews(new RequestMoreSubscriber<DiscoverNewsData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(DiscoverNewsData discoverNewsData) {
                if (discoverNewsData != null && discoverNewsData.getNews() != null) {
                    newsBeanList = discoverNewsData.getNews();
                }
                if (!newsBeanList.isEmpty()) {
                    sportNewsActivityView.updateRecyclerView(newsBeanList);
                }
                //没有更多数据了显示到底提示
                if (newsBeanList.size() < pageSize) {
                    sportNewsActivityView.showEndFooterView();
                }
            }
        }, page);
    }

    @Override
    public void addFollow(String id) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    discoverUserActivityView.addFollowResult(baseBean);
                }
            }
        }, id,"user");
    }

    @Override
    public void cancelFollow(String id) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    discoverUserActivityView.cancelFollowResult(baseBean);
                }
            }
        }, id,"user");
    }


    @Override
    public void addFollow(String id,String type) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    discoverUserActivityView.addFollowResult(baseBean);
                }
            }
        }, id,type);
    }

    @Override
    public void cancelFollow(String id,String type) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    discoverUserActivityView.cancelFollowResult(baseBean);
                }
            }
        }, id,type);
    }
}
