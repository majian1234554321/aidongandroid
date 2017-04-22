package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.VideoDetail;

import java.util.ArrayList;
import java.util.List;


public class RelatedVideoAdapter extends RecyclerView.Adapter<RelatedVideoAdapter.VideoHolder> {
    private Context context;
    private List<VideoDetail> data = new ArrayList<>();


    public RelatedVideoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VideoDetail> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }else {
           /* for (int i = 0; i < 10; i++) {
                VideoDetail b = new VideoDetail();
                b.setVideoName("测试视频" + i);
                b.setDuring("01:1" + i);
                if(i % 2 ==0){
                    b.setCover("http://wx2.sinaimg.cn/mw690/8245bf01ly1fekbu5bsjsj20ku0anq4c.jpg");
                }else {
                    b.setCover("http://wx1.sinaimg.cn/thumb180/6a5bce1bly1fekbxt4nx4j20k00j4t9y.jpg");
                }
                this.data.add(b);
            }*/
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_relate_video, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        VideoDetail bean = data.get(position);
       // ImageLoader.getInstance().displayImage(bean.getCover(),holder.cover);
        holder.name.setText(bean.getVideoName());
        holder.duration.setText(bean.getDuring());
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
