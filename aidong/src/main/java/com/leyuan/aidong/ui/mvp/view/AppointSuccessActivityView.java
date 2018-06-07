package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.GoodsBean;

import java.util.List;

/**
 * 预约成功
 * Created by song on 2017/3/13.
 */
public interface AppointSuccessActivityView extends HideHeadItemView {

    void updateRecyclerView(List<GoodsBean> goodsBeanList);

    void showEmptyView();
}
