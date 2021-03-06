package com.example.aidong.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class DateUtils {
    /**
     * 日期类型 *
     */
    public static final String yyyyMMDD = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String HHmmss = "HH:mm:ss";
    public static final String hhmmss = "HH:mm:ss";
    public static final String hhmm = "HH:mm";
    public static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
    public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";
    public static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";
    public static final String WeiBo_ITEM_DATE_FORMAT = "EEE MMM d HH:mm:ss Z yyyy";

    private static SimpleDateFormat SecondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public static long compareLongTime(String time) {
        long sysSecond = System.currentTimeMillis();

        try {
            Date date = SecondFormat.parse(time);
//            Logger.i("time", "time = " + time + ", data time = " + date.getTime() + ", current = " + sysSecond);
            return date.getTime() - sysSecond;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static String dateToString(Date date, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date stringToDate(String dateStr, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    public static String calendarToStr(Calendar calendar1, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(calendar1.getTime());

    }


    public static long dateToLongTime(String createdAt) {
        Date date = parseDate(createdAt, yyyyMMddHHmmss);
        if (date != null)
            return date.getTime() / 1000;
        return 0;
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param type 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期字符串转换为Date类型
     *
     * @param dateStr 日期字符串
     * @param type    日期字符串格式
     * @return Date对象
     */
    public static Date parseDate(String dateStr, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type, Locale.US);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 得到年
     *
     * @param date Date对象
     * @return 年
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;

    }

    /**
     * 得到月
     *
     * @param date Date对象
     * @return 月
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }

    /**
     * 得到日
     *
     * @param date Date对象
     * @return 日
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 转换日期 将日期转为今天, 昨天, 前天, XXXX-XX-XX, ...
     *
     * @param time 时间
     * @return 当前日期转换为更容易理解的方式
     */
    public static String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "今天";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "昨天";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
            return "前天";
        } else if (time > todayStartTime + oneDay) { // future
            return "将来某一天";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }

    /**
     * 转换日期 转换为更为人性化的时间
     *
     * @param time 时间
     * @return
     */
    public static String translateDate(long time, long curTime) {
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance();    //今天
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1分钟前";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + "分钟前";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<String> getSevenDate() {
        List<String> dates = new ArrayList<String>();
        Date date = new Date();//取时间
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            calendar.add(calendar.DATE, i == 0 ? 0 : +1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
            date = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            dates.add(dateString);
        }
        return dates;
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<String> getDays(int start, int limit) {
        List<String> dates = new ArrayList<String>();
        Date date = new Date();//取时间
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < limit; i++) {
            calendar.add(calendar.DATE, i == 0 ? 0 : +1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
            date = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            dates.add(dateString);
        }
        return dates;
    }


    public static List<String> getCourseSevenDate() {
        List<String> dates = new ArrayList<String>();
        Date date = new Date();//取时间
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            calendar.add(calendar.DATE, i == 0 ? 0 : +1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
            date = calendar.getTime();
            String weekOfDate = getWeekOfDate(date);
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
            String dateString = formatter.format(date);
            if (i == 0) {
                dates.add(dateString + " (今天)");
            } else {
                dates.add(dateString + " " + weekOfDate);
            }
        }
        return dates;
    }


    public static List<String> getCourseSevenDate2() {
        List<String> dates = new ArrayList<String>();
        Date date = new Date();//取时间
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            calendar.add(calendar.DATE, i == 0 ? 0 : +1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
            date = calendar.getTime();
            String weekOfDate = getWeekOfDate2(date);
            SimpleDateFormat formatter = new SimpleDateFormat("MM.dd");
            String dateString = formatter.format(date);
            if (dateString.startsWith("0")) {
                dateString =   dateString.substring(1, dateString.length());
            }

            dates.add(weekOfDate + "\n" + dateString);

        }
        return dates;
    }


    public static List<String> getLimitDays(int start, int limit) {
        Logger.i("getLimitDays", "start days = " + start + ", limit days = " + limit);
        List<String> dates = new ArrayList<>();
        Date date = new Date();//取时间
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        for (int i = start; i < start + limit; i++) {
            calendar.add(calendar.DATE, i == start ? start : +1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
            date = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            dates.add(dateString);
        }
        return dates;
    }

    public static List<String> getLimitDays(int limitDays, String limit_period) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);

//        currentHour = 18;
//        currentMin= 10;
        try {
            int limitHour = Integer.parseInt(limit_period.substring(0, 2));
            int limitMinute = Integer.parseInt(limit_period.substring(3, 5));

            Logger.i("getLimitDays", "currentHour = " + currentHour + ", currentMin = " + currentMin +
                    "limitHour = " + limitHour + ", limitMinute = " + limitMinute);
            if (currentHour < limitHour) {
                return getLimitDays(1, limitDays);
            } else if (currentHour == limitHour && currentMin < limitMinute) {
                return getLimitDays(1, limitDays);
            } else {
                return getLimitDays(2, limitDays);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return getSevenDate();
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysCode = {"(周日)", "(周一)", "(周二)", "(周三)", "(周四)", "(周五)", "(周六)"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }


    public static String getWeekOfDate2(Date date) {
        String[] weekDaysCode = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }


    public static String parseTime(long msgTime) {
        String time;
        Date date = new Date(msgTime);
        try {
            time = dateToString(date, yyyyMMddHHmmss);
        } catch (Exception e) {
            e.printStackTrace();
            time = msgTime + "";
        }
        return time;
    }


    public static long getCountdown(String date, String parseType, long totalMilliseconds) {
        long countdown = 0;
        Date d = parseDate(date, parseType);
        if (d != null)
            countdown = totalMilliseconds - (System.currentTimeMillis() - d.getTime());
        long during = System.currentTimeMillis() - d.getTime();
        Logger.i("countdown", "countdown = " + countdown
                + ", totalMilliseconds = " + totalMilliseconds
                + ", date = " + date
                + ", during = " + during
                + ", d.getTime() = " + d.getTime()
                + ", currentTimeMillis = " + System.currentTimeMillis())
        ;
        if (countdown < 0)
            countdown = 0;
        // if(totalMilliseconds>)

        return countdown;
    }

    public static long getCountdown(String date, long totalMilliseconds) {
        return getCountdown(date, yyyyMMddHHmmss, totalMilliseconds);
    }

    public static String duringToSecond(double time) {
//        int hour = (int) time / 60 * 60;
        int minute = ((int) time / 60);
        int second = (int) time % 60;
//        int million = (int) ((time - (int) time) * 1000);
        StringBuilder during = new StringBuilder();
        during.append(formatNumberZero(minute));
        during.append(":");
        during.append(formatNumberZero(second));


        Logger.i(" vidoe duringToSecond = " + during.toString());

        return during.toString();
    }

    private static String formatNumberZero(int num) {
        if (num > 9) {
            return num + "";
        } else {
            return "0" + num;
        }
    }

    public static boolean bigThanOneHour(String date) {
        if (TextUtils.isEmpty(date)) return false;
//        Date d = parseDate(date, yyyyMMddHHmm);
//        if (d != null) {
//            return d.getTime() - System.currentTimeMillis() > 3600 * 1000l;
//        } else {
//            return false;
//        }
        return false;
    }

    public static boolean started(String date) {
        Date d = parseDate(date, yyyyMMddHHmm);
        if (d != null) {
            return d.getTime() - System.currentTimeMillis() < 0;
        } else {
            return false;
        }
    }

    public static long getCounterDown(String startTime) {
        long countdown = 0;
        Date d = parseDate(startTime, yyyyMMddHHmm);
        if (d != null)
            countdown = d.getTime() - System.currentTimeMillis();


        if (countdown < 0)
            countdown = 0;
        return countdown;
    }

    public static String getCurrentTime() {
        return calendarToStr(Calendar.getInstance(), yyyyMMddHHmmss);
    }


    public static String getWeekByDateStr(String strDate) {
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(5, 7));
        int day = Integer.parseInt(strDate.substring(8, 10));

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);

        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);

        switch (weekIndex) {
            case 1:
                week = "(周一)";
                break;
            case 2:
                week = "(周二)";
                break;
            case 3:
                week = "(周三)";
                break;
            case 4:
                week = "(周四)";
                break;
            case 5:
                week = "(周五)";
                break;
            case 6:
                week = "(周六)";
                break;
            case 7:
                week = "(周日)";
                break;
        }
        return week;
    }


}
