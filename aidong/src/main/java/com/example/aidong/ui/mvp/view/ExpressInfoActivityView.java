package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.ExpressResultBean;

/**
 * 快递信息
 * Created by song on 17/06/01.
 */
public interface ExpressInfoActivityView {

    void updateExpressInfo(String cover,String expressName,ExpressResultBean expressResultBean);

    void showLoadingView();

    void hideLoadingView();

    void showEmptyView();
}
