package com.leyuan.aidong.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.adapter.PhotoWallAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改照片墙
 * Created by song on 2017/2/7.
 */
public class UpdatePhotoWall extends BaseActivity implements View.OnClickListener, PhotoWallAdapter.OnItemClickListener {
    private ImageView ivBack;
    private TextView tvFinish;
    private RecyclerView rvPhoto;
    private PhotoWallAdapter photoWallAdapter;
    private List<ImageBean> photos = new ArrayList<>();

    public static void start(Context context, ArrayList<ImageBean> photos) {
        Intent starter = new Intent(context, UpdatePhotoWall.class);
        starter.putParcelableArrayListExtra("photos",photos);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photo_wall);
        if(getIntent() != null){
            photos = getIntent().getParcelableArrayListExtra("photos");
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
        if(!photos.isEmpty()){
            photoWallAdapter.setData(photos);
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

    }

    @Override
    public void onPhotoItemClick(int position) {

    }
}
