package com.example.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.location.RecorderVideoInformation;

import java.util.ArrayList;

public class GirdAdapterSelectVideo extends BaseAdapter {
	private Context mContext;
	private ArrayList<RecorderVideoInformation> array = new ArrayList<RecorderVideoInformation>();
	private RecorderVideoInformation infro;
	private int select_position = 0;
	
	public GirdAdapterSelectVideo(Context context,ArrayList<RecorderVideoInformation> array)
	{
		mContext = context;
		this.array.clear();
		this.array.addAll(array);
	}

	public void freshData(ArrayList<RecorderVideoInformation> array)
	{
		this.array.clear();
		this.array.addAll(array);
		notifyDataSetChanged();
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
			convertView = View.inflate(mContext, R.layout.item_gridview_select_video, null);
			holder.img_girdview_select_video = (ImageView) 
					convertView.findViewById(R.id.img_girdview_select_video);
			holder.img_mark = (ImageView) 
					convertView.findViewById(R.id.img_mark);
			holder.img_circle = (ImageView) 
					convertView.findViewById(R.id.img_circle);
			holder.txt_duration = (TextView) 
					convertView.findViewById(R.id.txt_duration);
			holder.view_stroke_video = (View) 
					convertView.findViewById(R.id.view_stroke_video);
			
			
			convertView.setTag(holder);
		}
		
		else{
			holder = (Holder) convertView.getTag();
		}
		
		infro = array.get(position);
		
		if(position == 0)
		{
			holder.img_mark.setVisibility(View.GONE);
			holder.img_circle.setVisibility(View.GONE);
			holder.txt_duration.setVisibility(View.GONE);
			holder.view_stroke_video.setVisibility(View.GONE);
			holder.img_girdview_select_video.setImageResource(R.drawable.recorder_video);
		}
		else{
			if(select_position == position)
			{
				holder.img_circle.setImageResource(R.drawable.btn_video_select_pressed);
				holder.img_girdview_select_video.setImageBitmap(infro.getBitmap());
//				holder.img_girdview_select_video.setColorFilter(Color.parseColor("#77000000"));
				holder.view_stroke_video.setVisibility(View.VISIBLE);
			}
			else
			{
				holder.img_girdview_select_video.setImageBitmap(infro.getBitmap());
				holder.img_circle.setImageResource(R.drawable.btn_video_select_normal);
//				holder.img_girdview_select_video.setColorFilter(null);
				holder.view_stroke_video.setVisibility(View.INVISIBLE);
			}
			holder.img_mark.setVisibility(View.VISIBLE);
			holder.img_circle.setVisibility(View.VISIBLE);
			holder.txt_duration.setVisibility(View.VISIBLE);
			holder.txt_duration.setText(infro.getDurarion());
		}
		
		
		
		return convertView;
	}
	
	static class Holder
	{
		ImageView img_girdview_select_video;
		ImageView img_mark;
		ImageView img_circle;
		TextView txt_duration;
		View view_stroke_video;
	}

	public void freshSelectPosition(int select_position) {
		this.select_position = select_position;
		notifyDataSetChanged();
	}

}
