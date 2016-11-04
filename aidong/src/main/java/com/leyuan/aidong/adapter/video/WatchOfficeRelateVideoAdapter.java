package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.VideoDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 视界专题详情界面展开中相关视频RecyclerView 适配器
 * Created song pc on 2016/7/25.
 */
public class WatchOfficeRelateVideoAdapter extends RecyclerView.Adapter<WatchOfficeRelateVideoAdapter.RelateVideoViewHolder>{
    private List<VideoDetail> data = new ArrayList<>();
    private Context context;
    private ImageLoader imageLoader ;
    private DisplayImageOptions options;
    private OnVideoItemCLickListener listener;
    public WatchOfficeRelateVideoAdapter(Context context, ImageLoader imageLoader, DisplayImageOptions options,OnVideoItemCLickListener listener) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.options = options;
        this.listener = listener;
    }

    public void setData(List<VideoDetail> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RelateVideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_relate_video,null);
        RelateVideoViewHolder holder = new RelateVideoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RelateVideoViewHolder holder, int position) {
        final VideoDetail bean = data.get(position);
        imageLoader.displayImage(bean.getCover(),holder.cover,options);
        holder.name.setText(bean.getVideoName());
        holder.timeAndIndex.setText(new StringBuilder().append("第"+bean.getPhase()+"集").append("/").append(bean.getDuring()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onVideoClick(bean);
            }
        });
    }

    class RelateVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        TextView timeAndIndex;
        View itemView;

        public RelateVideoViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cover = (ImageView)itemView.findViewById(R.id.iv_video_cover);
            name = (TextView)itemView.findViewById(R.id.tv_video_name);
            timeAndIndex = (TextView)itemView.findViewById(R.id.tv_video_time_and_index);
        }
    }

    public interface OnVideoItemCLickListener{
        void onVideoClick(VideoDetail bean);
    }
}

