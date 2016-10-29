package com.leyuan.aidong.entity.video;


import java.io.Serializable;

public class LiveVideoInfo implements Serializable {
    private int liveId;
    private String liveName;
    private String liveAuthor;
    private String liveCover;
    private String liveBeginTime;
    private String liveEndTime;
    private int personCou;
    private String livePath;
    private String liveContent;
    private int commentsCou;
    private int praiseCou;

    public String getLiveContent() {
        return liveContent;
    }

    public void setLiveContent(String liveContent) {
        this.liveContent = liveContent;
    }

    public int getCommentsCou() {
        return commentsCou;
    }

    public void setCommentsCou(int commentsCou) {
        this.commentsCou = commentsCou;
    }

    public int getPraiseCou() {
        return praiseCou;
    }

    public void setPraiseCou(int praiseCou) {
        this.praiseCou = praiseCou;
    }

    public LiveVideoInfo() {

    }

    public LiveVideoInfo(int liveId, String liveName, String liveAuthor, String liveCover, String liveBeginTime, String liveEndTime, int personCou, String livePath) {
        this.liveId = liveId;
        this.liveName = liveName;
        this.liveAuthor = liveAuthor;
        this.liveCover = liveCover;
        this.liveBeginTime = liveBeginTime;
        this.liveEndTime = liveEndTime;
        this.personCou = personCou;
        this.livePath = livePath;
    }

    public int getPersonCou() {
        return personCou;
    }

    public void setPersonCou(int personCou) {
        this.personCou = personCou;
    }

    public String getLivePath() {
        return livePath;
    }

    public void setLivePath(String livePath) {
        this.livePath = livePath;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
    }

    public String getLiveAuthor() {
        return liveAuthor;
    }

    public void setLiveAuthor(String liveAuthor) {
        this.liveAuthor = liveAuthor;
    }

    public String getLiveBeginTime() {
        return liveBeginTime;
    }

    public void setLiveBeginTime(String liveBeginTime) {
        this.liveBeginTime = liveBeginTime;
    }

    public String getLiveEndTime() {
        return liveEndTime;
    }

    public void setLiveEndTime(String liveEndTime) {
        this.liveEndTime = liveEndTime;
    }
}
