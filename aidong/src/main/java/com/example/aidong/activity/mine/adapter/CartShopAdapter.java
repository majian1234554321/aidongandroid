package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中商家适配器
 * Created by song on 2016/9/8.
 */
public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.CartHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();

    public CartShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
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
    public void onBindViewHolder(CartHolder holder, int position) {

    }

    class CartHolder extends RecyclerView.ViewHolder {
        RadioButton rbCheck;
        TextView tvDeliveryFrom;
        TextView tvTime;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        public CartHolder(View itemView) {
            super(itemView);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rb_check);
            tvDeliveryFrom = (TextView) itemView.findViewById(R.id.tv_delivery_from);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }
}