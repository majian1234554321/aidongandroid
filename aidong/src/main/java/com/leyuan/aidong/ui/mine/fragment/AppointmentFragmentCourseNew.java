package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.Logger;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */
public class AppointmentFragmentCourseNew extends BaseFragment {
    protected static final String TAG = "AppointmentFragmentEventNew";
    private ViewPager viewPager;


    private TabLayout tab_layout;


    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private int tranPosition;

    public static AppointmentFragmentCourseNew newInstance(int tranPosition ) {
        AppointmentFragmentCourseNew fragment = new AppointmentFragmentCourseNew();
        Bundle bundle = new Bundle();
        bundle.putInt("tranPosition", tranPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_appointment_course, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i(TAG, "onViewCreated");
        Bundle bundle = getArguments();
        if (bundle != null) {
            tranPosition = bundle.getInt("tranPosition",0);
            Logger.i(TAG,"tranPosition = "+tranPosition);
            if(tranPosition <0 || tranPosition > 2) tranPosition = 0;
        }

        tab_layout =  view.findViewById(R.id.tab_layout);
        viewPager =  view.findViewById(R.id.vp_content);





        FragmentPagerItems pages = new FragmentPagerItems(getActivity());


        AppointmentFragmentCourseChild all = AppointmentFragmentCourseChild.newInstance(CourseAppointBean.APPOINTED);

        AppointmentFragmentCourseChildQueue joined = AppointmentFragmentCourseChildQueue.newInstance(CourseAppointBean.QUEUED);

        AppointmentFragmentCourseChild unJoined = AppointmentFragmentCourseChild.newInstance(CourseAppointBean.HISTORY);



        pages.add(FragmentPagerItem.of("预约课程", all.getClass(),
                new Bundler().putString("type", OrderFragment.ALL).get()));
        pages.add(FragmentPagerItem.of("排队课程", joined.getClass(),
                new Bundler().putString("type", OrderFragment.UN_PAID).get()));
        pages.add(FragmentPagerItem.of("历史课程", unJoined.getClass(),
                new Bundler().putString("type", OrderFragment.PAID).get()));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);

        viewPager.setAdapter(adapter);




        tab_layout.setupWithViewPager(viewPager);














        viewPager.setCurrentItem(tranPosition);
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








}
