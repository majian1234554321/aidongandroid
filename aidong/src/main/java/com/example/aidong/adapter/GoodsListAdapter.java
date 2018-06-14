package com.example.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .ui.home.activity.GoodsDetailActivity;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.constant.GoodsType;

import java.util.List;

/**
 * Created by user on 2017/8/1.
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.FilterViewHolder> {
    private Context context;
    private String type;
    private List<GoodsBean> goodList;

    public GoodsListAdapter(Context context, @GoodsType String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public int getItemCount() {
        if (goodList == null)
            return 0;
        return goodList.size();
    }


    public void setGoodsData(List<GoodsBean> beanList) {
        this.goodList = beanList;
    }

    @NonNull
    @Override
    public GoodsListAdapter.FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nurture_filter, parent, false);
        return new GoodsListAdapter.FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsListAdapter.FilterViewHolder holder, int position) {
        final GoodsBean bean = goodList.get(position);
        GlideLoader.getInstance().displayImage2(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.brand.setText(bean.getBrandName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(TextUtils.isEmpty(bean.getFloor_price()) ? bean.getPrice() : bean.getFloor_price())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.start(context, bean.getId(),bean.getType());

            }
        });
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView brand;
        TextView price;

        public FilterViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView) itemView.findViewById(R.id.tv_nurture_name);
            brand = (TextView) itemView.findViewById(R.id.tv_nurture_brand);
            price = (TextView) itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}

