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

    /*    String url = data.videos.cover;
        ViewGroup.LayoutParams p = ivVideo.getLayoutParams();

        float imageWidth;
        float imageHeight;
        try {
            imageWidth = FormatUtil.parseFloat(url.substring(url.indexOf("w=") + 2,url.indexOf("_h")));
            imageHeight = FormatUtil.parseFloat(url.substring(url.indexOf("h=") + 2,url.lastIndexOf(".")));
        }catch (Exception e){
            imageWidth = 400;
            imageHeight = 400;
            e.printStackTrace();
        }
        if(imageHeight >= imageWidth){          //竖图
            int height = (int) (ScreenUtil.getScreenWidth(context) / 2f);
            int width = (int) (imageWidth / imageHeight * height);
            p.height = height;
            p.width = width;
        }else{                                 //横图
            int width = (int) (ScreenUtil.getScreenWidth(context)* 2 / 5f);
            int height = (int) (imageHeight / imageWidth * width);
            p.width = width;
            p.height = height;
        }
*/
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
