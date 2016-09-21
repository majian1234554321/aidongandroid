package com.example.aidong.activity.discover;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailActivity extends BaseActivity{

    private ImageView ivBack;
    private TabLayout tabLayout;
    private ViewPager vpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_detail);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        vpContent = (ViewPager) findViewById(R.id.vp_content);


    }
}
