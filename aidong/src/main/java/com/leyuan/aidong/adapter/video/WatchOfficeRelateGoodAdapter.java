package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * 视界专题详情界面展开中相关商品RecyclerView 适配器
 * Created song pc on 2016/7/25.
 */
public class WatchOfficeRelateGoodAdapter extends RecyclerView.Adapter<WatchOfficeRelateGoodAdapter.RelateVideoViewHolder>{
    private List<GoodBean> data = new ArrayList<>();
    private Context context;
    private ImageLoader imageLoader ;
    private DisplayImageOptions options;
    private OnGoodsItemClickListener listener;
    public WatchOfficeRelateGoodAdapter(Context context, ImageLoader imageLoader, DisplayImageOptions options,OnGoodsItemClickListener listener) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.options = options;
        this.listener = listener;
    }

    public void setData(List<GoodBean> data) {
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
        final GoodBean bean = data.get(position);
        imageLoader.displayImage(bean.getFoodUrl(),holder.cover,options);
        holder.name.setText(bean.getFoodName());
        holder.price.setText(new StringBuilder().append("¥").append(bean.getFoodPrice()));
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
        void onGoodsClick(GoodBean bean);
    }
}
