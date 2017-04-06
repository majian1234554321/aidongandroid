package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.constant.DynamicType;

/**
 * 视频
 * Created by song on 2017/2/16.
 */
public class VideoViewHolder extends BaseCircleViewHolder{
    private ImageView ivVideo;

    public VideoViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {
        ivVideo = (ImageView) itemView.findViewById(R.id.dv_video);
    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, @DynamicType int viewType) {
        if(data.videos == null){
            return;
        }
        GlideLoader.getInstance().displayImage(data.videos.cover, ivVideo);
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onVideoClick(data.videos.url);
                }
            }
        });
    }
}
