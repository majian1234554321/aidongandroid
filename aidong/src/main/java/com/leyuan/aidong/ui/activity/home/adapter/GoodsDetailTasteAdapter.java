package com.leyuan.aidong.ui.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情口味适配器
 * Created by song on 2016/9/23.
 */
public class GoodsDetailTasteAdapter extends RecyclerView.Adapter<GoodsDetailTasteAdapter.TasteHolder>{

    private List<String> data = new ArrayList<>();

    public GoodsDetailTasteAdapter(List<String> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public TasteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_goods_taste,null);
        return new TasteHolder(view);
    }

    @Override
    public void onBindViewHolder(TasteHolder holder, int position) {
        String taste = data.get(position);
        holder.taste.setText(taste);
    }

    class TasteHolder extends RecyclerView.ViewHolder{
        TextView taste;
        public TasteHolder(View itemView) {
            super(itemView);
            taste = (TextView)itemView.findViewById(R.id.tv_taste);
        }
    }
}
