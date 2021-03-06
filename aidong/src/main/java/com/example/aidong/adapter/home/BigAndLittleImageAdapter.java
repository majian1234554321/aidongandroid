package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.HomeItemBean;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.home.activity.CourseListActivityNew;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页推荐商品的RecyclerView适配器
 * Created by song on 2016/7/14.
 */
public class BigAndLittleImageAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{
    private static final int NORMAL_ITEM = 1;
    private static final int SEE_MORE = 2;

    private Context context;
    private String type;
    private List<HomeItemBean> data = new ArrayList<>();
    private SeeMoreListener seeMoreListener;

    public BigAndLittleImageAdapter(Context context,String type) {
        this.context = context;
        this.type = type;
    }

    public void setSeeMoreListener(SeeMoreListener seeMoreListener) {
        this.seeMoreListener = seeMoreListener;
    }

    public void setData(List<HomeItemBean> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(data.size() > 3){        //大于3个子item时才显示查看更多
            return data.size() + 1;
        }else{
            return data.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position < data.size()){
            return NORMAL_ITEM;
        }else {
            return SEE_MORE;
        }
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL_ITEM){
            View view = View.inflate(parent.getContext(),R.layout.item_goods,null);
            return  new RecommendGoodsViewHolder(view);
        }else{
            View view = View.inflate(parent.getContext(),R.layout.item_see_more_rv,null);
            return  new MoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        if(position >= data.size()){
            return;
        }
        final HomeItemBean bean = data.get(position);
        if(holder instanceof RecommendGoodsViewHolder){
            GlideLoader.getInstance().displayImage(bean.getCover(), ((RecommendGoodsViewHolder)holder).image);
            ((RecommendGoodsViewHolder)holder).name.setText(bean.getName());
            ((RecommendGoodsViewHolder)holder).price.setText(String.format(context.getString(R.string.rmb_price_double),
                    FormatUtil.parseDouble(TextUtils.isEmpty(bean.getFloor_price()) ? bean.getPrice() : bean.getFloor_price())));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if("course".equals(type)){
                        CourseListActivityNew.start(context,bean.getName());       //课程跳列表而不是详情
                    }else {
                        ((MainActivity)context).toTargetDetailActivity(type,bean.getId());
                    }
                }
            });
        }
    }

     private class RecommendGoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView price;
         public RecommendGoodsViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.dv_goods);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            price = (TextView)itemView.findViewById(R.id.tv_price);
        }
    }

    private class MoreViewHolder extends RecyclerView.ViewHolder{
        ImageView more;

        public MoreViewHolder(View itemView) {
            super(itemView);
            more = (ImageView)itemView.findViewById(R.id.iv_see_more);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(seeMoreListener != null){
                        seeMoreListener.onSeeMore();
                    }
                }
            });
        }
    }

    public interface SeeMoreListener{
        void onSeeMore();
    }
}
