package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.RecommendAdapter;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.view.CartHeaderView;
import com.example.aidong .ui.mvp.presenter.RecommendPresent;
import com.example.aidong .ui.mvp.presenter.impl.RecommendPresentImpl;
import com.example.aidong .ui.mvp.view.CartActivityView;
import com.example.aidong.ui.mvp.view.HideHeadItemView;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong .utils.Constant.RECOMMEND_CART;


/**
 * 购物车
 * Created by song on 2016/9/8.
 * //todo shit 购物车的实现需要重构
 */
public class CartActivity extends BaseActivity implements CartActivityView, View.OnClickListener,
        CartHeaderView.CartHeaderCallback, OnRefreshListener,HideHeadItemView {
    private ImageView ivBack;
    private TextView tvEdit;

    private CartHeaderView cartHeaderView;
    private CustomRefreshLayout refreshLayout;
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

    private RecommendPresentImpl recommendPresent;
    private boolean isEditing = false;
    private boolean needLoadRecommendData = true;

    private List<String> reBuyIds = new ArrayList<>();

    //再次购买
    public static void start(Context context,List<String> reBuyIds) {
        Intent starter = new Intent(context, CartActivity.class);
        starter.putStringArrayListExtra("reBuyIds", (ArrayList<String>) reBuyIds);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recommendPresent = new RecommendPresentImpl(this,this);
        if(getIntent() != null){
            ArrayList<String> reBuyIds = getIntent().getStringArrayListExtra("reBuyIds");
            if(reBuyIds != null) {
                this.reBuyIds = reBuyIds;
            }
        }

        initView();
        setListener();
        cartHeaderView.pullToRefreshCartData();
    }


    @Override
    protected void onResume() {
        super.onResume();
       //
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
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
        cartHeaderView = new CartHeaderView(this,reBuyIds);
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
        RecyclerViewStateUtils.resetFooterViewState(recommendView);
        cartHeaderView.pullToRefreshCartData();
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (recommendList != null && recommendList.size() >= pageSize) {
                recommendPresent.requestMoreRecommendData(recommendView,pageSize,currPage, RECOMMEND_CART);
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
        if(refreshLayout.isRefreshing()){
            recommendList.clear();
            refreshLayout.setRefreshing(false);
        }
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
    public void onCartDataLoadFinish(boolean isCartEmpty) {
        refreshLayout.setRefreshing(false);
        if(needLoadRecommendData) {
            needLoadRecommendData = false;
            bottomLayout.setVisibility(View.VISIBLE);

            recommendPresent.pullToRefreshRecommendData(RECOMMEND_CART);
        }

        isEditing = false;
        updateEditStatus();

        if(!isCartEmpty){
            tvEdit.setVisibility(View.VISIBLE);
        }else {
            tvEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBottomStatusChange(boolean allChecked, double totalPrice, int settlementCount) {
        rbSelectAll.setChecked(allChecked);
        tvSettlement.setText(String.format(getString(R.string.settlement_count),settlementCount));
        tvTotalPrice.setText(String.format(getString(R.string.rmb_price_double),totalPrice));
        if (recommendList!=null&&recommendList.size()==0&&cartHeaderView!=null){
            cartHeaderView.showRecommendText(false);
        }
    }


    private void updateEditStatus(){
        tvEdit.setText(isEditing ? R.string.finish : R.string.edit);
        bottomDeleteLayout.setVisibility(isEditing ? View.VISIBLE :View.GONE);
        bottomNormalLayout.setVisibility(isEditing ? View.GONE : View.VISIBLE);

        cartHeaderView.setEditingStatus(isEditing);
        recommendAdapter.setData(isEditing ? emptyRecommendList : recommendList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideHeadItemView() {

    }
}
