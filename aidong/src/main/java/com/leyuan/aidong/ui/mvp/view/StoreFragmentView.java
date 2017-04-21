package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.HomeItemBean;

import java.util.List;

/**
 * 商城
 * Created by song on 2017/4/12.
 */
public interface StoreFragmentView {

    void updateRecyclerView(List<HomeItemBean> homeItemBeen);
}
