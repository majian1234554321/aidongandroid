package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;

/**
 * 配送信息适配器
 * Created by song on 2016/11/30.
 */
public class DeliveryInfoAdapter extends RecyclerView.Adapter<DeliveryInfoAdapter.DeliveryInfoHolder> {
    private Context context;

    public DeliveryInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public DeliveryInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivery_info, parent, false);
        return new DeliveryInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryInfoHolder holder, int position) {

    }

    class DeliveryInfoHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView dvGoodsCover;
        TextView tvGoodsPrice;
        TextView tvGoodDesc;
        TextView tvGoodCount;
        TextView tvGoodName;
        TextView tvExpress;
        TextView tvSelfDelivery;
        LinearLayout llSelfDelivery;
        LinearLayout llDeliveryAddress;
        TextView tvShop;
        TextView tvShopAddress;
        TextView tvDeliveryTime;

        public DeliveryInfoHolder(View itemView) {
            super(itemView);

            dvGoodsCover = (SimpleDraweeView) itemView.findViewById(R.id.dv_goods_cover);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tvGoodDesc = (TextView) itemView.findViewById(R.id.tv_good_desc);
            tvGoodCount = (TextView) itemView.findViewById(R.id.tv_good_count);
            tvGoodName = (TextView) itemView.findViewById(R.id.tv_good_name);
            tvExpress = (TextView) itemView.findViewById(R.id.tv_express);
            tvSelfDelivery = (TextView) itemView.findViewById(R.id.tv_self_delivery);
            llSelfDelivery = (LinearLayout) itemView.findViewById(R.id.ll_self_delivery);
            llDeliveryAddress = (LinearLayout) itemView.findViewById(R.id.ll_delivery_address);
            tvShop = (TextView) itemView.findViewById(R.id.tv_shop);
            tvShopAddress = (TextView) itemView.findViewById(R.id.tv_shop_address);
            tvDeliveryTime = (TextView) itemView.findViewById(R.id.tv_delivery_time);
        }
    }
}
