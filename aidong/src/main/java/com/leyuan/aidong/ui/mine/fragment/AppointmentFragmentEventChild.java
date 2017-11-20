package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.Logger;

/**
 * Created by user on 2017/11/16.
 */
public class AppointmentFragmentEventChild extends BaseFragment {
    protected static final String TAG = "AppointmentFragmentEventChild";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.i(TAG,"onCreateView");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appointment_envent_child, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i(TAG,"onViewCreated");
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Logger.i(TAG,"onActivityCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.i(TAG,"setUserVisibleHint = " +isVisibleToUser);
    }


    @Override
    public void onResume() {
        super.onResume();
        Logger.i(TAG,"onResume = " );
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i(TAG,"onPause" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i(TAG,"onDestroy" );
    }


}
