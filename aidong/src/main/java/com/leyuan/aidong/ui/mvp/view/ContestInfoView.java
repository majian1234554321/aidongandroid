package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.NewsBean;

import java.util.ArrayList;

/**
 * Created by user on 2018/3/2.
 */
public interface ContestInfoView {
    void onGetContestInfoData(ArrayList<NewsBean> info);
}
