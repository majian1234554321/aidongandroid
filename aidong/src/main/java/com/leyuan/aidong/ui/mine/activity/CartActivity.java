package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.RecommendAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.view.CartHeaderView;
import com.leyuan.aidong.ui.mvp.presenter.RecommendPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.RecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.utils.constant.RecommendGoods;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.REQUEST_SETTLEMENT_CART;


/**
 * 购物车
 * Created by song on 2016/9/8.
 * //todo shit 购物车的实现需要重构
 */
public class CartActivity extends BaseActivity implements CartActivityView, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, CartHeaderView.CartHeaderCallback {
    private ImageView ivBack;
    private TextView tvEdit;

    private CartHeaderView cartHeaderView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendView;
    private int currPage = 1;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<GoodsBean> recommendList = new ArrayList<>();
    private List<GoodsBean> emptyRecommendList = new ArrayList<>();

    private LinearLayout bottomLayout;
    private CheckBox rbSelectAll;
    private LinearLayout bottomNormalLayout;
    private TextView tvTotalPrice;
    private TextView tvSettlement;
    private LinearLayout bottomDeleteLayout;
    private TextView tvDelete;

    private RecommendPresent recommendPresent;
    private boolean isEditing = false;
    private boolean isFirstGetData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recommendPresent = new RecommendPresentImpl(this,this);
        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        recommendView = (RecyclerView)findViewById(R.id.rv_recommend);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        rbSelectAll = (CheckBox) findViewById(R.id.rb_select_all);
        bottomNormalLayout = (LinearLayout) findViewById(R.id.ll_bottom_normal);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvSettlement = (TextView) findViewById(R.id.tv_settlement);
        bottomDeleteLayout = (LinearLayout) findViewById(R.id.ll_bottom_delete);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        recommendView.addOnScrollListener(onScrollListener);
        cartHeaderView = new CartHeaderView(this);
        RecyclerViewUtils.setHeaderView(recommendView, cartHeaderView);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        rbSelectAll.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        cartHeaderView.setCartCallback(this);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        cartHeaderView.pullToRefreshCartData();
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (recommendList != null && recommendList.size() >= pageSize) {
                recommendPresent.requestMoreRecommendData(recommendView,pageSize,currPage,
                        RecommendGoods.CART);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                isEditing = !isEditing;
                updateEditStatus();
                break;
            case R.id.tv_settlement:
                cartHeaderView.settlementSelectGoods();
                break;
            case R.id.tv_delete:
                cartHeaderView.deleteSelectedGoods();
                break;
            case R.id.rb_select_all:
                cartHeaderView.changeAllGoodsStatus(rbSelectAll.isChecked());
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRecommendGoods(List<GoodsBean> goodsBeanList) {
        recommendList.addAll(goodsBeanList);
        recommendAdapter.setData(recommendList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyRecommendView() {
        cartHeaderView.showRecommendText(false);
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recommendView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onCartDataLoadFinish() {
        refreshLayout.setRefreshing(false);
        if(isFirstGetData) {
            isFirstGetData = false;
            tvEdit.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            cartHeaderView.showRecommendText(!isEditing);
            recommendPresent.pullToRefreshRecommendData(RecommendGoods.CART);
        }
    }

    @Override
    public void onAllCheckedChanged(boolean allChecked) {
        rbSelectAll.setChecked(allChecked);
    }

    @Override
    public void onPriceAndSettlementCountChanged(double totalPrice, int count) {
        tvSettlement.setText(String.format(getString(R.string.settlement_count),count));
        tvTotalPrice.setText(String.format(getString(R.string.rmb_price_double),totalPrice));
    }

    @Override
    public void onAllGoodsDeleted() {
        isEditing = false;
        updateEditStatus();
    }

    private void updateEditStatus(){
        tvEdit.setText(isEditing ? R.string.finish : R.string.edit);
        bottomDeleteLayout.setVisibility(isEditing ? View.VISIBLE :View.GONE);
        bottomNormalLayout.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        cartHeaderView.showRecommendText(!isEditing);
        recommendAdapter.setData(isEditing ? emptyRecommendList : recommendList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_SETTLEMENT_CART){
                List<ShopBean> shopBeanList = data.getParcelableArrayListExtra("shopBeanList");
                cartHeaderView.updateCartLocal(shopBeanList);
            }
        }
    }
}
