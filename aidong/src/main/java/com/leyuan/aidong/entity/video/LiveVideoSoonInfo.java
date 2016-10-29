package com.leyuan.aidong.entity.video;

import java.util.ArrayList;

public class LiveVideoSoonInfo {
    private String time;
    private ArrayList<LiveVideoInfo> liveVideoList;

    public LiveVideoSoonInfo(){

    }

    public LiveVideoSoonInfo(String time, ArrayList<LiveVideoInfo> liveVideoInfos) {
        this.time = time;
        liveVideoList = liveVideoInfos;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<LiveVideoInfo> getLiveVideoList() {
        return liveVideoList;
    }

    public void setLiveVideoList(ArrayList<LiveVideoInfo> liveVideoList) {
        this.liveVideoList = liveVideoList;
    }
}
