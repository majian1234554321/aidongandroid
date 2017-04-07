package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.video.VideoDetail;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public interface VideoDetailView {
    void onGetVideoDetailList(ArrayList<VideoDetail> video);

    void onLikesResult(boolean success);

    void onDeleteLikesResult(boolean success);
}
