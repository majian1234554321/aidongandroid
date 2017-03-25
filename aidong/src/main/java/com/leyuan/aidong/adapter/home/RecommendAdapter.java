package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.home.activity.CourseActivity;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐商品列表适配器
 * Created by song on 2016/8/17.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.GoodsHolder>{
    private Context context;
    private String type;
    private List<GoodsBean> data= new ArrayList<>();

    public RecommendAdapter(Context context) {
        this.context = context;
    }


    //compat
    public RecommendAdapter(Context context,String type) {
        this.context = context;
        this.type = type;
    }

    public void setData(List<GoodsBean> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_goods,parent,false);
        return new GoodsHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.dvGoods);
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = !TextUtils.isEmpty(bean.getType())?bean.getType(): type ;
                if("course".equals(t)){
                    CourseActivity.start(context,bean.getName());       //课程跳列表而不是详情
                }else {
                    ((MainActivity)context).toTargetDetailActivity(t,bean.getId());
                }
            }
        });
    }

    class GoodsHolder extends RecyclerView.ViewHolder {
         ImageView dvGoods;
         TextView tvName;
         TextView tvPrice;

        public GoodsHolder(View itemView) {
            super(itemView);

            dvGoods = (ImageView) itemView.findViewById(R.id.dv_goods);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView)itemView. findViewById(R.id.tv_price);
        }
    }
}
