package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.LikeData;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;
import com.leyuan.aidong.ui.mvp.model.FollowModel;
import com.leyuan.aidong.ui.mvp.model.impl.DynamicModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.view.DynamicParseUserView;

/**
 * Created by user on 2017/3/22.
 */
public class DynamicParsePresent {
    private DynamicParseUserView dynamicParseUserView;
    private Context context;
    private DynamicModel dynamicModel;
    private FollowModel followModel;

    public DynamicParsePresent(Context context, DynamicParseUserView view) {
        this.context = context;
        this.dynamicParseUserView = view;
        dynamicModel = new DynamicModelImpl();
        followModel = new FollowModelImpl();
    }

    public void getLikes(String dynamicId, final int page) {
        dynamicModel.getLikes(new ProgressSubscriber<LikeData>(context) {
            @Override
            public void onNext(LikeData likeData) {
                if (dynamicParseUserView != null) {
                    dynamicParseUserView.onGetUserData(likeData.getPublisher(), page);
                }
            }
        }, dynamicId, page);
    }

    public void addFollow(String id) {
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    dynamicParseUserView.addFollowResult(baseBean);
                }
            }
        }, id);
    }

    public void cancelFollow(String id) {
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    dynamicParseUserView.cancelFollowResult(baseBean);
                }
            }
        }, id);
    }
}
