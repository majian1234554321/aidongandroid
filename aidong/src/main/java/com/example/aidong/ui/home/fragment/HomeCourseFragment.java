package com.example.aidong.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.example.aidong.R;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.home.activity.LocationActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class HomeCourseFragment extends BaseFragment implements View.OnClickListener {


    private FrameLayout frame;
    private FragmentManager fm;
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentTransaction ft;
    private SwitchButton btCheckout;
    private TextView tv_location;


    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

             if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {

                 tv_location.setText(App.getInstance().getSelectedCity());

            }


        }
    };
    private HomeCourseListFragment homeCourseListFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);

        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        btCheckout = (SwitchButton) view.findViewById(R.id.bt_checkout);
        frame = (FrameLayout) view.findViewById(R.id.frame);
        fm = getChildFragmentManager();


        homeCourseListFragment = new HomeCourseListFragment();


        mFragments.add(homeCourseListFragment);
        mFragments.add(new HomeStoreListFragment());
        showFragment(0);
        btCheckout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Logger.i(TAG,"is checked = " + isChecked);
                if(isChecked){
                    showFragment(1);
                }else {
                    showFragment(0);
                }
            }
        });


        tv_location.setOnClickListener(this);
    }

    private void showFragment(int tag) {

        ft = fm.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            if (i != tag) {
                if (mFragments.get(i).isAdded()
                        && mFragments.get(i).isVisible()) {
                    ft.hide(mFragments.get(i));
                }
            } else {
                if (!mFragments.get(i).isAdded()) {
                    ft.add(R.id.frame, mFragments.get(i));
                }
                if (mFragments.get(i).isHidden()) {
                    ft.show(mFragments.get(i));
                }
            }
        }
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), LocationActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_location.setText(App.getInstance().getSelectedCity());
    }

//    public void setData(){
//        homeCourseListFragment.setData();
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(selectCityReceiver);
    }
}
