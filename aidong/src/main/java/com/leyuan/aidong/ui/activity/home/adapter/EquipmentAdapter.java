package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.ui.activity.home.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.ui.activity.home.GoodsDetailActivity.TYPE_EQUIPMENT;


/**
 * 装备的RecyclerView适配器
 * Created by song on 2016/7/16.
 */
public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>{
    private Context context;
    private List<EquipmentBean> data = new ArrayList<>();

    public EquipmentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<EquipmentBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_nurture_or_equipment,null);
        return new EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EquipmentViewHolder holder, int position) {
        final EquipmentBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.start(context,bean.getId(), TYPE_EQUIPMENT);
            }
        });
    }


    static class EquipmentViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView name;
        TextView price;

        public EquipmentViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}
