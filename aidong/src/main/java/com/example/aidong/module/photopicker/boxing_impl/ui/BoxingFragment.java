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

package com.example.aidong.module.photopicker.boxing_impl.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong .module.photopicker.boxing.AbsBoxingViewFragment;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.BoxingManager;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.AlbumEntity;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing.model.entity.impl.ImageMedia;
import com.example.aidong .module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.example.aidong .module.photopicker.boxing.utils.BoxingFileHelper;
import com.example.aidong .module.photopicker.boxing_impl.WindowManagerHelper;
import com.example.aidong .module.photopicker.boxing_impl.adapter.BoxingAlbumAdapter;
import com.example.aidong .module.photopicker.boxing_impl.adapter.BoxingMediaAdapter;
import com.example.aidong .module.photopicker.boxing_impl.view.MediaItemLayout;
import com.example.aidong .module.photopicker.boxing_impl.view.SpacesItemDecoration;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A full implement for {@link com.example.aidong .module.photopicker.boxing.presenter.PickerContract.View} supporting all the mode
 * in {@link BoxingConfig.Mode}.
 * use this to pick the picture.
 *
 * @author ChenSL
 */
public class BoxingFragment extends AbsBoxingViewFragment implements View.OnClickListener {

    public static final String TAG = "BoxingFragment";
    private static final int IMAGE_PREVIEW_REQUEST_CODE = 9086;
    private static final int IMAGE_CROP_REQUEST_CODE = 9087;

    private static final int GRID_COUNT = 3;
    private int mMaxCount = 6;
    private boolean mIsPreview;
    private boolean mIsCamera;

    private Button mPreBtn;
    private Button mOkBtn;
    private RecyclerView mRecycleView;
    private BoxingMediaAdapter mMediaAdapter;
    private BoxingAlbumAdapter mAlbumWindowAdapter;
    private ProgressDialog mDialog;
    private TextView mEmptyTxt;
    private TextView mTitleTxt;
    private PopupWindow mAlbumPopWindow;
    private ProgressBar mLoadingView;

    public static BoxingFragment newInstance() {
        return new BoxingFragment();
    }

    @Override
    public void onCreateWithSelectedMedias(Bundle savedInstanceState, @Nullable List<BaseMedia> selectedMedias) {
        mMaxCount = BoxingManager.getInstance().getBoxingConfig().getMaxCount();
        mAlbumWindowAdapter = new BoxingAlbumAdapter(getContext());
        mMediaAdapter = new BoxingMediaAdapter(getContext());
//        Logger.i("media","onCreateWithSelectedMedias size =   " + selectedMedias.size());
        mMediaAdapter.setSelectedMedias(selectedMedias);
    }

    @Override
    public void startLoadingMedia() {
        loadMedias();
        loadAlbum();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if (requestCode == STORAGE_PERMISSION) {
            startLoadingMedia();
        } else if (requestCode == CAMERA_AND_AUDIO_PERMISSIONS) {
            startCamera(getActivity(), this, null);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        if (requestCode == STORAGE_PERMISSION) {
            Toast.makeText(getContext(), R.string.storage_permission_deny, Toast.LENGTH_LONG).show();
            showEmptyData();
            getActivity().finish();
        } else if (requestCode == CAMERA_AND_AUDIO_PERMISSIONS) {
            Toast.makeText(getContext(), R.string.camera_permission_deny, Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmant_boxing_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        mEmptyTxt = (TextView) view.findViewById(R.id.empty_txt);
        mRecycleView = (RecyclerView) view.findViewById(R.id.media_recycleview);
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading);
        initRecycleView();

        boolean isMultiImageMode = BoxingManager.getInstance().getBoxingConfig().isMultiImageMode();
        View multiImageLayout = view.findViewById(R.id.multi_picker_layout);
        multiImageLayout.setVisibility(isMultiImageMode ? View.VISIBLE : View.GONE);
        if (isMultiImageMode) {
            mPreBtn = (Button) view.findViewById(R.id.choose_preview_btn);
            mOkBtn = (Button) view.findViewById(R.id.choose_ok_btn);

            mPreBtn.setOnClickListener(this);
            mOkBtn.setOnClickListener(this);
            updateMultiPickerLayoutState(mMediaAdapter.getSelectedMedias());
        }
    }

    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), GRID_COUNT);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.media_margin), GRID_COUNT));
        mMediaAdapter.setOnCameraClickListener(new OnCameraClickListener());
        mMediaAdapter.setOnCheckedListener(new OnMediaCheckedListener());
        mMediaAdapter.setOnMediaClickListener(new OnMediaClickListener());
        mRecycleView.setAdapter(mMediaAdapter);
        mRecycleView.addOnScrollListener(new ScrollListener());
    }

    @Override
    public void showMedia(List<BaseMedia> medias, int allCount) {
        if (medias == null || isEmptyData(medias)
                && isEmptyData(mMediaAdapter.getAllMedias())) {
            showEmptyData();
            return;
        }
        showData();
        Logger.i("media","showMedia size =   " + medias.size());
        mMediaAdapter.addAllData(medias);
        checkSelectedMedia(medias, mMediaAdapter.getSelectedMedias());
    }

    private boolean isEmptyData(List<BaseMedia> medias) {
        return medias.isEmpty() && !BoxingManager.getInstance().getBoxingConfig().isNeedCamera();
    }

    private void showEmptyData() {

        Logger.i(TAG,"showEmptyData");
        mLoadingView.setVisibility(View.GONE);
        mEmptyTxt.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    private void showData() {
        Logger.i(TAG,"showData");
        mLoadingView.setVisibility(View.GONE);
        mEmptyTxt.setVisibility(View.GONE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAlbum(@Nullable List<AlbumEntity> albums) {
        if ((albums == null || albums.isEmpty())
                && mTitleTxt != null) {
            mTitleTxt.setCompoundDrawables(null, null, null, null);
            mTitleTxt.setOnClickListener(null);
            return;
        }
        mAlbumWindowAdapter.addAllData(albums);
    }

    public BoxingMediaAdapter getMediaAdapter() {
        return mMediaAdapter;
    }

    @Override
    public void clearMedia() {
        mMediaAdapter.clearData();
    }

    private void updateMultiPickerLayoutState(List<BaseMedia> medias) {
        updateOkBtnState(medias);
        updatePreviewBtnState(medias);
    }

    private void updatePreviewBtnState(List<BaseMedia> medias) {
        if (mPreBtn == null || medias == null) {
            return;
        }
        boolean enabled = medias.size() > 0 && medias.size() <= mMaxCount;
        mPreBtn.setEnabled(enabled);
    }

    private void updateOkBtnState(List<BaseMedia> medias) {
        if (mOkBtn == null || medias == null) {
            return;
        }
        boolean enabled = medias.size() > 0 && medias.size() <= mMaxCount;
        mOkBtn.setEnabled(enabled);
        mOkBtn.setText(enabled ? getString(R.string.image_select_ok_fmt, String.valueOf(medias.size())
                , String.valueOf(mMaxCount)) : getString(R.string.ok));
    }

    @Override
    public void onCameraFinish(BaseMedia media) {
        dismissProgressDialog();
        mIsCamera = false;
        if (media == null) {
            return;
        }
        if (hasCropBehavior()) {
            startCrop(media, IMAGE_CROP_REQUEST_CODE);
        } else if (mMediaAdapter != null && mMediaAdapter.getSelectedMedias() != null) {
            List<BaseMedia> selectedMedias = mMediaAdapter.getSelectedMedias();
            selectedMedias.add(media);
            onFinish(selectedMedias);
        }
    }

    @Override
    public void onCameraError() {
        mIsCamera = false;
        dismissProgressDialog();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.choose_ok_btn) {
            onFinish(mMediaAdapter.getSelectedMedias());
        } else if (id == R.id.choose_preview_btn) {
            if (!mIsPreview) {
                mIsPreview = true;
                ArrayList<BaseMedia> medias = (ArrayList<BaseMedia>) mMediaAdapter.getSelectedMedias();
                Boxing.get().withIntent(getActivity(), BoxingPreviewActivity.class, medias)
                        .start(this, BoxingFragment.IMAGE_PREVIEW_REQUEST_CODE, BoxingConfig.ViewMode.PRE_EDIT);

            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PREVIEW_REQUEST_CODE) {
            mIsPreview = false;
            boolean isBackClick = data.getBooleanExtra(BoxingPreviewActivity.EXTRA_TYPE_BACK, false);
            List<BaseMedia> selectedMedias = data.getParcelableArrayListExtra(Boxing.EXTRA_SELECTED_MEDIA);
            onViewActivityRequest(selectedMedias, mMediaAdapter.getAllMedias(), isBackClick);
            if (isBackClick) {
                mMediaAdapter.setSelectedMedias(selectedMedias);
                mMediaAdapter.notifyDataSetChanged();
            }
            updateMultiPickerLayoutState(selectedMedias);
        }

    }

    private void onViewActivityRequest(List<BaseMedia> selectedMedias, List<BaseMedia> allMedias, boolean isBackClick) {
        if (isBackClick) {

            checkSelectedMedia(allMedias, selectedMedias);
        } else {
            onFinish(selectedMedias);
        }
    }


    @Override
    public void onCameraActivityResult(int requestCode, int resultCode, Intent data) {
        showProgressDialog();
        super.onCameraActivityResult(requestCode, resultCode, data);
    }

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.handling));
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.hide();
            mDialog.dismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<BaseMedia> medias = (ArrayList<BaseMedia>) getMediaAdapter().getSelectedMedias();
        onSaveMedias(outState, medias);
    }

    public void setTitleTxt(TextView titleTxt) {
        mTitleTxt = titleTxt;
        mTitleTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAlbumPopWindow == null) {
                    int height = WindowManagerHelper.getScreenHeight(v.getContext()) -
                            (WindowManagerHelper.getToolbarHeight(v.getContext())
                                    + WindowManagerHelper.getStatusBarHeight(v.getContext()));
                    View windowView = createWindowView();
                    mAlbumPopWindow = new PopupWindow(windowView, ViewGroup.LayoutParams.MATCH_PARENT,
                            height, true);
                    //mAlbumPopWindow.setAnimationStyle(R.style.progressDialog);
                    mAlbumPopWindow.setOutsideTouchable(true);
                    mAlbumPopWindow.setBackgroundDrawable(new ColorDrawable
                            (ContextCompat.getColor(v.getContext(), R.color.colorPrimaryAlpha)));
                    mAlbumPopWindow.setContentView(windowView);
                }
                mAlbumPopWindow.showAsDropDown(v, 0, 0);
            }

            @NonNull
            private View createWindowView() {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_album, null);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_recycleview);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.addItemDecoration(new SpacesItemDecoration(2, 1));

                View albumShadowLayout = view.findViewById(R.id.album_shadow);
                albumShadowLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissAlbumWindow();
                    }
                });
                mAlbumWindowAdapter.setAlbumOnClickListener(new OnAlbumItemOnClickListener());
                recyclerView.setAdapter(mAlbumWindowAdapter);
                return view;
            }
        });
    }

    private void dismissAlbumWindow() {
        if (mAlbumPopWindow != null && mAlbumPopWindow.isShowing()) {
            mAlbumPopWindow.dismiss();
        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();
            if (childCount > 0) {
                View lastChild = recyclerView.getChildAt(childCount - 1);
                RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
                int lastVisible = recyclerView.getChildAdapterPosition(lastChild);
                if (lastVisible == outerAdapter.getItemCount() - 1 && hasNextPage() && canLoadNextPage()) {
                    onLoadNextPage();
                }
            }
        }
    }

    private class OnMediaClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            BaseMedia media = (BaseMedia) v.getTag();
            int pos = (int) v.getTag(R.id.media_item_check);
            BoxingConfig.Mode mode = BoxingManager.getInstance().getBoxingConfig().getMode();
            if (mode == BoxingConfig.Mode.SINGLE_IMG) {
                singleImageClick(media);
            } else if (mode == BoxingConfig.Mode.MULTI_IMG) {
                multiImageClick(pos);
            } else if (mode == BoxingConfig.Mode.VIDEO) {
                videoClick(media);
            }
        }

        private void videoClick(BaseMedia media) {
//            if (((VideoMedia) media).getOriginalDuration() >= Constant.UPLOAD_VIDEO_MAX_DURATION) {
//                Toast.makeText(getContext(), "视频大于60s,暂不支持", Toast.LENGTH_LONG).show();
//            } else

            if (((VideoMedia) media).getOriginalDuration() < Constant.UPLOAD_VIDEO_MIN_DURATION) {
                Toast.makeText(getContext(), "视频少于5s,暂不支持", Toast.LENGTH_LONG).show();
            } else {
                ArrayList<BaseMedia> iMedias = new ArrayList<>();
                iMedias.add(media);
                onFinish(iMedias);
            }
        }

        private void multiImageClick(int pos) {
            if (!mIsPreview) {
                AlbumEntity albumMedia = mAlbumWindowAdapter.getCurrentAlbum();
                String albumId = albumMedia != null ? albumMedia.mBucketId : AlbumEntity.DEFAULT_NAME;
                mIsPreview = true;

                ArrayList<BaseMedia> medias = (ArrayList<BaseMedia>) mMediaAdapter.getSelectedMedias();
                Boxing.get().withIntent(getContext(), BoxingPreviewActivity.class, medias, pos, albumId)
                        .start(BoxingFragment.this, BoxingFragment.IMAGE_PREVIEW_REQUEST_CODE,
                                BoxingConfig.ViewMode.EDIT);
            }
        }

        private void singleImageClick(BaseMedia media) {
            ArrayList<BaseMedia> iMedias = new ArrayList<>();
            iMedias.add(media);
            if (hasCropBehavior()) {
                startCrop(media, IMAGE_CROP_REQUEST_CODE);
            } else {
                onFinish(iMedias);
            }
        }
    }


    private class OnCameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mIsCamera) {
                return;
            }
            if (BoxingManager.getInstance().getBoxingConfig().isVideoMode()) {
                startCamera(getActivity(), BoxingFragment.this, BoxingFileHelper.DEFAULT_SUB_DIR);
            } else {
                if (mMediaAdapter.getSelectedMedias().size() < mMaxCount) {
                    startCamera(getActivity(), BoxingFragment.this, BoxingFileHelper.DEFAULT_SUB_DIR);
                } else {
                    Toast.makeText(getActivity(), String.format(getString(R.string.too_many_picture)
                            , mMaxCount), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class OnMediaCheckedListener implements BoxingMediaAdapter.OnMediaCheckedListener {

        @Override
        public void onChecked(View view, BaseMedia iMedia) {
            if (!(iMedia instanceof ImageMedia)) {
                return;
            }
            ImageMedia photoMedia = (ImageMedia) iMedia;
            boolean isSelected = !photoMedia.isSelected();
            MediaItemLayout layout = (MediaItemLayout) view;
            List<BaseMedia> selectedMedias = mMediaAdapter.getSelectedMedias();
            if (isSelected) {
                if (selectedMedias.size() >= mMaxCount) {
                    Toast.makeText(getActivity(), String.format(getString(R.string.too_many_picture)
                            , mMaxCount), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!selectedMedias.contains(photoMedia)) {
                    if (photoMedia.isGifOverSize()) {
                        Toast.makeText(getActivity(), R.string.gif_too_big, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    selectedMedias.add(photoMedia);
                }
            } else {
                if (selectedMedias.size() >= 1 && selectedMedias.contains(photoMedia)) {
                    selectedMedias.remove(photoMedia);
                }
            }
            photoMedia.setSelected(isSelected);
            layout.setAnimatedChecked(isSelected);
            updateMultiPickerLayoutState(selectedMedias);
        }
    }

    private class OnAlbumItemOnClickListener implements BoxingAlbumAdapter.OnAlbumClickListener {

        @Override
        public void onClick(View view, int pos) {
            BoxingAlbumAdapter adapter = mAlbumWindowAdapter;
            if (adapter != null && adapter.getCurrentAlbumPos() != pos) {
                List<AlbumEntity> albums = adapter.getAlums();
                adapter.setCurrentAlbumPos(pos);

                AlbumEntity albumMedia = albums.get(pos);
                loadMedias(0, albumMedia.mBucketId);
                mTitleTxt.setText(albumMedia.mBucketName);

                for (AlbumEntity album : albums) {
                    album.mIsSelected = false;
                }
                albumMedia.mIsSelected = true;
                adapter.notifyDataSetChanged();
            }
            dismissAlbumWindow();
        }
    }

}
