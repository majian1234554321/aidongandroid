package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.NewsBean;

import java.util.List;

/**
 * 世界之窗
 * Created by song on 2016/11/9.
 */
public interface SportNewsActivityView {

    /**
     * 更新列表
     * @param newsBeanList VenuesBean集合
     */
    void updateRecyclerView(List<NewsBean> newsBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();
}
