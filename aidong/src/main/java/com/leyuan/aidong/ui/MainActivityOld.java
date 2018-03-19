package com.leyuan.aidong.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.module.chat.manager.EmMessageManager;
import com.leyuan.aidong.receivers.ChatMessageReceiver;
import com.leyuan.aidong.receivers.NewPushMessageReceiver;
import com.leyuan.aidong.ui.discover.fragment.DiscoverHomeFragment;
import com.leyuan.aidong.ui.home.fragment.HomeFragment;
import com.leyuan.aidong.ui.home.fragment.StoreFragment;
import com.leyuan.aidong.ui.mine.activity.CouponNewcomerActivity;
import com.leyuan.aidong.ui.mine.activity.setting.PhoneBindingActivity;
import com.leyuan.aidong.ui.mine.fragment.MineFragment;
import com.leyuan.aidong.ui.mvp.presenter.impl.CouponPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.VersionPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.CouponFragmentView;
import com.leyuan.aidong.ui.video.fragment.VideoHomeFragment;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.LocatinCityManager;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.SharePrefUtils;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.utils.autostart.CheckAutoStartUtils;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivityOld extends BaseActivity implements View.OnClickListener {

    private RelativeLayout tabNearLayout;
    private RelativeLayout tabFoundLayout;
    private RelativeLayout tabStoreLayout;
    private RelativeLayout tabDiscoverLayout;
    private RelativeLayout tabMineLayout;
    private ImageView img_new_message;
    private ImageView img_new_circle_message;

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fm;
    private FragmentTransaction ft;

    private long mPressedTime = 0;
    private ChatMessageReceiver chatMessageReceiver;
    private NewPushMessageReceiver newPushMessageReceiver;
    private int index;

    public static void start(Context context, int index) {
        Intent starter = new Intent(context, MainActivityOld.class);
        starter.putExtra("index", index);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.getInstance().isLogin() && TextUtils.isEmpty(App.getInstance().getUser().getMobile())) {
            UiManager.activityJump(this, PhoneBindingActivity.class);
        }

        LocatinCityManager.checkLocationCity(this);
        new VersionPresenterImpl(this).checkVersionAndShow();

        setContentView(R.layout.activity_main_old);
        if (getIntent() != null) {
            index = getIntent().getIntExtra("index", 0);
        }

        initView();
        initData();
        registerMessageReceiver();
        checkAutoStart();
    }

    private void initView() {
        tabNearLayout = (RelativeLayout) findViewById(R.id.tabNearLayout);
        tabFoundLayout = (RelativeLayout) findViewById(R.id.tabFoundLayout);
        tabStoreLayout = (RelativeLayout) findViewById(R.id.tabStoreLayout);

//        tabDiscoverLayout = (RelativeLayout) findViewById(R.id.tabContactorLayout);
        tabMineLayout = (RelativeLayout) findViewById(R.id.tabMineLayout);
        img_new_message = (ImageView) findViewById(R.id.img_new_message);
        img_new_circle_message = (ImageView) findViewById(R.id.img_new_circle_message);

        img_new_circle_message.setVisibility(App.getInstance().getCMDCirleDynamicBean() == null ||
                App.getInstance().getCMDCirleDynamicBean().isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void initData() {
        initFragments();
        tabNearLayout.setOnClickListener(this);
        tabFoundLayout.setOnClickListener(this);
        tabStoreLayout.setOnClickListener(this);
        tabDiscoverLayout.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
    }

    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragments.add(new HomeFragment());
        mFragments.add(new VideoHomeFragment());
        mFragments.add(new StoreFragment());
        mFragments.add(new DiscoverHomeFragment());
        mFragments.add(new MineFragment());
        setTabSelection(index);
        showFragment(index);
    }

    private BroadcastReceiver mainActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE)) {
                img_new_circle_message.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE)) {
                img_new_circle_message.setVisibility(View.GONE);
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_NEW_USER_REGISTER)) {
                getNewUserCouponInfo();


            }
            Logger.i("mainActivityReceiver", "onReceive action = " + intent.getAction());
        }
    };

    private void getNewUserCouponInfo() {

        CouponPresentImpl couponPresent = new CouponPresentImpl(this, new CouponFragmentView() {
            @Override
            public void updateRecyclerView(List<CouponBean> couponBeanList) {

                if(couponBeanList != null && !couponBeanList.isEmpty()){
                    CouponNewcomerActivity.start(MainActivityOld.this, (ArrayList<CouponBean>) couponBeanList);
                }
            }

            @Override
            public void showEmptyView() {

            }

            @Override
            public void showEndFooterView() {

            }
        });

        couponPresent.pullToRefreshData("valid");


    }

    private void registerMessageReceiver() {
        chatMessageReceiver = new ChatMessageReceiver(img_new_message);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                chatMessageReceiver, new IntentFilter(Constant.BROADCAST_ACTION_NEW_MESSAGE));

        newPushMessageReceiver = new NewPushMessageReceiver(img_new_message);
        registerReceiver(newPushMessageReceiver, new IntentFilter(NewPushMessageReceiver.ACTION));

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
        filter.addAction(Constant.BROADCAST_ACTION_NEW_USER_REGISTER);
        LocalBroadcastManager.getInstance(this).registerReceiver(mainActivityReceiver, filter);

    }

    private void checkAutoStart() {
        if (CheckAutoStartUtils.isNeedCheck(this)) {
            new DialogDoubleButton(this).setContentDesc("为了及时收到最新消息，请进入设置把应用加入自启动白名单")
                    .setBtnCancelListener(new ButtonCancelListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setBtnOkListener(new ButtonOkListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            CheckAutoStartUtils.skipToAutoStartView(MainActivityOld.this);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
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
            case R.id.tabStoreLayout:
                setTabSelection(2);
                showFragment(2);
                break;
//            case R.id.tabContactorLayout:
//                setTabSelection(3);
//                showFragment(3);
//                break;
            case R.id.tabMineLayout:
                setTabSelection(4);
                showFragment(4);
                img_new_message.setVisibility(View.GONE);

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
                tabStoreLayout.setSelected(true);
                tabStoreLayout.setClickable(false);
                break;
            case 3:
                tabDiscoverLayout.setSelected(true);
                tabDiscoverLayout.setClickable(false);
                break;
            case 4:
                tabMineLayout.setSelected(true);
                tabMineLayout.setClickable(false);
                break;
        }
    }

    protected void resetTabBtn() {
        tabNearLayout.setSelected(false);
        tabFoundLayout.setSelected(false);
        tabStoreLayout.setSelected(false);
        tabDiscoverLayout.setSelected(false);
        tabMineLayout.setSelected(false);

        tabNearLayout.setClickable(true);
        tabFoundLayout.setClickable(true);
        tabStoreLayout.setClickable(true);
        tabDiscoverLayout.setClickable(true);
        tabMineLayout.setClickable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        img_new_message.setVisibility(EmMessageManager.isHaveUnreadMessage() ? View.VISIBLE : View.GONE);
        CouponData couponData = SharePrefUtils.getNewUserCoupon(MainActivityOld.this);
        if(couponData != null && couponData.getCoupon()!= null &&! couponData.getCoupon().isEmpty()){
            CouponNewcomerActivity.start(this, (ArrayList<CouponBean>) couponData.getCoupons());
            SharePrefUtils.putNewUserCoupon(this,null);
        }
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterMessageReceiver();

    }

    private void unregisterMessageReceiver() {
        unregisterReceiver(newPushMessageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(chatMessageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mainActivityReceiver);
    }
}
