package com.leyuan.aidong.ui.mine.fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.chat.manager.EmMessageManager;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mine.account.LoginActivity;
import com.leyuan.aidong.ui.mine.activity.AddressActivity;
import com.leyuan.aidong.ui.mine.activity.AiDongMomentActivity;
import com.leyuan.aidong.ui.mine.activity.ApplyServiceActivity;
import com.leyuan.aidong.ui.mine.activity.AppointmentActivity;
import com.leyuan.aidong.ui.mine.activity.CartActivity;
import com.leyuan.aidong.ui.mine.activity.CouponActivity;
import com.leyuan.aidong.ui.mine.activity.FollowActivity;
import com.leyuan.aidong.ui.mine.activity.LoveCoinActivity;
import com.leyuan.aidong.ui.mine.activity.MessageActivity;
import com.leyuan.aidong.ui.mine.activity.OrderActivity;
import com.leyuan.aidong.ui.mine.activity.TabMinePersonalSettingsActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.AidongMineItem;

import retrofit2.http.HEAD;


public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private LinearLayout layout_no_login, linearLayout_guanzhu, linearLayout_beiguanzhu, layout_hot;
    private ImageButton button_login, btn_shop_car, btn_message;
    private RelativeLayout relativeLayout_my_logo, relativeLayout_yuyue, relativeLayout_dingdang;
    private ImageView imageView_head, img_new_message;
    private ImageView imageView_xinbie;
    private TextView textView_name, textView_guanzhushu, textView_beiguanzhushu, textView_popularity, textView_yysl, textView_yyjrw, textView_dd, textView_ddjrw;
    private AidongMineItem item_my_coin, item_my_coupon, item_sport_timing, item_address,
            item_recommend_friend, item_after_sale, item_setting;
    private UserCoach user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void initView() {
        layout_no_login = (LinearLayout) rootView.findViewById(R.id.layout_no_login);
        linearLayout_guanzhu = (LinearLayout) rootView.findViewById(R.id.linearLayout_guanzhu);
        linearLayout_beiguanzhu = (LinearLayout) rootView.findViewById(R.id.linearLayout_beiguanzhu);
        layout_hot = (LinearLayout) rootView.findViewById(R.id.layout_hot);

        button_login = (ImageButton) rootView.findViewById(R.id.button_login);
        btn_shop_car = (ImageButton) rootView.findViewById(R.id.btn_shop_car);
        btn_message = (ImageButton) rootView.findViewById(R.id.btn_message);
        img_new_message = (ImageView) rootView.findViewById(R.id.img_new_message);

        relativeLayout_my_logo = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_my_logo);
        relativeLayout_yuyue = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_yuyue);
        relativeLayout_dingdang = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_dingdang);

        imageView_head = (ImageView) rootView.findViewById(R.id.imageView_head);
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

    private void setViewEvent() {
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

    private void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        img_new_message.setVisibility(EmMessageManager.isHaveUnreadMessage() ? View.VISIBLE : View.GONE);
        if (App.mInstance.isLogin()) {
            relativeLayout_my_logo.setVisibility(View.VISIBLE);
            layout_no_login.setVisibility(View.GONE);
            user = App.mInstance.getUser();
            textView_name.setText(user.getName());
//            +"- " +user.getMobile()+"https://www.zhihu.com/question/31660075"
            //imageView_head.setImageURI(Uri.parse(user.getAvatar()));
            GlideLoader.getInstance().displayCircleImage(user.getAvatar(), imageView_head);
        } else {
            relativeLayout_my_logo.setVisibility(View.GONE);
            layout_no_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
//                UiManager.activityJump(getActivity(), MyShowActivityNew.class);
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
                //startActivity(new Intent(getContext(), UserInfoActivity.class));

               /* Intent intent = new Intent();
                intent.setClass(getActivity(), MyShowActivityNew.class);
//                intent.setClass(getActivity(), CompleteUserInfomationActivity.class);
                intent.putExtra(com.leyuan.aidong.utils.common.Constant.USER,App.mInstance.getUser());
                startActivity(intent);*/
                break;
            case R.id.relativeLayout_yuyue:
                UiManager.activityCheckLoginJump(getActivity(), AppointmentActivity.class);
                break;
            case R.id.relativeLayout_dingdang:
                UiManager.activityCheckLoginJump(getActivity(), OrderActivity.class);
                break;
            case R.id.item_my_coin:
                UiManager.activityJump(getActivity(), LoveCoinActivity.class);
                break;
            case R.id.item_my_coupon:
                UiManager.activityCheckLoginJump(getActivity(), CouponActivity.class);
                break;
            case R.id.item_sport_timing:
                UiManager.activityCheckLoginJump(getActivity(), AiDongMomentActivity.class);
                break;
            case R.id.item_address:
                // AddressActivity.start(getActivity());
                UiManager.activityCheckLoginJump(getActivity(), AddressActivity.class);
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
            case R.id.linearLayout_guanzhu:
                UiManager.activityCheckLoginJump(getActivity(), FollowActivity.class);
                break;
            case R.id.linearLayout_beiguanzhu:
                if (App.mInstance.isLogin()) {
                    FollowActivity.start(getContext(), 1);
                } else {
                    Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
        }
    }
}
