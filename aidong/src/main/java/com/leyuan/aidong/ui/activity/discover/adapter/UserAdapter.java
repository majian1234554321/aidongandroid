package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动同道适配器
 * Created by song on 2016/8/29.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{
    private Context context;
    private List<UserBean> data = new ArrayList<>();

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        UserBean bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
        holder.nickname.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
        if("0".equals(bean.getGender())){   //男
            holder.gender.setBackgroundResource(R.drawable.icon_man);
        }else {
            holder.gender.setBackgroundResource(R.drawable.icon_woman);
        }
    }

    class UserHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        ImageView gender;
        TextView nickname;
        TextView distance;
        ImageView follow;

        public UserHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_user_cover);
            gender = (ImageView)itemView.findViewById(R.id.iv_gender);
            nickname = (TextView)itemView.findViewById(R.id.tv_nickname);
            distance = (TextView)itemView.findViewById(R.id.tv_distance);
            follow = (ImageView)itemView.findViewById(R.id.iv_follow);
        }
    }
}
