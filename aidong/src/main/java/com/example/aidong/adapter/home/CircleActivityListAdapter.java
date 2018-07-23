package com.example.aidong.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.CampaignBean;
import com.example.aidong.ui.DisplayActivity;
import com.example.aidong .ui.competition.activity.ContestHomeActivity;
import com.example.aidong .ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.GlideLoader;
import com.iknow.android.utils.GlideUtils;

import java.util.List;

import static com.example.aidong.ui.App.context;

/**
 * Created by user on 2018/1/5.
 */
public class CircleActivityListAdapter extends RecyclerView.Adapter<CircleActivityListAdapter.ViewHolder> {
    private final Context context;
    private List<CampaignBean> data;

    public CircleActivityListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_circle_activity_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CampaignBean bean = data.get(position);
       // GlideLoader.getInstance().displayImage2(bean.getCover(), holder.imgCover);
        holder.txtType.setText("【" + bean.getTypeCZ() + "】");
        holder.txtName.setText(bean.getName());
//        if (TextUtils.isEmpty(bean.getLandmark())) {
//            holder.txtTime.setText(bean.getStart());
//        } else {
//
//        }


        GlideUtils.loadIntoUseFitWidth(context,bean.getCover(),R.drawable.img_default2, holder.imgCover);

        StringBuilder stringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(bean.landmark)){
            stringBuilder.append(bean.landmark).append(" ");
        }else {
            stringBuilder.append("");
        }


        if (bean.start_time.equals(bean.end_time)){
            holder.txtTime.setText(stringBuilder.append(bean.start_time));
        }else {
            holder.txtTime.setText(stringBuilder.append(bean.start_time).append("~") .append(bean.end_time));
        }


        holder.txt_sub_title.setText(((TextUtils.isEmpty(bean.getSlogan()))?"":bean.getSlogan()+ " | "  )+ bean.getFollows_count() + "人已关注");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.CAMPAIGN.equals(bean.type)) {
                    ActivityCircleDetailActivity.start(context, bean.getId());


//                    Intent intent = new Intent(context,DisplayActivity.class);
//                    intent.putExtra("TYPE","DetailsActivityH5Fragment");
//                    intent.putExtra("id",bean.campaign_detail);
//                    context.startActivity(intent);

                } else if (Constant.CONTEST.equals(bean.type)) {
                    ContestHomeActivity.start(context, bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    public void setData(List<CampaignBean> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private TextView txtType;
        private TextView txtName;
        private TextView txtTime, txt_sub_title;

        public ViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            txtType = (TextView) view.findViewById(R.id.txt_type);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txt_sub_title = (TextView) view.findViewById(R.id.txt_sub_title);
        }
    }
}
