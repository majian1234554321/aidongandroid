package com.example.aidong.entity.video;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public class VideoDetailResult {

    @SerializedName("videos")
    ArrayList<VideoDetail> video;

    public ArrayList<VideoDetail> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<VideoDetail> video) {
        this.video = video;
    }
}
