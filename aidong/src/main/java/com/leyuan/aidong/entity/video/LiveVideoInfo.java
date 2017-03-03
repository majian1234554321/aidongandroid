package com.leyuan.aidong.entity.video;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveVideoInfo implements Serializable {
    @SerializedName("id")
    private int liveId;

    @SerializedName("title")
    private String liveName;
    @SerializedName("author")
    private String liveAuthor;
    @SerializedName("cover")
    private String liveCover;
    @SerializedName("begin_time")
    private String liveBeginTime;

    @SerializedName("over_time")
    private String liveEndTime;

    @SerializedName("online")
    private int personCou;

    @SerializedName("path")
    private String livePath;

    @SerializedName("summary")
    private String liveContent;

    @SerializedName("chat_room_id")
    private String chat_room_id;
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

    public String getChat_room_id() {
        return chat_room_id;
    }

    public void setChat_room_id(String chat_room_id) {
        this.chat_room_id = chat_room_id;
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean equalDateSimple(LiveVideoInfo info) {
        if (TextUtils.equals(info.getDate(), this.getDate()))
            return true;
        return false;
    }

    private CharSequence getDate() {
        if (liveBeginTime == null)
            return null;
        return liveBeginTime.substring(0, 10);
    }
}
