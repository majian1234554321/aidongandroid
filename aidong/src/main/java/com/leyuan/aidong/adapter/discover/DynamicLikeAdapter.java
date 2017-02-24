package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈点赞用户
 * Created by song on 2016/12/26.
 */
public class DynamicLikeAdapter extends RecyclerView.Adapter<DynamicLikeAdapter.UserHolder>{
    private Context context;
    private List<DynamicBean.LikeUser.Item> data = new ArrayList<>();

    public DynamicLikeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DynamicBean.LikeUser.Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        DynamicBean.LikeUser.Item bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.avatar, holder.avatar);
    }

    class UserHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        public UserHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
        }
    }
}
