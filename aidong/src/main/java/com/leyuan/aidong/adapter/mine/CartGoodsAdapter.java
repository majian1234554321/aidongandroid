package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中单条商品适配器
 * Created by song on 2016/9/8.
 */
public class CartGoodsAdapter extends RecyclerView.Adapter<CartGoodsAdapter.GoodsHolder> {
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();
    private GoodsChangeListener goodsChangeListener;

    public CartGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
        if (data != null) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_good, parent, false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsHolder holder, final int position) {
        final GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        ArrayList<String> specValue = bean.getSpecValue();
        StringBuilder skuStr = new StringBuilder();
        for (String result : specValue) {
            skuStr.append(result);
        }

        holder.sku.setText(skuStr);
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));

        if (!TextUtils.isEmpty(bean.getRecommendCode())) {
            holder.code.setText(String.format(context.getString(R.string.recommend_code),
                    bean.getRecommendCode()));
        }

        if (bean.isOnline() && bean.getStock() != 0) {
            holder.check.setVisibility(View.VISIBLE);
            holder.check.setChecked(bean.isChecked());
            holder.dv_sold_out.setVisibility(View.GONE);
            holder.count.setText(bean.getAmount());
            holder.minus.setImageResource(FormatUtil.parseInt(bean.getAmount()) == 1 ?
                    R.drawable.icon_minus_gray : R.drawable.icon_minus);
            setShoppingClickEvent(holder, bean, position);

        } else {
            holder.check.setVisibility(View.GONE);
            holder.dv_sold_out.setVisibility(View.VISIBLE);
            holder.dv_sold_out.setImageResource(bean.isOnline() ? R.drawable.shop_sold_out : R.drawable.shop_out_of_stock);
            holder.minus.setImageResource(R.drawable.icon_minus_gray);
            holder.minus.setClickable(false);
            holder.count.setTextColor(Color.parseColor("#999999"));
            holder.count.setText("1");

            holder.count.setBackgroundResource(R.drawable.shape_stroke_gray);
            holder.add.setImageResource(R.drawable.icon_add_gray);
            holder.add.setClickable(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.start(context, bean.getProductId(), bean.getProductType());
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
                                if (goodsChangeListener != null) {
                                    goodsChangeListener.onGoodsDeleted(data.get(position).getId(), position);
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

    private void setShoppingClickEvent(final GoodsHolder holder, final GoodsBean bean, final int position) {
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //商品点击时改变商品状态，并通知商店相应改变
                bean.setChecked(!bean.isChecked());
                if (goodsChangeListener != null) {
                    goodsChangeListener.onGoodsStatusChanged();
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = FormatUtil.parseInt(holder.count.getText().toString());
                count++;
                if (goodsChangeListener != null) {
                    goodsChangeListener.onGoodsCountChanged(data.get(position).getId(), count, position);
                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = FormatUtil.parseInt(holder.count.getText().toString());
                if (count <= 1) {
                    return;
                }
                count--;
                if (goodsChangeListener != null) {
                    goodsChangeListener.onGoodsCountChanged(data.get(position).getId(), count, position);
                }
            }
        });

    }

    class GoodsHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        ImageView cover;
        TextView name;
        TextView price;
        TextView sku;
        TextView code;
        TextView count;
        ImageView minus;
        ImageView add;
        ImageView dv_sold_out;

        public GoodsHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.rb_check);
            cover = (ImageView) itemView.findViewById(R.id.dv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            sku = (TextView) itemView.findViewById(R.id.tv_sku);
            code = (TextView) itemView.findViewById(R.id.tv_code);
            minus = (ImageView) itemView.findViewById(R.id.iv_minus);
            count = (TextView) itemView.findViewById(R.id.tv_count);
            add = (ImageView) itemView.findViewById(R.id.iv_add);
            dv_sold_out = (ImageView) itemView.findViewById(R.id.dv_sold_out);
        }
    }

    public void setGoodsChangeListener(GoodsChangeListener listener) {
        this.goodsChangeListener = listener;
    }

    public interface GoodsChangeListener {
        void onGoodsStatusChanged();    //选中 未选中

        void onGoodsDeleted(String goodsId, int goodsPosition);  //删除

        void onGoodsCountChanged(String goodsId, int count, int goodsPosition); //改变数量
    }
}
