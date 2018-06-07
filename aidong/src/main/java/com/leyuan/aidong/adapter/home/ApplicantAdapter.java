package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动详情页参与者适配器
 * Created by song on 2016/8/24.
 */
public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantHolder>{
    private Context context;
    private List<UserBean> data = new ArrayList<>();

    public ApplicantAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UserBean> data) {
        if(data != null){
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ApplicantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_campaign_applicant,parent,false);
        return new ApplicantHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicantHolder holder, int position) {
        final UserBean bean = data.get(position);
        GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.cover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context,bean.getId());
            }
        });
    }

    class  ApplicantHolder extends RecyclerView.ViewHolder{

        ImageView cover;
        public ApplicantHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_user_cover);
        }
    }
}
