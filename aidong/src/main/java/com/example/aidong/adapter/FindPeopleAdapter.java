package com.example.aidong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.bean.PeopleBean;
import com.example.aidong.view.RoundAngleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-人RecyclerView的适配器
 * Created by song on 2016/7/15.
 */
public class FindPeopleAdapter extends RecyclerView.Adapter<FindPeopleAdapter.PeopleViewHolder>{

    private List<PeopleBean> date = new ArrayList<>();
    private ImageLoader loader = ImageLoader.getInstance();

    public FindPeopleAdapter(List<PeopleBean> date) {
        this.date = date;
    }

    public void setData(List<PeopleBean> date) {
        this.date = date;
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_people_rv,null);
        PeopleViewHolder holder = new PeopleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, int position) {
        PeopleBean bean = date.get(position);
        loader.displayImage(bean.getCover(),holder.cover);
        holder.name.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
        loader.displayImage(bean.getAttention(),holder.attention);
    }

     static class PeopleViewHolder extends RecyclerView.ViewHolder{
        RoundAngleImageView cover;
        TextView name;
        TextView distance;
        ImageView attention;

        public PeopleViewHolder (View itemView) {
            super(itemView);
            cover = (RoundAngleImageView) itemView.findViewById(R.id.iv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
            attention = (ImageView)itemView.findViewById(R.id.tv_attention);
        }
    }
}
