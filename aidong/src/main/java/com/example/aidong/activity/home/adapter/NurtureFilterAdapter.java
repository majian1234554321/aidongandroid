package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 营养品筛选界面适配器
 * Created by song on 2016/8/17.
 */
public class NurtureFilterAdapter extends RecyclerView.Adapter<NurtureFilterAdapter.FoodViewHolder>{

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {

    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView address;
        TextView time;

        public FoodViewHolder (View itemView) {
            super(itemView);
        }
    }
}
