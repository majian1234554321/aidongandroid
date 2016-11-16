package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.activity.home.BrandActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐商品列表适配器
 * Created by song on 2016/8/17.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.GoodsHolder>{
    private Context context;
    private String type;
    private List<GoodsBean> data= new ArrayList<>();

    public RecommendAdapter(Context context,String type) {
        this.context = context;
        this.type = type;
    }

    public void setData(List<GoodsBean> data) {
        if(data != null){
            this.data = data;
        }
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
        final GoodsBean bean = data.get(position);
        holder.dvGoods.setImageURI(bean.getCover());
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText(bean.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BrandActivity)context).toTagetDetailActivity(type,bean.getId());
            }
        });
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
