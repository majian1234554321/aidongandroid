package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.BaseGoodsBean;

import com.example.aidong.entity.MarketPartsBean;
import com.example.aidong.ui.home.activity.GoodsListActivity;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.constant.GoodsType;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong.utils.Constant.GOODS_FOODS;
import static com.example.aidong.utils.Constant.GOODS_NUTRITION;

/**
 * 营养品类型适配器
 * Created by song on 2016/8/17.
 */
public class CategoryAdapter2 extends RecyclerView.Adapter<CategoryAdapter2.FoodViewHolder> {
    private BaseGoodsBean.GoodsType goodsType;
    private Context context;
    private String type;
    private List<MarketPartsBean.ChildrenBeanX> data ;


    public CategoryAdapter2(Context context, @GoodsType String type) {
        this.context = context;
        this.type = type;
    }

    public CategoryAdapter2(Context context, BaseGoodsBean.GoodsType goodsType) {
        this.context = context;
        this.goodsType = goodsType;
    }

    public void setData(List<MarketPartsBean.ChildrenBeanX> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, final int position) {
        MarketPartsBean.ChildrenBeanX bean = data.get(position);
        if (TextUtils.isEmpty(bean.cover)) {
            if (GOODS_EQUIPMENT.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.icon_all_equipment, holder.cover);
            } else if (GOODS_NUTRITION.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.icon_all_nurture, holder.cover);
            } else if (GOODS_FOODS.equals(type)) {
                GlideLoader.getInstance().displayRoundLocalImage(R.drawable.icon_all_food_and_beverage, holder.cover);
            }
        } else {
            GlideLoader.getInstance().displayRoundImage(bean.cover, holder.cover);
        }
        holder.name.setText(bean.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsListActivity.start(context, type, position);
            }
        });
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;

        public FoodViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_category_cover);
            name = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }
}
