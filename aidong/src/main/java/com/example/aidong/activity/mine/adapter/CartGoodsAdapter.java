package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中单条商品适配器
 * Created by song on 2016/9/8.
 */
public class CartGoodsAdapter extends RecyclerView.Adapter<CartGoodsAdapter.GoodsHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();

    public CartGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_cart_good,null);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        holder.check.setChecked(bean.isChecked);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.desc.setText(bean.getName());
        holder.price.setText(bean.getPrice());
        holder.count.setText(bean.getAmount());

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.check.setChecked(!bean.isChecked);
                bean.isChecked = !bean.isChecked;
            }
        });
    }

    class GoodsHolder extends RecyclerView.ViewHolder {
        RadioButton check;
        SimpleDraweeView cover;
        TextView name;
        TextView price;
        TextView desc;
        TextView count;
        ImageView minus;
        ImageView add;

        public GoodsHolder(View itemView) {
            super(itemView);
            check = (RadioButton) itemView.findViewById(R.id.rb_check);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            desc = (TextView) itemView.findViewById(R.id.tv_desc);
            minus = (ImageView) itemView.findViewById(R.id.iv_minus);
            count = (TextView) itemView.findViewById(R.id.tv_count);
            add = (ImageView) itemView.findViewById(R.id.iv_add);
        }
    }
}
