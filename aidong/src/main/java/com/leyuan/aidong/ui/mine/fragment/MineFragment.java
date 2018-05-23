package com.leyuan.aidong.ui.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.data.SportRecordMonthData;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.module.chat.manager.EmMessageManager;
import com.leyuan.aidong.receivers.ChatMessageReceiver;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.DisplayActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.WebViewActivity;
import com.leyuan.aidong.ui.mine.activity.AddressActivity;
import com.leyuan.aidong.ui.mine.activity.AiDongMomentActivity;
import com.leyuan.aidong.ui.mine.activity.AppointmentMineActivityNew;
import com.leyuan.aidong.ui.mine.activity.CartActivity;
import com.leyuan.aidong.ui.mine.activity.CouponActivity;
import com.leyuan.aidong.ui.mine.activity.FollowActivity;
import com.leyuan.aidong.ui.mine.activity.LoveCoinActivity;
import com.leyuan.aidong.ui.mine.activity.MessageActivity;
import com.leyuan.aidong.ui.mine.activity.MyAttentionListActivity;
import com.leyuan.aidong.ui.mine.activity.MyMemberCardActivity;
import com.leyuan.aidong.ui.mine.activity.SportRecordActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mine.activity.setting.TabMinePersonalSettingsActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SportPresentImpl;
import com.leyuan.aidong.ui.mvp.view.MineInfoView;
import com.leyuan.aidong.ui.mvp.view.SportRecordView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Md5Utils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.AidongMineItem;

import java.security.Key;
import java.util.Random;


public class MineFragment extends BaseFragment implements View.OnClickListener, MineInfoView, SportRecordView {

    private View rootView;
    private LinearLayout layout_no_login, linearLayout_guanzhu, linearLayout_beiguanzhu, layout_hot;
    private RelativeLayout relativeLayout_my_logo;
    private ImageButton btn_shop_car, btn_message;
    private ImageView imageView_head, img_new_message, img_new_shop;
    private ImageView imageView_xinbie;
    private TextView textView_name, textView_guanzhushu, textView_beiguanzhushu, textView_popularity, txt_sport_record;
    //    , textView_yysl, textView_yyjrw, textView_dd, textView_ddjrw
    private TextView txtCurrentMonthTitle;
    private TextView txtSportRecord;
    private TextView txtClockNum;
    private TextView txtGoCourseNum;
    private TextView txtClassTotalTime;
    private TextView txtExpendCal;

    private AidongMineItem item_my_coin, item_my_coupon, item_sport_timing, item_address, item_my_yaoqing,
            item_recommend_friend, item_after_sale, item_setting, item_my_member_card, item_my_orders;
    private UserCoach user;
    private ChatMessageReceiver chatMessageReceiver;
    private MineInfoPresenterImpl presenter;
    private TextView txt_new_shop;

    private BroadcastReceiver registerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLoginState();
        }
    };
    private SportPresentImpl sportPresent;
    // private SwipeRefreshLayout swipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter registerFilter = new IntentFilter();
        registerFilter.addAction(Constant.BROADCAST_ACTION_REGISTER_SUCCESS);
        getActivity().registerReceiver(registerReceiver, registerFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        setViewEvent();
        initData();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new MineInfoPresenterImpl(getActivity(), this);

        sportPresent = new SportPresentImpl(getContext());
        sportPresent.setSportRecordView(this);

    }

    private void initView() {
        // swipeRefreshView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);


        layout_no_login = (LinearLayout) rootView.findViewById(R.id.layout_no_login);
        linearLayout_guanzhu = (LinearLayout) rootView.findViewById(R.id.linearLayout_guanzhu);
        linearLayout_beiguanzhu = (LinearLayout) rootView.findViewById(R.id.linearLayout_beiguanzhu);
        layout_hot = (LinearLayout) rootView.findViewById(R.id.layout_hot);

        btn_shop_car = (ImageButton) rootView.findViewById(R.id.btn_shop_car);
        btn_message = (ImageButton) rootView.findViewById(R.id.btn_message);
        img_new_message = (ImageView) rootView.findViewById(R.id.img_new_message);
        img_new_shop = (ImageView) rootView.findViewById(R.id.img_new_shop);
        txt_new_shop = (TextView) rootView.findViewById(R.id.txt_new_shop);

        relativeLayout_my_logo = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_my_logo);
        txtCurrentMonthTitle = (TextView) rootView.findViewById(R.id.txt_current_month_title);
        txtSportRecord = (TextView) rootView.findViewById(R.id.txt_sport_record);
        txtClockNum = (TextView) rootView.findViewById(R.id.txt_clock_num);
        txtGoCourseNum = (TextView) rootView.findViewById(R.id.txt_go_course_num);
        txtClassTotalTime = (TextView) rootView.findViewById(R.id.txt_class_total_time);
        txtExpendCal = (TextView) rootView.findViewById(R.id.txt_expend_cal);
        ImageView iv = (ImageView) rootView.findViewById(R.id.iv);

        iv.setOnClickListener(this);


//        relativeLayout_yuyue = (LinearLayout) rootView.findViewById(R.id.relativeLayout_yuyue);
//        relativeLayout_dingdang = (LinearLayout) rootView.findViewById(R.id.relativeLayout_dingdang);
//        layout_appoint_course= (LinearLayout) rootView.findViewById(R.id.layout_appoint_course);

        imageView_head = (ImageView) rootView.findViewById(R.id.imageView_head);
        imageView_xinbie = (ImageView) rootView.findViewById(R.id.imageView_xinbie);

        textView_name = (TextView) rootView.findViewById(R.id.textView_name);
        textView_guanzhushu = (TextView) rootView.findViewById(R.id.textView_guanzhushu);
        textView_beiguanzhushu = (TextView) rootView.findViewById(R.id.textView_beiguanzhushu);
        textView_popularity = (TextView) rootView.findViewById(R.id.textView_popularity);
        txt_sport_record = (TextView) rootView.findViewById(R.id.txt_sport_record);
//        textView_yysl = (TextView) rootView.findViewById(R.id.textView_yysl);
//        textView_yyjrw = (TextView) rootView.findViewById(R.id.textView_yyjrw);
//        textView_dd = (TextView) rootView.findViewById(R.id.textView_dd);
//        textView_ddjrw = (TextView) rootView.findViewById(R.id.textView_ddjrw);

        item_my_coin = (AidongMineItem) rootView.findViewById(R.id.item_my_coin);
        item_my_member_card = (AidongMineItem) rootView.findViewById(R.id.item_my_member_card);
        item_my_orders = (AidongMineItem) rootView.findViewById(R.id.item_my_orders);
        item_my_coupon = (AidongMineItem) rootView.findViewById(R.id.item_my_coupon);
        item_sport_timing = (AidongMineItem) rootView.findViewById(R.id.item_sport_timing);
        item_address = (AidongMineItem) rootView.findViewById(R.id.item_address);
        item_recommend_friend = (AidongMineItem) rootView.findViewById(R.id.item_recommend_friend);
        item_after_sale = (AidongMineItem) rootView.findViewById(R.id.item_after_sale);
        item_setting = (AidongMineItem) rootView.findViewById(R.id.item_setting);


        item_my_yaoqing = (AidongMineItem) rootView.findViewById(R.id.item_my_yaoqing);
        item_my_yaoqing.setOnClickListener(this);


    }

    private void setViewEvent() {
        txt_sport_record.setOnClickListener(this);
        btn_shop_car.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        imageView_head.setOnClickListener(this);
        linearLayout_guanzhu.setOnClickListener(this);
        linearLayout_beiguanzhu.setOnClickListener(this);
        layout_hot.setOnClickListener(this);
        item_my_coin.setOnClickListener(this);
        item_my_member_card.setOnClickListener(this);
        item_my_coupon.setOnClickListener(this);
        item_sport_timing.setOnClickListener(this);
        item_address.setOnClickListener(this);
        item_recommend_friend.setOnClickListener(this);
        item_after_sale.setOnClickListener(this);
        item_setting.setOnClickListener(this);
        item_my_orders.setOnClickListener(this);
        layout_no_login.setOnClickListener(this);


//        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                refreshLoginState();
//            }
//        });


    }

    private void initData() {
        chatMessageReceiver = new ChatMessageReceiver(img_new_message);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                chatMessageReceiver, new IntentFilter(Constant.BROADCAST_ACTION_NEW_MESSAGE));
    }


    @Override
    public void onResume() {
        super.onResume();
        img_new_message.setVisibility(EmMessageManager.isHaveUnreadMessage() ? View.VISIBLE : View.GONE);

        refreshLoginState();
    }

    private void refreshLoginState() {
        if (App.getInstance().isLogin()) {
            relativeLayout_my_logo.setVisibility(View.VISIBLE);
            layout_no_login.setVisibility(View.GONE);
            user = App.getInstance().getUser();
            textView_name.setText(user.getName());
            presenter.getMineInfo();
            sportPresent.getSportRecordNoProgress("", "");

            GlideLoader.getInstance().displayCircleImage(user.getAvatar(), imageView_head);
        } else {
            relativeLayout_my_logo.setVisibility(View.GONE);
            layout_no_login.setVisibility(View.VISIBLE);
            txt_new_shop.setVisibility(View.GONE);
            textView_guanzhushu.setText("0");
            textView_beiguanzhushu.setText("0");
            textView_popularity.setText("0");

            txtClockNum.setText("0");
            txtGoCourseNum.setText("0");
            txtClassTotalTime.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.item_my_yaoqing:
                if (App.getInstance().isLogin()) {
                    UiManager.activityJump(getActivity(), DisplayActivity.class, "TYPE","InvitationFragment");
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.iv:
                UserInfoActivity.start(getContext(), String.valueOf(App.mInstance.getUser().getId()));
                break;
            case R.id.layout_no_login:
                UiManager.activityJump(getActivity(), LoginActivity.class);
                break;
            case R.id.btn_shop_car:
                UiManager.activityCheckLoginJump(getActivity(), CartActivity.class);
                break;
            case R.id.btn_message:
                UiManager.activityCheckLoginJump(getActivity(), MessageActivity.class);
                break;
            case R.id.imageView_head:
                UserInfoActivity.start(getContext(), String.valueOf(App.mInstance.getUser().getId()));
                break;
            case R.id.item_my_orders:
                if (App.getInstance().isLogin()) {
                    AppointmentMineActivityNew.start(getActivity(), 0);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.txt_sport_record:
                UiManager.activityCheckLoginJump(getActivity(), SportRecordActivity.class);

                break;
            case R.id.layout_hot:
                UiManager.activityCheckLoginJump(getActivity(), AiDongMomentActivity.class);
                break;
//            case relativeLayout_yuyue:
//                if(App.getInstance().isLogin()){
//                    AppointmentMineCampaignActivityNew.start(getActivity(),0);
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//
//                break;
//            case R.id.layout_appoint_course:
//
//                if(App.getInstance().isLogin()){
//                    AppointmentMineCourseActivityNew.start(getActivity(),0);
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//
//                break;
//            case R.id.relativeLayout_dingdang:
//                UiManager.activityCheckLoginJump(getActivity(), OrderActivity.class);
//                break;
            case R.id.item_my_coin:
                UiManager.activityJump(getActivity(), LoveCoinActivity.class);
                break;
            case R.id.item_my_member_card:
                UiManager.activityCheckLoginJump(getActivity(), MyMemberCardActivity.class);
                break;
            case R.id.item_my_coupon:
                UiManager.activityCheckLoginJump(getActivity(), CouponActivity.class);
                break;
            case R.id.item_sport_timing:
                UiManager.activityCheckLoginJump(getActivity(), AiDongMomentActivity.class);
                break;
            case R.id.item_address:
                UiManager.activityCheckLoginJump(getActivity(), AddressActivity.class);
                break;
            case R.id.item_recommend_friend:
                ToastUtil.showConsecutiveShort("暂未开放");
                break;
            case R.id.item_after_sale:
                if (App.getInstance().isLogin()) {
                    WebViewActivity.start(getActivity(), "售后服务", ConstantUrl.URL_RETURN_SERVICE +
                            App.getInstance().getUser().getId() + "&&key=" + Md5Utils.createMd(App.getInstance().getToken()));
                } else {
                    UiManager.activityJump(getActivity(), LoginActivity.class);
                }

                break;
            case R.id.item_setting:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case R.id.linearLayout_guanzhu:
                MyAttentionListActivity.start(getActivity(), 0);

//                UiManager.activityCheckLoginJump(getActivity(), FollowActivity.class);
                break;
            case R.id.linearLayout_beiguanzhu:
                if (App.mInstance.isLogin()) {
                    FollowActivity.start(getContext(), 1);
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(chatMessageReceiver);
        getActivity().unregisterReceiver(registerReceiver);
        registerReceiver = null;
    }

    @Override
    public void onGetMineInfo(MineInfoBean mineInfoBean) {

        if (mineInfoBean.getCart_items_count() > 0) {
            txt_new_shop.setVisibility(View.VISIBLE);
            txt_new_shop.setText(String.valueOf(mineInfoBean.getCart_items_count()));
        } else {
            txt_new_shop.setVisibility(View.GONE);
        }
//        img_new_shop.setVisibility(mineInfoBean.getCart_items_count() > 0 ? View.VISIBLE : View.GONE);
        textView_guanzhushu.setText(mineInfoBean.getFollowings_count() + "");
        textView_beiguanzhushu.setText(mineInfoBean.getFollowers_count() + "");
//        textView_yysl.setText(mineInfoBean.getAppointed_count() + "");
//        textView_yyjrw.setText(mineInfoBean.getAppointing_count() + "");
//        textView_dd.setText(mineInfoBean.getPaid_orders_count() + "");
//        textView_ddjrw.setText(mineInfoBean.getUnpay_orders_count() + "");
        textView_popularity.setText(mineInfoBean.dynamics_count + "");
    }

    @Override
    public void onGetSportRecordData(SportRecordMonthData athletic) {
        if (athletic == null) return;
        txtClockNum.setText(athletic.days);
        txtGoCourseNum.setText(athletic.frequency);
        txtClassTotalTime.setText(athletic.during);
    }

    public void setData(SportRecordMonthData athletic) {
        if (athletic == null) return;
        txtClockNum.setText(athletic.days);
        txtGoCourseNum.setText(athletic.frequency);
        txtClassTotalTime.setText(athletic.during);
    }
}
