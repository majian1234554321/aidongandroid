package com.leyuan.support.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.leyuan.support.R;
import com.leyuan.support.ui.circle.fragment.CircleFragment;
import com.leyuan.support.ui.discover.fragment.DiscoverFragment;
import com.leyuan.support.ui.home.fragment.HomeFragment;
import com.leyuan.support.ui.mine.fragment.MineFragment;
import com.leyuan.support.util.KeyBoardUtil;

public class MainActivity extends BaseActivity {
    private static final String HOME_FRAGMENT = "home";
    private static final String DISCOVERY_FRAGMENT = "discovery";
    private static final String CIRCLE_FRAGMENT = "circle";
    private static final String MAIN_FRAGMENT = "main";

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private DiscoverFragment discoverFragment;
    private CircleFragment circleFragment;
    private MineFragment mainFragment;
    private String currentIndex;


    RelativeLayout homeTab;

    RelativeLayout discoverTab;

    RelativeLayout circleTab;

    RelativeLayout mineTab;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("index", currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.rl_circle);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getString("index");
            homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(HOME_FRAGMENT);
            discoverFragment = (DiscoverFragment) fragmentManager.findFragmentByTag(DISCOVERY_FRAGMENT);
            circleFragment = (CircleFragment) fragmentManager.findFragmentByTag(CIRCLE_FRAGMENT);
            mainFragment = (MineFragment) fragmentManager.findFragmentByTag(MAIN_FRAGMENT);
            resumeFragment(currentIndex, true);
        } else {
            setSelectFragment(HOME_FRAGMENT);
        }
    }

    @Override
    protected void onDestroy() {
        KeyBoardUtil.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }


    /*public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home:
                setSelectFragment(HOME_FRAGMENT);
                break;
            case R.id.rl_discover:
                setSelectFragment(DISCOVERY_FRAGMENT);
                break;
            case R.id.rl_circle:
                setSelectFragment(CIRCLE_FRAGMENT);
                break;
            case R.id.rl_mine:
                setSelectFragment(MAIN_FRAGMENT);
                break;
            default:
                break;
        }
    }*/

    /**
     * 用于切换fragment，并且设置底部button的焦点
     * @param index 需要切换到的具体页面
     */
    private void setSelectFragment(String index) {
        if (!index.equals(currentIndex)) {        //如果不位于当前页
            resumeFragment(index, false);
        } else {                                  //如果在当前页
            switch (currentIndex) {
                case HOME_FRAGMENT:

                    break;
                case DISCOVERY_FRAGMENT:

                    break;
                case CIRCLE_FRAGMENT:

                    break;
                case MAIN_FRAGMENT:

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 恢复当前Fragment
     * @param index  索引
     * @param screenRotate 屏幕是否旋转
     */
    private void resumeFragment(String index, boolean screenRotate) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragments(transaction);
        switch (index) {
            case HOME_FRAGMENT:
                homeTab.setSelected(true);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.contentLayout, homeFragment, HOME_FRAGMENT);
                } else {
                    transaction.show(homeFragment);
                    if (!screenRotate && currentIndex.equals(HOME_FRAGMENT) && homeFragment != null) {
                       // homeFragment.scrollToTop(false);
                    }
                }
                break;
            case DISCOVERY_FRAGMENT:
                discoverTab.setSelected(true);
                if (discoverFragment == null) {
                    discoverFragment = new DiscoverFragment();
                    transaction.add(R.id.contentLayout, discoverFragment, DISCOVERY_FRAGMENT);
                } else {
                    transaction.show(discoverFragment);
                }
                break;
            case CIRCLE_FRAGMENT:
                circleTab.setSelected(true);
                if (circleFragment == null) {
                    circleFragment = new CircleFragment();
                    transaction.add(R.id.contentLayout, circleFragment, CIRCLE_FRAGMENT);
                } else {
                    transaction.show(circleFragment);
                }
                break;
            case MAIN_FRAGMENT:
                mineTab.setSelected(true);
                if (mainFragment == null) {
                    mainFragment = new MineFragment();
                    transaction.add(R.id.contentLayout, mainFragment, MAIN_FRAGMENT);
                } else {
                    transaction.show(mainFragment);
                }
                break;
        }
        currentIndex = index;
        transaction.commit();
    }

    private void hideAllFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (discoverFragment != null) {
            transaction.hide(discoverFragment);
        }

        if (circleFragment != null) {
            transaction.hide(circleFragment);
        }
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        homeTab.setSelected(false);
        discoverTab.setSelected(false);
        circleTab.setSelected(false);
        mineTab.setSelected(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.sure_logout))
                    .setCancelable(true)
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((MyApplication) getApplication()).finishAll();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return false;
    }

}
