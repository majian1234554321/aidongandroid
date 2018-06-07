package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MoreVideoAdapter extends RecyclerView.Adapter<MoreVideoAdapter.VideoHolder> {
    private static final float IMAGE_RATIO = 370/208f;
    private Context context;
    private List<CourseVideoBean> data = new ArrayList<>();
    private MoreVideoAdapter.OnItemClickListener listener;

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
        final CourseVideoBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(),holder.cover);
        holder.name.setText(bean.getName());
        String during = Utils.formatTime(Math.round(FormatUtil.parseFloat(bean.getDuring())));
        holder.duration.setText(String.format(context.getString(R.string.course_type_and_during),
                bean.getTypeName(),during) );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(bean.getId());
                }
            }
        });
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        RelativeLayout videoLayout;
        ImageView cover;
        ImageView play;
        TextView name;
        TextView duration;

        public VideoHolder(View itemView) {
            super(itemView);
            videoLayout = (RelativeLayout) itemView.findViewById(R.id.rl_video);
            cover = (ImageView) itemView.findViewById(R.id.iv_cover);
            play = (ImageView) itemView.findViewById(R.id.iv_play);
            name = (TextView) itemView.findViewById(R.id.txt_course_name);
            duration = (TextView) itemView.findViewById(R.id.txt_course_type_duration);

            ViewGroup.LayoutParams params = videoLayout.getLayoutParams();
            float screenWidth = (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context,10))/2f;
            params.height = (int) (screenWidth / IMAGE_RATIO);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(String videoId);
    }
}
