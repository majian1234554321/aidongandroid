package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.DemoBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.DemoModel;
import com.leyuan.support.mvp.model.impl.DemoModelImplEasyVersion;
import com.leyuan.support.mvp.presenter.DemoActivityPresent;
import com.leyuan.support.mvp.view.DemoActivityView;
import com.leyuan.support.util.Constant;

import java.util.List;

/**
 *
 * Created by song on 2016/8/9.
 */
public class DemoActivityPresentImpl implements DemoActivityPresent {
    private Context context;
    private DemoActivityView demoView;
    private DemoModel demoModel;

    public DemoActivityPresentImpl(DemoActivityView demoView) {
        demoModel = new DemoModelImplEasyVersion();
        this.demoView = demoView;
        this.context = (Context) demoView;
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        demoModel.getDemoNews(new RefreshSubscriber<List<DemoBean>>(context,recyclerView) {
            @Override
            public void onNext(List<DemoBean> demoList) {
                if(demoList != null && demoList.isEmpty()){
                    demoView.showEmptyView();
                    demoView.hideRecyclerView();
                }else {
                    demoView.hideEmptyView();
                    demoView.showRecyclerView();
                    demoView.updateRecyclerView(demoList);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(final RecyclerView recyclerView, final int pageSize, int page) {
        demoModel.getDemoNews(new RequestMoreSubscriber<List<DemoBean>>(context,recyclerView,pageSize) {
            @Override
            public void onNext(List<DemoBean> demoList) {
                if(demoList != null && !demoList.isEmpty()){
                    demoView.updateRecyclerView(demoList);
                }

                //没有更多数据了显示到底提示
                if(demoList != null && demoList.size() < pageSize){
                    demoView.showEndFooterView();
                }
            }
        },page);
    }
}
