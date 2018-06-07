package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单中单条商品适配器
 * Created by song on 2016/9/8.
 */
public class ConfirmOrderGoodsAdapter extends RecyclerView.Adapter<ConfirmOrderGoodsAdapter.GoodsHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();
    private OnOrderItemClickListener listener;

    public ConfirmOrderGoodsAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm_order_good,parent,false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodsHolder holder, final int position) {
        final GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage2(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.count.setText(String.format(context.getString(R.string.x_count),bean.getAmount()));
        ArrayList<String> specName = bean.getSpecName();
        ArrayList<String> specValue = bean.getSpecValue();
        StringBuilder result = new StringBuilder();
        if(specName != null && specValue != null) {
            for (int i = 0; i < specValue.size(); i++) {
                result.append(specName.get(i)).append(":").append(specValue.get(i)).append(" ");
            }
        }
        if(!TextUtils.isEmpty(bean.getRecommendCode())){
            result.append(" ").append(String.format(context.getString(R.string.recommend_code),
                    bean.getRecommendCode()));
        }else {
            result.append("");
        }


        holder.sku.setText(result);
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick();
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

        public GoodsHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.rb_check);
            cover = (ImageView) itemView.findViewById(R.id.dv_cover);
            name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            sku = (TextView) itemView.findViewById(R.id.tv_sku);
            code = (TextView) itemView.findViewById(R.id.tv_recommend_code);
            count = (TextView) itemView.findViewById(R.id.tv_count);
        }
    }

    public void setListener(OnOrderItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnOrderItemClickListener{
        void onItemClick();
    }
}
