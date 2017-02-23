package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝和关注适配器
 * Created by song on 2016/9/10.
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.UserHolder> {
    private Context context;
    private List<UserBean> data = new ArrayList<>();

    public FollowAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_follow,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        UserBean bean = data.get(position);
        holder.avatar.setImageURI(bean.getAvatar());
        holder.nickname.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
        if("0".equals(bean.getGender())){   //男
            holder.gender.setBackgroundResource(R.drawable.icon_man);
        }else {
            holder.gender.setBackgroundResource(R.drawable.icon_woman);
        }
    }

    class UserHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView avatar;
        ImageView gender;
        ImageView follow;
        TextView nickname;
        TextView distance;

        public UserHolder(View itemView) {
            super(itemView);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.dv_user_cover);
            gender = (ImageView) itemView.findViewById(R.id.iv_gender);
            follow = (ImageView) itemView.findViewById(R.id.iv_follow);
            nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
        }
    }



}
