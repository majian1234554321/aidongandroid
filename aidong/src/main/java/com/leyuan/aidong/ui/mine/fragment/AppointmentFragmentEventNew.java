package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */
public class AppointmentFragmentEventNew extends BaseFragment implements View.OnClickListener {
    protected static final String TAG = "AppointmentFragmentEventNew";
    private ViewPager viewPager;
    private int currentItem;

    private TextView btAll;
    private TextView btJoinNo;
    private TextView btJoined;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_appointment_event, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i(TAG, "onViewCreated");

        btAll = (TextView) view.findViewById(R.id.bt_all);
        btJoinNo = (TextView) view.findViewById(R.id.bt_join_no);
        btJoined = (TextView) view.findViewById(R.id.bt_joined);
        viewPager = (ViewPager) view.findViewById(R.id.vp_content);

        btAll.setOnClickListener(this);
        btJoinNo.setOnClickListener(this);
        btJoined.setOnClickListener(this);

        AppointmentFragment all = AppointmentFragment.newInstance(AppointmentFragment.ALL);

        AppointmentFragment joined = AppointmentFragment.newInstance(AppointmentFragment.UN_JOIN);

        AppointmentFragment unJoined = AppointmentFragment.newInstance(AppointmentFragment.JOINED);
        mFragments.add(all);
        mFragments.add(joined);
        mFragments.add(unJoined);

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Logger.i(TAG, "onActivityCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.i(TAG, "setUserVisibleHint = " + isVisibleToUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
