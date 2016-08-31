package com.example.aidong.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.CourseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆详情界面课程列表适配器
 * Created by song on 2016/8/29.
 */
public class VenuesCourseAdapter extends RecyclerView.Adapter<VenuesCourseAdapter.CourseHolder>{
    private Context context;
    private List<CourseBean> data = new ArrayList<>();

    public VenuesCourseAdapter(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_venues_course,null);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {

    }

    class CourseHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        TextView addess;
        TextView time;
        TextView count;

        public CourseHolder(View itemView) {
            super(itemView);
        }
    }
}
