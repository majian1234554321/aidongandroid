package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.PhotoWallAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.module.photopicker.boxing_impl.view.SpacesItemDecoration;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.PhotoWallPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.PhotoWallPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UpdatePhotoWallActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.qiniu.IQiNiuCallback;
import com.leyuan.aidong.utils.qiniu.UploadToQiNiuManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 修改照片墙
 * Created by song on 2017/2/7.
 */
public class UpdatePhotoWallActivity extends BaseActivity implements View.OnClickListener,
        PhotoWallAdapter.OnItemClickListener, UpdatePhotoWallActivityView {
    private static final int REQUEST_CODE = 1024;

    private ImageView ivBack;
    private TextView tvFinish;
    private RecyclerView rvPhoto;
    private PhotoWallAdapter photoWallAdapter;
    private ArrayList<ImageBean> allSelectedImages = new ArrayList<>();
    private ArrayList<ImageBean> selectedNetImages = new ArrayList<>();
    private ArrayList<BaseMedia> selectedLocalImages = new ArrayList<>();
    private PhotoWallPresent photoWallPresent;

    private boolean isNetPhotoChanged = false;

    public static void start(Context context, List<ImageBean> photos) {
        Intent starter = new Intent(context, UpdatePhotoWallActivity.class);
        starter.putParcelableArrayListExtra("photos",(ArrayList<ImageBean>) photos);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photo_wall);
        photoWallPresent = new PhotoWallPresentImpl(this,this);
        if(getIntent() != null){
            selectedNetImages = getIntent().getParcelableArrayListExtra("photos");
            allSelectedImages.addAll(selectedNetImages);
        }
        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        photoWallAdapter = new PhotoWallAdapter(this);
        rvPhoto.setAdapter(photoWallAdapter);
        rvPhoto.setLayoutManager(new GridLayoutManager(this,4));
        rvPhoto.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin), 4));
        photoWallAdapter.setData(selectedNetImages);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        photoWallAdapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if(isNetPhotoChanged){
                    setResult(RESULT_OK,null);
                }
                finish();
                break;
            case R.id.tv_finish:
                if(selectedLocalImages.isEmpty()){
                    Toast.makeText(UpdatePhotoWallActivity.this,"请先选择要上传的本地图片",Toast.LENGTH_LONG).show();
                    return;
                }
                uploadToQiNiu();
                break;
            default:
                break;
        }
    }

    private void uploadToQiNiu(){
        showProgressDialog();
        UploadToQiNiuManager.getInstance().uploadImages(selectedLocalImages, new IQiNiuCallback(){
            @Override
            public void onSuccess(List<String> urls) {
                uploadToServer(urls);
            }
            @Override
            public void onFail() {
                dismissProgressDialog();
                Toast.makeText(UpdatePhotoWallActivity.this,"上传失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadToServer(List<String> qiNiuUrls){
        String[] photo = new String[qiNiuUrls.size()];
        for (int i = 0; i < qiNiuUrls.size(); i++) {
            photo[i] = qiNiuUrls.get(i);
        }
        photoWallPresent.addPhotos(photo);
    }

    @Override
    public void onAddImageItemClick() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.maxCount(8 - selectedNetImages.size()).isNeedPaging();
        Boxing.of(multi)
                .withIntent(this, BoxingActivity.class,selectedLocalImages)
                .start(this, REQUEST_CODE);
    }

    @Override
    public void onDeleteNetImage(int position) {
        photoWallPresent.deletePhotos(allSelectedImages.get(position).getId(),position);
    }

    @Override
    public void onDeleteLocalImage(int position) {
        allSelectedImages.remove(position);
        photoWallAdapter.notifyItemRemoved(position);
        photoWallAdapter.notifyItemRangeChanged(position,allSelectedImages.size());
        selectedLocalImages.remove(position - selectedNetImages.size());
        tvFinish.setVisibility(selectedLocalImages.isEmpty()?View.GONE:View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (rvPhoto == null || photoWallAdapter == null) {
                return;
            }
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (requestCode == REQUEST_CODE) {
                selectedLocalImages.clear();
                selectedLocalImages.addAll(medias);

                allSelectedImages.clear();
                allSelectedImages.addAll(selectedNetImages);
                for (BaseMedia selectedLocalImage : selectedLocalImages) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setPath(selectedLocalImage.getPath());
                    allSelectedImages.add(imageBean);
                }
                photoWallAdapter.setData(allSelectedImages);
                tvFinish.setVisibility(selectedLocalImages.isEmpty()?View.GONE:View.VISIBLE);
            }
        }
    }

    @Override
    public void deleteNetPhotoResult(BaseBean baseBean, int position) {
        if(baseBean.getStatus() == Constant.OK){
            selectedNetImages.remove(position);
            allSelectedImages.remove(position);
            photoWallAdapter.notifyItemRemoved(position);
            photoWallAdapter.notifyItemRangeChanged(position,allSelectedImages.size());
            isNetPhotoChanged = true;
            Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"删除失败"+baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void addPhotosResult(BaseBean baseBean) {
        dismissProgressDialog();
        if(baseBean.getStatus() == Constant.OK){
            setResult(RESULT_OK,null);
            finish();
            Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(isNetPhotoChanged){
            setResult(RESULT_OK,null);
        }
        finish();
    }
}
