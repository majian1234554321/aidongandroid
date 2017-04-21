package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.HomeItemBean;
import com.leyuan.aidong.ui.home.activity.BrandActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.recyclerView;

/**
 * 商城适配器
 * Created by song on 2017/4/12.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder>{
    private Context context;
    private List<HomeItemBean> data = new ArrayList<>();


    public StoreAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeItemBean> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public StoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store,parent,false);
        return new StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreHolder holder, int position) {
        HomeItemBean bean = data.get(position);

        BigAndLittleImageAdapter adapter = new BigAndLittleImageAdapter(context,bean.getType());
        holder.goods.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.goods.setAdapter(adapter);

        adapter.setSeeMoreListener(new BigAndLittleImageAdapter.SeeMoreListener() {
            @Override
            public void onSeeMore() {

            }
        });
    }


    class StoreHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        RecyclerView goods;

        public StoreHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.iv_food_cover);
            goods = (RecyclerView) itemView.findViewById(R.id.rv_food);
        }
    }
}
