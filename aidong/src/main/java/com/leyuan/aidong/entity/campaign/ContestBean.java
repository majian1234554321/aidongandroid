package com.leyuan.aidong.entity.campaign;

import java.util.ArrayList;

/**
 * Created by user on 2018/2/6.
 */
public class ContestBean {

    String id;//: 赛事编号,
    String name;//: "赛事名称",
    String background;//: "背景图",
    String theme_color;//: "主题色调",
    String video;//: "赛事介绍视频",
    String status;//: '赛事状态' #preliminary-海选 semi_finals-复赛 finals-决赛,
    ArrayList<String> divistion;//: ["赛区-1","赛区-2"]

}
