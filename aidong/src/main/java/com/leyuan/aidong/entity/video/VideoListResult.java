package com.leyuan.aidong.entity.video;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public class VideoListResult {

    @SerializedName("video")
    ArrayList<SpecialTopicInfo> video;

    public ArrayList<SpecialTopicInfo> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<SpecialTopicInfo> video) {
        this.video = video;
    }
}
