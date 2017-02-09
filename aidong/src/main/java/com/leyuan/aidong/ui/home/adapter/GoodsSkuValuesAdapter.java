package com.leyuan.aidong.ui.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsSkuValueBean;


import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格值适配器
 * Created by song on 2016/9/23.
 */
public class GoodsSkuValuesAdapter extends RecyclerView.Adapter<GoodsSkuValuesAdapter.ValueHolder>{
    private Context context;
    private List<GoodsSkuValueBean> data = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public GoodsSkuValuesAdapter(Context context, List<GoodsSkuValueBean> data) {
        this.context = context;
        if(data != null){
            this.data = data;
        }
    }

    public void setItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ValueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_taste,parent,false);
        return new ValueHolder(view);
    }

    @Override
    public void onBindViewHolder(final ValueHolder holder, final int position) {
        final GoodsSkuValueBean bean = data.get(position);
        holder.value.setText(bean.getValue());
        if(bean.isSelected()){
            holder.value.setTextColor(Color.parseColor("#ffffffff"));
            holder.value.setBackgroundResource(R.drawable.shape_solid_corner_black);
        }else if(!bean.isAvailable()){
            holder.value.setTextColor(Color.parseColor("#55999999"));
            holder.value.setBackgroundResource(R.drawable.shape_solid_corner_white);
        }else {
            holder.value.setTextColor(Color.parseColor("#ff000000"));
            holder.value.setBackgroundResource(R.drawable.shape_solid_corner_white);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bean.isAvailable()){       //不可用
                    return;
                }
                if(bean.isSelected()){          //已选中 --> 取消选中
                    if(itemClickListener != null){
                        itemClickListener.onCancelSelectItem(position);
                    }
                }else{                          //未选中 --> 选中
                    if(itemClickListener != null){
                        itemClickListener.onSelectItem(position);
                    }
                }

                if(itemClickListener != null){
                    itemClickListener.onItemClick();
                }
            }
        });
    }

    class ValueHolder extends RecyclerView.ViewHolder{
        TextView value;
        public ValueHolder(View itemView) {
            super(itemView);
            value = (TextView)itemView.findViewById(R.id.tv_taste);
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
        void onSelectItem(int position);
        void onCancelSelectItem(int position);
    }
}
