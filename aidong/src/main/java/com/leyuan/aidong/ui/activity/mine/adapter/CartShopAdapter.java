package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.activity.mine.CartActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中商家适配器
 * Created by song on 2016/9/8.
 */
public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.CartHolder>{
    private Context context;
    private List<ShopBean> data = new ArrayList<>();

    public CartShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopBean> data) {
        if(data != null){
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_cart_shop,null);
        return new CartHolder(view);
    }


    @Override
    public void onBindViewHolder(final CartHolder holder, int position) {
        final ShopBean bean = data.get(position);
        holder.tvShopName.setText(bean.getShopname());
        holder.tvTime.setText(bean.getOpentime());
        holder.rvShop.setLayoutManager(new LinearLayoutManager(context));
        final CartGoodsAdapter goodsAdapter = new CartGoodsAdapter(context,holder.rbCheck);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());

        holder.rbCheck.setChecked(bean.isChecked());

        holder.rbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.setChecked(isChecked);

                /**************商店与商店中的商品联动*******************/
                //通知该商店下的商品都处于选中或者未选中的状态
                for (GoodsBean goodsBean : bean.getItem()) {
                    goodsBean.setChecked(isChecked);
                }
                goodsAdapter.notifyDataSetChanged();

                /**************商店与购物车联动************************/
                //如果该购物车处于全选状态，并且此时操作是取消商店全选，则取消购物车全选
                if(((CartActivity)context).rbSelectAll.isChecked() && !isChecked){
                    ((CartActivity)context).rbSelectAll.setChecked(false);
                }
                //如果该购物车处于未选中状态，此时操作又是商店全选，并且购物车中商店已全部选中，则购物车全选
                if(!((CartActivity)context).rbSelectAll.isChecked() && isChecked){
                    boolean isAllShopChecked = false;
                    for (ShopBean shopBean : data) {
                        isAllShopChecked = shopBean.isChecked();
                        if(!isAllShopChecked){
                            break;
                        }
                    }
                    if(isAllShopChecked){
                        ((CartActivity)context).rbSelectAll.setChecked(true);
                    }
                }
            }
        });
    }

   /* public double getShopPrice(){
        double shopPrice = 0d;
        for (GoodsBean goodsBean : bean.getItem()) {
            if(goodsBean.isChecked()){
                shopPrice += Double.parseDouble(goodsBean.getPrice())
                        * Integer.parseInt(goodsBean.getAmount());
            }
        }
        return shopPrice;
    }*/

    class CartHolder extends RecyclerView.ViewHolder {
        CheckBox rbCheck;
        TextView tvShopName;
        TextView tvTime;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        public CartHolder(View itemView) {
            super(itemView);
            rbCheck = (CheckBox) itemView.findViewById(R.id.rb_check);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }
}
