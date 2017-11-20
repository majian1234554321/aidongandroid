package com.leyuan.aidong.ui.mine.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseLazyFragment;

/**
 * Created by user on 2017/11/14.
 */
public class AppointmentFragmentCourseNew extends BaseLazyFragment {

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appointment, null);
        return view;
    }

    @Override
    public void fetchData() {

    }
}
