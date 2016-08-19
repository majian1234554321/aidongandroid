package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.CampaignBean;

import java.util.List;

/**
 * 活动列表适配器
 * Created by song on 2016/8/19.
 */
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder>{
    private int itemNormalHeight;
    private int itemMaxHeight;
    private boolean isFirst = true;
    private List<CampaignBean> data;

    public CampaignAdapter(int itemNormalHeight, int itemMaxHeight) {
        this.itemNormalHeight = itemNormalHeight;
        this.itemMaxHeight = itemMaxHeight;
    }

    public void setData(List<CampaignBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_campaign,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(isFirst && position ==0){
            isFirst = false;
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemMaxHeight));
        }else {
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemNormalHeight));
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView address;
        TextView time;

        public ViewHolder (View itemView) {
            super(itemView);
            itemView.getLayoutParams().height = itemMaxHeight;
            cover = (ImageView)itemView.findViewById(R.id.iv_campaign_cover);
            name = (TextView)itemView.findViewById(R.id.tv_campaign_name);
            address = (TextView)itemView.findViewById(R.id.tv_campaign_address);
            time = (TextView)itemView.findViewById(R.id.tv_campaign_time);
        }
    }
}