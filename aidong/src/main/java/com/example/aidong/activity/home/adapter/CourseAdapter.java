package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 小团体课列表适配器
 * Created by song on 2016/8/17.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {

    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView address;
        TextView time;

        public CourseViewHolder (View itemView) {
            super(itemView);


        }
    }
}
