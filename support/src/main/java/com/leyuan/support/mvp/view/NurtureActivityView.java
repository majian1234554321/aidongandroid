package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.NurtureBean;

import java.util.List;

/**
 * 营养品
 * Created by song on 2016/8/15.
 */
public interface NurtureActivityView {
    /**
     * 更新列表
     * @param nurtureBeanList
     */
    void updateRecyclerView(List<NurtureBean> nurtureBeanList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
