package com.example.aidong.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.example.aidong .ui.App;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
	// 使用BitmapFactory.Options的inSampleSize参数来缩放
	public Bitmap resizeImage(String path, int width, int height) {
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
	/**
	 * 

	 * @param width 要获取的图片的宽,单位为dp
	 * @param height 要获的图片的高，单位为dp
	 * @return 如果mode < 0 或 >5 ，抛出illegalArgumentException；如果imgpath 为null，返回null； 正常返回需要的图片路径
	 */
	public static String getImageUrl(Context context,String imgPath,int mode,int width,int height)
	{
		if (mode < 0 || mode >= 5) {
			throw new IllegalArgumentException("模型必须大于等于0，小于等于5");
		}
		if (imgPath == null) {
			return null;
		}
		if (width == 0 && height == 0) {
			return getUrlEqualWidth(context, imgPath);
		}
		
		
		return imgPath+"?imageView2/" +mode+"/w/" + Utils.dip2px(context, width) +"/h/" + Utils.dip2px(context, height);
	}
	/**
	 * 建议用这个
	 * @param imgPath 原图片url
	 * @param mode 模式 0 :限定缩略图的长边最多为width，短边最多为height，进行等比缩放，不裁剪,适合移动设备缩略图; 
	 * 模式 1 :限定缩略图的宽最少为<Width>，高最少为<Height>，进行等比缩放，居中裁剪; 
	 * 模式2 :限定缩略图的宽最多为<Width>，高最多为<Height>，进行等比缩放，不裁剪；
	 * 模式3 :限定缩略图的宽最少为<Width>，高最少为<Height>，进行等比缩放，不裁剪。
	 * 模式4 :限定缩略图的长边最少为<width>，短边最少为<height>，进行等比缩放，不裁剪，适合在手持设备做图片的全屏查看，某一个边可能会超出屏幕。
	 * 模式5 :限定缩略图的长边最少为<width>，短边最少为<height>，进行等比缩放，居中裁剪
	 * @param width 单位为px
	 * @param height 单位为px
	 * @return 如果mode < 0 或 >5 ，抛出illegalArgumentException；如果imgpath 为null，返回null； 正常返回需要的图片路径
	 */
	public static String getImageUrl(String imgPath,int mode,int width,int height)
	{
		if (mode < 0 || mode >= 5) {
			throw new IllegalArgumentException("模型必须大于等于0，小于等于5");
		}
		if (imgPath == null) {
			return null;
		}
		if (width == 0 && height == 0) {
			return getUrlEqualWidth(App.mInstance, imgPath);
		}
		
		
		return imgPath+"?imageView2/" +mode+"/w/" + width +"/h/" +  height;
	}
	
	/**
	 * @param imgPath 原图片url
	 * @param mode 模式
	 * @param width 要获取的图片的宽 
	 * @return
	 */
	public static String getImageUrlByWidth(String imgPath,int mode,int width)
	{
		if (mode < 0 || mode >= 5) {
			throw new IllegalArgumentException("模型必须大于等于0，小于等于5");
		}
		if (imgPath == null) {
			return null;
		}
		if (width == 0 ) {
			return getUrlEqualWidth(App.mInstance, imgPath);
		}
		
		
		return imgPath+"?imageView2/" +mode+"/w/" + width ;
	}
	
	/**
	 * @param imgPath 原图片url
	 * @param mode 模式
	 * @param height 高
	 * @return
	 */
	public static String getImageUrlByHeight(String imgPath,int mode,int height)
	{
		if (mode < 0 || mode >= 5) {
			throw new IllegalArgumentException("模型必须大于等于0，小于等于5");
		}
		if (imgPath == null) {
			return null;
		}
		if ( height == 0) {
			return getUrlEqualWidth(App.mInstance, imgPath);
		}
		
		
		return imgPath+"?imageView2/" +mode+"/h/" + height;
	}
	
	/**
	 * 获取一张图片，这张图片的宽高都等于屏幕宽度
	 * @param imgPath 图片原始路径
	 */
	public static String getUrlEqualWidth(Context context,String imgPath){
		
		int width = Utils.getScreenWidth(context);
	
		
		return imgPath+"?imageView2/" +0+"/w/" + width +"/h/" +  width;
		
	}
	
	
	
	/**
	 * 获取一张图片，这张图片的宽高都等于屏幕宽度的4分之1
	 * @param imgPath 图片原始路径
	 */
	public static String getUrlQuarterWidth(Context context,String imgPath){
		
		int width = Utils.getScreenWidth(context) / 4;
	
		
		return imgPath+"?imageView2/" +0+"/w/" + width +"/h/" +  width;
		
	}
	/**
	 * 获取一张图片，这张图片的宽高都等于屏幕宽度的一半
	 * @param imgPath 图片原始路径
	 */
	public static String getUrlHalfWidth(Context context,String imgPath){
		
		int width = Utils.getScreenWidth(context) / 2;
	
		
		return imgPath+"?imageView2/" +0+"/w/" + width +"/h/" +  width;
		
	}
	
	/**
	 * 获取一张图片，这张图片的宽高都等于屏幕的宽高,注:模式为4
	 * @param imgPath 图片原始路径
	 */
	public static String getUrlWindow(Context context,String imgPath){
		
		int width = Utils.getScreenWidth(context);
		int heigh =  Utils.getScreenHeight(context);
	
		
		return imgPath+"?imageView2/" +4+"/w/" + width +"/h/" +  heigh;
		
	}
	
	/**
	 * 获取一张图片，这张图片的宽高都等于给定的dp值
	 * @param imgPath
	 * @param dp 
	 * @return
	 */
	public static String getUrlByDp(Context context,String imgPath,int dp)
	{
		
		if (imgPath == null) {
			return null;
		}
		if (dp == 0) {
			return getUrlEqualWidth(context, imgPath);
		}
		
		
		return imgPath+"?imageView2/" +0+"/w/" + Utils.dip2px(context, dp) +"/h/" + Utils.dip2px(context, dp);
	}

	public static int[] getImageWidthAndHeight(String path){
		BitmapFactory.Options options = new BitmapFactory.Options();

		/**
		 * 最关键在此，把options.inJustDecodeBounds = true;
		 * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
		 */
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
		/**
		 *options.outHeight为原始图片的高
		 */
		return new int[]{options.outWidth,options.outHeight};
	}

	public static byte[] compressFile(String path) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
		return outputStream.toByteArray();
	}


	public static void saveImg(Context context,ImageView touchImageView, String imgUrl) {
		final String filePath = Constant.DEFAULT_SAVE_IMAGE_PATH
				+ getFileName(imgUrl);

		Drawable drawable = touchImageView.getDrawable();
		if (drawable != null && drawable instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			try {
				ImageUtil.saveImageToSD(context, filePath, bitmap, 100);
				ToastGlobal.showLong("保存成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private static String getFileName(String imgUrl) {
		int index = imgUrl.lastIndexOf('/') + 1;
		if (index == -1) {
			return System.currentTimeMillis() + ".jpeg";
		}
		return imgUrl.substring(index);
	}

	/**
	 * 写图片文件到SD卡
	 * @throws IOException
	 */
	public static void saveImageToSD(Context ctx, String filePath,
									 Bitmap bitmap, int quality) throws IOException {
		if (bitmap != null) {
			File file = new File(filePath.substring(0,
					filePath.lastIndexOf(File.separator)));
			if (!file.exists()) {
				file.mkdirs();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
			bitmap.compress(Bitmap.CompressFormat.PNG, quality, bos);
			bos.flush();
			bos.close();
			if (ctx != null) {
				scanPhoto(ctx, filePath);
			}
		}
	}


	private static void scanPhoto(Context ctx, String imgFileName) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File file = new File(imgFileName);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		ctx.sendBroadcast(mediaScanIntent);
	}
}
