package com.leyuan.aidong.entity.campaign;

import com.leyuan.aidong.entity.CoordinateBean;

/**
 * Created by user on 2018/2/23.
 */
public class ContestScheduleBean {


    public String id;// 编号,
    public String class_date;//"时间"
    public String class_time;//"时间"
    public String store_name;//场馆,
    public String address;// "地址",
    public int place; //名额
    public String status; //
    public String score;
    public boolean appointed;// 是否已报名
    public CoordinateBean coordinate;
    public boolean expired;



}
