package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动同道适配器
 * Created by song on 2016/8/29.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    private Context context;
    private List<UserBean> data = new ArrayList<>();

    public UserAdapter(Context context, List<UserBean> data) {
        this.context = context;
        this.data = data;
    }

    public UserAdapter(Context context) {
        this.context = context;
        UserBean bean = new UserBean();
        bean.setAvatar("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        bean.setDistance("200");
        bean.setName("谁啊这是");
        for(int i=0; i<20;i++){
            this.data.add(bean);
        }
        notifyDataSetChanged();
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
        View view = View.inflate(context, R.layout.item_user,null);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        UserBean bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
        holder.nickname.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
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
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(context,90)));
        }
    }
}
