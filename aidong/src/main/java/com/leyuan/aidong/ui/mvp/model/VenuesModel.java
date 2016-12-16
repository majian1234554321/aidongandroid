package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.data.CoachData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.data.VenuesDetailData;

import java.util.List;

import rx.Subscriber;

/**
 * 场馆
 * Created by Song on 2016/8/2.
 */
public interface VenuesModel {


    /**
     * 获取场馆品牌信息
     * @return 课程分类
     */
    List<CategoryBean> getGymBrand();

    /**
     * 获取商圈信息
     * @return 商圈信息
     */
    List<DistrictBean> getBusinessCircle();

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
     void getVenuesDetail(Subscriber<VenuesDetailData> subscribe, String id);

    /**
     * 获取场馆教练列表
     * @param subscriber 返回Subscriber
     * @param id  场馆id
     */
     void getCoaches(Subscriber<CoachData> subscriber,String id);

    /**
     * 获取场馆课程列表
     * @param subscriber 返回Subscriber
     * @param id  场馆id
     */
    void getCourses(Subscriber<CourseData> subscriber, String id);

}
