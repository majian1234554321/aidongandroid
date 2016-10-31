package com.leyuan.aidong.entity.video;

public class SpecialTopicInfo {

    private int vId;
    private String name;
    //    private String cover;
    private int finished;
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

    public  Latest getLatest() {
        return latest;
    }

    public void setLatest( Latest latest) {
        this.latest = latest;
    }

    public class Latest {
        private int phase;
        private String author;
        private String updated;
        private String videoName;
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
