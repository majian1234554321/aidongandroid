package com.leyuan.lovesport.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.leyuan.lovesport.common.Constant;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
	public static final String TAKEPHOTO_SDPATH = Constants.FILE_FOLDER + "/"
			+ "photo" + "/";
	public static void setListViewHeightBasedOnChildren(ListView listView,
			int attHeight) {
		ListAdapter listAdapter = listView.getAdapter();
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
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))
				+ attHeight;
		listView.setLayoutParams(params);
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
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
	
	public static void setListViewHeightBasedOnChildren(final ListView listView, final Handler handler) {
		final ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int totalHeight = 0;
				for (int i = 0; i < listAdapter.getCount(); i++) {
					View listItem = listAdapter.getView(i, null, listView);
					listItem.measure(0, 0);
					totalHeight += listItem.getMeasuredHeight();
				}

				final ViewGroup.LayoutParams params = listView.getLayoutParams();
				params.height = totalHeight
						+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						listView.setLayoutParams(params);
					}
				});
			}
		}).start();

		
		
	}

	/**
	 * 手机号正�?
	 * 
	 * @param mobiles
	 *            手机�?
	 * @return true:是手机号 false:不是手机�?
	 */
	public static boolean isMobileNO(String mobiles) {
		
		if (mobiles.length() == 11) {
			//根据需求临时判断
			return true;
		}
		
		Pattern p = Pattern.compile("^(1[3,4,5,8,7])\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * E-mail正则
	 * 
	 * @param email
	 *            邮箱
	 * @return true:�? false:不是
	 */
	public static boolean isEmail(String email) {
		String str = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	// 使用BitmapFactory.Options的inSampleSize参数来缩�?
		public static Bitmap resizeImage(String path, int width, int height) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;// 不加载bitmap到内存中
			BitmapFactory.decodeFile(path, options);
			int outWidth = options.outWidth;
			int outHeight = options.outHeight;
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			options.inSampleSize = 3;
			if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
				int sampleSize = (outWidth / width + outHeight / height) / 2;
				options.inSampleSize = sampleSize;
			}
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(path, options);
		}

	public static File getTakePhotoFileFolder() {
		File dir = null;
		if (SDUtil.isExsitSDCard()) {
			dir = SDUtil.getSDPath(TAKEPHOTO_SDPATH);
			if (dir == null) {
				return null;
			} else if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return dir;
	}
	public static File getTakePhotoFile(File folder, String FileName) {
		File f = new File(folder, FileName + ".jpg");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return f;
	}
	
	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	
	/**
	 * 显示用户权限图片
	 * 
	 * @param tags
	 * @param vipView
	 * @param coachView
	 */
	public static void setUserTagView(ArrayList<Integer> tags, ImageView vipView,
			ImageView coachView, ImageView officalView) {
		if(tags == null)
			return;
		if (vipView != null) {
			if (tags.get(Constant.ID_VIP) > 0) {
				vipView.setVisibility(View.VISIBLE);
			} else {
				vipView.setVisibility(View.GONE);
			}
		}

		if (officalView != null) {
			if (tags.get(Constant.ID_OFFICAL) > 0) {
				officalView.setVisibility(View.VISIBLE);
			} else {
				officalView.setVisibility(View.GONE);
			}
		}

		if (coachView != null) {
			if (tags.get(Constant.ID_COACH) > 0) {
				coachView.setVisibility(View.VISIBLE);
			} else {
				coachView.setVisibility(View.GONE);
			}
		}

	}
	
	public static String unixTime(String time)
	{
		
		String date = "";
		try {
			date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(Long.valueOf(time) * 1000));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			date= time;
		}
		return date;
	}
	
	public static String convertToMoney(String money) {
		try
		{
			NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CHINA);
			return formatter.format(Double.valueOf(money));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	public static String getIMEI(Context context){
		String imei = ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE))
				.getDeviceId();
		return imei;
	}
	
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@SuppressLint("NewApi") 
	public static void setLayoutBackgroud(ViewGroup layout, Drawable drawable){
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			 layout.setBackground(drawable);
		    } else {
		    	layout.setBackgroundDrawable(drawable);
		    }
	}
	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

}
