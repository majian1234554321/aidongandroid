package com.leyuan.aidong.ui.mine.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.CartShopAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.home.activity.ConfirmOrderActivity;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ICartHeaderView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.ORDER_FROM_CART;

/**
 * the header of cart view
 * Created by song on 2017/2/22.
 */
public class CartHeaderView extends RelativeLayout implements ICartHeaderView,CartShopAdapter.ShopChangeListener{
    private Context context;
    private LinearLayout root;
    private SwitcherLayout switcherLayout;
    private TextView tvRecommend;
    private RecyclerView shopView;
    private CartShopAdapter shopAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();

    private CartPresent cartPresent;
    private CartHeaderCallback callback;
    private int goodsCount;

    public CartHeaderView(Context context) {
        this(context,null,0);
    }

    public CartHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CartHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        cartPresent = new CartPresentImpl(context, this);
        initView();
        cartPresent.commonLoadData(switcherLayout);
    }

    private void initView(){
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_cart_view,this,true);
        root = (LinearLayout) headerView.findViewById(R.id.root);
        shopView = (RecyclerView)headerView.findViewById(R.id.rv_cart);
        tvRecommend = (TextView) headerView.findViewById(R.id.tv_recommend);
        switcherLayout = new SwitcherLayout(context,shopView);
        shopView.setLayoutManager(new LinearLayoutManager(context));
        shopAdapter = new CartShopAdapter(context);
        shopView.setAdapter(shopAdapter);
        shopAdapter.setShopChangeListener(this);
    }

    public void pullToRefreshCartData(){
        cartPresent.pullToRefreshData();
        if(callback != null ){
            callback.onTotalPriceChanged(0f);
            callback.onAllCheckedChanged(false);
        }
    }

    @Override
    public void updateRecyclerView(List<ShopBean> list) {
        shopBeanList.clear();
        shopBeanList.addAll(list);
        shopAdapter.setData(shopBeanList);
        if(callback != null ){
            callback.onCartDataLoadFinish();
        }
    }

    @Override
    public void showEmptyGoodsView() {
        if(callback != null ){
            callback.onCartDataLoadFinish();
            callback.onAllGoodsDeleted();
        }
        shopBeanList.clear();
        shopAdapter.setData(shopBeanList);
        View view = View.inflate(context,R.layout.empty_cart,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void onShopStatusChanged(int position) {
        boolean isAllShopChecked = isAllShopChecked();
        double totalPrice = calculateTotalPrice();
        if(callback != null){
            callback.onAllCheckedChanged(isAllShopChecked);
            callback.onTotalPriceChanged(totalPrice);
        }
        shopAdapter.notifyItemChanged(position);
    }

    @Override
    public void onGoodsDeleted(String goodsId,int shopPosition,int goodsPosition) {
        cartPresent.deleteSingleGoods(goodsId,shopPosition,goodsPosition);
    }

    @Override
    public void onGoodsCountChanged(String goodsId,int count,int shopPosition,int goodsPosition) {
        this.goodsCount = count;
        cartPresent.updateGoodsCount(goodsId,count,shopPosition,goodsPosition);
    }

    @Override
    public void updateGoodsCountResult(BaseBean baseBean,int shopPosition,int goodsPosition) {
        if(baseBean.getStatus() == 1){
            // change date local
            ShopBean shop = shopBeanList.get(shopPosition);
            GoodsBean goods = shop.getItem().get(goodsPosition);
            goods.setAmount(String.valueOf(goodsCount));
            shopAdapter.notifyItemChanged(shopPosition);
            if(callback != null){
                callback.onTotalPriceChanged(calculateTotalPrice());
            }
        }else {
            Toast.makeText(context,R.string.update_fail,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteSingleGoodsResult(BaseBean baseBean,String id,int shopPosition,int goodsPosition) {
        if(baseBean.getStatus() == 1){
            //change date local
            ShopBean shop = shopBeanList.get(shopPosition); // first,remove this goods
            shop.getItem().remove(goodsPosition);
            if(shop.getItem().isEmpty()){   //if the shop is empty ,remove it
                shopBeanList.remove(shop);
                if(shopBeanList.isEmpty()){ //if the cart is empty ,show empty cart
                    showEmptyGoodsView();
                    return;
                }
                shopAdapter.notifyItemRemoved(shopPosition);
            }else {                        // if the shop's goods is all checked , check the shop too.
                boolean isAllCheck = true;
                for (GoodsBean bean : shop.getItem()) {
                    if(!bean.isChecked()){
                        isAllCheck = false;
                        break;
                    }
                }
                shop.setChecked(isAllCheck);
                shopAdapter.notifyItemChanged(shopPosition);
            }
            if(callback != null){
                callback.onTotalPriceChanged(calculateTotalPrice());
                callback.onAllCheckedChanged(isAllShopChecked());
            }
        }else {
            Toast.makeText(context,R.string.delete_fail,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteMultiGoodsResult(BaseBean baseBean) {
        pullToRefreshCartData();
    }

    private boolean isAllShopChecked(){
        if(shopBeanList == null || shopBeanList.isEmpty()){
            return false;
        }

        boolean isAllShopChecked = true;
        for (ShopBean bean : shopBeanList) {
            if(!bean.isChecked()){
                isAllShopChecked = false;
                break;
            }
        }
        return isAllShopChecked;
    }

    private double calculateTotalPrice(){
        double totalPrice = 0;
        for (GoodsBean goodsBean : getSelectedGoods()) {
            totalPrice += FormatUtil.parseDouble(goodsBean.getPrice())
                    * FormatUtil.parseInt(goodsBean.getAmount());
        }
        return totalPrice;
    }

    private List<GoodsBean> getSelectedGoods() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (ShopBean bean : shopBeanList) {
            for (GoodsBean goodsBean : bean.getItem()) {
                if (goodsBean.isChecked()) {
                    goodsBeanList.add(goodsBean);
                }
            }
        }
        return goodsBeanList;
    }

    public void deleteSelectedGoods(){
        List<GoodsBean> selectedGoods = getSelectedGoods();
        if(selectedGoods.isEmpty()){
            Toast.makeText(context,R.string.tip_select_goods,Toast.LENGTH_LONG).show();
            return;
        }
        StringBuilder ids = new StringBuilder();
        for (GoodsBean selectedGood : selectedGoods) {
            ids.append(selectedGood.getId()).append(",");
        }
        cartPresent.deleteMultiGoods(ids.toString());
    }

    public void settlementSelectGoods(){
        ArrayList<ShopBean> selectedShops = getSelectedShops();
        if(selectedShops.isEmpty()){
            Toast.makeText(context,R.string.tip_select_goods,Toast.LENGTH_LONG).show();
            return;
        }
        ConfirmOrderActivity.start(context, ORDER_FROM_CART,selectedShops,calculateTotalPrice());
    }

    public void changeAllGoodsStatus(boolean checked){
        for (ShopBean bean : shopBeanList) {
            bean.setChecked(checked);
            for (GoodsBean goodsBean : bean.getItem()) {
                goodsBean.setChecked(checked);
            }
        }
        if(callback != null){
            callback.onTotalPriceChanged(calculateTotalPrice());
        }
        shopAdapter.notifyDataSetChanged();
    }

    public void showRecommendText(boolean visibility){
        tvRecommend.setVisibility(visibility ? VISIBLE : GONE);
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

    public void setCartCallback(CartHeaderCallback callback) {
        this.callback = callback;
    }

    public interface CartHeaderCallback{
        void onCartDataLoadFinish();
        void onAllCheckedChanged(boolean allChecked);
        void onTotalPriceChanged(double totalPrice);
        void onAllGoodsDeleted();
    }
}