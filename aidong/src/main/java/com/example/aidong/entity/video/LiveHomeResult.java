package com.example.aidong.entity.video;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public class LiveHomeResult {

    @SerializedName("live")
    private LiveHome live;

    public LiveHome getLive() {
        return live;
    }

    public void setLive(LiveHome live) {
        this.live = live;
    }

    public class LiveHome {
        @SerializedName("now")
        private ArrayList<LiveVideoInfo> now;
        @SerializedName("more")
        private ArrayList<LiveVideoInfo> more;

        @SerializedName("default")
        private LiveVideoInfo empty;

        public ArrayList<LiveVideoInfo> getNow() {
            return now;
        }

        public void setNow(ArrayList<LiveVideoInfo> now) {
            this.now = now;
        }

        public ArrayList<LiveVideoInfo> getMore() {
            return more;
        }

        public void setMore(ArrayList<LiveVideoInfo> more) {
            this.more = more;
        }

        public LiveVideoInfo getEmpty() {
            return empty;
        }

        public void setEmpty(LiveVideoInfo empty) {
            this.empty = empty;
        }
    }
}
