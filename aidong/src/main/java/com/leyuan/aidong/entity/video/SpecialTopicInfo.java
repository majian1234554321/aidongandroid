package com.leyuan.aidong.entity.video;

import com.google.gson.annotations.SerializedName;

public class SpecialTopicInfo {

    @SerializedName("id")
    private int vId;
    @SerializedName("name")
    private String name;

    @SerializedName("finished")
    private int finished;

    @SerializedName("latest")
    private Latest latest;

    public int getId() {
        return vId;
    }

    public void setId(int id) {
        this.vId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public Latest getLatest() {
        return latest;
    }

    public void setLatest(Latest latest) {
        this.latest = latest;
    }

    public class Latest {

        @SerializedName("phase")
        private int phase;
        @SerializedName("author")
        private String author;
        @SerializedName("updated_at")
        private String updated;
        @SerializedName("name")
        private String videoName;
        @SerializedName("cover")
        private String cover;

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public int getPhase() {
            return phase;
        }

        public void setPhase(int phase) {
            this.phase = phase;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

}
