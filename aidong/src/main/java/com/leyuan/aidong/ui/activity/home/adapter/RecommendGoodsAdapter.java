package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.aidong.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页推荐商品的RecyclerView适配器
 * Created by song on 2016/7/14.
 */
public class RecommendGoodsAdapter extends RecyclerView.Adapter<RecommendGoodsAdapter.RecommendGoodsViewHolder>{
    private Context context;
    private List<GoodsBean> date = new ArrayList<>();

    public RecommendGoodsAdapter(Context context) {
        this.context = context;
    }

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
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("商品详情",context);
            }
        });
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
