package com.leyuan.aidong.adapter.contest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.campaign.ContestVideoBean;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

/**
 * 运动之窗适配器
 * Created by song on 2016/10/17.
 */
public class ContestRuleVideoAdapter extends RecyclerView.Adapter<ContestRuleVideoAdapter.NewsHolder> {
    private Context context;
    private List<ContestVideoBean> data;

    public ContestRuleVideoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ContestVideoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contest_rule, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final ContestVideoBean bean = data.get(position);
        GlideLoader.getInstance().displayImage2(bean.cover, holder.cover);
        holder.title.setText(bean.title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class)
                        .setData(Uri.parse(bean.video))
                        .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
                context.startActivity(intent);

            }
        });
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;

        public NewsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            cover = (ImageView) itemView.findViewById(R.id.dv_cover);
        }
    }
}
