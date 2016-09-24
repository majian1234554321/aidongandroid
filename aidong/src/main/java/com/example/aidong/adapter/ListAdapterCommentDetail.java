package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.model.Comment;
import com.example.aidong.utils.SmileUtils;
import com.example.aidong.widget.customview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ListAdapterCommentDetail extends BaseAdapter {

	private Context context;
	private ArrayList<Comment> array = new ArrayList<Comment>();
	private OnClickListener avatarClickListener;
	
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public ListAdapterCommentDetail(Context context) {
		this.context = context;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_picture)
		.showImageForEmptyUri(R.drawable.icon_picture)
		.showImageOnFail(R.drawable.icon_picture)
		.cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public void freshArrayData(ArrayList<Comment> array) {
		this.array.clear();
		this.array.addAll(array);
		notifyDataSetChanged();
	}
	public void addItem(Comment comment){
		this.array.add(0,comment);
		notifyDataSetChanged();
	}

	public void setAvatarClickListener(OnClickListener l) {
		avatarClickListener = l;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.array.size();
	}

	@Override
	public Comment getItem(int arg0) {
		// TODO Auto-generated method stub
		return array.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_comment_item, null);
			holder.iconView = (CircleImageView) convertView
					.findViewById(R.id.imageView);
			holder.txtUsernameView = (TextView) convertView
					.findViewById(R.id.txtUsername);
			holder.hobbyView = (TextView) convertView
					.findViewById(R.id.txtHobby);
			holder.txtTimeView = (TextView) convertView
					.findViewById(R.id.txtTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Comment item = array.get(position);
		imageLoader.displayImage(item.getUser().getAvatar(), holder.iconView, options);
		holder.iconView.setTag(item.getUser());
		holder.iconView.setOnClickListener(avatarClickListener);
		holder.txtUsernameView.setText(item.getUser().getName());
		Spannable builder = new SpannableStringBuilder(
				item.getContent());
		SmileUtils.addSmiles(context, builder);
		holder.hobbyView.setText(builder);
		long second = Long.parseLong(item.getCreated());
		// String time [] = ActivityTool.getTimeCalShow(context, second);
		long createTime = Long.parseLong(item.getCreated());
		long currentTime = System.currentTimeMillis() / 1000;
		long during = currentTime - createTime;
		String timestr = null;
		if (during < 3600) {
			timestr = String.valueOf((during / 60))
					+ context.getResources().getString(R.string.s_minute);
		} else if (during < 3600 * 24) {
			timestr = String.valueOf((during / 3600))
					+ context.getResources().getString(R.string.s_hour);
		} else if (during < 3600 * 24 * 7) {
			timestr = String.valueOf((during / (3600 * 24)))
					+ context.getResources().getString(R.string.s_day);
		} else if (during < 3600 * 24 * 30) {
			timestr = String.valueOf((during / (3600 * 24 * 7)))
					+ context.getResources().getString(R.string.s_week);
		} else if (during < 3600 * 24 * 365) {
			timestr = String.valueOf((during / (3600 * 24 * 30)))
					+ context.getResources().getString(R.string.s_month);
		} else {
			timestr = String.valueOf((during / (3600 * 24 * 365)))
					+ context.getResources().getString(R.string.s_year);
		}
		holder.txtTimeView.setText(timestr.toString());
		// holder.txtTimeView.setText(time[2]);
		return convertView;
	}

	private class ViewHolder {
		CircleImageView iconView;
		TextView txtUsernameView;
		TextView hobbyView;
		TextView txtTimeView;
	}

}
