package com.leyuan.aidong.entity.course;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/20.
 */
public class CourseSeat {

    boolean need;// true/false,#是否需要选座
    int row;//  '行数',
    int col;//  '列数',
    ArrayList<String> removed;//  ["移除座位(行数_列数)"],
    ArrayList<String> reserved;//  ["保留座位(行数_列数)"],
    ArrayList<String> appointed;//  ["已预约座位(行数_列数)"]
}
