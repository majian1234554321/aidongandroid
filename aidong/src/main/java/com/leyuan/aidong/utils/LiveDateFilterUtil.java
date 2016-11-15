package com.leyuan.aidong.utils;

import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.entity.video.LiveVideoSoonInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LiveDateFilterUtil {

    private static SimpleDateFormat SecondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


    public static String compareDate(String yearMonthDay) {
        int cMonth = Calendar.getInstance().get(Calendar.MONTH);
        int cDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        try {
            Date date = dayFormat.parse(yearMonthDay);
            int month = date.getMonth();
            int day = date.getDate();

            if (month == cMonth) {
                if (cDay == day) {
                    return "今日直播";
                } else if ((day - cDay) == 1) {
                    return "明日直播";
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return yearMonthDay;
    }


    public ArrayList<LiveVideoSoonInfo> sortLiveVideo(ArrayList<LiveVideoInfo> lives) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        ArrayList<LiveVideoSoonInfo> soonInfos = new ArrayList<>();

        if (lives == null || lives.size() == 0) {
            return soonInfos;
        }

        LiveVideoSoonInfo firstSoonInfo = new LiveVideoSoonInfo();
        LiveVideoInfo firstLiveInfo = lives.get(0);

        try {
            int date = SecondFormat.parse(firstLiveInfo.getLiveBeginTime()).getDate();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 1; i < lives.size() - 1; i++) {

        }

        for (LiveVideoInfo info : lives) {
            if (info.getLiveBeginTime() != null) {
                try {
                    Date date = SecondFormat.parse(info.getLiveBeginTime());
                    date.getDate();

                    for (LiveVideoSoonInfo soonInfo : soonInfos) {

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return soonInfos;
    }

    public static String countDownTime(String liveBeginTime) {
        long sysSecond = System.currentTimeMillis();

        try {
            Date date = SecondFormat.parse(liveBeginTime);
            long dateSecond = date.getTime();

            Date diffDate = new Date(dateSecond - sysSecond);
            return hourFormat.format(diffDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return liveBeginTime;
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", h,m,s);
    }

    public static int compareTime(String time) {
        long sysSecond = System.currentTimeMillis();

        try {
            Date date = SecondFormat.parse(time);
            int diff = (int) ((date.getTime() - sysSecond) / 1000);

            return diff;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
