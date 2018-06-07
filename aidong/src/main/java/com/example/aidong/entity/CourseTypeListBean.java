package com.example.aidong.entity;

import java.util.List;

/**
 * 课程分类列表
 * Created by song on 17/05/18.
 */
public class CourseTypeListBean {

    private List<CategoryBean> couses;

    public List<CategoryBean> getCouses() {
        return couses;
    }

    public void setCouses(List<CategoryBean> couses) {
        this.couses = couses;
    }
}
