package com.leyuan.aidong.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static final String TAKEPHOTO_SDPATH = Constant.FILE_FOLDER + "/"
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
		if (SDCardUtil.isExsitSDCard()) {
			dir = SDCardUtil.getSDPath(TAKEPHOTO_SDPATH);
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

	public static List<Camera.Size> getResolutionList(Camera camera)
	{
		Camera.Parameters parameters = camera.getParameters();
		List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
		return previewSizes;
	}

	public static class ResolutionComparator implements Comparator<Camera.Size> {

		@Override
		public int compare(Size lhs, Size rhs) {
			if(lhs.height!=rhs.height)
				return lhs.height-rhs.height;
			else
				return lhs.width-rhs.width;
		}

	}

	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
	private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座",
			"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
	/**
	 * 通过生日计算星座
	 * @param month 1 - 12
	 * @param day
	 * @return
	 */
	public static String getConstellation(int month, int day) {
		return day < dayArr[month - 1] ? constellationArr[month - 1]
				: constellationArr[month];
	}

	/**
	 * 通过给出体重和身高计算身体质量指数
	 * @param weight 体重（单位是千克）
	 * @param height 身高（单位是米）
	 */
	public static float calBMI(float weight,float height) {
		return weight / (height * height);
	}

	public static String getData(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		// 当前日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = format.format(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			long old = date.getTime();
			long currTime = System.currentTimeMillis();
			long spanTime = currTime - old;
			// 传入日期
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String timeDate = timeFormat.format(date);
			//当前日期的前一天
			String beforeDate = getSpecifiedDayBefore();
			if(spanTime < 60000  * 60 ) { // 小于1h
				return "刚刚";
			} else if(spanTime < 60000  * 60 * 24) { // xx分钟前
				return spanTime / (60000*60) + "小时前";
			} /*else if (currDate.equals(timeDate)) {
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				return "今天 " + sdf2.format(date);
			} */else if (timeDate.equals(beforeDate)) {
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				return "昨天" + sdf2.format(date);
			} else
				/*if ((3600000*24)*2 <spanTime) */
			{
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				return sdf2.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}


	/**
	 * 获得当前日期的前一天
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getSpecifiedDayBefore() {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		try {
			Date endDate = dft.parse(dft.format(date.getTime()));
			return dft.format(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dft.format(new Date());
	}



	private final static int TIME_NUMBERS = 60;

	/**将总秒数转换为时分秒表达形式
	* @param seconds 任意秒数
	* @return %s分%s秒
	*/
	public static String formatTime(long seconds) {
		long mm = seconds / TIME_NUMBERS ;
		long ss = seconds < TIME_NUMBERS ? seconds : seconds % TIME_NUMBERS;
		return (mm == 0 ? "00" : (mm < 10 ? "0" + mm : mm)) + ":"
				+ (ss == 0 ? "00" : (ss < 10 ? "0" + ss : ss) );
	}

	public static Bitmap rotateBitmap(Bitmap bm, final int orientationDegree) {
		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
		float targetX, targetY;
		if (orientationDegree == 90) {
			targetX = bm.getHeight();
			targetY = 0;
		} else {
			targetX = bm.getHeight();
			targetY = bm.getWidth();
		}

		final float[] values = new float[9];
		m.getValues(values);

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		m.postTranslate(targetX - x1, targetY - y1);

		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);

		return bm1;
	}


	public static Bitmap getVideoThumbnail(String videoPath) {
		MediaMetadataRetriever media =new MediaMetadataRetriever();
		media.setDataSource(videoPath);
		Bitmap bitmap = media.getFrameAtTime();
		return bitmap;
	}

}
