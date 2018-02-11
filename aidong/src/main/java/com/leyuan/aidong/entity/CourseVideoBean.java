package com.leyuan.aidong.entity;

import java.io.Serializable;

/**
 * 课程视频
 * Created by song on 2017/4/26.
 */
public class CourseVideoBean  implements Serializable{

    private String id;
    private String cat_id;
    private String name;
    private String cover;
    private String file;
    private String during;
    private String introduce;
    private String type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTypeName() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
