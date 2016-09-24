package com.example.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.ui.activity.home.CampaignDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.aidong.entity.CampaignBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表适配器
 * Created by song on 2016/8/19.
 */
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder>{
    private Context context;
    private List<CampaignBean> data = new ArrayList<>();

    public CampaignAdapter(Context context) {
        this.context = context;
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

        CampaignBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.hot.setText(bean.getName());
        holder.address.setText(bean.getLandmart());
        holder.time.setText(bean.getStart_time());

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignDetailActivity.newInstance(context,"1");
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView cover;
        public TextView hot;
        public TextView address;
        public TextView time;

        public ViewHolder (View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_campaign_cover);
            hot = (TextView)itemView.findViewById(R.id.tv_campaign_hot);
            address = (TextView)itemView.findViewById(R.id.tv_campaign_address);
            time = (TextView)itemView.findViewById(R.id.tv_campaign_time);
        }
    }
}