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
public class SixImageViewHolder extends BaseCircleViewHolder{
    private ImageView one;
    private ImageView two;
    private ImageView three;
    private ImageView four;
    private ImageView five;
    private ImageView six;

    public SixImageViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {
        one = (ImageView) itemView.findViewById(R.id.dv_one);
        two = (ImageView) itemView.findViewById(R.id.dv_two);
        three = (ImageView) itemView.findViewById(R.id.dv_three);
        four = (ImageView) itemView.findViewById(R.id.dv_four);
        five = (ImageView) itemView.findViewById(R.id.dv_five);
        six = (ImageView) itemView.findViewById(R.id.dv_six);
    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, @DynamicType int viewType) {
        int width =  (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context,10))/3;
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(0),
                width), one);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(1),
                width), two);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(2),
                width), three);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(3),
                width), four);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(4),
                width), five);
        GlideLoader.getInstance().displayImage(QiNiuImageProcessUtils.minWidthScale(context,data.image.get(5),
                width), six);

        final List<ImageView> imageViewList = new LinkedList<>();
        imageViewList.add(one);
        imageViewList.add(two);
        imageViewList.add(three);
        imageViewList.add(four);
        imageViewList.add(five);
        imageViewList.add(six);

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

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    final  List<Rect> rectList = ImageRectUtils.getDrawableRects(imageViewList);
                    callback.onImageClick(data.image,rectList,5);
                }
            }
        });
    }
}
