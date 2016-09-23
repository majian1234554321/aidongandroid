package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.activity.home.CourseDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.CourseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课列表适配器
 * Created by song on 2016/8/17.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    private Context context;
    private List<CourseBean> data = new ArrayList<>();

    public CourseAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_course,null);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        CourseBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());
        holder.time.setText(String.format(context.getString(R.string.time_with_line),bean.getClass_time(),bean.getBreak_time()));
        holder.count.setText(String.format(context.getString(R.string.course_count),bean.getApplied_count(),bean.getPlace()));
        holder.distance.setText(String.format(context.getString(R.string.distance_km),bean.getDistance()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailActivity.actionStart(context,"1");
            }
        });
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView price;
        TextView name;
        TextView address;
        TextView time;
        TextView count;
        TextView distance;

        public CourseViewHolder (View itemView) {
            super(itemView);

            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_course_cover);
            price = (TextView)itemView.findViewById(R.id.tv_course_price);
            name = (TextView)itemView.findViewById(R.id.tv_course_name);
            address = (TextView)itemView.findViewById(R.id.tv_course_address);
            time = (TextView)itemView.findViewById(R.id.tv_course_time);
            count = (TextView)itemView.findViewById(R.id.tv_course_count);
            distance = (TextView)itemView.findViewById(R.id.tv_course_distance);
        }
    }
}
