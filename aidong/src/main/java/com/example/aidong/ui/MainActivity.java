package com.example.aidong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.ui.activity.vedio.media.TabTheIndividualDynaminActivity;
import com.example.aidong.ui.fragment.home.HomeFragment;
import com.example.aidong.ui.fragment.mine.MineFragment;
import com.example.aidong.ui.fragment.discover.SportCircleFragment;
import com.example.aidong.entity.model.result.MsgResult;
import com.example.aidong.utils.Utils;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;


public class MainActivity extends BaseActivity implements IHttpCallback, View.OnClickListener {

    private RelativeLayout tabNearLayout;
    private RelativeLayout tabFoundLayout;
    private RelativeLayout tabContactorLayout;
    private RelativeLayout tabMineLayout;
    private View hazy_view;

    private List<Fragment> mFragments = new ArrayList<>();
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
        hazy_view = findViewById(R.id.hazy_view);

    }

    private void initData() {
        initFragments();
        initHazyView();
        tabNearLayout.setOnClickListener(this);
        tabFoundLayout.setOnClickListener(this);
        tabContactorLayout.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
    }

    private void initHazyView() {
        int redActionButtonSize = getResources().getDimensionPixelSize(
                R.dimen.pref_56dp);
        int redActionMenuRadius = getResources().getDimensionPixelSize(
                R.dimen.pref_150dp);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(
                R.dimen.pref_80dp);

        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(
                R.drawable.cerma_big));
        FloatingActionButton.LayoutParams cParams = new FloatingActionButton.LayoutParams(
                redActionButtonSize, redActionButtonSize);
        fabIconNew.setLayoutParams(cParams);
        FloatingActionButton.LayoutParams cbParams = new FloatingActionButton.LayoutParams(
                FloatingActionButton.LayoutParams.WRAP_CONTENT,
                FloatingActionButton.LayoutParams.WRAP_CONTENT);
        cbParams.setMargins(0, 0, 0, 0);

        FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(
                this).setContentView(fabIconNew, cParams)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .setLayoutParams(cbParams).build();
        rightLowerButton.setBackgroundDrawable(null);

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        rLSubBuilder.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.sub_selector));
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(
                blueSubActionButtonSize, blueSubActionButtonSize);
        rLSubBuilder.setLayoutParams(blueParams);

        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_albm));
        rlIcon3.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_photograph));

        TextView tv1 = new TextView(this);
        TextView tv3 = new TextView(this);

        tv1.setText("照片");
        tv1.setPadding(Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 15));
        tv1.setTextColor(getResources().getColor(R.color.color_white));
        tv3.setText("视频");
        tv3.setPadding(Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 15));
        tv3.setTextColor(getResources().getColor(R.color.color_white));

        SubActionButton tcSub1 = rLSubBuilder.setContentView(rlIcon1,
                blueParams).build();
        SubActionButton tcSub3 = rLSubBuilder.setContentView(rlIcon3,
                blueParams).build();

        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(
                this)
                .addSubActionView(tcSub1)
                .addSubActionView(tcSub3)
                //                .addSubTextView(rLSubBuilder.setContentView(tv1, null).build())
                //                .addSubTextView(rLSubBuilder.setContentView(tv3, null).build())
                .attachTo(rightLowerButton).setRadius(redActionMenuRadius)
                .setStartAngle(-120).setEndAngle(-60).build();

        tcSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //                if (BaseApp.mInstance.isLogin()) {
                Intent intent = new Intent(MainActivity.this,
                        TabTheIndividualDynaminActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                //                } else {
                //                    Intent intent = new Intent(MainActivity.this,
                //                            LoginActivity.class);
                //                    startActivity(intent);
                //                }
                rightLowerMenu.close(true);

            }
        });

        tcSub3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //                if (BaseApp.mInstance.isLogin()) {
                Intent intent = new Intent(MainActivity.this,
                        TabTheIndividualDynaminActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
                //                } else {
                //                    Intent intent = new Intent(MainActivity.this,
                //                            LoginActivity.class);
                //                    startActivity(intent);
                //                }
                rightLowerMenu.close(true);
            }
        });

        rightLowerMenu
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu menu) {
                        hazy_view.setVisibility(View.VISIBLE);
                        fabIconNew.setImageResource(R.drawable.btn_close);
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu menu) {
                        hazy_view.setVisibility(View.GONE);
                        fabIconNew.setImageResource(R.drawable.cerma_big);
                    }
                });
    }

    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragments.add(new HomeFragment());
        //mFragments.add(new FindFragment());
        mFragments.add(new SportCircleFragment());
        //        mFragments.add(new TabFoundDynamicFragment());
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
