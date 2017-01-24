package com.leyuan.aidong.entity;

/**
 * 活动实体
 * Created by song on 2016/8/18.
 */
public class CampaignBean {
    private String id;
    private String name;
    private String cover;
    private String start_time;
    private String landmart;
    
    public String getId() {
        return id;
    }

    public void setId(String campaign_id) {
        this.id = campaign_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getLandmart() {
        return landmart;
    }

    public void setLandmart(String landmart) {
        this.landmart = landmart;
    }

    @Override
    public String toString() {
        return "CampaignBean{" +
                "campaign_id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", start_time='" + start_time + '\'' +
                ", landmart='" + landmart + '\'' +
                '}';
    }
}
