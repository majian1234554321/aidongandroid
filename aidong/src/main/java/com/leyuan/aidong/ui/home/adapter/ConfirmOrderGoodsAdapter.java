package com.leyuan.aidong.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单中单条商品适配器
 * Created by song on 2016/9/8.
 */
public class ConfirmOrderGoodsAdapter extends RecyclerView.Adapter<ConfirmOrderGoodsAdapter.GoodsHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();


    public ConfirmOrderGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
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
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm_order_good,parent,false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsHolder holder, final int position) {
        final GoodsBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.count.setText(String.format(context.getString(R.string.x_count),bean.getAmount()));
        ArrayList<String> specValue = bean.getSpec_value();
        StringBuilder skuStr = new StringBuilder();
        for (String result : specValue) {
            skuStr.append(result);
        }
        holder.sku.setText(skuStr);
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));
    }

    class GoodsHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        SimpleDraweeView cover;
        TextView name;
        TextView price;
        TextView sku;
        TextView code;
        TextView count;

        public GoodsHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.rb_check);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            sku = (TextView) itemView.findViewById(R.id.tv_sku);
            code = (TextView) itemView.findViewById(R.id.tv_recommend_code);
            count = (TextView) itemView.findViewById(R.id.tv_count);
        }
    }
}
