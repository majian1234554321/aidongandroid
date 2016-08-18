package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.GoodsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页推荐商品的RecyclerView适配器
 * Created by song on 2016/7/14.
 */
public class RecommendGoodsAdapter extends RecyclerView.Adapter<RecommendGoodsAdapter.RecommendGoodsListViewHolder>{

    List<GoodsBean> date = new ArrayList<>();
    ImageLoader imageLoader = ImageLoader.getInstance();

    public RecommendGoodsAdapter(List<GoodsBean> date) {
        this.date = date;
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    @Override
    public RecommendGoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_goods,null);
        return new RecommendGoodsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendGoodsListViewHolder holder, int position) {
        GoodsBean bean = date.get(position);
        imageLoader.displayImage(bean.getCover(),holder.image);
        holder.name.setText(bean.getName());
        holder.price.setText(bean.getPrice());
    }

     static class RecommendGoodsListViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView price;
        public RecommendGoodsListViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_goods);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            price = (TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
