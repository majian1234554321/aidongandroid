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
import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.AppointmentFragmentCourseChild;
import com.leyuan.aidong.ui.mine.fragment.AppointmentFragmentCourseChildQueue;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.CommonTitleLayout;

import java.util.ArrayList;

public class AppointmentMineCourseActivityNew extends BaseActivity implements View.OnClickListener {

    protected static final String TAG = "AppointmentFragmentEventNew";
    private ViewPager viewPager;
    private int currentItem;

    private TextView btAll;
    private TextView btJoinNo;
    private TextView btJoined;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private int tranPosition;
    private CommonTitleLayout layoutTitle;

    public static void start(Context context,  int tranPosition) {
        Intent intent = new Intent(context, AppointmentMineCourseActivityNew.class);
        intent.putExtra("tranPosition", tranPosition);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment_mine_course_new);

        Logger.i(TAG, "onViewCreated");

        tranPosition = getIntent().getIntExtra("tranPosition", 0);
        Logger.i(TAG, "tranPosition = " + tranPosition);
        if (tranPosition < 0 || tranPosition > 2) tranPosition = 0;

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);

        btAll = (TextView) findViewById(R.id.bt_all);
        btJoinNo = (TextView) findViewById(R.id.bt_join_no);
        btJoined = (TextView) findViewById(R.id.bt_joined);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        btAll.setOnClickListener(this);
        btJoinNo.setOnClickListener(this);
        btJoined.setOnClickListener(this);
        layoutTitle.setLeftIconListener(this);

        AppointmentFragmentCourseChild all = AppointmentFragmentCourseChild.newInstance(CourseAppointBean.APPOINTED);

        AppointmentFragmentCourseChildQueue joined = AppointmentFragmentCourseChildQueue.newInstance(CourseAppointBean.QUEUED);

        AppointmentFragmentCourseChild unJoined = AppointmentFragmentCourseChild.newInstance(CourseAppointBean.HISTORY);
        mFragments.add(all);
        mFragments.add(joined);
        mFragments.add(unJoined);

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
        viewPager.setCurrentItem(tranPosition);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();

                break;

            case R.id.bt_all:

                currentItem = 0;
                viewPager.setCurrentItem(currentItem);

                break;
            case R.id.bt_join_no:
                currentItem = 1;
                viewPager.setCurrentItem(currentItem);

                break;
            case R.id.bt_joined:
                currentItem = 2;
                viewPager.setCurrentItem(currentItem);

                break;
        }
    }

    private void changeTitleTag(int i) {
        Logger.i("AppointmentFragmentEventNew", "changeTitleTag = " + i);
        switch (i) {
            case 0:
                btAll.setTextColor(getResources().getColor(R.color.main_red));
                btJoinNo.setTextColor(getResources().getColor(R.color.c6));
                btJoined.setTextColor(getResources().getColor(R.color.c6));
                break;
            case 1:
                btAll.setTextColor(getResources().getColor(R.color.c6));
                btJoinNo.setTextColor(getResources().getColor(R.color.main_red));
                btJoined.setTextColor(getResources().getColor(R.color.c6));
                break;
            case 2:
                btAll.setTextColor(getResources().getColor(R.color.c6));
                btJoinNo.setTextColor(getResources().getColor(R.color.c6));
                btJoined.setTextColor(getResources().getColor(R.color.main_red));
                break;
        }
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

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(TAG, "onResume = ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "onDestroy");
    }
}
