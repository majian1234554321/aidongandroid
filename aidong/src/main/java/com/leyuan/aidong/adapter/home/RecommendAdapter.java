package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.FormatUtil;

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
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_goods,parent,false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        holder.dvGoods.setImageURI(bean.getCover());
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity)context).toTargetDetailActivity(bean.getType(),bean.getId());
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
