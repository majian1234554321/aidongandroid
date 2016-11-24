package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中商家适配器
 * Created by song on 2016/9/8.
 */
//todo 优化：利用观察者模式改写回调和判断逻辑
public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.CartHolder> {
    private Context context;
    private boolean isEditing = false;
    private List<ShopBean> data = new ArrayList<>();
    private ShopChangeListener shopChangeListener;

    public CartShopAdapter(Context context) {
        this.context = context;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
        notifyDataSetChanged();
    }

    public void setData(List<ShopBean> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
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
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final ShopBean bean = data.get(position);
        holder.tvShopName.setText(bean.getShopname());
        holder.tvTime.setText(bean.getOpentime());
        holder.rvShop.setLayoutManager(new LinearLayoutManager(context));
        final CartGoodsAdapter goodsAdapter = new CartGoodsAdapter(context);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());
        goodsAdapter.setEditing(isEditing);
        holder.check.setChecked(isEditing ? bean.isEidtChecked() : bean.isChecked());
        goodsAdapter.setGoodsChangeListener(new CartGoodsAdapter.GoodsChangeListener() {
            @Override
            public void onGoodsDeleted(String goodsId) {
                if (shopChangeListener != null) {
                    shopChangeListener.onGoodsDeleted(goodsId);
                }
            }

            @Override
            public void onGoodsCountChanged(String goodsId,int count) {
                if (shopChangeListener != null) {
                    shopChangeListener.onGoodsCountChanged(goodsId,count);
                }
            }

            @Override
            public void onGoodsStatusChanged() {          //商品选中状态变化时通知更改商店和购物车的状态
                boolean isAllGoodsSelected = true;
                if(isEditing){
                    for (GoodsBean goodsBean : bean.getItem()) {
                        if(!goodsBean.isEditChecked()){
                            isAllGoodsSelected = false;
                            break;
                        }
                    }
                    bean.setEidtChecked(isAllGoodsSelected);
                }else{
                    for (GoodsBean goodsBean : bean.getItem()) {
                        if(!goodsBean.isChecked()){
                            isAllGoodsSelected = false;
                            break;
                        }
                    }
                    bean.setChecked(isAllGoodsSelected);
                }
                if (shopChangeListener != null) {
                    shopChangeListener.onShopStatusChanged();
                }
            }
        });

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {            //商店点击时改变商店和商店中商品的状态，并通知购物车相应改变
                if(isEditing){
                    bean.setEidtChecked(!bean.isEidtChecked());
                    for (GoodsBean goodsBean : bean.getItem()) {
                        goodsBean.setEditChecked(bean.isEidtChecked());
                    }
                }else {
                    bean.setChecked(!bean.isChecked());
                    for (GoodsBean goodsBean : bean.getItem()) {
                        goodsBean.setChecked(bean.isChecked());
                    }
                }
                if (shopChangeListener != null) {
                    shopChangeListener.onShopStatusChanged();
                }
            }
        });
    }

    class CartHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        TextView tvShopName;
        TextView tvTime;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        public CartHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.rb_check);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }

    public void setShopChangeListener(ShopChangeListener listener) {
        this.shopChangeListener = listener;
    }

    public interface ShopChangeListener {
        void onShopStatusChanged();
        void onGoodsDeleted(String goodsId);
        void onGoodsCountChanged(String goodsId,int count);
    }
}
