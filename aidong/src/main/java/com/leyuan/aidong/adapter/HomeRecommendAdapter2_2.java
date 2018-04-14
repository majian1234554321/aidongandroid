package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.ui.competition.activity.ContestHomeActivity;
import com.leyuan.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

public class HomeRecommendAdapter2_2 extends RecyclerView.Adapter<HomeRecommendAdapter2_2.ViewHolder> {
    private  Context context;
    private List<CampaignBean> campaigns;

    public HomeRecommendAdapter2_2(Context context,Object campaigns){
        this.context = context;
        this.campaigns = (List<CampaignBean>) campaigns;
    }

    @NonNull
    @Override
    public HomeRecommendAdapter2_2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendAdapter2_2.ViewHolder holder, int position) {
        final CampaignBean campaignBean = (CampaignBean)campaigns.get(position);
        if (campaignBean != null) {
            GlideLoader.getInstance().displayImage(campaignBean.getCover(), holder.imgCover);
            holder.txtType.setText("【" + campaignBean.getTypeCZ() + "】");
            holder.txtTime.setText(campaignBean.getStart());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
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
