package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单中商品适配器
 * Created by song on 2016/9/1.
 */
public class OrderGoodAdapter extends RecyclerView.Adapter<OrderGoodAdapter.GoodHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();

    public OrderGoodAdapter(Context context, List<GoodsBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public GoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_order_goods,null);
        return new GoodHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodHolder holder, int position) {
        GoodsBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.price.setText(bean.getPrice());
        holder.count.setText(bean.getAmount());
    }

    class GoodHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView name;
        TextView price;
        TextView desc;
        TextView count;

        public GoodHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView)itemView.findViewById(R.id.tv_good_name);
            price = (TextView)itemView.findViewById(R.id.tv_goods_price);
            desc = (TextView)itemView.findViewById(R.id.tv_good_desc);
            count = (TextView)itemView.findViewById(R.id.tv_good_count);
        }
    }
}
