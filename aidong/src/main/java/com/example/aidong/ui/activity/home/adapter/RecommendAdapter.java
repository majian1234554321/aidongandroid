package com.example.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.aidong.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐商品列表适配器
 * Created by song on 2016/8/17.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.GoodsHolder>{
    private Context context;
    private List<GoodsBean> data= new ArrayList<>();

    public RecommendAdapter(Context context) {
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
        View view = View.inflate(context, R.layout.item_recommend_goods,null);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {


    }

    class GoodsHolder extends RecyclerView.ViewHolder {
         SimpleDraweeView dvGoods;
         TextView tvName;
         TextView tvPrice;

        public GoodsHolder(View itemView) {
            super(itemView);

            dvGoods = (SimpleDraweeView)itemView.findViewById(R.id.dv_goods);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView)itemView. findViewById(R.id.tv_price);
        }
    }
}
