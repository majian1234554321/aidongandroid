package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.ui.course.CourseVideoDetailActivityNew;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class DetailsRelativeViedeoAdapter extends RecyclerView.Adapter<DetailsRelativeViedeoAdapter.ViewHolder> {


    private final Context context;
    private String courseId;
    private List<CourseVideoBean> videos;

    public DetailsRelativeViedeoAdapter(Context context) {
        this.context = context;
    }

    public DetailsRelativeViedeoAdapter(Context context, String id) {
        this.context = context;
        this.courseId = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details_relative_video, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CourseVideoBean bean = videos.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.img_cover);
        holder.txtSubTitle.setText("#"+bean.getTypeName()+"#");
        holder.txtTitle.setText(bean.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseVideoDetailActivityNew.start(context, courseId, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videos == null)
            return 0;
//        return videos.size() >= 2 ? 2 : videos.size();
        return videos.size();
    }


    public void setData(List<CourseVideoBean> videos) {

        if(videos.size()<=2){
            this.videos = videos;
        }else {
            this.videos = videos.subList(0,2);
        }


        notifyDataSetChanged();
    }

    public void setCourseID(String courseId) {
        this.courseId = courseId;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_cover;
        private TextView txtSubTitle;
        private TextView txtTitle;

        public ViewHolder(View view) {
            super(view);
            img_cover = (ImageView) view.findViewById(R.id.img_cover);
            txtSubTitle = (TextView) view.findViewById(R.id.txt_sub_title);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
        }
    }
}
