package com.example.aidong.ui.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.aidong.entity.CampaignDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动详情页参与者适配器
 * Created by song on 2016/8/24.
 */
public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantHolder>{

    private List<CampaignDetailBean.Applicant> data = new ArrayList<>();

    public void setData(List<CampaignDetailBean.Applicant> data) {
        this.data = data;
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
        CampaignDetailBean.Applicant bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
    }

    class  ApplicantHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView cover;
        public ApplicantHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_user_cover);
        }
    }
}
