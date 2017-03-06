package com.leyuan.aidong.entity.video;

import com.google.gson.annotations.SerializedName;

public class VideoDetail {
    @SerializedName("id")
    private int vId;

    @SerializedName("cover")
    private String cover;

    @SerializedName("m3u8")
    private String video;

    @SerializedName("phase")
    private int phase;


    @SerializedName("name")
    private String videoName;

    @SerializedName("author")
    private String author;

    @SerializedName("during")
    private String during;

    @SerializedName("introduce")
    private String introduce;

    @SerializedName("likes_counter")
    private int likesCount;
    @SerializedName("comments_counter")
    private String commentsCount;

    @SerializedName("updated_at")
    private String updated_at;
    private int contentId;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    //    public VideoDetail(int vId, String cover, String video, int phase, String videoName,
//                       String author, String during, String introduce, String likesCount, String commentsCount) {
//        this.vId = vId;
//        this.cover = cover;
//        this.video = video;
//        this.phase = phase;
//        this.videoName = videoName;
//        this.author = author;
//        this.during = during;
//        this.introduce = introduce;
//        this.likesCount = likesCount;
//        this.commentsCount = commentsCount;
//    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
}
