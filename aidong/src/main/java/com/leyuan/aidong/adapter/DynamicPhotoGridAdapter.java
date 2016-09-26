package com.leyuan.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.AttributeImages;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public class DynamicPhotoGridAdapter extends AbstractCommonAdapter {

	private Context mContext;
	private List<AttributeImages> images;
	public DynamicPhotoGridAdapter( Context mContext,List<AttributeImages> images)
	{
		this.mContext = mContext;
		this.images = images;
		if (images.size() ==1) {
			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.icon_default_number_one)
//			.showImageForEmptyUri(R.drawable.icon_default_number_one)
//			.showImageOnFail(R.drawable.icon_default_number_one)
			.cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();
		}else if (images.size() == 2 || images.size() ==4) {
			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.icon_default_number_two)
//			.showImageForEmptyUri(R.drawable.icon_default_number_two)
//			.showImageOnFail(R.drawable.icon_default_number_two)
			.cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();
		}else  {
			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.icon_default_number_six)
//			.showImageForEmptyUri(R.drawable.icon_default_number_six)
//			.showImageOnFail(R.drawable.icon_default_number_six)
			.cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();
			
		}
	}
	
	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_dynamic_photo_grid, null);
			holder.imgView = (ImageView) convertView
					.findViewById(R.id.imgIconView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AttributeImages image = images.get(position);
//		Log.i("dynamic", "图片链接 -- " + image.getUrl(holder.imgView.getWidth(), holder.imgView.getHeight()));
		imageLoader.displayImage(image.getUrl(0, 0), holder.imgView, options);
		
		return convertView;
	}

	 static class ViewHolder {
		ImageView imgView;
	}
}
