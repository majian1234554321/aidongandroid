package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_campaign,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CampaignBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.hot.setText(bean.getName());
        holder.address.setText(bean.getLandmark());
        holder.time.setText(bean.getStart());

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignDetailActivity.start(context,bean.getId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cover;
        public TextView hot;
        public TextView address;
        public TextView time;

        public ViewHolder (View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_campaign_cover);
            hot = (TextView)itemView.findViewById(R.id.tv_campaign_hot);
            address = (TextView)itemView.findViewById(R.id.tv_campaign_address);
            time = (TextView)itemView.findViewById(R.id.tv_campaign_time);
        }
    }
}