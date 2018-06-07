package com.leyuan.aidong.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.aidong.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.leyuan.aidong.ui.App.context;

/**
 * glide
 * Created by song on 2017/2/21.
 */
public class GlideLoader {
    private static GlideLoader INSTANCE = new GlideLoader();

    private GlideLoader() {

    }

    public static GlideLoader getInstance() {
        return INSTANCE;
    }

    public void displayImage(String imgUrl, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .thumbnail(0.2f)
                .centerCrop()
                .placeholder(new ColorDrawable(0xffc6c6c6))
                .into(imageView);
    }

    public void displayImage2(String imgUrl, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .thumbnail(0.2f)
                .centerCrop()
                .placeholder(R.drawable.img_default)
                .into(imageView);
    }

    public void displayCircleImage(String imgUrl, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .bitmapTransform(new CropCircleTransformation(getContext(imageView)))
                .placeholder(R.drawable.icon_avatar_default)
                .into(imageView);
    }

    public void displayRoundImage(String imgUrl, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .bitmapTransform(new CenterCrop(getContext(imageView)),
                        new RoundedCornersTransformation(getContext(imageView), DensityUtil.dp2px(getContext(imageView),5), 0))
                .placeholder(new ColorDrawable(0xffc6c6c6))
                .into(imageView);
    }


    public void displayRoundAvatarImage(String imgUrl, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .bitmapTransform(new CropCircleTransformation(getContext(imageView)))
                .placeholder(R.drawable.icon_avatar_default)
                .into(imageView);

//        Glide.with(getContext(imageView))
//                .load(imgUrl)
//                .bitmapTransform(new CenterCrop(getContext(imageView)),
//                        new RoundedCornersTransformation(getContext(imageView), DensityUtil.dp2px(getContext(imageView),7), 0))
//                .placeholder(R.drawable.icon_avatar_default)
//                .into(imageView);
    }

    public void displayRoundLocalImage(@DrawableRes int resId, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(resId)
                .bitmapTransform(new CenterCrop(getContext(imageView)),
                        new RoundedCornersTransformation(getContext(imageView), DensityUtil.dp2px(getContext(imageView),7), 0))
                .placeholder(new ColorDrawable(0xffc6c6c6))
                .into(imageView);
    }

    public void displayCircleImage(@DrawableRes int resId, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(resId)
                .bitmapTransform(new CropCircleTransformation(getContext(imageView)))
                .placeholder(R.drawable.icon_avatar_default)
                .into(imageView);
    }

    public void displayDrawableGifImage(@DrawableRes int resId, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(resId)
                .asGif()
                .into(imageView);
    }


    public void displayImageWithBlur(String imgUrl, ImageView imageView) {
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .thumbnail(0.2f)
                .placeholder(new ColorDrawable(0xffc6c6c6))
                // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”
                .bitmapTransform(new BlurTransformation(getContext(imageView),25,4))
                .into(imageView);
    }

    private Context getContext(@Nullable ImageView imageView) {
        if (imageView!=null&&imageView.getContext()!=null){
            return  imageView.getContext();
        }else {
            return context;
        }
    }
}
