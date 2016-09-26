package com.leyuan.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseApp;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.LikeData;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.utils.ImageUtil;
import com.leyuan.aidong.widget.customview.CircleImageView;

import java.util.ArrayList;

public class LikeListAdapter extends AbstractCommonAdapter {

	private Context context;
	private ArrayList<LikeData> array;
	
	private boolean islogin;
	private int mxid;
	
	public LikeListAdapter(Context context	) {
		this.context = context;
		this.array = new ArrayList<LikeData>();
		islogin = BaseApp.mInstance.isLogin();
		if (islogin) {
				mxid = BaseApp.mInstance.getUser().getMxid();
		}
		
	}
	
//	public LikeListAdapter(Context context	,ArrayList<LikeData> array) {
//		this.context = context;
//		isLogin = BaseApp.mInstance.isLogin();
//		this.array = new ArrayList<LikeData>();
//		this.array.addAll(array);
//		
//	}
	
	public void freshData(ArrayList<LikeData> array){
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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_user_click_like, null);
			holder.img_head_portrait = (CircleImageView) convertView.findViewById(R.id.img_head_portrait);
			holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
			holder.img_add_friend = (ImageView) convertView.findViewById(R.id.img_add_friend);
			holder.img_jiao = (ImageView) convertView.findViewById(R.id.img_jiao);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
      	UserCoach user = array.get(position).getUser();
      	imageLoader.displayImage(ImageUtil.getImageUrl(context, user.getAvatar(), 0, 40, 40), holder.img_head_portrait, options);
      	holder.txt_name.setText(user.getName()+"");
      	holder.txt_content.setText(user.getSignature()+"");
//      	Map<String, UserCoach> contactList = BaseApp.mInstance.getContactList();
      	holder.img_add_friend.setImageResource(R.drawable.btn_add_normal);
      	holder.img_add_friend.setTag(user);
      	holder.img_add_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (l != null) {
					l.onAddFriendClick(v);
				}
			}
		});

      	
      	holder.img_add_friend.setClickable(true);
		if (islogin) {
			if (user.getMxid() == mxid) {
				holder.img_add_friend.setImageResource(R.drawable.btn_add_pressed);
				holder.img_add_friend.setClickable(false);
			}
			
//			for(String id : contactList.keySet()){
//				if (TextUtils.equals(user.getMxid()+"", id)) {
//					holder.img_add_friend.setImageResource(R.drawable.btn_add_pressed);
//					holder.img_add_friend.setClickable(false);
//				}
//			}
		}
		
		if (user.getIdentity() == 1) {
			holder.img_jiao.setVisibility(View.VISIBLE);
		}else{
			holder.img_jiao.setVisibility(View.GONE);
		}
		holder.img_head_portrait.setTag(user);
		holder.img_head_portrait.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (l != null) {
					l.onHeaderClick(v);
				}
			}
		});
		
		
		
		return convertView;
	}
	
	
	static class ViewHolder{
		CircleImageView  img_head_portrait;
		TextView  txt_name;
		TextView  txt_content;
		ImageView  img_add_friend;
		ImageView img_jiao;
	}
	
	private OnUserLikeClickListener l;
	public void setOnUserLikeClickListener(OnUserLikeClickListener l){
		this.l = l;
	}
	
	public interface OnUserLikeClickListener{
		public void onHeaderClick(View v);
		public void onAddFriendClick(View v);
	}

}
