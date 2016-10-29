package com.leyuan.aidong.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.mine.AddressActivity;
import com.leyuan.aidong.ui.activity.mine.ApplyServiceActivity;
import com.leyuan.aidong.ui.activity.mine.AppointmentActivity;
import com.leyuan.aidong.ui.activity.mine.CartActivity;
import com.leyuan.aidong.ui.activity.mine.CouponActivity;
import com.leyuan.aidong.ui.activity.mine.FollowActivity;
import com.leyuan.aidong.ui.activity.mine.LoveCoinActivity;
import com.leyuan.aidong.ui.activity.mine.OrderActivity;
import com.leyuan.aidong.ui.activity.mine.TabMinePersonalSettingsActivity;
import com.leyuan.aidong.widget.customview.AidongMineItem;
import com.leyuan.aidong.widget.customview.CircleImageView;
import com.leyuan.commonlibrary.manager.UiManager;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private LinearLayout layout_no_login, linearLayout_guanzhu, linearLayout_beiguanzhu, layout_hot;
    private ImageButton button_login, btn_shop_car, btn_message;
    private RelativeLayout relativeLayout_my_logo, relativeLayout_yuyue, relativeLayout_dingdang;
    private CircleImageView imageView_head;
    private ImageView imageView_xinbie;
    private TextView textView_name, textView_guanzhushu, textView_beiguanzhushu, textView_popularity, textView_yysl, textView_yyjrw, textView_dd, textView_ddjrw;
    private AidongMineItem item_my_coin, item_my_coupon, item_sport_timing, item_address,
            item_recommend_friend, item_after_sale, item_setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        layout_no_login = (LinearLayout) rootView.findViewById(R.id.layout_no_login);
        linearLayout_guanzhu = (LinearLayout) rootView.findViewById(R.id.linearLayout_guanzhu);
        linearLayout_beiguanzhu = (LinearLayout) rootView.findViewById(R.id.linearLayout_beiguanzhu);
        layout_hot = (LinearLayout) rootView.findViewById(R.id.layout_hot);

        button_login = (ImageButton) rootView.findViewById(R.id.button_login);
        btn_shop_car = (ImageButton) rootView.findViewById(R.id.btn_shop_car);
        btn_message = (ImageButton) rootView.findViewById(R.id.btn_message);

        relativeLayout_my_logo = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_my_logo);
        relativeLayout_yuyue = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_yuyue);
        relativeLayout_dingdang = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_dingdang);

        imageView_head = (CircleImageView) rootView.findViewById(R.id.imageView_head);
        imageView_xinbie = (ImageView) rootView.findViewById(R.id.imageView_xinbie);

        textView_name = (TextView) rootView.findViewById(R.id.textView_name);
        textView_guanzhushu = (TextView) rootView.findViewById(R.id.textView_guanzhushu);
        textView_beiguanzhushu = (TextView) rootView.findViewById(R.id.textView_beiguanzhushu);
        textView_popularity = (TextView) rootView.findViewById(R.id.textView_popularity);
        textView_yysl = (TextView) rootView.findViewById(R.id.textView_yysl);
        textView_yyjrw = (TextView) rootView.findViewById(R.id.textView_yyjrw);
        textView_dd = (TextView) rootView.findViewById(R.id.textView_dd);
        textView_ddjrw = (TextView) rootView.findViewById(R.id.textView_ddjrw);

        item_my_coin = (AidongMineItem) rootView.findViewById(R.id.item_my_coin);
        item_my_coupon = (AidongMineItem) rootView.findViewById(R.id.item_my_coupon);
        item_sport_timing = (AidongMineItem) rootView.findViewById(R.id.item_sport_timing);
        item_address = (AidongMineItem) rootView.findViewById(R.id.item_address);
        item_recommend_friend = (AidongMineItem) rootView.findViewById(R.id.item_recommend_friend);
        item_after_sale = (AidongMineItem) rootView.findViewById(R.id.item_after_sale);
        item_setting = (AidongMineItem) rootView.findViewById(R.id.item_setting);
    }

    private void initData() {
        button_login.setOnClickListener(this);
        btn_shop_car.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        imageView_head.setOnClickListener(this);
        linearLayout_guanzhu.setOnClickListener(this);
        linearLayout_beiguanzhu.setOnClickListener(this);
        layout_hot.setOnClickListener(this);
        relativeLayout_yuyue.setOnClickListener(this);
        relativeLayout_dingdang.setOnClickListener(this);
        item_my_coin.setOnClickListener(this);
        item_my_coupon.setOnClickListener(this);
        item_sport_timing.setOnClickListener(this);
        item_address.setOnClickListener(this);
        item_recommend_friend.setOnClickListener(this);
        item_after_sale.setOnClickListener(this);
        item_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case R.id.btn_shop_car:
                UiManager.activityJump(getActivity(), CartActivity.class);
                break;
            case R.id.btn_message:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case R.id.imageView_head:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case R.id.relativeLayout_yuyue:
                UiManager.activityJump(getActivity(), AppointmentActivity.class);
                break;
            case R.id.relativeLayout_dingdang:
                UiManager.activityJump(getActivity(), OrderActivity.class);
                break;
            case R.id.item_my_coin:
                UiManager.activityJump(getActivity(), LoveCoinActivity.class);
                break;
            case R.id.item_my_coupon:
                UiManager.activityJump(getActivity(), CouponActivity.class);
                break;
            case R.id.item_sport_timing:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case R.id.item_address:
                UiManager.activityJump(getActivity(), AddressActivity.class);
                break;
            case R.id.item_recommend_friend:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case R.id.item_after_sale:
                UiManager.activityJump(getActivity(), ApplyServiceActivity.class);
                break;
            case R.id.item_setting:
                UiManager.activityJump(getActivity(), TabMinePersonalSettingsActivity.class);
                break;
            case  R.id.linearLayout_guanzhu:
                UiManager.activityJump(getActivity(), FollowActivity.class);
                break;
        }
    }
}
