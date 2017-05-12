package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface DynamicPresent {

    void commonLoadData(SwitcherLayout switcherLayout);
    void pullToRefreshData();
    void requestMoreData(RecyclerView recyclerView, int size, int page);

    void postDynamic(boolean isPhoto,String content,String...media);

    void addComment(String id,String content);

    void pullToRefreshComments(String id);
    void requestMoreComments(RecyclerView recyclerView,String id,int page,int pageSize);

    void addLike(String id,int position);
    void cancelLike(String id,int position);
    void getLikes(String id,int page);

    void reportDynamic(String id,String type);

    void addFollow(String id);
    void cancelFollow(String id);
    void deleteDynamic(String id);
}
