package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class RecommendGoodsAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{
    private static final int NORMAL_ITEM = 1;
    private static final int SEE_MORE = 2;

    private Context context;
    private List<GoodsBean> data = new ArrayList<>();

    public RecommendGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < data.size()){
            return NORMAL_ITEM;
        }else {
            return SEE_MORE;
        }
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL_ITEM){
            View view = View.inflate(parent.getContext(),R.layout.item_goods,null);
            return  new RecommendGoodsViewHolder(view);
        }else{
            View view = View.inflate(parent.getContext(),R.layout.item_see_more_rv,null);
            return  new MoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        if(position >= data.size()){
            return;
        }
        final GoodsBean bean = data.get(position);
        if(holder instanceof RecommendGoodsViewHolder){
            ((RecommendGoodsViewHolder)holder).image.setImageURI(bean.getCover());
            ((RecommendGoodsViewHolder)holder).name.setText(bean.getName());
            ((RecommendGoodsViewHolder)holder).price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show("商品详情",context);
                }
            });
        }
    }

     private class RecommendGoodsViewHolder extends RecyclerView.ViewHolder{
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

    private class MoreViewHolder extends RecyclerView.ViewHolder{
        ImageView more;

        public MoreViewHolder(View itemView) {
            super(itemView);
            more = (ImageView)itemView.findViewById(R.id.iv_see_more);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
