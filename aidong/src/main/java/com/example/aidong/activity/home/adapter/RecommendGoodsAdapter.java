package com.example.aidong.activity.home.adapter;

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
 * 首页推荐商品的RecyclerView适配器
 * Created by song on 2016/7/14.
 */
public class RecommendGoodsAdapter extends RecyclerView.Adapter<RecommendGoodsAdapter.RecommendGoodsViewHolder>{

    private List<GoodsBean> date = new ArrayList<>();

    public void setDate(List<GoodsBean> date) {
        this.date = date;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    @Override
    public RecommendGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_goods,null);
        return new RecommendGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendGoodsViewHolder holder, int position) {
        GoodsBean bean = date.get(position);
        holder.image.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.price.setText(bean.getPrice());
    }

     static class RecommendGoodsViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image;
        TextView name;
        TextView price;
        public RecommendGoodsViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.dv_goods);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            price = (TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
