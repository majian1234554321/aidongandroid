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

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesDetailBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.NurtureActivity;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesDetailFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailFragment extends BaseFragment implements View.OnClickListener ,VenuesDetailFragmentView {
    private SwitcherLayout switcherLayout;
    private RelativeLayout contentLayout;
    private BGABanner bannerLayout;
    private FloatingActionButton shareButton;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvDistance;
    private TextView tvFood;
    private TextView tvNurture;
    private TextView tvEquipment;
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
    private VenuesDetailBean venuesBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_detail, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VenuesPresent venuesPresent = new VenuesPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getString("id");
        }
        initView(view);
        setListener();
        venuesPresent.getVenuesDetail(switcherLayout,id);
    }

    private void initView(View view) {
        contentLayout = (RelativeLayout) view.findViewById(R.id.main_content);
        switcherLayout = new SwitcherLayout(getContext(),contentLayout);
        bannerLayout = (BGABanner) view.findViewById(R.id.banner);
        shareButton = (FloatingActionButton) view.findViewById(R.id.fb_share);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        tvFood = (TextView) view.findViewById(R.id.tv_food);
        tvNurture = (TextView) view.findViewById(R.id.tv_nurture);
        tvEquipment = (TextView) view.findViewById(R.id.tv_equipment);
        tvAddress = (TextView) view.findViewById(R.id.tv_address);
        tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        tvOpenTime = (TextView) view.findViewById(R.id.tv_open_time);
        subbranchLayout = (LinearLayout) view.findViewById(R.id.ll_subbranch);
        tvSubbranch = (TextView) view.findViewById(R.id.tv_subbranch);
        ivParking = (ImageView) view.findViewById(R.id.iv_parking);
        ivWifi = (ImageView) view.findViewById(R.id.iv_wifi);
        ivBath = (ImageView) view.findViewById(R.id.iv_bath);
        ivFood = (ImageView) view.findViewById(R.id.iv_food);
    }

    private void setListener(){
        shareButton.setOnClickListener(this);
        tvFood.setOnClickListener(this);
        tvNurture.setOnClickListener(this);
        tvEquipment.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvSubbranch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fb_share:
                break;
            case R.id.tv_food:
                break;
            case R.id.tv_nurture:
                startActivity(new Intent(getContext(), NurtureActivity.class));
                break;
            case R.id.tv_equipment:
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_phone:
                break;
            case R.id.tv_subbranch:
                break;
            default:
                break;
        }
    }

    @Override
    public void setVenuesDetail(VenuesDetailBean venuesBean) {
        ((VenuesDetailActivity)getActivity()).loadFinish(venuesBean);
        tvName.setText(venuesBean.getName());
        tvAddress.setText(venuesBean.getAddress());
        tvPhone.setText(venuesBean.getTel());
        tvOpenTime.setText(venuesBean.getBusiness_time());
        ivParking.setImageResource(venuesBean.getService().contains("1") ? R.drawable.icon_parking :
                R.drawable.icon_parking_gray);
        ivWifi.setImageResource(venuesBean.getService().contains("2") ? R.drawable.icon_wifi :
                R.drawable.icon_wifi_gray);
        ivBath.setImageResource(venuesBean.getService().contains("3") ? R.drawable.icon_bath :
                R.drawable.icon_bath_gray);
        ivFood.setImageResource(venuesBean.getService().contains("4") ? R.drawable.icon_food :
                R.drawable.icon_food_gray);
    }

}
