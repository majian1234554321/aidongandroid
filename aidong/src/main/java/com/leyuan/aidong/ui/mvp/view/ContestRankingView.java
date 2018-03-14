package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.UserBean;

import java.util.ArrayList;

/**
 * Created by user on 2018/3/1.
 */
public interface ContestRankingView {
    void onGetRankingData(ArrayList<UserBean> ranking);
}
