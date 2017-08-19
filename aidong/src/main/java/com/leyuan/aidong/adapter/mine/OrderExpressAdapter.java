package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ConfirmOrderGoodsAdapter;
import com.leyuan.aidong.entity.ParcelBean;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;

/**
 * 订单列表中快递包裹适配器
 * Created by song on 2016/9/8.
 */
public class OrderExpressAdapter extends RecyclerView.Adapter<OrderExpressAdapter.CartHolder> {
    private Context context;
    private List<ParcelBean> data = new ArrayList<>();

    public OrderExpressAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ParcelBean> data) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_express,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final ParcelBean bean = data.get(position);
        if(DELIVERY_EXPRESS.equals(bean.getPickUpWay())){
            holder.tvShopName.setText("仓库发货");
        }else {
            holder.tvShopName.setText(bean.getAddress());
        }
        holder.tvDeliveryType.setText("快递");
        holder.rvShop.setLayoutManager(new LinearLayoutManager(context));
        final ConfirmOrderGoodsAdapter goodsAdapter = new ConfirmOrderGoodsAdapter(context);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());
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
}
