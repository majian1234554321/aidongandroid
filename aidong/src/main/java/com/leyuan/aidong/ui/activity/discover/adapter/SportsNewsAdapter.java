package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.ui.activity.discover.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗适配器
 * Created by song on 2016/10/17.
 */
public class SportsNewsAdapter extends RecyclerView.Adapter<SportsNewsAdapter.NewsHolder>{
    private Context context;
    private List<NewsBean> data = new ArrayList<>();

    public SportsNewsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NewsBean> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sport_news,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final NewsBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.title.setText(bean.getTitle());
        holder.time.setText(bean.getDatetime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.start(context,bean.getTitle(),bean.getDatetime(),bean.getBody());
            }
        });
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView title;
        TextView time;

        public NewsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_cover);
        }
    }
}
