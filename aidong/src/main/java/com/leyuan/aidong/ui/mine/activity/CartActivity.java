package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.ConfirmOrderActivity;
import com.leyuan.aidong.adapter.home.RecommendAdapter;
import com.leyuan.aidong.adapter.mine.CartShopAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.ui.home.activity.ConfirmOrderActivity.ORDER_CART;


/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartActivity extends BaseActivity implements CartActivityView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CartShopAdapter.ShopChangeListener{
    private ImageView ivBack;
    private TextView tvEdit;

    //购物车
    private SwipeRefreshLayout refreshLayout;
    private SwitcherLayout switcherLayout;
    private RecyclerView shopView;
    private CartShopAdapter shopAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();

    //推荐
    private LinearLayout recommendLayout;
    private RecyclerView recommendView;
    private RecommendAdapter recommendAdapter;
    private List<GoodsBean> recommendList = new ArrayList<>();

    private LinearLayout bottomLayout;
    private CheckBox rbSelectAll;
    private LinearLayout bottomNormalLayout;
    private TextView tvTotalPrice;
    private TextView tvSettlement;
    private LinearLayout bottomDeleteLayout;
    private TextView tvDelete;

    private CartPresent cartPresent;
    private boolean isEditing = false;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartPresent = new CartPresentImpl(this,this);

        initView();
        setListener();
        cartPresent.commonLoadingData(switcherLayout);
        cartPresent.pullToRefreshRecommendData();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        shopView = (RecyclerView)findViewById(R.id.rv_cart);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,shopView);
        recommendLayout = (LinearLayout) findViewById(R.id.ll_recommend);
        recommendView = (RecyclerView)findViewById(R.id.rv_recommend);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        rbSelectAll = (CheckBox) findViewById(R.id.rb_select_all);
        bottomNormalLayout = (LinearLayout) findViewById(R.id.ll_bottom_normal);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvSettlement = (TextView) findViewById(R.id.tv_settlement);
        bottomDeleteLayout = (LinearLayout) findViewById(R.id.ll_bottom_delete);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        shopView.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new CartShopAdapter(this);
        shopView.setAdapter(shopAdapter);
        shopView.setNestedScrollingEnabled(false);
        recommendView.setNestedScrollingEnabled(false);
        recommendAdapter = new RecommendAdapter(this);
        recommendView.setAdapter(recommendAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recommendView.setLayoutManager(manager);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        rbSelectAll.setOnClickListener(this);
        shopAdapter.setShopChangeListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                isEditing = !isEditing;
                tvEdit.setText(isEditing ? R.string.finish : R.string.edit);
                bottomDeleteLayout.setVisibility(isEditing ? View.VISIBLE :View.GONE);
                bottomNormalLayout.setVisibility(isEditing ? View.GONE : View.VISIBLE);
                recommendLayout.setVisibility(isEditing ? View.GONE : View.VISIBLE);
                break;
            case R.id.tv_settlement:
                ArrayList<ShopBean> selectedShops = getSelectedShops();
                if(selectedShops.isEmpty()){
                    Toast.makeText(this,R.string.tip_select_goods,Toast.LENGTH_LONG).show();
                    return;
                }
                ConfirmOrderActivity.start(this,ORDER_CART,selectedShops,totalPrice);
                break;
            case R.id.tv_delete:
                List<GoodsBean> selectedGoods = getSelectedGoods();
                if(selectedGoods.isEmpty()){
                    Toast.makeText(this,R.string.tip_select_goods,Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuilder ids = new StringBuilder();
                for (int i = 0; i < selectedGoods.size(); i++) {

                }
                for (GoodsBean selectedGood : selectedGoods) {
                    ids.append(selectedGood.getId()).append(",");
                }
                cartPresent.deleteCart(ids.toString());
                break;
            case R.id.rb_select_all:
                //该变购物车状态同时改变购物车中的商店和商品的属性
                for (ShopBean bean : shopBeanList) {
                    bean.setChecked(rbSelectAll.isChecked());
                    for (GoodsBean goodsBean : bean.getItem()) {
                        goodsBean.setChecked(rbSelectAll.isChecked());
                    }
                }
                shopAdapter.notifyDataSetChanged();
                setTotalPrice();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        cartPresent.pullToRefreshData();
        if(!isEditing) {
            cartPresent.pullToRefreshRecommendData();
        }
    }

    @Override
    public void updateRecyclerView(List<ShopBean> list) {
        tvEdit.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.VISIBLE);
        switcherLayout.showContentLayout();
       // recommendList.clear();
        shopBeanList.clear();
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        shopBeanList.addAll(list);
        shopAdapter.setData(shopBeanList);
    }

    @Override
    public void updateRecommendGoods(List<GoodsBean> goodsBeanList) {
        recommendLayout.setVisibility(View.VISIBLE);
        recommendList.clear();
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        recommendList.addAll(goodsBeanList);
        recommendAdapter.setData(recommendList);
    }

    @Override
    public void setUpdateCart(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            onRefresh();
            setTotalPrice();
        }else {
            Toast.makeText(this,R.string.update_fail,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setDeleteCart(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            onRefresh();
            setTotalPrice();
        }else {
            Toast.makeText(this,R.string.delete_fail,Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void showEndFooterView() {

    }

    @Override
    public void showEmptyGoodsView() {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        View view = View.inflate(this,R.layout.empty_cart,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEmptyRecommendGoodsView() {
        recommendLayout.setVisibility(View.GONE);
    }

    @Override
    public void onShopStatusChanged() {
        boolean isAllShopSelected = true;
        for (ShopBean bean : shopBeanList) {
           if(!bean.isChecked()){
                isAllShopSelected = false;
                break;
            }
        }
        rbSelectAll.setChecked(isAllShopSelected);
        shopAdapter.notifyDataSetChanged();
        setTotalPrice();
    }

    @Override
    public void onGoodsDeleted(String goodsId) {
        cartPresent.deleteCart(goodsId);
    }


    @Override
    public void onGoodsCountChanged(String goodsId,int count) {
        cartPresent.updateCart(goodsId,count);
    }

    private void setTotalPrice(){
        totalPrice = 0;
        for (GoodsBean goodsBean : getSelectedGoods()) {
            totalPrice += FormatUtil.parseDouble(goodsBean.getPrice())
                    * FormatUtil.parseInt(goodsBean.getAmount());
        }
        tvTotalPrice.setText(String.format(getString(R.string.rmb_price_double),totalPrice));
    }

    private List<GoodsBean> getSelectedGoods(){
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (ShopBean bean : shopBeanList) {
            for (GoodsBean goodsBean : bean.getItem()) {
                if(goodsBean.isChecked()){
                    goodsBeanList.add(goodsBean);
                }
            }
        }
        return goodsBeanList;
    }

    private ArrayList<ShopBean> getSelectedShops(){
        ArrayList<ShopBean> selectedShopBeanList = new ArrayList<>();
        for (ShopBean shopBean : shopBeanList) {
            if(shopBean.isChecked()){
                selectedShopBeanList.add(shopBean);
            }else{
                ShopBean tempShopBean = new ShopBean();
                List<GoodsBean> goodsBeanList = new ArrayList<>();
                for (GoodsBean goodsBean : shopBean.getItem()) {
                    if(goodsBean.isChecked()){
                        goodsBeanList.add(goodsBean);
                    }
                }
                if(!goodsBeanList.isEmpty()){
                    tempShopBean.setItem(goodsBeanList);
                    tempShopBean.setName(shopBean.getName());
                    selectedShopBeanList.add(tempShopBean);
                }
            }
        }
        return selectedShopBeanList;
    }
}
