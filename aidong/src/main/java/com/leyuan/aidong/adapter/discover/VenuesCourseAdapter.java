package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.utils.FormatUtil;

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
        if(data != null){
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venues_course, parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        final CourseBean bean = data.get(position);
        holder.name.setText(bean.getName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));
        holder.address.setText(bean.getAddress());
        holder.time.setText(String.format(context.getString(R.string.time_with_line),
                bean.getClassTime(),bean.getBreakTime()));
        holder.count.setText(String.format(context.getString(R.string.venues_course_count),
                bean.getAppliedCount(),bean.getPlace()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailActivity.start(context,bean.getCode());
            }
        });
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        TextView address;
        TextView time;
        TextView count;

        public CourseHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_course_name);
            price = (TextView) itemView.findViewById(R.id.tv_course_price);
            address = (TextView) itemView.findViewById(R.id.tv_course_address);
            time = (TextView) itemView.findViewById(R.id.tv_course_time);
            count = (TextView) itemView.findViewById(R.id.tv_course_count);
        }
    }
}
