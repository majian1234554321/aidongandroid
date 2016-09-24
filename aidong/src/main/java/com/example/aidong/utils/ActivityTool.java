package com.example.aidong.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.aidong.ui.BaseApp;
import com.example.aidong.R;
import com.example.aidong.ui.activity.mine.MyShowActivityNew;
import com.example.aidong.utils.common.Constant;
import com.example.aidong.utils.interfaces.TabChoniceInterface;
import com.example.aidong.entity.model.UserCoach;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ActivityTool implements TabChoniceInterface {

	private static final String TAG = "ActivityTool";
	public static final int CODE_MAN = 0;
	public static final int CODE_WOMEN = 1;
	private static final double EARTH_RADIUS = 6378137.0;

	public static double getDistance(double longitude1, double latitude1,
			double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	// private static final ActivityClass[] TAB_CLASS = {
	// new ActivityClass(R.string.tabNear, R.drawable.tab_near_selector,
	// TabNearActivity.class, 0, null),
	// new ActivityClass(R.string.tabFound, R.drawable.tab_found_selector,
	// TabFoundActivity.class, 0, null),
	// new ActivityClass(R.string.tabMsg, R.drawable.tab_msg_selector,
	// TabMsgActivity.class, 0, null),
	// new ActivityClass(R.string.tabContactor,
	// R.drawable.tab_contactor_selector,
	// TabContactorActivity.class, 0, null),
	// new ActivityClass(R.string.tabMine, R.drawable.tab_mine_selector,
	// TabMineActivity.class, 0, null) };

	public static final ActivityClass[] RECOMMEND_CLASS = {
			new ActivityClass(R.string.RecommendWeixin, R.drawable.weixin,
					null, 0, null),
			new ActivityClass(R.string.RecommendWeibo, R.drawable.weibo, null,
					0, null),
			new ActivityClass(R.string.RecommendQQ, R.drawable.qq, null, 0,
					null),
			new ActivityClass(R.string.RecommendContactor,
					R.drawable.contactor, null, 0, null)
	};

	private static final HashMap<Integer, Gender> GENDER_RES = new HashMap<Integer, Gender>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8310897385428312945L;
		{
			put(0, new Gender(CODE_MAN, R.drawable.nan, R.string.men));
			put(1, new Gender(CODE_WOMEN, R.drawable.woman, R.string.women));
		}
	};

	public static final int INDIC_IMAGE_ARRAY[] = { R.drawable.place_holder_logo,
			R.drawable.place_holder_logo, R.drawable.place_holder_logo,
			 };
	 public static ActivityClass getRecommendActivityClass(int index) {
	 return RECOMMEND_CLASS[index];
	 }


	public static Gender getGender(int code) {
		if (GENDER_RES.get(code) == null) {
			return new Gender(CODE_MAN, R.drawable.man, R.string.men);
		}
		return GENDER_RES.get(code);
	}

	public static Gender getGender(Context context, String gender) {
		Gender g = new Gender();
		if (gender.equals(context.getResources().getString(R.string.men))) {
			g.code = 0;
		} else {
			g.code = 1;
		}
		return g;
	}

	public static class ActivityClass {
		public int titleRes;
		public int subTitleRes;
		public int picRes;
		public String action;
		public Class<? extends Activity> activityClass;

		public ActivityClass(int titleRes, int picRes,
				Class<? extends Activity> activityClass,
				int subTitleRes, String action) {
			this.titleRes = titleRes;
			this.picRes = picRes;
			this.subTitleRes = subTitleRes;
			this.activityClass = activityClass;
			this.action = action;
		}
	}

	public static class Gender {
		public int code;
		public int drawableRes;
		public int genderString;

		public Gender() {

		}

		public Gender(int code, int drawableRes, int genderString) {
			this.code = code;
			this.drawableRes = drawableRes;
			this.genderString = genderString;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static int getDisplayScreenResolution(Context context) {
		int ver = Build.VERSION.SDK_INT;
		int screen_w = 0;
		int screen_h = 0;
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		display.getMetrics(dm);

		float density = dm.density;
		int densityDPI = dm.densityDpi;

		Log.e(TAG, "density = " + density + " densityDPI = " + densityDPI);

		screen_w = dm.widthPixels;

		Log.e(TAG, "Run1 first get resolution:" + dm.widthPixels + " * "
				+ dm.heightPixels + ", ver " + ver);
		if (ver < 13) {
			screen_h = dm.heightPixels;
		} else if (ver == 13) {
			try {
				Method mt = display.getClass().getMethod("getRealHeight");
				screen_h = (Integer) mt.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ver > 13) {
			try {
				Method mt = display.getClass().getMethod("getRawHeight");
				screen_h = (Integer) mt.invoke(display);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Log.e(TAG, "Run2 Calibration  resolution:" + screen_w + " * "
				+ screen_h);

		return 0;
	}

	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.NO_STR;
		}
	}

	public static String getAppName(Context context, int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = context.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}


	public static String[] getTimeCalShow(Context c, long otherTime) {
		final String timeShow = c.getResources().getString(
				R.string.unknown_time);
		final String s_year = c.getResources().getString(R.string.s_year);
		final String s_month = c.getResources().getString(R.string.s_month);
		final String s_day = c.getResources().getString(R.string.s_day);
		final String s_hour = c.getResources().getString(R.string.s_hour);
		final String s_minute = c.getResources().getString(R.string.s_minute);
		final String s_second = c.getResources().getString(R.string.s_second);
		String timeString[] = new String[3];
		// 获取手机年月日时分秒
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		Log.e(TAG, "year = " + year);
		int month = cal.get(Calendar.MONTH) + 1;
		int dayForYear = cal.get(Calendar.DAY_OF_YEAR); // 一年中的第几天
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		// 获取动态或其他发布的年月日时分秒
		// 因为传过来的是秒为单位，所以需要转换成毫秒
		Log.e(TAG, "otherTime = " + otherTime * 1000);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(otherTime * 1000);
		int otheryear = cal2.get(Calendar.YEAR);
		int othermonth = cal2.get(Calendar.MONTH);
		int otherdayForYear = cal2.get(Calendar.DAY_OF_YEAR); // 一年中的第几天
		int otherhour = cal2.get(Calendar.HOUR_OF_DAY);
		int otherminute = cal2.get(Calendar.MINUTE);
		int othersecond = cal2.get(Calendar.SECOND);
		Log.e(TAG, "otheryear = " + otheryear);
		timeString[0] = String.valueOf(othermonth + 1)
				+ c.getResources().getString(R.string.month);
		timeString[1] = String.valueOf(cal2.get(Calendar.DAY_OF_MONTH));
		if (year - otheryear > 0) {
			timeString[2] = String.valueOf(year - otheryear) + s_year;
			return timeString;
		}
		if (month - othermonth > 0) {
			timeString[2] = String.valueOf(month - othermonth) + s_month;
			return timeString;
		}
		if (dayForYear - otherdayForYear > 0) {
			timeString[2] = String.valueOf(dayForYear - otherdayForYear)
					+ s_day;
			return timeString;
		}
		if (hour - otherhour > 0) {
			timeString[2] = String.valueOf(hour - otherhour) + s_hour;
			return timeString;
		}
		if (minute - otherminute > 0) {
			timeString[2] = String.valueOf(minute - otherminute) + s_minute;
			return timeString;
		}
		if (second - othersecond > 0) {
			timeString[2] = String.valueOf(second - othersecond) + s_second;
			return timeString;
		}
		return timeString;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		Log.e(TAG, "listAdapter = " + listAdapter);
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 手机号正则
	 * 
	 * @param mobiles
	 *            手机号
	 * @return true:是手机号 false:不是手机号
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(1[3,4,5,8])\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * E-mail正则
	 * 
	 * @param email
	 *            邮箱
	 * @return true:是 false:不是
	 */
	public static boolean isEmail(String email) {
		String str = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 数字正则表达
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static void startShowActivity(Context context, UserCoach user) {
		ArrayList<Integer> tags = user.getTags();
		Intent i = new Intent();
		if (BaseApp.mInstance.isLogin() && BaseApp.mInstance.getUser() != null) {
			if (user.getMxid() == BaseApp.mInstance.getUser().getMxid()) {
				if(tags.get(Constant.ID_COACH) > 0){
//					i.setClass(context, PtMyshowActivity.class);
				}else{
//					i.setClass(context, TabMineMyShowActivity.class);
//					i.setClass(context, MyShowActivityNew.class);
				}
				
			} else {
				if (tags.get(Constant.ID_OFFICAL) > 0) {
//					i.setClass(context, MyShowVenuesActivity.class);
				} else if (tags.get(Constant.ID_COACH) > 0) {
//					i.setClass(context, PtMyshowActivity.class);
				} else {
//					i.setClass(context, MyShowActivityNew.class);
				}
			}
		} else {
			if (tags.get(Constant.ID_OFFICAL) > 0) {
//				i.setClass(context, MyShowVenuesActivity.class);
			} else if (tags.get(Constant.ID_COACH) > 0) {
//				i.setClass(context, PtMyshowActivity.class);
			} else {
//				i.setClass(context, MyShowActivityNew.class);
			}
		}
		// i.putExtra(BUNDLE_USER, u);
		i.setClass(context, MyShowActivityNew.class);
		i.putExtra("user", user);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}
	
	
	
	
	
	
	
	
	public static void startShowByIdentity(Context context, UserCoach user) {
		
//
//		int identity = user.getIdentity();
//		Intent intent = new Intent();
//		if (identity == 0) {
//			intent.setClass(context, MyShowActivityNew.class);
//
//		}else if (identity == 1) {
//			intent.setClass(context, PtMyshowActivity.class);
//		} else if(identity == 2){
//			intent.setClass(context, MyShowVenuesActivity.class);
//			Venues venues = new Venues();
//			venues.setMxid(user.getMxid());
//			venues.setName(user.getName());
//			venues.setAvatar(user.getAvatar());
//			venues.setAddress(user.getAddress());
//			venues.setAuth(1);
//
//			intent.putExtra("venues", venues);
//		}
//		intent.putExtra("user", user);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		context.startActivity(intent);
	}
	public static ActivityClass[] getSearchActivityClassArray() {
		return SEARCH_CLASS;
	}
	private static final ActivityClass[] SEARCH_CLASS = {
//		new ActivityClass(R.string.the_official_service_no,
//				R.drawable.official_service_number, AddListActivity.class,
//				R.string.txt_official_get_more_service,
//				BaseActivity.ACTION_ADD_OFFICAL),
//		new ActivityClass(R.string.the_mx_group, R.drawable.mx_group,
//				AddListActivity.class, R.string.txt_find_people_to_play,BaseActivity.ACTION_ADD_GROUP),
				};
	public static ActivityClass getSearchActivityClass(int index) {
		return SEARCH_CLASS[index];
	}
	public static int getAddTitleStringRes(String action) {
		int res = ADD_TITLE_MAP.get(action);
		return res;
	}
	private static final HashMap<String, Integer> ADD_TITLE_MAP = new HashMap<String, Integer>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5222484583264980645L;
		{
//			put(BaseActivity.ACTION_ADD_FRIEND, R.string.add_friend);
//			put(BaseActivity.ACTION_ADD_FRIEND_BY_ECHAT, R.string.add_friend);
//			put(BaseActivity.ACTION_ADD_GROUP, R.string.add_Group);
//			put(BaseActivity.ACTION_ADD_OFFICAL, R.string.add_offical);

		}

	};
}
