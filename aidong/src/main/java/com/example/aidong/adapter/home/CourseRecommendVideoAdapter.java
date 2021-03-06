package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.CourseVideoBean;
import com.example.aidong .utils.DensityUtil;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.ScreenUtil;
import com.example.aidong .utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class CourseRecommendVideoAdapter extends RecyclerView.Adapter<CourseRecommendVideoAdapter.ViewHolder> {
    private static final float IMAGE_RATIO = 317/176f;
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
                    listener.onItemClick(bean.getCat_id(),bean.getId());
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
            ViewGroup.LayoutParams params = imgCover.getLayoutParams();
            float width = (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context, 30)) / 2f;
            params.height = (int) (width / IMAGE_RATIO);
        }
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(String catId,String videoId);
    }
}
