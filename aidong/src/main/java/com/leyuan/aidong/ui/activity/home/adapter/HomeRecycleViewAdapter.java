package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.widget.customview.MyListView;
import com.leyuan.aidong.widget.overscroll.IOverScrollDecor;
import com.leyuan.aidong.widget.overscroll.IOverScrollUpdateListener;
import com.leyuan.commonlibrary.util.ToastUtil;

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
    private List<HomeBean> data = new ArrayList<>();


    public HomeRecycleViewAdapter(Context context) {
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
            return TYPE_RECOMMEND_GOODS;
        }else if(TextUtils.equals("cover",data.get(position).getDisplay())){
            return TYPE_RECOMMEND_ACTIVITY;
        }else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_RECOMMEND_ACTIVITY){
            View view = View.inflate(parent.getContext(), R.layout.item_recommend_activity,null);
            return new RecommendActivityViewHolder(view);
        }else if(viewType == TYPE_RECOMMEND_GOODS){
            View view = View.inflate(parent.getContext(),R.layout.item_home_recommend_goods,null);
            return new RecommendGoodsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeBean bean = data.get(position);
        if(holder instanceof RecommendActivityViewHolder){
            RecommendCampaignsAdapter adapter = new RecommendCampaignsAdapter(context);
            ((RecommendActivityViewHolder) holder).listView.setAdapter(adapter);
            adapter.addList(bean.getCategory());
        }else if(holder instanceof  RecommendGoodsViewHolder){
            ((RecommendGoodsViewHolder) holder).cover.setImageURI(bean.getCategory().get(0).getCover());
            ((RecommendGoodsViewHolder) holder).cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show("美女",context);
                }
            });
            RecommendGoodsAdapter adapter = new RecommendGoodsAdapter(context);
            ((RecommendGoodsViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            ((RecommendGoodsViewHolder) holder).recyclerView.setAdapter(adapter);
           

            ArrayList<GoodsBean> list = new ArrayList<>();
            for(int i =0; i<10;i++){
                GoodsBean bean1 = new GoodsBean();
                if(i % 2 == 0) {
                    bean1.setName("美女");
                    bean1.setPrice("100");
                    bean1.setCover("http://o8e1adk04.bkt.clouddn.com/image/2016/07/23/e391a4e8e20206696ff465db27f1c56a.jpg");
                }else {
                    bean1.setName("github");
                    bean1.setPrice("10");
                    bean1.setCover("http://o8e1adk04.bkt.clouddn.com/image/2016/07/15/8fd670b0-74c3-43ef-8a69-412dceff15d9.jpg");
                }
                list.add(bean1);
            }
            adapter.setData(list);
           // adapter.setData(bean.getCategory().get(0).getItem());
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
            itemView.setLayoutParams(new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
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
            itemView.setLayoutParams(new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            listView = (MyListView) itemView.findViewById(R.id.lv_recommend);
        }
    }
}
