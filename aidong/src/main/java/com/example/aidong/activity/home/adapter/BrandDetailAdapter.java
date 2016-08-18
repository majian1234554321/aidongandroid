package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 品牌商品列表适配器
 * Created by song on 2016/8/17.
 */
public class BrandDetailAdapter extends RecyclerView.Adapter<BrandDetailAdapter.BrandViewHolder>{

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position) {

    }

    class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView address;
        TextView time;

        public BrandViewHolder (View itemView) {
            super(itemView);
        }
    }
}
