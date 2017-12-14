package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.WatchOfficeCourseBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

/**
 * 视界专题详情界面展开中相关课程ListView适配器
 * Created by song on 2016/7/25.
 */
public class WatchOfficeRelateCoursesAdapter extends RecyclerView.Adapter<WatchOfficeRelateCoursesAdapter.ViewHolder> {

    private OnRelateCourseClickListener listener;
    Context context;
    private List<WatchOfficeCourseBean> courseList;

    public WatchOfficeRelateCoursesAdapter(Context context, OnRelateCourseClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_relate_courses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WatchOfficeCourseBean bean = courseList.get(position);

        holder.txtCourseName.setText(bean.getDictName());
        GlideLoader.getInstance().displayImage(bean.getConUrl(), holder.ivCourseCover);
        holder.ivCourseCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onCourseClick(bean);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (courseList == null)
            return 0;
        return courseList.size();
    }

    public void addList(List<WatchOfficeCourseBean> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCourseCover;
        private TextView txtCourseName;

        public ViewHolder(View view) {
            super(view);
            ivCourseCover = (ImageView) view.findViewById(R.id.iv_course_cover);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
        }
    }

    public interface OnRelateCourseClickListener {
        void onCourseClick(WatchOfficeCourseBean bean);
    }
}
