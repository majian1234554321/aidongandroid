package com.leyuan.aidong.ui.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.ui.discover.activity.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面 新闻配器
 * Created by song on 2016/12/12.
 */
public class DiscoverNewsAdapter extends RecyclerView.Adapter<DiscoverNewsAdapter.NewsHolder>{
    private Context context;
    private List<NewsBean> news = new ArrayList<>();

    public DiscoverNewsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NewsBean> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover_news,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final NewsBean bean = news.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.title.setText(bean.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.start(context,bean.getTitle(),bean.getDatetime(),bean.getBody());
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView title;

        public NewsHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_cover);
            title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
