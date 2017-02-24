package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.home.activity.OldGoodsDetailActivity;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.TYPE_NURTURE;


/**
 * 营养品的RecyclerView适配器
 * Created by song on 2016/7/16.
 */
public class NurtureAdapter extends RecyclerView.Adapter<NurtureAdapter.NurtureViewHolder>{
    private Context context;
    private List<GoodsBean> data = new ArrayList<>();

    public NurtureAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public NurtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_nurture_or_equipment,null);
        return new NurtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NurtureViewHolder holder, int position) {
        final GoodsBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldGoodsDetailActivity.start(context,bean.getId(), TYPE_NURTURE);
            }
        });
    }


    static class NurtureViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        TextView price;

        public NurtureViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}
