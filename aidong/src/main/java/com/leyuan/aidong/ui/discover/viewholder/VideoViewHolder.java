package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.GlideLoader;

/**
 * 视频
 * Created by song on 2017/2/16.
 */
public class VideoViewHolder extends BaseCircleViewHolder{

    private ImageView dvVideo;
    private ImageButton ibPlay;

    public VideoViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {

        dvVideo = (ImageView) itemView.findViewById(R.id.dv_video);
        ibPlay = (ImageButton) itemView.findViewById(R.id.ib_play);

    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, int viewType) {
        GlideLoader.getInstance().displayImage(data.video.cover, dvVideo);
        dvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onVideoClick(data.video.url);
                }
            }
        });
    }
}
