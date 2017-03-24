package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UpdateDeliveryBean;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.constant.DeliveryType;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改配送信息适配器
 * Created by song on 2017/2/8.
 */
public class UpdateDeliveryInfoAdapter extends RecyclerView.Adapter<UpdateDeliveryInfoAdapter.DeliveryInfoHolder> {
    private Context context;
    private List<UpdateDeliveryBean> data = new ArrayList<>();
    private SelfDeliveryShopListener listener;

    public UpdateDeliveryInfoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UpdateDeliveryBean> data) {
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
    public void onBindViewHolder(final DeliveryInfoHolder holder, final int position) {
        final UpdateDeliveryBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getGoods().getCover(), holder.dvCover);
        holder.tvGoodsName.setText(bean.getGoods().getName());
        holder.tvCount.setText(String.format(context.getString(R.string.x_count),bean.getGoods().getAmount()));
        ArrayList<String> specValue = bean.getGoods().getSpecValue();
        StringBuilder skuStr = new StringBuilder();
        for (String result : specValue) {
            skuStr.append(result);
        }
        holder.tvSku.setText(skuStr);
        holder.tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getGoods().getPrice())));
        if(!TextUtils.isEmpty(bean.getGoods().getRecommendCode())){
            holder.tvRecommendCode.setText(String.format(context.getString(R.string.recommend_code),
                    bean.getGoods().getRecommendCode()));
        }

        if(DeliveryType.EXPRESS.equals(bean.getDeliveryInfo().getType())){
            selectExpress(holder);
            holder.tvShop.setText("请选择");
            holder.tvShopAddress.setVisibility(View.GONE);
        }else {
            selectSelfDelivery(holder);
            holder.tvShop.setText(bean.getDeliveryInfo().getInfo().getName());
            holder.tvShopAddress.setText(bean.getDeliveryInfo().getInfo().getAddress());
            holder.tvShopAddress.setVisibility(View.VISIBLE);
        }

        holder.tvSelfDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSelfDelivery(holder);
            }
        });

        holder.llSelfDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onSelfDeliveryClick(position);
                }
            }
        });

        holder.tvExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectExpress(holder);
                if(listener != null){
                    listener.onExpressClick(position);
                }
            }
        });
    }

    private void selectExpress(DeliveryInfoHolder holder){
        holder.tvExpress.setTextColor(Color.parseColor("#ffffff"));
        holder.tvSelfDelivery.setTextColor(Color.parseColor("#000000"));
        holder.tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_black);
        holder.tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_white);
        holder.llSelfDelivery.setVisibility(View.GONE);
    }

    private void selectSelfDelivery(DeliveryInfoHolder holder){
        holder.tvExpress.setTextColor(Color.parseColor("#000000"));
        holder.tvSelfDelivery.setTextColor(Color.parseColor("#ffffff"));
        holder.tvExpress.setBackgroundResource(R.drawable.shape_solid_corner_white);
        holder.tvSelfDelivery.setBackgroundResource(R.drawable.shape_solid_corner_black);
        holder.llSelfDelivery.setVisibility(View.VISIBLE);

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

    public void setListener(SelfDeliveryShopListener listener) {
        this.listener = listener;
    }

    public interface SelfDeliveryShopListener{
        void onSelfDeliveryClick(int position);
        void onExpressClick(int position);
    }
}
