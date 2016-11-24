package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中单条商品适配器
 * Created by song on 2016/9/8.
 */
public class CartGoodsAdapter extends RecyclerView.Adapter<CartGoodsAdapter.GoodsHolder>{
    private Context context;
    private boolean isEditing = false;
    private List<GoodsBean> data = new ArrayList<>();
    private GoodsChangeListener goodsChangeListener;

    public CartGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
        notifyDataSetChanged();
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
        View view = View.inflate(context,R.layout.item_cart_good,null);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsHolder holder, final int position) {
        final GoodsBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.desc.setText(bean.getName());
        holder.price.setText(String.format
                (context.getString(R.string.rmb_price),bean.getPrice()));
        holder.count.setText(bean.getAmount());
        holder.check.setChecked(isEditing ? bean.isEditChecked() : bean.isChecked());

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //商品点击时改变商品状态，并通知商店相应改变
                if(isEditing){
                    bean.setEditChecked(!bean.isEditChecked());
                }else {
                    bean.setChecked(!bean.isChecked());
                }
                if(goodsChangeListener != null){
                    goodsChangeListener.onGoodsStatusChanged();
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = FormatUtil.parseInt(holder.count.getText().toString());
                count ++;
                if(goodsChangeListener != null){
                    goodsChangeListener.onGoodsCountChanged(data.get(position).getId(),count);
                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = FormatUtil.parseInt(holder.count.getText().toString());
                if(count > 1){
                    count --;
                }
                if(goodsChangeListener != null){
                    goodsChangeListener.onGoodsCountChanged(data.get(position).getId(),count);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GoodsDetailActivity.start(context,"1");
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.delete_confirm))
                        .setCancelable(true)
                        .setPositiveButton(context.getString(R.string.sure), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(goodsChangeListener != null){
                                    goodsChangeListener.onGoodsDeleted(data.get(position).getId());
                                }
                            }
                        })
                        .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                return true;
            }
        });
    }

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

    public void setGoodsChangeListener(GoodsChangeListener listener) {
        this.goodsChangeListener = listener;
    }

    public interface GoodsChangeListener {
        void onGoodsStatusChanged();
        void onGoodsDeleted(String goodsId);
        void onGoodsCountChanged(String goodsId,int count);
    }
}
