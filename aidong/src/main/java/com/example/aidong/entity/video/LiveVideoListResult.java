package com.example.aidong.entity.video;

import java.util.ArrayList;

public class LiveVideoListResult {
    private int code;
    private Result result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        ArrayList<LiveVideoInfo> liveVideo;
        ArrayList<LiveVideoSoonInfo> liveVideoMore;
        LiveVideoInfo liveHome;
        LiveVideoInfo liveDetail;

        public LiveVideoInfo getLiveDetail() {
            return liveDetail;
        }

        public void setLiveDetail(LiveVideoInfo liveDetail) {
            this.liveDetail = liveDetail;
        }

        public ArrayList<LiveVideoInfo> getLiveVideo() {
            return liveVideo;
        }

        public void setLiveVideo(ArrayList<LiveVideoInfo> liveVideo) {
            this.liveVideo = liveVideo;
        }

        public ArrayList<LiveVideoSoonInfo> getLiveVideoMore() {
            return liveVideoMore;
        }

        public void setLiveVideoMore(ArrayList<LiveVideoSoonInfo> liveVideoMore) {
            this.liveVideoMore = liveVideoMore;
        }

        public LiveVideoInfo getLiveHome() {
            return liveHome;
        }

        public void setLiveHome(LiveVideoInfo liveHome) {
            this.liveHome = liveHome;
        }
    }


}
