package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.DeliveryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 自提门店适配器
 * Created by song on 2016/9/22.
 */
public class SelfDeliveryAdapter extends RecyclerView.Adapter<SelfDeliveryAdapter.ShopHolder> {
    private Context context;
    private List<DeliveryBean> data = new ArrayList<>();

    public SelfDeliveryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DeliveryBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_self_delivery, null);
        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopHolder holder, int position) {
        DeliveryBean bean = data.get(position);
        holder.tvShopName.setText(bean.getName());
        holder.tvAddress.setText(bean.getAddress());
        holder.tvDistance.setText(bean.getDistance());
    }

    class ShopHolder extends RecyclerView.ViewHolder {
        RadioButton rbCheck;
        TextView tvShopName;
        TextView tvAddress;
        TextView tvDistance;

        public ShopHolder(View itemView) {
            super(itemView);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rb_check);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);

        }
    }
}
