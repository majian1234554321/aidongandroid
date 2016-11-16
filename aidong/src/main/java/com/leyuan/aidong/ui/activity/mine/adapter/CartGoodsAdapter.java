package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中单条商品适配器
 * Created by song on 2016/9/8.
 */
public class CartGoodsAdapter extends RecyclerView.Adapter<CartGoodsAdapter.GoodsHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();
    private CheckBox shopCheckBox;
    private double price = 0d;

    public CartGoodsAdapter(Context context,CheckBox shopCheckBox) {
        this.context = context;
        this.shopCheckBox = shopCheckBox;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_cart_good,null);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.desc.setText(bean.getName());
        holder.price.setText(String.
                format(context.getString(R.string.rmb_price),bean.getPrice()));
        holder.count.setText(bean.getAmount());
        holder.check.setChecked(bean.isChecked());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setChecked(holder.check.isChecked());
                /*******商品与商店联动************/
                //如果当前商品所属的商店处于选中状态，并且对该商品的操作是取消选中，则取消商店的选中状态
                if(shopCheckBox.isChecked() && !holder.check.isChecked()){
                    shopCheckBox.setChecked(false);
                }
                //如果当前商品所属的商店处于未选中状态，对该商品的操作又是选中，对且该商店下的其他商品都处于选中状态，
                //则让该商店处于选中状态
                if(!shopCheckBox.isChecked() && holder.check.isChecked()){
                    boolean isAllGoodsChecked = false;
                    for (GoodsBean goodsBean : data) {
                        isAllGoodsChecked = goodsBean.isChecked();
                        if(!isAllGoodsChecked){
                            break;
                        }
                    }
                    if(isAllGoodsChecked){
                        shopCheckBox.setChecked(true);
                    }
                }
            }
        });

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    price += Double.parseDouble(bean.getPrice()) * Integer.parseInt(bean.getAmount());
                }else{
                    price -= Double.parseDouble(bean.getPrice()) * Integer.parseInt(bean.getAmount());
                }
                Toast.makeText(context,"总价" + price,Toast.LENGTH_SHORT).show();
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.count.setText((Integer.parseInt(holder.count.getText().toString()) + 1)+"");
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.count.getText().toString()) >1){
                    holder.count.setText((Integer.parseInt(holder.count.getText().toString()) - 1)+"");
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GoodsDetailActivity.start(context,"1");
            }
        });
    }


   /* public double getGoodsPrice(){
        //ToDo 转换不安全
        return Double.parseDouble(bean.getPrice()) * Integer.parseInt(bean.getAmount());
    }*/

    class GoodsHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        SimpleDraweeView cover;
        TextView name;
        TextView price;
        TextView desc;
        TextView count;
        ImageView minus;
        ImageView add;

        public GoodsHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.rb_check);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            desc = (TextView) itemView.findViewById(R.id.tv_desc);
            minus = (ImageView) itemView.findViewById(R.id.iv_minus);
            count = (TextView) itemView.findViewById(R.id.tv_count);
            add = (ImageView) itemView.findViewById(R.id.iv_add);
        }
    }
}
