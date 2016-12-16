package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.SelfDeliveryAdapter;
import com.leyuan.aidong.entity.DeliveryBean;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SelfDeliveryVenuesActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
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
    private int currPage;
    private List<DeliveryBean> data;
    private SelfDeliveryAdapter deliveryAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private GoodsDetailPresent goodsPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_delivery);
        goodsPresent = new GoodsDetailPresentImpl(this,this);
        initView();
        setListener();
        //goodsPresent.commonLoadVenues(switcherLayout,);
    }


    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
               // present.pullToRefreshBrandData(id);
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
      //  RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
               // present.requestMorBrandeData(recyclerView,pageSize,currPage,id);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                break;
            default:
                break;
        }
    }

    @Override
    public void setGymBrand(List<CategoryBean> gymBrandBeanList) {

    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {

    }

    @Override
    public void updateRecyclerView(List<VenuesBean> venuesBeanList) {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
