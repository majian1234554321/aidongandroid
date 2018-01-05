package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.kyleduo.switchbutton.SwitchButton;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class HomeCourseFragment extends BaseFragment {


    private FrameLayout frame;
    private FragmentManager fm;
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentTransaction ft;
    private SwitchButton btCheckout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btCheckout = (SwitchButton) view.findViewById(R.id.bt_checkout);
        frame = (FrameLayout) view.findViewById(R.id.frame);
        fm = getChildFragmentManager();
        mFragments.add(new HomeCourseListFragment());
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

}
