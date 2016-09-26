package com.leyuan.aidong.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.leyuan.aidong.ui.BaseApp;

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
			return getUrlEqualWidth(BaseApp.mInstance, imgPath);
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
			return getUrlEqualWidth(BaseApp.mInstance, imgPath);
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
			return getUrlEqualWidth(BaseApp.mInstance, imgPath);
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
	

}
