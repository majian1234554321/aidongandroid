package com.example.aidong.ui.mvp.model;


import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.DistrictBean;
import com.example.aidong .entity.data.CoachData;
import com.example.aidong .entity.data.CourseData;
import com.example.aidong .entity.data.VenuesData;
import com.example.aidong .entity.data.VenuesDetailData;

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
     * 获取场馆类型
     * @return
     */
    List<String> getGymTypes();


    /**
     * 获取场馆列表
     * @param subscriber 返回Subscriber
     * @param page 页码
     */
     void getVenues(Subscriber<VenuesData> subscriber, int page,String brand_id,
                    String landmark,String area,String gymTypes);

    void getSlefSupportVenues(Subscriber<VenuesData> subscriber, int page);

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
    void getCourses(Subscriber<CourseData> subscriber, String id,String day);

    /**
     * 预约场馆
     * @param subscriber 返回Subscriber
     * @param id 场馆id
     * @param date 预约时间
     * @param period 预约时段
     * @param name 用户名
     * @param mobile 电话
     */
    void appointVenues(Subscriber<BaseBean> subscriber,String id,String date,String period,
                       String name,String mobile);

    /**
     * 预约教练
     * @param subscriber 返回Subscriber
     * @param id 场馆id
     * @param coachId 教练id
     * @param date 预约时间
     * @param period 预约时段
     * @param name 用户名
     * @param mobile 电话
     */
    void appointCoach(Subscriber<BaseBean> subscriber,String id,String coachId,String date,
                      String period, String name,String mobile);

}
