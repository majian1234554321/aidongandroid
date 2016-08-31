package com.example.aidong.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面 用户适配器
 * Created by song on 2016/8/31.
 */
public class DiscoverUserAdapter extends RecyclerView.Adapter<DiscoverUserAdapter.UserHolder>{
    private Context context;
    private List<UserBean> data = new ArrayList<>();

    public DiscoverUserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_discover_user,null);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        UserBean bean = data.get(position);
        holder.avatar.setImageURI(bean.getAvatar());

    }

    class UserHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView avatar;
        ImageView man;
        ImageView female;

        public UserHolder(View itemView) {
            super(itemView);
            avatar = (SimpleDraweeView)itemView.findViewById(R.id.dv_user_cover);
            man = (ImageView)itemView.findViewById(R.id.iv_man);
            female = (ImageView)itemView.findViewById(R.id.iv_female);
        }
    }
}
