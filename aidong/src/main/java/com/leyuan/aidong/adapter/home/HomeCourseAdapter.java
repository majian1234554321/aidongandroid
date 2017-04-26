package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.home.activity.CourseVideoDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页课程适配器
 * Created by song on 2017/4/11.
 */
public class HomeCourseAdapter extends RecyclerView.Adapter<HomeCourseAdapter.CourseHolder>{
    private Context context;
    private List<CategoryBean> data = new ArrayList<>();

    public HomeCourseAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CategoryBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_course_new,parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        CategoryBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundImage(bean.getImage(),holder.cover);
        holder.name.setText(bean.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              context.startActivity(new Intent(context, CourseVideoDetailActivity.class));
            }
        });
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        public CourseHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.iv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
