package com.leyuan.aidong.entity.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VideoDetail implements Parcelable {
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
    private byte isParsed;


    protected VideoDetail(Parcel in) {
        vId = in.readInt();
        cover = in.readString();
        video = in.readString();
        phase = in.readInt();
        videoName = in.readString();
        author = in.readString();
        during = in.readString();
        introduce = in.readString();
        likesCount = in.readInt();
        commentsCount = in.readString();
        updated_at = in.readString();
        contentId = in.readInt();
        isParsed = in.readByte();
    }

    public static final Creator<VideoDetail> CREATOR = new Creator<VideoDetail>() {
        @Override
        public VideoDetail createFromParcel(Parcel in) {
            return new VideoDetail(in);
        }

        @Override
        public VideoDetail[] newArray(int size) {
            return new VideoDetail[size];
        }
    };

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

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

    public boolean isParsed() {
        return isParsed > 0;
    }

    public void setIsParsed(boolean isParsed) {
        if (isParsed) {
            this.isParsed = 1;
        } else {
            this.isParsed = 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(vId);
        dest.writeString(cover);
        dest.writeString(video);
        dest.writeInt(phase);
        dest.writeString(videoName);
        dest.writeString(author);
        dest.writeString(during);
        dest.writeString(introduce);
        dest.writeInt(likesCount);
        dest.writeString(commentsCount);
        dest.writeString(updated_at);
        dest.writeInt(contentId);
        dest.writeByte(isParsed);
    }
}
