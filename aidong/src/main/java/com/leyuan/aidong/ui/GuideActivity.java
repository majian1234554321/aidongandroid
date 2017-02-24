package com.leyuan.aidong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.SharePrefUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

/**
 * 引导页
 * Created by song on 2017/2/24.
 */
public class GuideActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        BGABanner banner = (BGABanner) findViewById(R.id.banner);
        banner.setOverScrollMode(View.OVER_SCROLL_NEVER);
        final List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.guide1));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.guide2));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.guide3));
        ImageView lastImage = BGABannerUtil.getItemImageView(this, R.drawable.guide4);
        views.add(lastImage);
        banner.setData(views);

        lastImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefUtils.putBoolean(GuideActivity.this,"isFirstEnter",false);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
            }
        });
    }
}
