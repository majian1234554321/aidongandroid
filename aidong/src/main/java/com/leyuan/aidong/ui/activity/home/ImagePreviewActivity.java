package com.leyuan.aidong.ui.activity.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.ImagePreviewAdapter;
import com.leyuan.aidong.ui.activity.home.view.ImagePreviewTopBar;
import com.leyuan.aidong.widget.customview.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览
 * Created by song on 2016/10/20.
 */
public class ImagePreviewActivity extends BaseActivity implements ImagePreviewTopBar.OnOptionsListener, ImagePreviewAdapter.HandleListener {
    private ImagePreviewTopBar topBar;
    private ViewPagerFixed viewpager;

    private int position;
    private int totalCount;
    private List<String> data;
    private ImagePreviewAdapter previewAdapter;
    private AlertDialog.Builder builder;

    public static void start(Context context, ArrayList<String> urls, int position) {
        Intent starter = new Intent(context, ImagePreviewActivity.class);
        starter.putExtra("urls",urls);
        starter.putExtra("position",position);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setupWindowAnimations();
        setContentView(R.layout.activity_image_preview);
        if(getIntent() != null){
            data = this.getIntent().getStringArrayListExtra("urls");
            position = getIntent().getIntExtra("position", 0);
            totalCount = data.size();
        }

        initView();
        setListener();
    }

    private void setListener() {
        topBar.setOnMoreOptionsListener(this);
        previewAdapter.setListener(this);
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initView() {
        topBar = (ImagePreviewTopBar) findViewById(R.id.top_bar);
        viewpager = (ViewPagerFixed) findViewById(R.id.viewpager);
        previewAdapter = new ImagePreviewAdapter(this,data);
        viewpager.setAdapter(previewAdapter);
        viewpager.setCurrentItem(position);

        if(totalCount == 1){
            topBar.setPageIndicatorVisible(View.GONE);
        }else{
            topBar.setPager(position + 1 + "/" + totalCount);
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onMoreOptionsClick(View view) {
        if(builder == null) {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("保存");
        builder.show();
    }

    @Override
    public void onSingleTag() {
        finish();
    }

    @Override
    public void onLongClick() {
        if(builder == null) {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("保存");
        builder.show();
    }

    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            topBar.setPager(position + 1 + "/" + totalCount);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode slide = new Explode();
        slide.setDuration(200);
        slide.setMode(Visibility.MODE_IN);
        ChangeBounds changeBounds = new ChangeBounds();
        Transition changeBoundsTransition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(changeBoundsTransition);
        getWindow().setEnterTransition(slide);
    }
}
