package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.video.SpecialTopicInfo;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public interface VideoListViewLister {

    void onGetVideoList(ArrayList<SpecialTopicInfo> video);
}
