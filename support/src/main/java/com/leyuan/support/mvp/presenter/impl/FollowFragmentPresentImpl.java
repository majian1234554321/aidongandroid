package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.UserBean;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.FollowModel;
import com.leyuan.support.mvp.model.impl.FollowModelImpl;
import com.leyuan.support.mvp.presenter.FollowFragmentPresent;
import com.leyuan.support.mvp.view.FollowFragmentView;
import com.leyuan.support.util.Constant;

import java.util.List;

/**
 * 关注
 * Created by song on 2016/8/19.
 */
public class FollowFragmentPresentImpl implements FollowFragmentPresent{
    private Context context;
    private FollowModel followModel;
    private FollowFragmentView followFragmentView;

    public FollowFragmentPresentImpl(Context context, FollowFragmentView followFragmentView) {
        this.context = context;
        this.followFragmentView = followFragmentView;
        followModel = new FollowModelImpl();
    }

    @Override
    public void pullToRefreshData() {
        followModel.getFollowings(new RefreshSubscriber<List<UserBean>>(context) {
            @Override
            public void onNext(List<UserBean> userBeanList) {
                if(userBeanList != null && userBeanList.isEmpty()){
                    followFragmentView.showEmptyView();
                    followFragmentView.hideRecyclerView();
                }else {
                    followFragmentView.hideEmptyView();
                    followFragmentView.showRecyclerView();
                    followFragmentView.updateRecyclerView(userBeanList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        followModel.getFollowings(new RequestMoreSubscriber<List<UserBean>>(context,recyclerView,pageSize) {
            @Override
            public void onNext(List<UserBean> userBeanList) {
                if(userBeanList != null && !userBeanList.isEmpty()){
                    followFragmentView.updateRecyclerView(userBeanList);
                }

                //没有更多数据了显示到底提示
                if(userBeanList != null && userBeanList.size() < pageSize){
                    followFragmentView.showEndFooterView();
                }
            }
        },page);
    }

    @Override
    public void addFollow(int id) {
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {

            }
        },id);
    }

    @Override
    public void cancelFollow(int id) {
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {

            }
        },id);
    }
}
