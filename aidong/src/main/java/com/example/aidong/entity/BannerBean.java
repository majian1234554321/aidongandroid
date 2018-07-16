package com.example.aidong.entity;


import java.io.Serializable;

/**
 * Banner
 * Created by song on 2016/8/30.
 */
public class BannerBean implements Serializable {
    private String title;       //广告标题
    private String position;    //广告位置,#0-开机广告 1-首页广告位 2-弹出广告位
    private String type;        //广告类型,#10-内嵌网页 11-外部网页 20-场馆 21-营养品 22-课程 23-活动

    public String name ;
                                // gym: 20,nutrition: 21,course: 22,campaign:23,equipment: 24,food: 25,ticket: 26,inner: 10, outer: 11
    private String image;       //广告图片
    private String link;        //关联信息当类型为网页时-关联信息为网址 当类型为场馆|营养品|课程|活动时为关联ID

    public String campaign_detail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "title='" + title + '\'' +
                ", position='" + position + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
