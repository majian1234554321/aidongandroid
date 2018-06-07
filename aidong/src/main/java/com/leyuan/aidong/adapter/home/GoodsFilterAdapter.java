package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.EquipmentBean;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.constant.GoodsType;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;

/**
 * 营养品筛选界面适配器
 * Created by song on 2016/8/17.
 */
public class GoodsFilterAdapter extends RecyclerView.Adapter<GoodsFilterAdapter.FilterViewHolder>{
    private Context context;
    private String type;
    private List<NurtureBean> nurtureList = new ArrayList<>();
    private List<EquipmentBean> equipmentList = new ArrayList<>();

    public GoodsFilterAdapter(Context context,@GoodsType String type) {
        this.context = context;
        this.type = type;
    }

    @Deprecated
    public void setNurtureList(List<NurtureBean> nurtureList) {
        if(nurtureList != null){
            this.nurtureList = nurtureList;
        }
    }

    @Deprecated
    public void setEquipmentList(List<EquipmentBean> equipmentList) {
        if(equipmentList != null)
        this.equipmentList = equipmentList;
    }

    @Override
    public int getItemCount() {
        if(type.equals(GOODS_EQUIPMENT)){
            return equipmentList.size();
        }else {
            return nurtureList.size();
        }
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nurture_filter,parent,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        if(GOODS_NUTRITION.equals(type)){
            final NurtureBean bean = nurtureList.get(position);
            GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
            holder.name.setText(bean.getName());
            holder.brand.setText(bean.getBrandName());
            holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(TextUtils.isEmpty(bean.getFloor_price()) ? bean.getPrice() : bean.getFloor_price())));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsDetailActivity.start(context,bean.getId(), GOODS_NUTRITION);
                }
            });
        }else {
            final EquipmentBean bean = equipmentList.get(position);
            GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
            holder.name.setText(bean.getName());
            holder.brand.setText(bean.getBrandName());
            holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                    FormatUtil.parseDouble(bean.getPrice())));

            holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                    FormatUtil.parseDouble(TextUtils.isEmpty(bean.getFloor_price()) ? bean.getPrice() : bean.getFloor_price())));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsDetailActivity.start(context, bean.getId(), GOODS_EQUIPMENT);
                }
            });
        }
    }



    class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView brand;
        TextView price;

        public FilterViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            brand = (TextView)itemView.findViewById(R.id.tv_nurture_brand);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}
