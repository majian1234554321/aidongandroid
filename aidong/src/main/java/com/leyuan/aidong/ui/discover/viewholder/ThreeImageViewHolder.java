package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.ImageRectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 图片
 * Created by song on 2017/2/16.
 */
public class ThreeImageViewHolder extends BaseCircleViewHolder{
    private SimpleDraweeView one;
    private SimpleDraweeView two;
    private SimpleDraweeView three;

    public ThreeImageViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
    }

    @Override
    public void onFindChildView(@NonNull View rootView) {
        one = (SimpleDraweeView) itemView.findViewById(R.id.dv_one);
        two = (SimpleDraweeView) itemView.findViewById(R.id.dv_two);
        three = (SimpleDraweeView) itemView.findViewById(R.id.dv_three);
    }

    @Override
    public void onBindDataToChildView(@NonNull final DynamicBean data, int position, int viewType) {
        one.setImageURI(data.image.get(0));
        two.setImageURI(data.image.get(1));
        three.setImageURI(data.image.get(2));

        final List<ImageView> imageViewList = new LinkedList<>();
        imageViewList.add(one);
        imageViewList.add(two);
        imageViewList.add(three);

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
    }
}
