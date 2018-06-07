package com.example.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.NewsBean;
import com.example.aidong .ui.discover.activity.NewsDetailActivity;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗适配器
 * Created by song on 2016/10/17.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{
    private Context context;
    private List<NewsBean> data = new ArrayList<>();

    public NewsAdapter(Context context) {
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
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.title.setText(bean.getTitle());
        holder.time.setText(bean.getDatetime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.start(context,bean);
            }
        });
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;
        TextView time;

        public NewsHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            cover = (ImageView) itemView.findViewById(R.id.dv_cover);
        }
    }
}
