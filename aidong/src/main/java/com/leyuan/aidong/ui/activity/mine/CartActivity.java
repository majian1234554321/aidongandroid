package com.leyuan.aidong.ui.activity.mine;

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

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.ConfirmOrderActivity;
import com.leyuan.aidong.ui.activity.home.adapter.RecommendAdapter;
import com.leyuan.aidong.ui.activity.mine.adapter.CartShopAdapter;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Created by song on 2016/9/8.
 */
public class CartActivity extends BaseActivity implements CartActivityView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private ImageView ivBack;
    private TextView tvEdit;

    //商品
    private View headerView;
    private RecyclerView shopView;
    private CartShopAdapter shopAdapter;
    private List<ShopBean> data = new ArrayList<>();

    //推荐
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recommendView;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    public CheckBox rbSelectAll;
    private LinearLayout bottomNormalLayout;
    private TextView tvTotalPrice;
    private TextView tvSettlement;
    private LinearLayout bottomDeleteLayout;
    private TextView tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        setListener();
        initData();
        shopAdapter.setData(data);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        headerView = View.inflate(this,R.layout.header_cart,null);
        shopView = (RecyclerView) headerView.findViewById(R.id.rv_cart_header);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        recommendView = (RecyclerView)findViewById(R.id.rv_goods);
        rbSelectAll = (CheckBox) findViewById(R.id.rb_select_all);
        bottomNormalLayout = (LinearLayout) findViewById(R.id.ll_bottom_normal);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvSettlement = (TextView) findViewById(R.id.tv_settlement);
        bottomDeleteLayout = (LinearLayout) findViewById(R.id.ll_bottom_delete);
        tvDelete = (TextView) findViewById(R.id.tv_delete);

        setColorSchemeResources(refreshLayout);
        shopView.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new CartShopAdapter(this);
        shopView.setAdapter(shopAdapter);
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recommendView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recommendView.getAdapter(), manager.getSpanCount()));
        recommendView.setLayoutManager(manager);
        RecyclerViewUtils.setHeaderView(recommendView,headerView);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        rbSelectAll.setOnClickListener(this);
    }

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                break;
            case R.id.tv_settlement:
                ConfirmOrderActivity.start(this);
                break;
            case R.id.tv_delete:
                break;
            case R.id.rb_select_all:
                /***购物车与购物车中的商店联动**/
                for (ShopBean bean : data) {
                    bean.setChecked(rbSelectAll.isChecked());
                }
                shopAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

   /* private double getCartPrice(){
        double cartPrice = 0d;
        for (ShopBean bean : data) {
            if(bean.isChecked()){
                cartPrice +=
            }
        }
        return cartPrice;
    }*/

    //下拉刷新
    @Override
    public void onRefresh() {

    }

    private  void initData(){
        for (int i = 0; i < 5; i++) {
            ShopBean bean = new ShopBean();
            bean.setShopname("第" + (i + 1) +"个商店" );

            List<GoodsBean> goods = new ArrayList<>();
            for (int i1 = 0; i1 < 2; i1++) {
                GoodsBean good = new GoodsBean();
                good.setName("第" + (i + 1) +"个商店中的第" + (i1 + 1)+"件商品");
                good.setAmount((i+1) + "");
                if(i1 % 2 == 0){
                    good.setPrice("10");
                    good.setCover("http://ww1.sinaimg.cn/mw690/6592c2e0jw1f9dps0ijpjj20qo0ynwht.jpg");
                }else {
                    good.setPrice("50");
                    good.setCover("http://ww4.sinaimg.cn/mw690/718878b5jw1f9dlw9chi5j20f00ciq41.jpg");
                }
                goods.add(good);
            }
            bean.setItem(goods);
            this.data.add(bean);
        }
    }
}
