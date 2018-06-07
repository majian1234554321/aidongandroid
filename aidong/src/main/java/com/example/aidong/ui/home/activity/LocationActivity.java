package com.example.aidong.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.CityAdapter;
import com.example.aidong .entity.user.MineInfoBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.HomePresent;
import com.example.aidong .ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.HomePresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.example.aidong .ui.mvp.presenter.impl.SystemPresentImpl;
import com.example.aidong .ui.mvp.view.LocationActivityView;
import com.example.aidong .ui.mvp.view.MineInfoView;
import com.example.aidong .ui.mvp.view.RequestCountInterface;
import com.example.aidong .ui.mvp.view.SystemView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.RequestResponseCount;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .widget.SimpleTitleBar;

import java.util.List;

/**
 * 城市切换界面
 * Created by song on 2016/8/23.
 */
public class LocationActivity extends BaseActivity implements LocationActivityView, CityAdapter.OnCitySelectListener, SystemView, MineInfoView, RequestCountInterface {
    private SimpleTitleBar titleBar;
    private TextView tvLocation;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private ImageView img_selected;
//    private SystemPresentImpl systemPresent;
//    private MineInfoPresenterImpl presenter;
    int requestNum ;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        HomePresent homePresent = new HomePresentImpl(this, this);
        initView();
        homePresent.getOpenCity();
//        systemPresent = new SystemPresentImpl(this);
//        systemPresent.setSystemView(this);
//        presenter = new MineInfoPresenterImpl(this, this);
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        recyclerView = (RecyclerView) findViewById(R.id.rv_city);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_selected = (ImageView) findViewById(R.id.img_selected);
        tvLocation.setText(App.getInstance().getLocationCity() == null ? "上海" : App.getInstance().getLocationCity());
        img_selected.setVisibility(TextUtils.equals(App.getInstance().getLocationCity(), App.getInstance().getSelectedCity()) ?
                View.VISIBLE : View.GONE);

        cityAdapter = new CityAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void setOpenCity(final List<String> cities) {
        cityAdapter.setData(cities);
    }

    @Override
    public void onCitySelect(String str) {
        if (!TextUtils.equals(App.getInstance().getSelectedCity(), str)) {
            App.getInstance().setSelectedCity(str);

            RequestResponseCount requestResponse = new RequestResponseCount(this);

            if (App.getInstance().isLogin()) {
                MineInfoPresenterImpl mineInfoPresenter = new MineInfoPresenterImpl(this);
                mineInfoPresenter.setOnRequestResponse(requestResponse);
                mineInfoPresenter.getMineInfo();
                requestNum++;
            }

            SystemPresentImpl systemPresent = new SystemPresentImpl(this);
            systemPresent.setOnRequestResponse(requestResponse);
            systemPresent.getSystemInfoSelected(Constant.OS);
            requestNum++;





            CourseConfigPresentImpl coursePresentNew = new CourseConfigPresentImpl(this);
            coursePresentNew.setOnRequestResponse(requestResponse);
            coursePresentNew.getCourseFilterConfig();
            requestNum++;



        }else {
            finish();
        }
    }

    @Override
    public void onGetSystemConfiguration(boolean b) {
//        if (App.getInstance().isLogin()) {
//            presenter.getMineInfo();
//        } else {
//            sendBroadcastAndFinish();
//        }
    }

    @Override
    public void onGetMineInfo(MineInfoBean mineInfoBean) {
//        sendBroadcastAndFinish();
    }


    private void sendBroadcastAndFinish() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_SELECTED_CITY));
        ToastGlobal.showLong("已切换到" + App.getInstance().getSelectedCity());
        finish();

    }

    @Override
    public void onRequestCount(int requestCount) {

        Logger.i("LocationActivity","requestCount = " +requestCount);
        if(requestCount >= requestNum){
            sendBroadcastAndFinish();
        }
    }
}
