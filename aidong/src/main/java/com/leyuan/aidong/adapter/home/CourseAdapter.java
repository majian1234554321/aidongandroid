package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;

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
        if(data != null) {
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        final CourseBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());
        holder.time.setText(String.format(context.getString(R.string.time_with_line),bean.getClassTime(),bean.getBreakTime()));
        holder.count.setText(String.format(context.getString(R.string.course_count),bean.getAppliedCount(),bean.getPlace()));
        holder.distance.setText(String.format(context.getString(R.string.distance_km),bean.getDistance()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailActivity.start(context,bean.getCode());
            }
        });
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView price;
        TextView name;
        TextView address;
        TextView time;
        TextView count;
        TextView distance;

        public CourseViewHolder (View itemView) {
            super(itemView);

            cover = (ImageView)itemView.findViewById(R.id.dv_course_cover);
            price = (TextView)itemView.findViewById(R.id.tv_course_price);
            name = (TextView)itemView.findViewById(R.id.tv_course_name);
            address = (TextView)itemView.findViewById(R.id.tv_course_address);
            time = (TextView)itemView.findViewById(R.id.tv_course_time);
            count = (TextView)itemView.findViewById(R.id.tv_course_count);
            distance = (TextView)itemView.findViewById(R.id.tv_course_distance);
        }
    }
}
