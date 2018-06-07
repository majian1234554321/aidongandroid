package com.example.aidong.entity;

/**
 * 爱动圈点赞
 * Created by song on 2017/1/16.
 */
public class LikeBean {
    private String id;
    private String published_at;
    private Publisher publisher;

    public class Publisher{
        private String publisher_id;
        private String name;
        private String avatar;
        private String gender;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
