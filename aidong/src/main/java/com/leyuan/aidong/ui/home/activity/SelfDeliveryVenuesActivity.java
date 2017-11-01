package com.leyuan.aidong.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.SelfDeliveryAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DeliveryBean;
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
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择自提门店
 * Created by song on 2016/9/14.
 */
public class SelfDeliveryVenuesActivity extends BaseActivity implements View.OnClickListener, SelfDeliveryVenuesActivityView {
    private ImageView ivBack;
    private TextView tvFinish;

    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private VenuesFilterView filterView;

    private int currPage;
    private List<VenuesBean> data;
    private SelfDeliveryAdapter deliveryAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private GoodsDetailPresent goodsPresent;
    private VenuesPresent venuesPresent;
    private String goodsType;
    private String goodsId;
    private String brandId;
    private String businessCircle;
    private DeliveryBean deliveryBean;

    public static void startForResult(Activity context, String goodsId, String goodsType, DeliveryBean bean, int requestCode) {
        Intent starter = new Intent(context, SelfDeliveryVenuesActivity.class);
        starter.putExtra("goodsId", goodsId);
        starter.putExtra("goodsType", goodsType);
        starter.putExtra("deliveryBean", bean);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_delivery);
        setSlideAnimation();
        goodsPresent = new GoodsDetailPresentImpl(this, this);
        venuesPresent = new VenuesPresentImpl(this, this);
        if (getIntent() != null) {
            goodsType = getIntent().getStringExtra("goodsType");
            goodsId = getIntent().getStringExtra("goodsId");
            deliveryBean = getIntent().getParcelableExtra("deliveryBean");
        }

        initView();
        setListener();
        if (goodsId != null) {
            venuesPresent.getGymBrand();
            venuesPresent.getBusinessCircle();
            goodsPresent.commonLoadVenues(switcherLayout, goodsType, goodsId, brandId, businessCircle);
        }

    }


    private void initView() {
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
        filterView = (VenuesFilterView) findViewById(R.id.filter_view);
        filterView.hideTypeFilerView();
        filterView.setOnFilterClickListener(new VenuesFilterView.OnFilterClickListener() {
            @Override
            public void onBrandItemClick(String id) {
                brandId = id;
                refreshLayout.setRefreshing(true);
                goodsPresent.pullToRefreshVenues(goodsType, goodsId, brandId, businessCircle);
            }

            @Override
            public void onBusinessCircleItemClick(String area,String address) {
                businessCircle = address;
                refreshLayout.setRefreshing(true);
                goodsPresent.pullToRefreshVenues(goodsType, goodsId, brandId, businessCircle);
            }

            @Override
            public void onTypeItemClick(String type) {

            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setProgressViewOffset(true, 50, 250);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                goodsPresent.pullToRefreshVenues(goodsType, goodsId, brandId, businessCircle);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_address);
        data = new ArrayList<>();
        deliveryAdapter = new SelfDeliveryAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(deliveryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                goodsPresent.requestMoreVenues(recyclerView, pageSize, goodsType, goodsId, currPage, brandId, businessCircle);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                compatFinish();
                break;
            case R.id.tv_finish:
                VenuesBean checked = null;
                for (VenuesBean venuesBean : data) {
                    if (venuesBean.isChecked()) {
                        checked = venuesBean;
                        break;
                    }
                }
                if (checked == null) {
                    Toast.makeText(this, "请选择自提场馆", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("venues", checked);
                    setResult(RESULT_OK, intent);
                    compatFinish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setGymBrand(List<CategoryBean> gymBrandBeanList) {
        if (gymBrandBeanList != null) {
            filterView.setBrandList(gymBrandBeanList);
        }
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {
        if (circleBeanList != null) {
            filterView.setCircleList(circleBeanList);
        }
    }

    @Override
    public void onRefreshData(List<VenuesBean> venuesBeanList) {
        switcherLayout.showContentLayout();
        if (deliveryBean != null && deliveryBean.getInfo() != null) {
            for (VenuesBean venuesBean : venuesBeanList) {
                if (venuesBean.getId().equals(deliveryBean.getInfo().getId())) {
                    venuesBean.setChecked(true);
                    break;
                }
            }
        }

        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }

        data.addAll(venuesBeanList);
        deliveryAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreData(List<VenuesBean> venuesBeanList) {
        data.addAll(venuesBeanList);
        deliveryAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(this, R.layout.empty_venues, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
