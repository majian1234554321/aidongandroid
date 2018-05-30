package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.AppointmentFragmentCourseNew;
import com.leyuan.aidong.ui.mine.fragment.AppointmentFragmentEventNew;
import com.leyuan.aidong.ui.mine.fragment.AppointmentFragmentGoods;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;

/**
 * 我的预约
 * Created by song on 2016/8/31.
 */
public class AppointmentMineActivityNew extends BaseActivity implements View.OnClickListener {
    private static final java.lang.String TAG = "AppointmentMineActivityNew";
    private ViewPager viewPager;
    private int currentItem;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private TextView bt_course_appoint, bt_event_appoint, bt_goods_appoint;
    private int bottomPosition, topPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        topPosition = getIntent().getIntExtra("topPosition", 0);

        bottomPosition = getIntent().getIntExtra("bottomPosition", 0);


        setContentView(R.layout.activity_appointment_mine);
        bt_course_appoint = (TextView) findViewById(R.id.bt_course_appoint);
        bt_event_appoint = (TextView) findViewById(R.id.bt_event_appoint);
        bt_goods_appoint = (TextView) findViewById(R.id.bt_goods_appoint);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.bt_course_appoint).setOnClickListener(this);
        findViewById(R.id.bt_event_appoint).setOnClickListener(this);
        findViewById(R.id.bt_goods_appoint).setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.vp_content);


        AppointmentFragmentCourseNew course = AppointmentFragmentCourseNew.newInstance(bottomPosition);

        AppointmentFragmentEventNew event = AppointmentFragmentEventNew.newInstance(bottomPosition);
        AppointmentFragmentGoods goods = AppointmentFragmentGoods.newInstance(bottomPosition);


        mFragments.add(course);
        mFragments.add(event);
        mFragments.add(goods);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        viewPager.addOnPageChangeListener(new MyPageChangeListener());

        viewPager.setCurrentItem(topPosition);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_course_appoint:
                currentItem = 0;
                viewPager.setCurrentItem(currentItem);

                break;
            case R.id.bt_event_appoint:
                currentItem = 1;
                viewPager.setCurrentItem(currentItem);

                break;
            case R.id.bt_goods_appoint:
                currentItem = 2;
                viewPager.setCurrentItem(currentItem);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Fragment page = adapter.getPage(currentItem);
//        if(page != null && page instanceof AppointmentFragment){
//            ((AppointmentFragment) page).fetchData();
//        }
    }

    private void changeTitleTag(int i) {
        Logger.i("appoint mine activity", "changeTitleTag = " + i);
        switch (i) {
            case 0:
                bt_course_appoint.setTextColor(getResources().getColor(R.color.white));
                bt_event_appoint.setTextColor(getResources().getColor(R.color.c9));
                bt_goods_appoint.setTextColor(getResources().getColor(R.color.c9));
                break;
            case 1:
                bt_course_appoint.setTextColor(getResources().getColor(R.color.c9));
                bt_event_appoint.setTextColor(getResources().getColor(R.color.white));
                bt_goods_appoint.setTextColor(getResources().getColor(R.color.c9));
                break;
            case 2:
                bt_course_appoint.setTextColor(getResources().getColor(R.color.c9));
                bt_event_appoint.setTextColor(getResources().getColor(R.color.c9));
                bt_goods_appoint.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    public static void start(Context context, int topPosition, int bottomPosition) {
        Intent intent = new Intent(context, AppointmentMineActivityNew.class);
        intent.putExtra("topPosition", topPosition);
        intent.putExtra("bottomPosition", bottomPosition);
        context.startActivity(intent);
    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            changeTitleTag(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


}
