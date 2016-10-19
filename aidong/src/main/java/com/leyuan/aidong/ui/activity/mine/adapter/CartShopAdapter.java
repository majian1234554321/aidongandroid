package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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
public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.CartHolder>{
    private Context context;
    private List<ShopBean> data = new ArrayList<>();

    public CartShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopBean> data) {
        this.data = data;
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
        final CartGoodsAdapter goodsAdapter = new CartGoodsAdapter(context);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());

        holder.rbCheck.setChecked(bean.isChecked());
        holder.rbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (GoodsBean goodsBean : bean.getItem()) {
                    goodsBean.setChecked(!bean.isChecked());
                    holder.rbCheck.setChecked(!bean.isChecked());
                }
                goodsAdapter.notifyDataSetChanged();
            }
        });
    }

    class CartHolder extends RecyclerView.ViewHolder {
        RadioButton rbCheck;
        TextView tvShopName;
        TextView tvTime;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        public CartHolder(View itemView) {
            super(itemView);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rb_check);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }
}
