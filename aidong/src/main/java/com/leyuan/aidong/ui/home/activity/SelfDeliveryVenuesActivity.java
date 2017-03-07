package com.leyuan.aidong.ui.home.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.SelfDeliveryAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.view.VenuesFilterView;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SelfDeliveryVenuesActivityView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择自提门店
 * Created by song on 2016/9/14.
 */
public class SelfDeliveryVenuesActivity extends BaseActivity implements View.OnClickListener,SelfDeliveryVenuesActivityView{
    private ImageView ivBack;
    private TextView tvFinish;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private VenuesFilterView filterView;

    private int currPage;
    private List<VenuesBean> data;
    private SelfDeliveryAdapter deliveryAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private GoodsDetailPresent goodsPresent;
    private VenuesPresent venuesPresent;
    private String goodsType;
    private String id;
    private String brandId;
    private String businessCircle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_self_delivery);
        goodsPresent = new GoodsDetailPresentImpl(this,this);
        venuesPresent = new VenuesPresentImpl(this,this);
        if(getIntent() != null){
            goodsType = getIntent().getStringExtra("goodsType");
            id = getIntent().getStringExtra("id");
        }

        initView();
        setListener();

        venuesPresent.getGymBrand();
        venuesPresent.getBusinessCircle();
        goodsPresent.commonLoadVenues(switcherLayout,goodsType,id);
    }


    private void initView(){
        initFilterView();
        initSwipeRefreshLayout();
        initRecyclerView();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
    }

    private void initFilterView() {
        filterView = (VenuesFilterView)findViewById(R.id.filter_view);
        filterView.setOnFilterClickListener(new VenuesFilterView.OnFilterClickListener() {
            @Override
            public void onBrandItemClick(String id) {
                brandId = id;
                //todo refresh
            }

            @Override
            public void onBusinessCircleItemClick(String address) {
                businessCircle = address;
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        refreshLayout.setProgressViewOffset(true,100,250);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                goodsPresent.pullToRefreshVenues(goodsType,id);
            }
        });

    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView)findViewById(R.id.rv_address);
        data = new ArrayList<>();
        deliveryAdapter = new SelfDeliveryAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(deliveryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                goodsPresent.requestMoreVenues(recyclerView,pageSize,goodsType,id,currPage);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                compatFinish();
                break;
            case R.id.tv_finish:
                VenuesBean checked = null;
                for (VenuesBean venuesBean : data) {
                    if(venuesBean.isChecked()){
                        checked = venuesBean;
                        break;
                    }
                }
                if(checked == null){
                    Toast.makeText(this,"请选择自提场馆",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("venues",checked);
                    setResult(RESULT_OK,intent);
                    compatFinish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setGymBrand(List<CategoryBean> gymBrandBeanList) {
        if(gymBrandBeanList != null){
            filterView.setBrandList(gymBrandBeanList);
        }
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {
        if(circleBeanList != null){
            filterView.setCircleList(circleBeanList);
        }
    }

    @Override
    public void updateRecyclerView(List<VenuesBean> venuesBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(venuesBeanList);
        deliveryAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide bottom = new Slide();
        bottom.setDuration(300);
        bottom.excludeTarget(android.R.id.statusBarBackground,true);
        bottom.excludeTarget(R.id.rl_top,true);
        bottom.setSlideEdge(Gravity.BOTTOM);
        getWindow().setEnterTransition(bottom);
    }

}
