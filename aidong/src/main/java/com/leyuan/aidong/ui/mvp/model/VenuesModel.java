package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.data.CoachData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.data.VenuesDetailData;

import rx.Subscriber;

/**
 * 场馆
 * Created by Song on 2016/8/2.
 */
public interface VenuesModel {

    /**
     * 获取场馆列表
     * @param subscriber 返回Subscriber
     * @param page 页码
     */
     void getVenues(Subscriber<VenuesData> subscriber, int page);

    /**
     * 获取场馆详情
     * @param subscribe 返回Subscriber
     * @param id 场馆id
     */
     void getVenuesDetail(Subscriber<VenuesDetailData> subscribe, int id);

    /**
     * 获取场馆教练列表
     * @param subscriber 返回Subscriber
     * @param id  场馆id
     */
     void getCoaches(Subscriber<CoachData> subscriber,int id);

    /**
     * 获取场馆课程列表
     * @param subscriber 返回Subscriber
     * @param id  场馆id
     */
    void getCourses(Subscriber<CourseData> subscriber, int id);

}
