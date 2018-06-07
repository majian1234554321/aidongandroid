package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.competition.activity.ContestHomeActivity;
import com.leyuan.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class HomeRecommendActivityAdapter extends RecyclerView.Adapter<HomeRecommendActivityAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<CampaignBean> campaigns;

    public HomeRecommendActivityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CampaignBean campaignBean = campaigns.get(position);
        if (campaignBean != null) {
            GlideLoader.getInstance().displayImage2(campaignBean.getCover(), holder.imgCover);
            holder.txtType.setText("【" + campaignBean.getTypeCZ() + "】");

            if(campaignBean.start_time.equals(campaignBean.end_time)){
                holder.txtTime.setText(campaignBean.start_time);
            }else {
                holder.txtTime.setText(campaignBean.start_time+"~"+campaignBean.end_time);
            }

            holder.txtName.setText(campaignBean.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Constant.CAMPAIGN.equals(campaignBean.type)) {
                        ActivityCircleDetailActivity.start(context, campaignBean.getId());
                    } else if (Constant.CONTEST.equals(campaignBean.type)) {
                        ContestHomeActivity.start(context,campaignBean);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (campaigns == null)
            return 0;
        return campaigns.size();
    }

    public void setData(ArrayList<CampaignBean> campaign) {
        this.campaigns = campaign;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private TextView txtType;
        private TextView txtName;
        private TextView txtTime;

        public ViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            txtType = (TextView) view.findViewById(R.id.txt_type);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
        }
    }
}
