package com.leyuan.lovesport.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;

public class DateUtil {

	/** 比较给定时间与当前时间，ture代表给定时间大于当前时间  */
	public static boolean compareTime(String endTime) {
		String[] end = endTime.split("-");
		int[] end_time = new int[3];
		if (end.length >= 3) {
			for (int i = 0; i < end.length; i++) {
				end_time[i] = Integer.parseInt(end[i]);
			}
			Time now = new Time();
			now.setToNow();
			if (end_time[0] > now.year) {
				return true;
			} else if (end_time[0] == now.year) {
				if (end_time[1] > (now.month + 1)) {
					return true;
				} else if (end_time[1] == (now.month + 1)) {
					if (end_time[2] >= now.monthDay) {
						return true;
					} else
						return false;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;

	}
	
	/**
	 * 
	 * @param format 格式化字符串的时间格式，根据返回值自己定义
	 * @param date 描述时间的字符串
	 * @return 返回的是date与当前时间的比较结果：一年前 几月前 几小时前 几分钟前等；若出错放回null
	 */
	public static String formatStringDateToString(SimpleDateFormat format,String date){
		String time = null;
		try {
			Date created_date = format.parse(date);
			Date current_date = new Date();
			if (created_date.getYear() < current_date.getYear()) {
				time ="一年前";
			}else if(created_date.getMonth() < current_date.getMonth()){
				
				time =(current_date.getMonth() -created_date.getMonth())+"月前";
			}else if(created_date.getDate() < current_date.getDate()){
				
				time =(current_date.getDate() -created_date.getDate())+"天前";
			}else if(created_date.getHours() < current_date.getHours()){
				
				time =(current_date.getHours() -created_date.getHours())+"小时前";
			}else if (created_date.getMinutes() <current_date.getMinutes()) {
				time =(current_date.getMinutes() -created_date.getMinutes())+"分钟前";
			}else{
				time ="刚刚";
			}
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return time;
	}
	
	
	
	
}
