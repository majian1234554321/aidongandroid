package com.leyuan.aidong.adapter.home;

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
 * 搜索商品适配器 包括营养品和装备
 * Created by song on 2016/12/6.
 */
public class SearchGoodsAdapter extends RecyclerView.Adapter<SearchGoodsAdapter.GoodsViewHolder>{
    private Context context;
    private List<GoodsBean> goods = new ArrayList<>();

    public SearchGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> goods) {
        this.goods = goods;
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nurture_filter,parent,false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        final GoodsBean bean = goods.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.brand.setText(bean.getMarket_price());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CompatGoodsDetailActivity.start(context,bean.getId(), CompatGoodsDetailActivity.TYPE_NURTURE);
            }
        });
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView brand;
        TextView price;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            brand = (TextView)itemView.findViewById(R.id.tv_nurture_brand);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}
