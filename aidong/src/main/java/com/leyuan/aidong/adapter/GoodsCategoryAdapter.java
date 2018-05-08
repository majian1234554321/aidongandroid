package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseGoodsBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.home.activity.GoodsListActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

import static com.leyuan.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.GOODS_FOODS;
import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;
import static com.leyuan.aidong.utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2017/9/19.
 */
public class GoodsCategoryAdapter extends RecyclerView.Adapter<GoodsCategoryAdapter.GoodsViewHolder> {
    private BaseGoodsBean.GoodsType goodsType;
    private Context context;
    private String type;
    private ArrayList<CategoryBean> data;


    public GoodsCategoryAdapter(Context context,  String type) {
        this.context = context;
        this.type = type;
    }

    public GoodsCategoryAdapter(Context context, BaseGoodsBean.GoodsType goodsType) {
        this.context = context;
        this.goodsType = goodsType;
    }

    public void setData(ArrayList<CategoryBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, final int position) {
        CategoryBean bean = data.get(position);
        if (TextUtils.isEmpty(bean.getImage())) { //要改
            if (GOODS_EQUIPMENT.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.icon_all_equipment, holder.cover);
            } else if (GOODS_NUTRITION.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.icon_all_nurture, holder.cover);
            } else if (GOODS_FOODS.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.icon_all_food_and_beverage, holder.cover);
            }else if (GOODS_TICKET.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.ticket_all_type, holder.cover);
            }
        } else {
            GlideLoader.getInstance().displayRoundImage(bean.getImage(), holder.cover);
        }
        holder.name.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsListActivity.start(context, type, position);
            }
        });
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_category_cover);
            name = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }
}
