package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;


public class MoreVideoAdapter extends RecyclerView.Adapter<MoreVideoAdapter.VideoHolder> {
    private Context context;
    private List<CourseVideoBean> data = new ArrayList<>();


    public MoreVideoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseVideoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_more_video, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        CourseVideoBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(),holder.cover);
        holder.name.setText(bean.getName());
        holder.duration.setText("#"+bean.getType_name() );


    }

    class VideoHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        ImageView play;
        TextView name;
        TextView duration;

        public VideoHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.iv_cover);
            play = (ImageView) itemView.findViewById(R.id.iv_play);
            name = (TextView) itemView.findViewById(R.id.txt_course_name);
            duration = (TextView) itemView.findViewById(R.id.txt_course_type_duration);
        }
    }
}
