package com.leyuan.aidong.entity.course;

import com.leyuan.aidong.entity.CoachBean;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseBeanNew {
    String id;// 课程编号,
    String name;// "课程名",
    String class_time;//  "上课时间 - 下课时间"，
    ArrayList<String> tag;//  ["标签"],
    String strength;// 强度,
    String coach_name;// "教练名",
    String coach_avatar;// "教练头像",
    int reservable;// 　是否可以预约,#0-否　１-是
    String reserve_time;// "预约时间",
    boolean has_queued;// 是否已经预约,#true是　false-否
    boolean has_appointed;// 是否已经预约,#true是　false-否


    //详情
    CoachBean coach;
    double price;//"价格

    double member_price;// "会员价格",

    Store store;//门店

    String introduce;//课程详情
//    boolean reservable;// 是否需要预约，
//    String reserve_time;//开始预约时间,
   CourseSeat seat;

}
