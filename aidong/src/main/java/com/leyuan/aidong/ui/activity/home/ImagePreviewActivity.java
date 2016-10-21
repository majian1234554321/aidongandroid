package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.ImagePreviewAdapter;
import com.leyuan.aidong.ui.activity.home.view.ImageOptionPopupWindow;
import com.leyuan.aidong.ui.activity.home.view.ImagePreviewTopBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览
 * Created by song on 2016/10/20.
 */
public class ImagePreviewActivity extends BaseActivity implements ImagePreviewTopBar.OnMoreOptionsListener, ImagePreviewAdapter.OnSingleTagListener {
    private ImagePreviewTopBar topBar;
    private ViewPager viewpager;

    private int position;
    private int totalCount;
    private List<String> data;
    private ImagePreviewAdapter previewAdapter;

    public static void start(Context context, ArrayList<String> urls, int position) {
        Intent starter = new Intent(context, ImagePreviewActivity.class);
        starter.putExtra("urls",urls);
        starter.putExtra("position",position);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        previewAdapter.setOnSingleTagListener(this);
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initView() {
        topBar = (ImagePreviewTopBar) findViewById(R.id.top_bar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
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
    public void onMoreOptionsClick(View view) {
        ImageOptionPopupWindow popupWindow = new ImageOptionPopupWindow(ImagePreviewActivity.this);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void onSingleTag() {
        finish();
    }

    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            topBar.setPager(position + 1 + "/" + totalCount);
        }
    }
}
