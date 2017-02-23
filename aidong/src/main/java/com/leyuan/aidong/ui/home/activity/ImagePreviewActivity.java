package com.leyuan.aidong.ui.home.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.adapter.home.ImagePreviewAdapter;
import com.leyuan.aidong.ui.home.view.ImagePreviewTopBar;
import com.leyuan.aidong.widget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private View view;

    public static void start(Context context, ArrayList<String> urls, int position) {
        Intent starter = new Intent(context, ImagePreviewActivity.class);
        starter.putExtra("urls",urls);
        starter.putExtra("position",position);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();

        setContentView(R.layout.activity_image_preview);
        if(getIntent() != null){
            data = this.getIntent().getStringArrayListExtra("urls");
            position = getIntent().getIntExtra("position", 0);
            totalCount = data.size();
        }

        initView();
        setListener();

     /*   supportPostponeEnterTransition();
        viewpager.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        viewpager.getViewTreeObserver().removeOnPreDrawListener(this);
                        supportStartPostponedEnterTransition();
                        return true;
                    }
                }
        );*/

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                sharedElements.put(ViewCompat.getTransitionName(view),view);
            }
        });
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
        compatFinish();
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
    public void onSingleTag(View view) {
        this.view = view;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            finishAfterTransition();
        }else {
            finish();
        }
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
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        /*Fade fade = new Fade();
        fade.setDuration(100);
        fade.setInterpolator(new AccelerateInterpolator(2));
        fade.setMode(Visibility.MODE_IN);
        getWindow().setEnterTransition(fade);
        getWindow().setSharedElementEnterTransition(new ChangeBounds());*/
    }


}
