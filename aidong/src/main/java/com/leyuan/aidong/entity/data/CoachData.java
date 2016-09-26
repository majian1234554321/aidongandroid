package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.CoachBean;

import java.util.ArrayList;

/**
 * 教练
 * Created by song on 2016/8/30.
 */
public class CoachData {
    private ArrayList<CoachBean> coach;

    public ArrayList<CoachBean> getCoach() {
        return coach;
    }

    public void setCoach(ArrayList<CoachBean> coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "CoachData{" +
                "coach=" + coach +
                '}';
    }
}
