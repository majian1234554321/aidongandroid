package com.example.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong.model.bean.HomeBean;
import com.example.aidong.view.MyListView;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<HomeBean> data = new ArrayList<>();

    public HomeRecycleViewAdapter(List<HomeBean> data,Context context) {
        this.data = data;
        this.context = context;
    }

    public void setData(List<HomeBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals("list",data.get(position).getDisplay())){
            return TYPE_RECOMMEND_ACTIVITY;
        }else if(TextUtils.equals("cover",data.get(position).getDisplay())){
            return TYPE_RECOMMEND_GOODS;
        }else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_RECOMMEND_ACTIVITY){
            View view = View.inflate(parent.getContext(),R.layout.item_recommend_activity_rv,null);
            RecommendActivityViewHolder holder = new RecommendActivityViewHolder(view);
            return holder;
        }else if(viewType == TYPE_RECOMMEND_GOODS){
            View view = View.inflate(parent.getContext(),R.layout.item_recommend_goods_rv,null);
            RecommendGoodsViewHolder holder = new RecommendGoodsViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeBean bean = data.get(position);
        if(holder instanceof  RecommendActivityViewHolder){
            RecommendActivityListAdapter adapter = new RecommendActivityListAdapter();
            adapter.setList(bean.getCategory());
            ((RecommendActivityViewHolder) holder).listView.setAdapter(adapter);
        }else if(holder instanceof  RecommendGoodsViewHolder){
            imageLoader.displayImage(bean.getCategory().get(position).getCover(),((RecommendGoodsViewHolder) holder).imageView);
            RecommendGoodsListAdapter adapter = new RecommendGoodsListAdapter(bean.getCategory().get(position).getItem());
            ((RecommendGoodsViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            ((RecommendGoodsViewHolder) holder).recyclerView.setAdapter(adapter);
        }
    }


    /**
     * 推荐商品ViewHolder
     */
    private static class RecommendGoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RecyclerView recyclerView;

        public RecommendGoodsViewHolder (View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_recommend);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend);
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
