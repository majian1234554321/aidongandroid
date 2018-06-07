/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.leyuan.aidong.module.photopicker.boxing_impl.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.BoxingMediaLoader;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.ImageMedia;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.WindowManagerHelper;
import com.leyuan.aidong.module.photopicker.boxing_impl.utils.OptAnimationLoader;
import com.leyuan.aidong.utils.Constant;


/**
 * A media layout for {@link android.support.v7.widget.RecyclerView} item, including image and video <br/>
 *
 * @author ChenSL
 */
public class MediaItemLayout extends FrameLayout {
    private static final int BIG_IMG_SIZE = 5 * 1024 * 1024;

    private Context context;
    private ImageView mCheckImg;
    private View mVideoLayout;
    private View mFontLayout;
    private View disabledLayout;
    private ImageView mCoverImg;
    private ScreenType mScreenType;

    private enum ScreenType {
        SMALL(100), NORMAL(180), LARGE(320);
        int value;

        ScreenType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public MediaItemLayout(Context context) {
        this(context, null, 0);
    }

    public MediaItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_media_item, this, true);
        mCoverImg = (ImageView) view.findViewById(R.id.media_item);
        mCheckImg = (ImageView) view.findViewById(R.id.media_item_check);
        mVideoLayout = view.findViewById(R.id.video_layout);
        mFontLayout = view.findViewById(R.id.media_font_layout);
        disabledLayout = view.findViewById(R.id.media_disabled);
        mScreenType = getScreenType(context);
        setImageRect(context);
    }

    private void setImageRect(Context context) {
        int screenHeight = WindowManagerHelper.getScreenHeight(context);
        int screenWidth = WindowManagerHelper.getScreenWidth(context);
        int width = 100;
        if (screenHeight != 0 && screenWidth != 0) {
            width = (screenWidth - getResources().getDimensionPixelOffset(R.dimen.media_margin) * 4) / 3;
        }
        mCoverImg.getLayoutParams().width = width;
        mCoverImg.getLayoutParams().height = width;
        mFontLayout.getLayoutParams().width = width;
        mFontLayout.getLayoutParams().height = width;
    }

    private ScreenType getScreenType(Context context) {
        int type = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        ScreenType result;
        switch (type) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                result = ScreenType.SMALL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                result = ScreenType.NORMAL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                result = ScreenType.LARGE;
                break;
            default:
                result = ScreenType.NORMAL;
                break;
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public void setDrawable(Drawable drawable) {
        if (mCoverImg != null) {
            mCoverImg.setImageDrawable(drawable);
        }
    }

    public void setMedia(BaseMedia media) {
        if (media instanceof ImageMedia) {
            mVideoLayout.setVisibility(GONE);
            setImageCover(((ImageMedia) media).getThumbnailPath());
        } else if (media instanceof VideoMedia) {
            mVideoLayout.setVisibility(VISIBLE);
            VideoMedia videoMedia = (VideoMedia) media;
            ((TextView) mVideoLayout.findViewById(R.id.video_duration_txt)).setText(videoMedia.getDuration());
            ((TextView) mVideoLayout.findViewById(R.id.video_size_txt)).setText(videoMedia.getSizeByUnit());
            setVideoCover(videoMedia.getPath(),videoMedia.getOriginalDuration());
        }
    }

    private void setImageCover(@NonNull String path) {
        if (mCoverImg == null || TextUtils.isEmpty(path)) {
            return;
        }
        BoxingMediaLoader.getInstance().displayThumbnail(mCoverImg, path, mScreenType.getValue(), mScreenType.getValue());
    }

    private void setVideoCover(@NonNull String path,Long during) {
        if (mCoverImg == null || TextUtils.isEmpty(path)) {
            return;
        }
//        if(during >= Constant.UPLOAD_VIDEO_MAX_DURATION){
//            disabledLayout.setVisibility(VISIBLE);
//        }else
        if(during < Constant.UPLOAD_VIDEO_MIN_DURATION) {
            disabledLayout.setVisibility(VISIBLE);
        }else {
            disabledLayout.setVisibility(GONE);
        }
        BoxingMediaLoader.getInstance().displayThumbnail(mCoverImg, path, mScreenType.getValue(), mScreenType.getValue());
    }

    public void setAnimatedChecked(boolean isChecked){
        if (isChecked) {
            mFontLayout.setVisibility(View.VISIBLE);
            mCheckImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_checked_red));
            Animation animation = OptAnimationLoader.loadAnimation(context, R.anim.modal_in);
            mCheckImg.startAnimation(animation);
        } else {
            mFontLayout.setVisibility(View.GONE);
            mCheckImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_unchecked_white));
            Animation animation = OptAnimationLoader.loadAnimation(context, R.anim.modal_out);
            mCheckImg.startAnimation(animation);
        }
    }

    @SuppressWarnings("deprecation")
    public void setChecked(boolean isChecked) {
        if (isChecked) {
            mFontLayout.setVisibility(View.VISIBLE);
            mCheckImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_checked_red));
        } else {
            mFontLayout.setVisibility(View.GONE);
            mCheckImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_unchecked_white));
        }
    }

}
