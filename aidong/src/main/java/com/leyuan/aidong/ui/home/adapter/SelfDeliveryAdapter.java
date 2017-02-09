package com.leyuan.aidong.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自提门店适配器
 * Created by song on 2016/9/22.
 */
public class SelfDeliveryAdapter extends RecyclerView.Adapter<SelfDeliveryAdapter.VenuesHolder> {
    private Context context;
    private List<VenuesBean> data = new ArrayList<>();

    public SelfDeliveryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VenuesBean> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_self_delivery, null);
        return new VenuesHolder(view);
    }

    @Override
    public void onBindViewHolder(final VenuesHolder holder, final int position) {
        final VenuesBean bean = data.get(position);
        holder.checkBox.setChecked(bean.isChecked());
        holder.tvShopName.setText(bean.getName());
        holder.tvAddress.setText(bean.getAddress());
        holder.tvDistance.setText(String.format(context.getResources().getString(R.string.distance_km),
                String.valueOf(FormatUtil.parseDouble(bean.getDistance()))));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.isChecked()){
                    bean.setChecked(false);
                }else {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setChecked(i==position);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    class VenuesHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvAddress;
        TextView tvShopName;
        TextView tvDistance;

        public VenuesHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
        }
    }
}
