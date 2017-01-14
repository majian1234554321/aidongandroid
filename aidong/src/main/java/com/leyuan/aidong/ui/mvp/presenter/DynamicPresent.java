package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface DynamicPresent {

    void commonLoadData(SwitcherLayout switcherLayout);
    void pullToRefreshData();
    void requestMoreData(RecyclerView recyclerView, int size, int page);


    void postImageDynamic(String content,String...image);
    void postVideoDynamic(String content, String video);
    void postDynamic(String content, String video,String...image);


    void addComment(String id,String content);

    void pullToRefreshComments(String id);
    void requestMoreComments(RecyclerView recyclerView,String id,int page,int pageSize);
}
