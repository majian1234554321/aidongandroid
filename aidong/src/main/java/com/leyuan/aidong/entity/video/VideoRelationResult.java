package com.leyuan.aidong.entity.video;

import com.leyuan.aidong.entity.GoodsBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public class VideoRelationResult {

    VideoRelation relation;

    public class VideoRelation {

        ArrayList<VideoDetail> videos;
        ArrayList<WatchOfficeCourseBean> course;
        ArrayList<GoodsBean> good;

        public ArrayList<VideoDetail> getVideo() {
            return videos;
        }

        public void setVideo(ArrayList<VideoDetail> video) {
            this.videos = video;
        }

        public ArrayList<WatchOfficeCourseBean> getCourse() {
            return course;
        }

        public void setCourse(ArrayList<WatchOfficeCourseBean> course) {
            this.course = course;
        }

        public ArrayList<GoodsBean> getGood() {
            return good;
        }

        public void setGood(ArrayList<GoodsBean> good) {
            this.good = good;
        }
    }
}
