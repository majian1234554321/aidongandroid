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

package com.leyuan.aidong.module.photopicker.boxing_impl.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.AbsBoxingViewActivity;
import com.leyuan.aidong.module.photopicker.boxing.loader.IBoxingCallback;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.ImageMedia;
import com.leyuan.aidong.module.photopicker.boxing.utils.BoxingLog;

import java.lang.ref.WeakReference;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * show raw image with the control of finger gesture.
 *
 * @author ChenSL
 */
public class BoxingPreviewFragment extends Fragment {
    private static final String BUNDLE_IMAGE = "com.bilibili.boxing_impl.ui.BoxingPreviewFragment.image";
    private static final int MAX_SCALE = 15;
    private PhotoView mImageView;
    private ProgressBar mProgress;
    private ImageMedia mMedia;
    private PhotoViewAttacher mAttacher;

    public static BoxingPreviewFragment newInstance(@NonNull ImageMedia image) {
        BoxingPreviewFragment fragment = new BoxingPreviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMedia = getArguments().getParcelable(BUNDLE_IMAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_boxing_raw_image, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgress = (ProgressBar) view.findViewById(R.id.loading);
        mImageView = (PhotoView) view.findViewById(R.id.photo_view);
        mAttacher = new PhotoViewAttacher(mImageView);
        //mAttacher.setRotatable(true);
        //mAttacher.setToRightAngle(true);
        ((AbsBoxingViewActivity) getActivity()).loadRawImage(mImageView, mMedia.getPath(), new BoxingCallback(this));
    }

    private void dismissProgressDialog() {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
        }
        BoxingPreviewActivity activity = getThisActivity();
        if (activity != null && activity.mProgressBar != null) {
            activity.mProgressBar.setVisibility(View.GONE);
        }
    }

    private BoxingPreviewActivity getThisActivity() {
        Activity activity = getActivity();
        if (activity instanceof BoxingPreviewActivity) {
            return (BoxingPreviewActivity) activity;
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAttacher != null) {
            mAttacher.cleanup();
            mAttacher = null;
            mImageView = null;
        }
    }

    private static class BoxingCallback implements IBoxingCallback {
        private WeakReference<BoxingPreviewFragment> mWr;

        BoxingCallback(BoxingPreviewFragment fragment) {
            mWr = new WeakReference<>(fragment);
        }

        @Override
        public void onSuccess() {
            if (mWr.get() == null || mWr.get().mImageView == null) {
                return;
            }
            mWr.get().dismissProgressDialog();
            Drawable drawable = mWr.get().mImageView.getDrawable();
            PhotoViewAttacher attacher = mWr.get().mAttacher;
            if (attacher != null) {
                if (drawable.getIntrinsicHeight() > (drawable.getIntrinsicWidth() << 2)) {
                    // handle the super height image.
                    int scale = drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                    scale = Math.min(MAX_SCALE, scale);
                    attacher.setMaximumScale(scale);
                    attacher.setScale(scale, true);
                }
                attacher.update();
            }
            BoxingPreviewActivity activity = mWr.get().getThisActivity();
            if (activity != null && activity.mGallery != null) {
                activity.mGallery.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFail(Throwable t) {
            if (mWr.get() == null) {
                return;
            }
            BoxingLog.d(t != null ? t.getMessage() : "load raw image error.");
            mWr.get().mImageView.setImageResource(R.drawable.ic_broken_image);
            if (mWr.get().mAttacher != null) {
                mWr.get().mAttacher.update();
            }
        }
    }
}
