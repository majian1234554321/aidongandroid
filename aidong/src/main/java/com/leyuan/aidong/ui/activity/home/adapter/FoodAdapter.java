package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.FoodBean;
import com.leyuan.aidong.ui.activity.home.OldGoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.ui.activity.home.GoodsDetailActivity.TYPE_FOODS;

/**
 * 健康列表适配器
 * Created by song on 2016/8/17.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private static final int IMAGE_ALIGN_LEFT = 1;
    private static final int IMAGE_ALIGN_RIGHT = 2;

    private Context context;
    private List<FoodBean> data = new ArrayList<>();

    public FoodAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FoodBean> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? IMAGE_ALIGN_LEFT :IMAGE_ALIGN_RIGHT;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == IMAGE_ALIGN_LEFT){
            view = View.inflate(parent.getContext(), R.layout.item_food_image_left,null);
        }else {
            view = View.inflate(parent.getContext(), R.layout.item_food_image_right,null);
        }
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        final FoodBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
        if(bean.getCrowd() != null){
            holder.function.setText(bean.getCrowd().get(0));
        }

        List<String> material = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        if(bean.getMaterial() != null){
            material.addAll(bean.getMaterial());
        }

        //最多6个食材
        while (material.size() > 6){
            material.remove(6);
        }

        for(int i=0; i<material.size();i++){
            if(i != material.size() - 1){
                sb.append(material.get(i)).append(",");
            }else {
                sb.append(material.get(i));
            }
        }

        holder.material.setText(String.format(context.getString(R.string.food_material),sb));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldGoodsDetailActivity.start(context, bean.getFood_id(),TYPE_FOODS);
            }
        });
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;
        TextView material;
        TextView function;
        TextView price;

        public FoodViewHolder (View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_food_cover);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            material = (TextView)itemView.findViewById(R.id.tv_material);
            function = (TextView)itemView.findViewById(R.id.tv_function);
            price = (TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
