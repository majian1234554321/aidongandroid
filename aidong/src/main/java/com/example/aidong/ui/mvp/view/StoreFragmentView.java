package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BannerBean;
import com.example.aidong .entity.HomeBean;

import java.util.List;

/**
 * 商城
 * Created by song on 2017/4/12.
 */
public interface StoreFragmentView {

    void updateBanners(List<BannerBean> bannerBeanList);

    void updateRecyclerView(List<HomeBean> homeItemBeen);

    void showEmptyView();
}
