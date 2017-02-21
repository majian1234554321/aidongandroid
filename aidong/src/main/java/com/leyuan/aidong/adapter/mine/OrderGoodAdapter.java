package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.utils.GlideLoader;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_goods,parent,false);
        return new GoodHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
        holder.count.setText(String.format(context.getString(R.string.x_count),bean.getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // GoodsDetailActivity.start(context,bean.getSku_code());
            }
        });
    }

    class GoodHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        TextView price;
        TextView desc;
        TextView count;

        public GoodHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView)itemView.findViewById(R.id.tv_good_name);
            price = (TextView)itemView.findViewById(R.id.tv_goods_price);
            desc = (TextView)itemView.findViewById(R.id.tv_good_desc);
            count = (TextView)itemView.findViewById(R.id.tv_good_count);
        }
    }
}
