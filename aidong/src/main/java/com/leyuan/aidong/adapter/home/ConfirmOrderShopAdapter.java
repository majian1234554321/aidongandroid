package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ShopBean;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;

/**
 * 确认订单中商家适配器
 * Created by song on 2016/9/8.
 */
public class ConfirmOrderShopAdapter extends RecyclerView.Adapter<ConfirmOrderShopAdapter.CartHolder> {
    private Context context;
    private List<ShopBean> data = new ArrayList<>();
    private DeliveryTypeListener listener;


    public ConfirmOrderShopAdapter(Context context) {
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm_order_shop,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final ShopBean bean = data.get(position);
        holder.tvShopName.setText(bean.getPickUp().getInfo().getName());
        String type = DELIVERY_EXPRESS.equals(bean.getPickUp().getType()) ? "快递" : "自提";
        holder.tvDeliveryType.setText(type);
        holder.rvShop.setLayoutManager(new LinearLayoutManager(context));
        final ConfirmOrderGoodsAdapter goodsAdapter = new ConfirmOrderGoodsAdapter(context);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());

        holder.tvDeliveryType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onDeliveryTypeClick(position);
                }
            }
        });
    }

    class CartHolder extends RecyclerView.ViewHolder {
        TextView tvShopName;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        public CartHolder(View itemView) {
            super(itemView);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }

    public void setListener(DeliveryTypeListener listener) {
        this.listener = listener;
    }

    public interface DeliveryTypeListener{
        void onDeliveryTypeClick(int position);
    }
}
