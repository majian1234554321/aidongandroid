package com.leyuan.aidong.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.ui.home.activity.BrandActivity;
import com.leyuan.aidong.widget.customview.MyListView;

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
        if(TextUtils.equals("list",data.get(position).getStyle())){          //大图加小图
            return TYPE_RECOMMEND_GOODS;
        }else if(TextUtils.equals("cover",data.get(position).getStyle())){   //大图列表
            return TYPE_RECOMMEND_ACTIVITY;
        }else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_RECOMMEND_ACTIVITY){
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_big_image_list,parent,false);
            return new CoverImageViewHolder(view);
        }else if(viewType == TYPE_RECOMMEND_GOODS){
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_big_and_little_image,parent,false);
            return new BigAndLittleImageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final HomeBean bean = data.get(position);
        if(holder instanceof CoverImageViewHolder){
            CoverImageAdapter adapter = new CoverImageAdapter(context);
            ((CoverImageViewHolder) holder).tvName.setText(bean.getTitle());
            ((CoverImageViewHolder) holder).listView.setAdapter(adapter);
            adapter.addList(bean.getItem());
        }else if(holder instanceof BigAndLittleImageViewHolder){
            ((BigAndLittleImageViewHolder) holder).cover.setImageURI(bean.getImage());
            ((BigAndLittleImageViewHolder) holder).cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BrandActivity.start(context, bean.getType(),bean.getId(),bean.getTitle(),
                            bean.getImage(),bean.getIntroduce());
                }
            });
            BigAndLittleImageAdapter adapter = new BigAndLittleImageAdapter(context,bean.getType());
            ((BigAndLittleImageViewHolder) holder).recyclerView.setLayoutManager(
                    new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            ((BigAndLittleImageViewHolder) holder).recyclerView.setAdapter(adapter);
            adapter.setData(bean.getItem());
            adapter.setSeeMoreListener(new BigAndLittleImageAdapter.SeeMoreListener() {
                @Override
                public void onSeeMore() {
                    BrandActivity.start(context,bean.getType(),bean.getId(),bean.getTitle(),
                            bean.getImage(),bean.getIntroduce());
                }
            });
        }
    }


    /**
     * 大图加小图ViewHolder
     */
    private static class BigAndLittleImageViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        RecyclerView recyclerView;

        public BigAndLittleImageViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_cover);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend_good);
        }
    }

    /**
     * 大图列表ViewHolder
     */
    private static class CoverImageViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        MyListView listView;

        public CoverImageViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            listView = (MyListView) itemView.findViewById(R.id.lv_recommend);
        }
    }
}
