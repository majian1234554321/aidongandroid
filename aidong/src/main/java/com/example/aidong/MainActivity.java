package com.example.aidong;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.aidong.model.result.MsgResult;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;


public class MainActivity extends BaseActivity implements IHttpCallback, View.OnClickListener {

    private RelativeLayout tabNearLayout;
    private RelativeLayout tabFoundLayout;
    private RelativeLayout tabContactorLayout;
    private RelativeLayout tabMineLayout;

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConfig();
        initView();
        initData();
    }

    private void initView() {
        tabNearLayout = (RelativeLayout) findViewById(R.id.tabNearLayout);
        tabFoundLayout = (RelativeLayout) findViewById(R.id.tabFoundLayout);
        tabContactorLayout = (RelativeLayout) findViewById(R.id.tabContactorLayout);
        tabMineLayout = (RelativeLayout) findViewById(R.id.tabMineLayout);
    }

    private void initData() {
        tabNearLayout.setOnClickListener(this);
        tabFoundLayout.setOnClickListener(this);
        tabContactorLayout.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
        initDefaultFragment();
    }

    private void initDefaultFragment() {
        fm = getFragmentManager();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initConfig() {
        ShareSDK.initSDK(this);
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
        if (requestCode == 1) {
            MsgResult result = (MsgResult) data;
            if (result.getCode() == 1) {
                ToastUtil.show("请求成功", this);
            }
        }
    }

    @Override
    public void onError(String reason, int requestCode) {

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
                tabContactorLayout.setSelected(true);
                tabContactorLayout.setClickable(false);
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
        tabContactorLayout.setSelected(false);
        tabMineLayout.setSelected(false);

        tabNearLayout.setClickable(true);
        tabFoundLayout.setClickable(true);
        tabContactorLayout.setClickable(true);
        tabMineLayout.setClickable(true);
    }
}
