package com.leyuan.aidong.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 爱动圈评论
 * Created by song on 2017/1/14.
 */
public class CommentBean {
    @SerializedName("id")
    private String id;
    @SerializedName("content")
    private String content;
    @SerializedName("created_at")
    private String published_at;

    @SerializedName("publisher")
    private Publisher publisher;

    public class Publisher {
        @SerializedName("publisher_id")
        private String publisher_id;

        @SerializedName("name")
        private String name;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("gender")
        private String gender;
        @SerializedName("id")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPublisher_id() {
            return publisher_id;
        }

        public void setPublisher_id(String publisher_id) {
            this.publisher_id = publisher_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }


    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPublishedAt() {
        return published_at;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedAt(String published_at) {
        this.published_at = published_at;
    }
}
