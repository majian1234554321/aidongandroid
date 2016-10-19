package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗适配器
 * Created by song on 2016/10/17.
 */
public class SportsNewsAdapter extends RecyclerView.Adapter<SportsNewsAdapter.NewsHolder>{
    private Context context;
    private List<String> data = new ArrayList<>();

    public void setData(List<String> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_sport_news,null);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {

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
