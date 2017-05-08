package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class CourseRecommendVideoAdapter extends RecyclerView.Adapter<CourseRecommendVideoAdapter.ViewHolder> {
    private Context context;
    private List<CourseVideoBean> data = new ArrayList<>();
    private ItemClickListener listener;


    public CourseRecommendVideoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseVideoBean> data) {
        if(data != null) {
            data.remove(0);
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemt_course_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CourseVideoBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(),holder.imgCover);
        holder.txtCourseName.setText(bean.getName());
        String during = Utils.formatTime(Math.round(FormatUtil.parseFloat(bean.getDuring())));
        holder.txtCourseTypeDuration.setText(String.format(
                context.getString(R.string.course_type_and_during), bean.getTypeName(),during));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(bean.getId());
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private TextView txtCourseName;
        private TextView txtCourseTypeDuration;

        public ViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtCourseTypeDuration = (TextView) view.findViewById(R.id.txt_course_type_duration);
        }
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(String videoId);
    }
}
