package com.leyuan.aidong.entity.video;

import java.util.ArrayList;

public class LiveVideoSoonInfo {
    private String time;
    private ArrayList<LiveVideoInfo> liveVideoList;

    public LiveVideoSoonInfo() {

    }

    public LiveVideoSoonInfo(String time, ArrayList<LiveVideoInfo> liveVideoInfos) {
        this.time = time;
        liveVideoList = liveVideoInfos;
    }

    public String getTime() {
        if (time == null && liveVideoList != null && !liveVideoList.isEmpty())
            time = liveVideoList.get(0).getLiveBeginTime();
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

    public static ArrayList<LiveVideoSoonInfo> createMoreLive(ArrayList<LiveVideoInfo> more) {

        if (more == null || more.isEmpty())
            return null;
        ArrayList<LiveVideoSoonInfo> array = new ArrayList<>();
        LiveVideoSoonInfo soonInfo = new LiveVideoSoonInfo();
        LiveVideoInfo pre = more.get(0);
        soonInfo.addLiveVide(pre);

        for (LiveVideoInfo liveVideo : more) {
            if (pre.equalDateSimple(liveVideo)) {
                soonInfo.addLiveVide(liveVideo);
            } else {
                array.add(soonInfo);
                pre = liveVideo;
                soonInfo = new LiveVideoSoonInfo();
                soonInfo.addLiveVide(pre);
            }
        }

        return array;
    }

    private void addLiveVide(LiveVideoInfo liveVideo) {
        liveVideoList.add(liveVideo);
    }
}
