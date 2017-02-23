package com.leyuan.aidong.adapter.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵说明适配器
 * Created by song on 2016/9/20.
 */
public class CouponDescAdapter extends RecyclerView.Adapter<CouponDescAdapter.TextHolder>{
    private List<String> data = new ArrayList<>();

    public CouponDescAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_coupon_desc,null);
        return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(TextHolder holder, int position) {
        String desc = data.get(position);
        holder.desc.setText(desc);
    }

    class TextHolder extends RecyclerView.ViewHolder{
        TextView desc;
        public TextHolder(View itemView) {
            super(itemView);
            desc = (TextView)itemView.findViewById(R.id.tv_desc);
        }
    }
}
