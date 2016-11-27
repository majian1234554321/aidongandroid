package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.activity.home.GoodsDetailActivity;
import com.leyuan.aidong.ui.activity.home.GoodsFilterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品筛选界面适配器
 * Created by song on 2016/8/17.
 */
public class GoodsFilterAdapter extends RecyclerView.Adapter<GoodsFilterAdapter.FilterViewHolder>{
    private Context context;
    private int type;
    private List<NurtureBean> nurtureList = new ArrayList<>();
    private List<EquipmentBean> equipmentList = new ArrayList<>();

    public GoodsFilterAdapter(Context context,int type) {
        this.context = context;
        this.type = type;
    }

    public void setNurtureList(List<NurtureBean> nurtureList) {
        if(nurtureList != null){
            this.nurtureList = nurtureList;
        }
    }

    public void setEquipmentList(List<EquipmentBean> equipmentList) {
        if(equipmentList != null)
        this.equipmentList = equipmentList;
    }

    @Override
    public int getItemCount() {
        if(type == GoodsFilterActivity.TYPE_EQUIPMENT){
            return equipmentList.size();
        }else {
            return nurtureList.size();
        }

    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nurture_filter,parent,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        if(type == GoodsFilterActivity.TYPE_NURTURE){
            final NurtureBean bean = nurtureList.get(position);
            holder.cover.setImageURI(bean.getCover());
            holder.name.setText(bean.getName());
            holder.brand.setText(bean.getMarket_price());
            holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsDetailActivity.start(context,bean.getId(), GoodsDetailActivity.TYPE_NURTURE);
                }
            });
        }else {
            final EquipmentBean bean = equipmentList.get(position);
            holder.cover.setImageURI(bean.getCover());
            holder.name.setText(bean.getName());
            holder.brand.setText(bean.getMarket_price());
            holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsDetailActivity.start(context, bean.getId(),GoodsDetailActivity.TYPE_EQUIPMENT);
                }
            });
        }

    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;
        TextView brand;
        TextView price;

        public FilterViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            brand = (TextView)itemView.findViewById(R.id.tv_nurture_brand);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}
