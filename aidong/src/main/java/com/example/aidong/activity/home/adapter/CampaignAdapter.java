package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.CampaignBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表适配器
 * Created by song on 2016/8/19.
 */
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder>{
    private Context context;
    private int itemNormalHeight;
    private int itemMaxHeight;
    private boolean isFirst = true;
    private List<CampaignBean> data = new ArrayList<>();

    public CampaignAdapter(Context context,int itemNormalHeight, int itemMaxHeight) {
        this.context = context;
        this.itemNormalHeight = itemNormalHeight;
        this.itemMaxHeight = itemMaxHeight;
    }

    public void setData(List<CampaignBean> data) {
        this.data = data;
    }

    public void setFirst(boolean first) {
        isFirst = first;
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
            holder.name.setVisibility(View.VISIBLE);
            holder.address.setVisibility(View.VISIBLE);
            holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.item_min_text_size));
        }else {
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemNormalHeight));
            holder.name.setVisibility(View.GONE);
            holder.address.setVisibility(View.GONE);
            holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.item_max_text_size));
        }
        CampaignBean bean = data.get(position);
        holder.cover.setImageURI(bean.getImage());
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getLandmart());
        holder.time.setText(bean.getStart_time());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView cover;
        public TextView name;
        public TextView address;
        public TextView time;

        public ViewHolder (View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,itemMaxHeight));
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_campaign_cover);
            name = (TextView)itemView.findViewById(R.id.tv_campaign_name);
            address = (TextView)itemView.findViewById(R.id.tv_campaign_address);
            time = (TextView)itemView.findViewById(R.id.tv_campaign_time);
        }
    }
}