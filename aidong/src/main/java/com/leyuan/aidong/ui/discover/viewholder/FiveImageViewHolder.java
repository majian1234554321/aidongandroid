package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ImageRectUtils;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.utils.constant.DynamicType;
import com.leyuan.aidong.utils.qiniu.QiNiuImageProcessUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 图片
 * Created by song on 2017/2/16.
 */
public class FiveImageViewHolder extends BaseCircleViewHolder{
    private ImageView one;
    private ImageView two;
    private ImageView three;
    private ImageView four;
    private ImageView five;

    public FiveImageViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {
        one = (ImageView) itemView.findViewById(R.id.dv_one);
        two = (ImageView) itemView.findViewById(R.id.dv_two);
        three = (ImageView) itemView.findViewById(R.id.dv_three);
        four = (ImageView) itemView.findViewById(R.id.dv_four);
        five = (ImageView) itemView.findViewById(R.id.dv_five);

    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, @DynamicType int viewType) {
        int bigWidth =  (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context,5))/2;
        int smallWidth =  (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context,10))/3;

        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(0),
                bigWidth), one);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(1),
                bigWidth), two);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(2),
                smallWidth), three);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(3),
                smallWidth), four);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(4),
                smallWidth), five);
        final List<ImageView> imageViewList = new LinkedList<>();
        imageViewList.add(one);
        imageViewList.add(two);
        imageViewList.add(three);
        imageViewList.add(four);
        imageViewList.add(five);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    final  List<Rect> rectList = ImageRectUtils.getDrawableRects(imageViewList);
                    callback.onImageClick(data.image,rectList ,0);
                }
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    final  List<Rect> rectList = ImageRectUtils.getDrawableRects(imageViewList);
                    callback.onImageClick(data.image,rectList ,1);
                }
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    final  List<Rect> rectList = ImageRectUtils.getDrawableRects(imageViewList);
                    callback.onImageClick(data.image,rectList,2);
                }
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    final  List<Rect> rectList = ImageRectUtils.getDrawableRects(imageViewList);
                    callback.onImageClick(data.image,rectList,3);
                }
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    final  List<Rect> rectList = ImageRectUtils.getDrawableRects(imageViewList);
                    callback.onImageClick(data.image,rectList,4);
                }
            }
        });
    }
}
