package com.example.aidong.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.SamplePagerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.support.entity.CourseDetailBean;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.impl.CourseModelImpl;
import com.leyuan.support.util.DensityUtil;
import com.leyuan.support.widget.customview.ViewPagerIndicator;

/**
 * 课程详情
 * Created by song on 2016/8/17.
 */
public class CourseDetailActivity extends BaseActivity{
    private static final float radio = 9/16;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private ViewPager viewPager;
    private ViewPagerIndicator indicator;

    private SimpleDraweeView avatar;

    private String id;


    /**
     * 跳转课程详情界面
     * @param id 课程id
     */
    public static void newInstance(Context context, String id){
        Intent intent = new Intent(context,CourseDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_new);

        initView();

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("id");
        }

        new CourseModelImpl().getCourseDetail(new ProgressSubscriber<CourseDetailBean>(this) {
            @Override
            public void onNext(CourseDetailBean courseDetailBean) {

            }
        },id);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
        return true;
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.vp_campaign);
        indicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());

        avatar = (SimpleDraweeView) findViewById(R.id.dv_avatar);

    }



    private  class MyOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener{
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), 0, 0, 0));

            if(maxScroll - Math.abs(verticalOffset) < DensityUtil.dp2px(CourseDetailActivity.this,35)){
                ToastUtil.show("hide",CourseDetailActivity.this);
                avatar.setVisibility(View.GONE);
            }else {
                ToastUtil.show("show",CourseDetailActivity.this);
                avatar.setVisibility(View.VISIBLE);
            }
        }
    }




}
