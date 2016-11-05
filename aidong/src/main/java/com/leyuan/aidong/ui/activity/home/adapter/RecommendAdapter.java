package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.activity.home.GoodsDetailActivity;

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
        }else{
            for (int i = 0; i < 10; i++) {
                GoodsBean bean = new GoodsBean();
                if(i % 2 == 0){
                    bean.setCover("http://ww2.sinaimg.cn/mw690/006uFQHggw1f94x7xfic2j30qo0zi47p.jpg");
                    bean.setName("AiFukuhara");
                    bean.setPrice("999");
                }else{
                    bean.setCover("http://ww3.sinaimg.cn/mw690/006uFQHggw1f9e6ex106aj30qo0zmtib.jpg");
                    bean.setName("food");
                    bean.setPrice("9");
                }
                this.data.add(bean);
            }
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
        GoodsBean bean = data.get(position);
        holder.dvGoods.setImageURI(bean.getCover());
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText(bean.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.start(context,"1");
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
