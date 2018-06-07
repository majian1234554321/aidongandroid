package com.example.aidong.entity.data;

import com.example.aidong .entity.CourseVideoBean;

import java.util.List;

/**
 * 课程视频
 * Created by song on 2017/4/26.
 */
public class CourseVideoData {

    private String title;
    private List<CourseVideoBean> videos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CourseVideoBean> getVideos() {
        return videos;
    }

    public void setVideos(List<CourseVideoBean> videos) {
        this.videos = videos;
    }
}
