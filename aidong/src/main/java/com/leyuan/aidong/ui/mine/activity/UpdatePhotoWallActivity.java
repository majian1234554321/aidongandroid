package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.PhotoWallAdapter;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.BaseActivity;

import java.util.ArrayList;


/**
 * 修改照片墙
 * Created by song on 2017/2/7.
 */
public class UpdatePhotoWallActivity extends BaseActivity implements View.OnClickListener, PhotoWallAdapter.OnItemClickListener {
    private static final int REQUEST_CODE = 1024;

    private ImageView ivBack;
    private TextView tvFinish;
    private RecyclerView rvPhoto;
    private PhotoWallAdapter photoWallAdapter;
    private ArrayList<BaseMedia> selectedImages = new ArrayList<>();

    public static void start(Context context, ArrayList<ImageBean> photos) {
        Intent starter = new Intent(context, UpdatePhotoWallActivity.class);
        starter.putParcelableArrayListExtra("photos",photos);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photo_wall);
        if(getIntent() != null){
            selectedImages = getIntent().getParcelableArrayListExtra("photos");
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
        if(!selectedImages.isEmpty()){
            photoWallAdapter.setData(selectedImages);
        }
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
                finish();
                break;
            case R.id.tv_finish:

                break;
        }
    }


    @Override
    public void onAddImageItemClick() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needGif().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(this, BoxingActivity.class,selectedImages).start(this, REQUEST_CODE);
    }

    @Override
    public void onPhotoItemClick(int position) {

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
                selectedImages.clear();
                selectedImages.addAll(medias);
                photoWallAdapter.setData(selectedImages);
            }
        }
    }
}
