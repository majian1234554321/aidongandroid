package com.leyuan.support.entity;

/**
 * Banner
 * Created by song on 2016/8/30.
 */
public class BannerBean {

    private String id;
    private String image;
    private String recommend_type;
    private String url;
    private String sort;
    private String site;
    private String app_type;
    private String content;
    private String remark;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecommend_type() {
        return recommend_type;
    }

    public void setRecommend_type(String recommend_type) {
        this.recommend_type = recommend_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", recommend_type='" + recommend_type + '\'' +
                ", url='" + url + '\'' +
                ", sort='" + sort + '\'' +
                ", site='" + site + '\'' +
                ", app_type='" + app_type + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
