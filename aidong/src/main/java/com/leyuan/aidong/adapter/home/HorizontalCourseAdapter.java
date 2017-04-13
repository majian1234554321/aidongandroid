package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页课程适配器
 * Created by song on 2017/4/12.
 */
public class HorizontalCourseAdapter extends RecyclerView.Adapter<HorizontalCourseAdapter.CourseHolder>{
    private Context context;
    private List<CourseBean> data = new ArrayList<>();

    public HorizontalCourseAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBean> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_course,parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        CourseBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundImage(bean.getCover(),holder.cover);
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        public CourseHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.iv_cover);
        }
    }
}
