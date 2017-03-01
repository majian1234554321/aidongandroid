package com.leyuan.aidong.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * glide
 * Created by song on 2017/2/21.
 */
public class GlideLoader {
    private static GlideLoader INSTANCE = new GlideLoader();
    private GlideLoader(){

    }
    public static GlideLoader getInstance() {
        return INSTANCE;
    }

    public void displayImage(String imgUrl, ImageView imageView) {
        displayImageWithDefaultConfig(imgUrl,imageView).into(imageView);
    }

    public void displayCircleImage(String imgUrl, ImageView imageView){
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .bitmapTransform(new CropCircleTransformation(getContext(imageView)))
                .placeholder(R.drawable.place_holder_user)
                .into(imageView);
    }

    public void displayRoundImage(String imgUrl,ImageView imageView){
        Glide.with(getContext(imageView))
                .load(imgUrl)
                .bitmapTransform(new RoundedCornersTransformation(getContext(imageView),20,20))
                .centerCrop()
                .placeholder(new ColorDrawable(0xffc6c6c6))
                .into(imageView);
    }

    public void displayDrawableGifImage(@DrawableRes int resId, ImageView imageView){
        Glide.with(getContext(imageView))
                .load(resId)
                .asGif()
                .into(imageView);
    }

    private BitmapRequestBuilder displayImageWithDefaultConfig(String imgUrl,ImageView imageView) {
        return Glide.with(getContext(imageView))
                .load(imgUrl)
                .asBitmap()
                .thumbnail(0.1f)
                .centerCrop()
                .placeholder(new ColorDrawable(0xffc6c6c6));
    }

    private Context getContext(@Nullable ImageView imageView) {
        if (imageView == null) {
            return App.context;
        }
        return imageView.getContext();
    }
}