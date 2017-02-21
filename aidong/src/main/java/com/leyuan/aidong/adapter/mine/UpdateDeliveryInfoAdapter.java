package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改配送信息适配器
 * Created by song on 2017/2/8.
 */
public class UpdateDeliveryInfoAdapter extends RecyclerView.Adapter<UpdateDeliveryInfoAdapter.DeliveryInfoHolder> {
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();

    public UpdateDeliveryInfoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DeliveryInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_update_delivery_info, parent, false);
        return new DeliveryInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeliveryInfoHolder holder, int position) {
        GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.dvCover);
        holder.tvGoodsName.setText(bean.getName());
        holder.tvCount.setText(String.format(context.getString(R.string.x_count),bean.getAmount()));
        ArrayList<String> specValue = bean.getSpec_value();
        StringBuilder skuStr = new StringBuilder();
        for (String result : specValue) {
            skuStr.append(result);
        }
        holder.tvSku.setText(skuStr);
        holder.tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));

        holder.tvExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvExpress.setTextColor(Color.parseColor("#ffffff"));
                holder.tvSelfDelivery.setTextColor(Color.parseColor("#000000"));
                holder.tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_black);
                holder.tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_white);
                holder.llSelfDelivery.setVisibility(View.GONE);
            }
        });

        holder.tvSelfDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvExpress.setTextColor(Color.parseColor("#000000"));
                holder.tvSelfDelivery.setTextColor(Color.parseColor("#ffffff"));
                holder.tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_white);
                holder.tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_black);
                holder.llSelfDelivery.setVisibility(View.VISIBLE);
            }
        });

    }

    class DeliveryInfoHolder extends RecyclerView.ViewHolder {
        ImageView dvCover;
        TextView tvGoodsName;
        TextView tvGoodsPrice;
        TextView tvSku;
        TextView tvRecommendCode;
        TextView tvCount;
        TextView tvExpress;
        TextView tvSelfDelivery;
        LinearLayout llSelfDelivery;
        LinearLayout llDeliveryAddress;
        TextView tvShop;
        TextView tvShopAddress;
        TextView tvDeliveryTime;

        public DeliveryInfoHolder(View itemView) {
            super(itemView);
            dvCover = (ImageView) itemView.findViewById(R.id.dv_cover);
            tvGoodsName = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tvGoodsPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tvSku = (TextView) itemView.findViewById(R.id.tv_sku);
            tvRecommendCode = (TextView) itemView.findViewById(R.id.tv_recommend_code);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
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
