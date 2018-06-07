package com.leyuan.aidong.ui.discover.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.VenuesDetailBean;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.discover.activity.VenuesSubbranchActivity;
import com.leyuan.aidong.ui.home.activity.GoodsListActivity;
import com.leyuan.aidong.ui.home.activity.MapActivity;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesDetailFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.utils.TelephoneManager;
import com.leyuan.aidong.widget.SwitcherLayout;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.example.aidong.R.id.tv_subbranch;
import static com.leyuan.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailFragment extends BaseFragment implements View.OnClickListener, VenuesDetailFragmentView {
    private SwitcherLayout switcherLayout;
    private RelativeLayout contentLayout;
    private BGABanner bannerLayout;
    private FloatingActionButton shareButton;
    private TextView tvName;
    private TextView tvPrice, tv_price_separator;
    private TextView tvDistance;
    private LinearLayout nurtureLayout;
    private LinearLayout equipmentLayout;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvOpenTime;
    private LinearLayout subbranchLayout;
    private TextView tvSubbranch;
    private ImageView ivParking;
    private ImageView ivWifi;
    private ImageView ivBath;
    private ImageView ivFood;

    private String id;
    private VenuesDetailBean venues;

    private SharePopupWindow sharePopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VenuesPresent venuesPresent = new VenuesPresentImpl(getContext(), this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }

        initView(view);
        setListener();
        venuesPresent.getVenuesDetail(switcherLayout, id);
        sharePopupWindow = new SharePopupWindow(getActivity());
    }

    private void initView(View view) {
        contentLayout = (RelativeLayout) view.findViewById(R.id.main_content);
        switcherLayout = new SwitcherLayout(getContext(), contentLayout);
        bannerLayout = (BGABanner) view.findViewById(R.id.banner);
        shareButton = (FloatingActionButton) view.findViewById(R.id.fb_share);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tv_price_separator = (TextView) view.findViewById(R.id.tv_price_separator);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
        nurtureLayout = (LinearLayout) view.findViewById(R.id.ll_nurture);
        equipmentLayout = (LinearLayout) view.findViewById(R.id.ll_equipment);
        tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        tvOpenTime = (TextView) view.findViewById(R.id.tv_open_time);
        subbranchLayout = (LinearLayout) view.findViewById(R.id.ll_subbranch);
        tvSubbranch = (TextView) view.findViewById(tv_subbranch);
        ivParking = (ImageView) view.findViewById(R.id.iv_parking);
        ivWifi = (ImageView) view.findViewById(R.id.iv_wifi);
        ivBath = (ImageView) view.findViewById(R.id.iv_bath);
        ivFood = (ImageView) view.findViewById(R.id.iv_food);

        ViewGroup.LayoutParams layoutParams = bannerLayout.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenWidth(getContext());
    }

    private void setListener() {
        shareButton.setOnClickListener(this);
        nurtureLayout.setOnClickListener(this);
        equipmentLayout.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvSubbranch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_share:
                if (venues != null) {
                    String image = "";
                    if (venues.getPhoto() != null && !venues.getPhoto().isEmpty()) {
                        image = venues.getPhoto().get(0);
                    }
                    sharePopupWindow.showAtBottom(venues.getName() + Constant.I_DONG_FITNESS, venues.getIntroduce(),
                            image, ConstantUrl.URL_SHARE_GYM + venues.getId());
                }

                break;

            case R.id.ll_equipment:
                GoodsListActivity.start(getContext(), GOODS_EQUIPMENT, venues.getName(), venues.getId());
                break;
            case R.id.ll_nurture:
                GoodsListActivity.start(getContext(), GOODS_NUTRITION, venues.getName(), venues.getId());
                break;
            case R.id.tv_address:
                if (venues != null) {
                    MapActivity.start(getActivity(), "场馆地址", venues.getName(), venues.getAddress(),
                            venues.getCoordinate().getLat() + "", venues.getCoordinate().getLng() + "");
                }
                break;
            case R.id.tv_phone:
                TelephoneManager.callImmediate(getActivity(), venues.getTel());
                break;
            case tv_subbranch:
                VenuesSubbranchActivity.start(getActivity(), venues.getBrother());
                break;
            default:
                break;
        }
    }

    @Override
    public void setVenuesDetail(VenuesDetailBean venues) {
        ((VenuesDetailActivity) getActivity()).loadFinish(venues);
        this.venues = venues;

        tvName.setText(venues.getName());
        if (venues.getPrice() == null) {
            tvPrice.setText("");
            tv_price_separator.setText("");
        } else {
            tvPrice.setText(venues.getPrice() + "元起");
        }

        tvDistance.setText(venues.getDistanceFormat());
        tvAddress.setText(venues.getAddress());
        tvPhone.setText(venues.getTel());
        tvOpenTime.setText(venues.getBusiness_time());
        if (venues.getBrother() != null && !venues.getBrother().isEmpty()) {
            subbranchLayout.setVisibility(View.VISIBLE);
            tvSubbranch.setText("其他" + venues.getBrother().size() + "家分店");
        } else {
            subbranchLayout.setVisibility(View.GONE);
        }

        ivParking.setImageResource(venues.getService().contains("1") ? R.drawable.icon_parking :
                R.drawable.icon_parking_gray);
        ivWifi.setImageResource(venues.getService().contains("2") ? R.drawable.icon_wifi :
                R.drawable.icon_wifi_gray);
        ivBath.setImageResource(venues.getService().contains("3") ? R.drawable.icon_bath :
                R.drawable.icon_bath_gray);
        ivFood.setImageResource(venues.getService().contains("4") ? R.drawable.icon_food :
                R.drawable.icon_food_gray);

        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage(((String) model), (ImageView) view);
            }
        });

        bannerLayout.setData(venues.getPhoto(), null);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
    }
}
