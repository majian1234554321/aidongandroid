package com.leyuan.aidong.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.discover.fragment.DiscoverHomeFragment;
import com.leyuan.aidong.ui.home.fragment.HomeFragment;
import com.leyuan.aidong.ui.mine.fragment.MineFragment;
import com.leyuan.aidong.ui.video.fragment.VideoHomeFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout tabNearLayout;
    private RelativeLayout tabFoundLayout;
    private RelativeLayout tabDiscoverLayout;
    private RelativeLayout tabMineLayout;

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        tabNearLayout = (RelativeLayout) findViewById(R.id.tabNearLayout);
        tabFoundLayout = (RelativeLayout) findViewById(R.id.tabFoundLayout);
        tabDiscoverLayout = (RelativeLayout) findViewById(R.id.tabContactorLayout);
        tabMineLayout = (RelativeLayout) findViewById(R.id.tabMineLayout);
    }

    private void initData() {
        initFragments();
        tabNearLayout.setOnClickListener(this);
        tabFoundLayout.setOnClickListener(this);
        tabDiscoverLayout.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
    }


    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragments.add(new HomeFragment());
        mFragments.add(new VideoHomeFragment());
        mFragments.add(new DiscoverHomeFragment());
        mFragments.add(new MineFragment());
        setTabSelection(0);
        showFragment(0);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.tabNearLayout:
                setTabSelection(0);
                showFragment(0);
                break;
            case R.id.tabFoundLayout:
                setTabSelection(1);
                showFragment(1);
                break;
            case R.id.tabContactorLayout:
                setTabSelection(2);
                showFragment(2);
                break;
            case R.id.tabMineLayout:
                setTabSelection(3);
                showFragment(3);
                break;

            default:
                break;
        }
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

    private void setTabSelection(int index) {
        resetTabBtn();
        switch (index) {
            case 0:
                tabNearLayout.setSelected(true);
                tabNearLayout.setClickable(false);
                break;
            case 1:
                tabFoundLayout.setSelected(true);
                tabFoundLayout.setClickable(false);
                break;
            case 2:
                tabDiscoverLayout.setSelected(true);
                tabDiscoverLayout.setClickable(false);
                break;
            case 3:
                tabMineLayout.setSelected(true);
                tabMineLayout.setClickable(false);
                break;
        }
    }

    protected void resetTabBtn() {
        tabNearLayout.setSelected(false);
        tabFoundLayout.setSelected(false);
        tabDiscoverLayout.setSelected(false);
        tabMineLayout.setSelected(false);

        tabNearLayout.setClickable(true);
        tabFoundLayout.setClickable(true);
        tabDiscoverLayout.setClickable(true);
        tabMineLayout.setClickable(true);
    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            exitApp();
        }
    }


}
