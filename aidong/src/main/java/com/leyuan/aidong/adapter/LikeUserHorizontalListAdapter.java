package com.leyuan.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.AttributeDynamics;
import com.leyuan.aidong.entity.model.AttributeDynamics.LikeUser;
import com.leyuan.aidong.widget.customview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

public class LikeUserHorizontalListAdapter extends AbstractCommonAdapter {
	private Context context;
	ArrayList<AttributeDynamics.LikeUser> array;
	private String TAG = "ListAdapterActivities";
	
	public LikeUserHorizontalListAdapter(Context context) {
		this.context = context;
		this.array = new ArrayList<>();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.xin_geren)
		.showImageForEmptyUri(R.drawable.xin_geren)
		.showImageOnFail(R.drawable.xin_geren)
		.cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	public LikeUserHorizontalListAdapter(Context context, ArrayList<AttributeDynamics.LikeUser> array) {
		this.context = context;
		this.array = new ArrayList<AttributeDynamics.LikeUser>();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.xin_geren)
		.showImageForEmptyUri(R.drawable.xin_geren)
		.showImageOnFail(R.drawable.xin_geren)
		.cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
		this.array.clear();
		this.array.addAll(array);
		
	}
	
	

	public void freshData(ArrayList<LikeUser> array) {
		this.array.clear();
		this.array.addAll(array);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return array.size() + 1;
	}

	@Override
	public LikeUser getItem(int position) {
		if (position < array.size()) {
			return array.get(position);
		}
		else{
			return null;
		}
		
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_horizontal_list_like_user, null);
			holder.mxlist_item_head = (CircleImageView) convertView
					.findViewById(R.id.mxlist_item_head);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//不判断会为空
		if (array.size() > 0) {
			if (position < array.size()) {
				LikeUser user = array.get(position);
				imageLoader.displayImage(user.getUser().getAvatar(),
						holder.mxlist_item_head,options);
			}
			else{
				holder.mxlist_item_head.setImageResource(R.drawable.icon_more_like);
			}
			
		
		}
		return convertView;
	}

	private class ViewHolder {
		CircleImageView mxlist_item_head;
		
	}

}
