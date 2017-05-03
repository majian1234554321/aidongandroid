package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CourseDetailData;
import com.leyuan.aidong.entity.data.AppointmentDetailData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.CourseVideoData;
import com.leyuan.aidong.entity.data.PayOrderData;

import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public interface CourseModel {

    /**
     * 获取课程分类信息
     * @return 课程分类
     */
    List<CategoryBean> getCategory();

    /**
     * 获取商圈信息
     * @return 商圈信息
     */
    List<DistrictBean> getBusinessCircle();

    /**
     * 获取课程列表
     * @param subscriber Subscriber
     * @param cat 类型
     * @param day 从当前开始向后天数
     * @param page 页码
     */
    void getCourses(Subscriber<CourseData> subscriber, String day, String cat,String landmark, int page);

    /**
     * 获取课程详情
     * @param subscriber Subscriber
     * @param id 课程id
     */
    void getCourseDetail(Subscriber<CourseDetailData> subscriber, String id);

    /**
     * 购买课程
     * @param subscriber Subscriber
     * @param id 课程id
     * @param couponId 优惠券id
     * @param integral 使用积分数量
     * @param payType   支付类型
     * @param contactName 用户名
     * @param contactMobile 手机号
     */
    void buyCourse(Subscriber<PayOrderData> subscriber,String id,String couponId,String integral,
                   String payType,String contactName,String contactMobile,String isVip);

    /**
     * 获取课程预约详情
     * @param subscriber Subscriber
     * @param id 课程code
     */
    void getCourseAppointDetail(Subscriber<AppointmentDetailData> subscriber, String id);

    /**
     * 获取课程视频
     * @param subscriber
     * @param relate relation-加载最多五个视频 all-所有
     * @param id
     * @param page
     */
    void getCourseVideo(Subscriber<CourseVideoData> subscriber,String relate,String id, int page,String videoId);
}
