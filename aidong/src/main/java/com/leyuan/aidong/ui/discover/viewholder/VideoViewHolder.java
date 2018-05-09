package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ScreenUtil;
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
        final ViewGroup.LayoutParams p = ivVideo.getLayoutParams();




        Glide.with(context)
                .load(data.videos.cover)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
//                        Log.d("VideoViewHolder", "width " + width); //200px
//                        Log.d("VideoViewHolder", "height " + height); //200px


                        if (width<=height){
                            float heights = ScreenUtil.getScreenWidth(context) / 2f;
                            float widths =  (float)ScreenUtil.getScreenWidth(context) / ScreenUtil.getScreenHeight(context) * heights;
                            p.height = (int) heights;
                            p.width = (int) widths;
                            ivVideo.setLayoutParams(p);
                            ivVideo.setImageBitmap(bitmap);

                            Log.d("VideoViewHolder", "width1 " + heights); //200px
                            Log.d("VideoViewHolder", "height1 " + widths); //200px

                        }else {
                            float heights = ScreenUtil.getScreenWidth(context) / 2f;
                            float widths =  (float)ScreenUtil.getScreenWidth(context) / ScreenUtil.getScreenHeight(context) * heights;
                            p.height = (int) widths;
                            p.width = (int) heights;
                            ivVideo.setLayoutParams(p);
                            ivVideo.setImageBitmap(bitmap);


                            Log.d("VideoViewHolder", "width2 " + heights); //200px
                            Log.d("VideoViewHolder", "height2 " + widths); //200px

                        }

                    }
                });


       // GlideLoader.getInstance().displayImage(data.videos.cover, ivVideo);

        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onVideoClick(data.videos.url,ivVideo);
                }
            }
        });
    }
}
