package com.example.aidong.adapter;

import android.graphics.Bitmap;
import android.widget.BaseAdapter;

import com.example.aidong.R;
import com.example.aidong.http.Logic;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class AbstractCommonAdapter extends BaseAdapter{

	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected Logic logic = new Logic();
	public AbstractCommonAdapter(){
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_picture)
		.showImageForEmptyUri(R.drawable.icon_picture)
		.showImageOnFail(R.drawable.icon_picture)
		.cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

}
