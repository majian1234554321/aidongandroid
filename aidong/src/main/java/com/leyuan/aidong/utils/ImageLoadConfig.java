package com.leyuan.aidong.utils;

import android.graphics.Bitmap.Config;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;



/**
 * 加载图片配置
 */
public class ImageLoadConfig {

	private DisplayImageOptions options;
	
	private Builder builder;
	
	public ImageLoadConfig() {
		builder = new Builder();
	}
	
	
	/**
	 * @Title: getOptions 
	 * @Description: TODO(please write your description)
	 * @param:  @param Loading
	 * @param:  @return  
	 * @return: DisplayImageOptions    返回类型 
	 * @throws
	 */
	public DisplayImageOptions getOptions(int Loading) {
		options = builder.showImageOnLoading(Loading)
		.showImageForEmptyUri(Loading)
		.showImageOnFail(Loading)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Config.RGB_565)
		.build();
		return options;
	}
	
	public DisplayImageOptions getOptionsWithStretched(int Loading) {
		options = builder.showImageOnLoading(Loading)
		.showImageForEmptyUri(Loading)
		.showImageOnFail(Loading)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.bitmapConfig(Config.RGB_565)
		.build();
		return options;
	}
	
	/**
	 * @Title: getOptions 
	 * @Description: TODO(please write your description)
	 * @param:  @param Loading
	 * @param:  @return  
	 * @return: DisplayImageOptions    返回类型 
	 * @throws
	 */
	public DisplayImageOptions getOptions(int Loading, boolean memoryCache, boolean discCache) {
		options = builder.showImageOnLoading(Loading)
		.showImageForEmptyUri(Loading)
		.showImageOnFail(Loading)
		.cacheInMemory(memoryCache)
		.cacheOnDisc(discCache)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Config.RGB_565)
		.build();
		return options;
	}
	
	/**
	 * @Title: getOptions 
	 * @Description: TODO(please write your description)
	 * @param:  @param Loading
	 * @param:  @return  
	 * @return: DisplayImageOptions    返回类型 
	 * @throws
	 */
	public DisplayImageOptions getOptions(int Loading, boolean cache, Config config) {
		options = builder.showImageOnLoading(Loading)
		.showImageForEmptyUri(Loading)
		.showImageOnFail(Loading)
		.cacheInMemory(cache)
		.cacheOnDisc(cache)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(config)
		.build();
		return options;
	}
	
	/**
	 * @Title: getOptions 
	 * @Description: TODO(please write your description)
	 * @param:  @param Loading
	 * @param:  @param cache
	 * @param:  @param type
	 * @param:  @param config
	 * @param:  @return  
	 * @return: DisplayImageOptions
	 * @throws
	 */
	public DisplayImageOptions getOptions(int Loading, boolean cache, ImageScaleType type, Config config) {
		options = builder.showImageOnLoading(Loading)
		.showImageForEmptyUri(Loading)
		.showImageOnFail(Loading)
		.cacheInMemory(cache)
		.cacheOnDisc(cache)
		.imageScaleType(type)
		.bitmapConfig(config)
		.build();
		return options;
	}
	
	/**
	 * @Title: getOptions 
	 * @Description: TODO(please write your description)
	 * @param:  @param imageOnLoading
	 * @param:  @param emptyUri
	 * @param:  @param cache
	 * @param:  @param type
	 * @param:  @param config
	 * @param:  @return  
	 * @return: DisplayImageOptions    返回类型 
	 * @throws
	 */
	public DisplayImageOptions getOptions(int imageOnLoading, int emptyUri, boolean cache, ImageScaleType type, Config config) {
		options = builder.showImageOnLoading(imageOnLoading)
		.showImageForEmptyUri(emptyUri)
		.showImageOnFail(imageOnLoading)
		.cacheInMemory(cache)
		.cacheOnDisc(cache)
		.imageScaleType(type)
		.bitmapConfig(config)
		.build();
		return options;
	}
}





















