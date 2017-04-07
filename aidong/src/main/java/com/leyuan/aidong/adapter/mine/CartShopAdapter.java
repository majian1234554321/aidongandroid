package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShopBean;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;

/**
 * 购物车中商家适配器
 * Created by song on 2016/9/8.
 */
//todo 优化：利用观察者模式改写回调和判断逻辑
public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.CartHolder> {
    private Context context;
    private List<ShopBean> data = new ArrayList<>();
    private ShopChangeListener shopChangeListener;

    public CartShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopBean> data) {
        if (data != null) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_shop, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final ShopBean bean = data.get(position);
        holder.tvShopName.setText(bean.getPickUp().getInfo().getName());
        String type = DELIVERY_EXPRESS.equals(bean.getPickUp().getType()) ? "快递" : "自提";
        holder.tvDeliveryType.setText(type);
        holder.rvShop.setLayoutManager(new LinearLayoutManager(context));
        final CartGoodsAdapter goodsAdapter = new CartGoodsAdapter(context);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());
        if (bean.allItemIsSoldOut()) {
            holder.check.setVisibility(View.GONE);
        } else {
            holder.check.setVisibility(View.VISIBLE);
            holder.check.setChecked(bean.isChecked());
        }
        goodsAdapter.setGoodsChangeListener(new CartGoodsAdapter.GoodsChangeListener() {
            @Override
            public void onGoodsDeleted(String goodsId, int goodsPosition) {
                if (shopChangeListener != null) {
                    shopChangeListener.onGoodsDeleted(goodsId, position, goodsPosition);
                }
            }

            @Override
            public void onGoodsCountChanged(String goodsId, int count, int goodsPosition) {
                if (shopChangeListener != null) {
                    shopChangeListener.onGoodsCountChanged(goodsId, count, position, goodsPosition);
                }
            }

            @Override
            public void onGoodsStatusChanged() {          //商品选中状态变化时通知更改商店和购物车的状态
                boolean isAllGoodsSelected = true;
                for (GoodsBean goodsBean : bean.getItem()) {
                    if (!goodsBean.isChecked()) {
                        isAllGoodsSelected = false;
                        break;
                    }
                }
                bean.setChecked(isAllGoodsSelected);
                if (shopChangeListener != null) {
                    shopChangeListener.onShopStatusChanged(position);
                }
            }
        });

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {            //商店点击时改变商店和商店中商品的状态，并通知购物车相应改变
                bean.setChecked(!bean.isChecked());
                for (GoodsBean goodsBean : bean.getItem()) {
                    goodsBean.setChecked(bean.isChecked());
                }
                if (shopChangeListener != null) {
                    shopChangeListener.onShopStatusChanged(position);
                }
            }
        });
    }

    class CartHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        TextView tvShopName;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        public CartHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.rb_check);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }

    public void setShopChangeListener(ShopChangeListener listener) {
        this.shopChangeListener = listener;
    }

    public interface ShopChangeListener {
        void onShopStatusChanged(int position);

        void onGoodsDeleted(String goodsId, int shopPosition, int goodsPosition);

        void onGoodsCountChanged(String goodsId, int count, int shopPosition, int goodsPosition);

    }
}
