package com.example.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.example.aidong .widget.SwitcherLayout;

import java.util.Map;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface DynamicPresent {

    void commonLoadData(SwitcherLayout switcherLayout);

    void commonLoadDataFollow(SwitcherLayout switcherLayout);

    void requestMoreDataFollow(RecyclerView recyclerView, int size, int page);

    void pullToRefreshData();

    void pullToRefreshDataFollow();

    void requestMoreData(RecyclerView recyclerView, int size, int page);

    void getDynamicDetail(String id);

//    void postDynamic(boolean isPhoto, String content, String type,
//                     String link_id,
//                     String position_name, String...media);

//    void postDynamic(boolean isPhoto, String content, String type,
//                     String link_id,
//                     String position_name, String latitude, String longitude, String... media);

    void postDynamic(boolean isPhoto, String content, String type, String link_id,
                     String position_name, String latitude, String longitude,
                     Map<String, String> itUser, String... media);

    void addComment(String id, String content);

    void addComment(String id, String content, Map<String, String> itUser,Map<String, String> replyUserMap);

    void pullToRefreshComments(String id);
    void requestMoreComments(RecyclerView recyclerView,String id,int page,int pageSize);

    void addLike(String id,int position);
    void cancelLike(String id,int position);
    void getLikes(String id,int page);

    void reportDynamic(String id,String type);

    void addFollow(String id,String type);
    void cancelFollow(String id,String type);
    void deleteDynamic(String id);
}
