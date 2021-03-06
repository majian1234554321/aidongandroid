package com.example.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * 视界专题详情界面展开中相关商品RecyclerView 适配器
 * Created song pc on 2016/7/25.
 */
public class WatchOfficeRelateGoodAdapter extends RecyclerView.Adapter<WatchOfficeRelateGoodAdapter.RelateVideoViewHolder>{
    private List<GoodsBean> data = new ArrayList<>();
    private Context context;

    private OnGoodsItemClickListener listener;
    public WatchOfficeRelateGoodAdapter(Context context,OnGoodsItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RelateVideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_relate_good,null);
        RelateVideoViewHolder holder = new RelateVideoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RelateVideoViewHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.price.setText(new StringBuilder().append("¥").append(bean.getPrice() == null?bean.getMarket_price():bean.getPrice()));
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGoodsClick(bean);
            }
        });
    }


    class RelateVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        TextView price;

        public RelateVideoViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.iv_good_cover);
            name = (TextView)itemView.findViewById(R.id.tv_good_name);
            price = (TextView)itemView.findViewById(R.id.tv_good_price);
        }
    }

    public interface OnGoodsItemClickListener{
        void onGoodsClick(GoodsBean bean);
    }
}
