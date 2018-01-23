package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.entity.course.CourseBeanNew;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/22.
 */
public class HomeData {
    ArrayList<CourseBeanNew> course;
    ArrayList<CampaignBean>  campaign;
    ArrayList<CoachBean> coach;


    public ArrayList<CourseBeanNew> getCourse() {
        return course;
    }

    public ArrayList<CampaignBean> getCampaign() {
        return campaign;
    }

    public ArrayList<CoachBean> getCoach() {
        return coach;
    }
}
