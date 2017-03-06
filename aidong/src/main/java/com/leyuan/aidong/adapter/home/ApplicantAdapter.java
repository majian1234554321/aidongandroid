package com.leyuan.aidong.adapter.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动详情页参与者适配器
 * Created by song on 2016/8/24.
 */
public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantHolder>{
    private List<UserBean> data = new ArrayList<>();

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
        View view = View.inflate(parent.getContext(),R.layout.item_campaign_applicant,null);
        return new ApplicantHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicantHolder holder, int position) {
        UserBean bean = data.get(position);
        GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.cover);
    }

    class  ApplicantHolder extends RecyclerView.ViewHolder{

        ImageView cover;
        public ApplicantHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_user_cover);
        }
    }
}
