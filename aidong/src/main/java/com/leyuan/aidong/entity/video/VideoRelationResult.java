package com.leyuan.aidong.entity.video;

import com.leyuan.aidong.entity.GoodsBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/2.
 */
public class VideoRelationResult {

    VideoRelation relation;

    public VideoRelation getRelation() {
        return relation;
    }

    public void setRelation(VideoRelation relation) {
        this.relation = relation;
    }

    public class VideoRelation {

        ArrayList<VideoDetail> video;
        ArrayList<WatchOfficeCourseBean> course;
        ArrayList<GoodsBean> good;

        public ArrayList<VideoDetail> getVideo() {
            return video;
        }

        public void setVideo(ArrayList<VideoDetail> video) {
            this.video = video;
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
