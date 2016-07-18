package com.example.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong.model.RecommendActivityBean;
import com.example.aidong.model.RecommendGoodsBean;
import com.example.aidong.model.RecycleviewItemBean;
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

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<RecycleviewItemBean> date = new ArrayList<>();

    public List<RecycleviewItemBean> getData () {
        return date;
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(date.get(position).display);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_RECOMMEND_ACTIVITY){
            View view = View.inflate(parent.getContext(),R.layout.item_recommend_activity_rv,null);
            RecommendActivityViewHolder holder = new RecommendActivityViewHolder(view);
            return holder;
        }else if(viewType == TYPE_RECOMMEND_GOODS){
            View view = View.inflate(parent.getContext(),R.layout.item_recommend_goods_rv,null);
            RecommendGoodsViewHolder holder = new RecommendGoodsViewHolder(view,parent.getContext());
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  RecommendActivityViewHolder){
            RecommendActivityBean bean = (RecommendActivityBean)date.get(position).t;
            RecommendActivityListAdapter adapter = new RecommendActivityListAdapter();
            adapter.setList(bean.category);
            ((RecommendActivityViewHolder) holder).listView.setAdapter(adapter);
        }else if(holder instanceof  RecommendGoodsViewHolder){
            RecommendGoodsBean bean = (RecommendGoodsBean)date.get(position).t;
            imageLoader.displayImage(bean.cover,((RecommendGoodsViewHolder) holder).imageView);
            RecommendGoodsListAdapter adapter = new RecommendGoodsListAdapter(bean.item);
            ((RecommendGoodsViewHolder) holder).recyclerView.setAdapter(adapter);
        }
    }


    /**
     * 推荐商品ViewHolder
     */
    private static class RecommendGoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RecyclerView recyclerView;

        public RecommendGoodsViewHolder (View itemView,Context context) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_recommend);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
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
