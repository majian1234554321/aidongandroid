package com.leyuan.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.leyuan.aidong.R;

import java.util.ArrayList;

public class GirdAdapterSelectVideoCover extends BaseAdapter {
	private Context mContext;
	private ArrayList<Bitmap> array = new ArrayList<Bitmap>();
	private int select_position = -1;
	
	public GirdAdapterSelectVideoCover(Context context,ArrayList<Bitmap> array)
	{
		mContext = context;
		this.array.clear();
		this.array.addAll(array);
	}

	
	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null)
		{
			holder = new Holder();
			convertView = View.inflate(mContext, R.layout.item_grid_select_video_cover, null);
			holder.img_cover = (ImageView) convertView.findViewById(R.id.img_cover);
			holder.img_cover_select = (ImageView) convertView.findViewById(R.id.img_cover_select);
			convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		Bitmap bitmap = array.get(position);
		holder.img_cover.setImageBitmap(bitmap);
		if(select_position == position)
		{
			holder.img_cover_select.setImageResource(R.drawable.skyblue_platform_checked);
		}
		else
		{
			holder.img_cover_select.setImageResource(R.drawable.skyblue_platform_checked_disabled);
		}
		
		
		return convertView;
	}
	
	static class Holder
	{
		ImageView img_cover;
		ImageView img_cover_select;
		
		
	}

	public void freshSelectPosition(int select_position) {
		this.select_position = select_position;
		notifyDataSetChanged();
	}

}
