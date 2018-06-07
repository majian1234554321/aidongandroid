package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.SearchHistoryBean;

import java.util.List;

/**
 * 搜索界面
 * Created by song on 2016/10/19.
 */
public interface SearchActivityView {

    void setHistory(List<SearchHistoryBean> historyList);

    void setSearchResult(Object o);

}
