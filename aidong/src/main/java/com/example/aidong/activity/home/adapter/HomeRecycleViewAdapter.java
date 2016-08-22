package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong.view.MyListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.HomeItemBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页RecycleView适配器
 * Created by song on 2016/7/14.
 */
public class HomeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_RECOMMEND_ACTIVITY = 1;
    public static final int TYPE_RECOMMEND_GOODS = 2;

    private Context context;
    private List<HomeItemBean> data = new ArrayList<>();


    public HomeRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeItemBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals("list",data.get(position).getDisplay())){
            return TYPE_RECOMMEND_GOODS;
        }else if(TextUtils.equals("cover",data.get(position).getDisplay())){
            return TYPE_RECOMMEND_ACTIVITY;
        }else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_RECOMMEND_ACTIVITY){
            View view = View.inflate(parent.getContext(), R.layout.item_recommend_activity,null);
            return new RecommendActivityViewHolder(view);
        }else if(viewType == TYPE_RECOMMEND_GOODS){
            View view = View.inflate(parent.getContext(),R.layout.item_recommend_goods,null);
            return new RecommendGoodsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeItemBean bean = data.get(position);
        if(holder instanceof RecommendActivityViewHolder){
            RecommendCampaignsAdapter adapter = new RecommendCampaignsAdapter();
            ((RecommendActivityViewHolder) holder).listView.setAdapter(adapter);
            adapter.addList(bean.getCategory());
        }else if(holder instanceof  RecommendGoodsViewHolder){
            //ImageLoader.getInstance().displayImage(bean.getCategory().get(0).getCover(),((RecommendGoodsViewHolder) holder).cover);
            ((RecommendGoodsViewHolder) holder).cover.setImageURI(bean.getCategory().get(0).getCover());
            RecommendGoodsAdapter adapter = new RecommendGoodsAdapter();
            ((RecommendGoodsViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            ((RecommendGoodsViewHolder) holder).recyclerView.setAdapter(adapter);
            adapter.setDate(bean.getCategory().get(0).getItem());
        }
    }


    /**
     * 推荐商品ViewHolder
     */
    private static class RecommendGoodsViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        RecyclerView recyclerView;

        public RecommendGoodsViewHolder (View itemView) {
            super(itemView);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_recommend_good);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend_good);
        }
    }

    /**
     * 推荐活动ViewHolder
     */
    private static class RecommendActivityViewHolder extends RecyclerView.ViewHolder{
        MyListView listView;

        public RecommendActivityViewHolder (View itemView) {
            super(itemView);
            listView = (MyListView) itemView.findViewById(R.id.lv_recommend);
        }
    }
}
