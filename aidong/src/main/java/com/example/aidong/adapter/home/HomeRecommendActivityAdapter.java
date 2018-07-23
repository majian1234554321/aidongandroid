package com.example.aidong.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.aidong.R;
import com.example.aidong .entity.CampaignBean;
import com.example.aidong.ui.DisplayActivity;
import com.example.aidong .ui.competition.activity.ContestHomeActivity;
import com.example.aidong .ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.GlideLoader;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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




            Glide.with(context)
                    .load(campaignBean.getCover())
                    .asBitmap()//强制Glide返回一个Bitmap对象
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            Log.e("width_height", "width " + width  + "--------height " + height);
                        }
                    });








           // Log.i("IMAGE","图片的高度"+holder.imgCover.getMeasuredHeight()+"图片的宽度"+holder.imgCover.getMeasuredWidth()+"图片宽高比例"+(holder.imgCover.getMeasuredWidth()/holder.imgCover.getMeasuredHeight()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Constant.CAMPAIGN.equals(campaignBean.type)) {
                    //   ActivityCircleDetailActivity.start(context, campaignBean.getId());


                        Intent intent = new Intent(context,DisplayActivity.class);
                        intent.putExtra("TYPE","DetailsActivityH5Fragment");
                        intent.putExtra("id",campaignBean.campaign_detail);
                        context.startActivity(intent);


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
