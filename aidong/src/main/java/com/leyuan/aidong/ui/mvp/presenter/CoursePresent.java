package com.leyuan.aidong.ui.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 课程列表
 * Created by song on 2016/8/13.
 */
public interface CoursePresent {

    /**
     * 获取课程分类
     */
    void getCategory();

    /**
     * 获取商圈信息
     */
    void getBusinessCircle();

    /**
     * 第一次加载数据
     */
    void commendLoadData(SwitcherLayout switcherLayout,String day,String category, String landmark);

    /**
     * 下拉刷新
     * @param category 课程类型
     * @param day 从当前开始向后天数
     */
    void pullToRefreshData(String day,String category, String landmark);


    /**
     * 上拉加载更多
     * @param recyclerView 执行刷新的RecyclerView
     * @param pageSize 每页刷新的数据量
     * @param category 课程类型
     * @param day 从当前开始向后天数
     * @param page 页码
     */
    void requestMoreData(RecyclerView recyclerView, int pageSize, String day,String category,
                         String landmark, int page);

    /**
     * 获取课程详情
     * @param id 课程id
     */
    void getCourseDetail(SwitcherLayout switcherLayout,String id);

    /**
     * 购买课程
     * @param id 活动id
     * @param couponId 优惠券id
     * @param integral 积分
     * @param payType 支付类型
     * @param contactName 联系人
     * @param contactMobile 联系人电话
     */
    void buyCourse(String id, String couponId, String integral, String payType, String contactName,
                   String contactMobile, PayInterface.PayListener listener);

    /**
     * 添加关注
     * @param id
     */
    void addFollow(String id);

    /**
     * 取消关注
     * @param id
     */
    void cancelFollow(String id);

    /**
     * 获取指定课程可用优惠券
     * @param id
     */
    void getSpecifyCourseCoupon(String id);

    void getScrollDate(String day,String category);
}
