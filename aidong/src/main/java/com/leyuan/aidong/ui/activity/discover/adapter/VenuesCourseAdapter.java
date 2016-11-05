package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;

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
        }else {
            for (int i = 0; i < 10; i++) {
                CourseBean bean = new CourseBean();
                if( i % 2 == 0){
                    bean.setPrice("999");
                    bean.setAddress("日本东京大道");
                    bean.setName("AiFukuhara ");
                }else{
                    bean.setPrice("888");
                    bean.setAddress("上海长宁区妇幼保健院");
                    bean.setName("保健 ");
                }
                this.data.add(bean);
            }
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
        CourseBean bean = data.get(position);
        holder.name.setText(bean.getName());
        holder.price.setText(bean.getPrice());
        holder.address.setText(bean.getAddress());
        //holder.count.setText();
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
