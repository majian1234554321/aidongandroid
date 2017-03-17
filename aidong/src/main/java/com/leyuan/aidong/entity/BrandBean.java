package com.leyuan.aidong.entity;

/**
 * 品牌详情实体
 * Created by song on 2016/8/18.
 */
public class BrandBean {
    private String id;
    private String logo;
    private String name;
    String gyms_count;

    public String getGyms_count() {
        return gyms_count;
    }

    public void setGyms_count(String gyms_count) {
        this.gyms_count = gyms_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
