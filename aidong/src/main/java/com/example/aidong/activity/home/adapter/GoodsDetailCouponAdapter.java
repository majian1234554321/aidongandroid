package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.leyuan.support.entity.CouponBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情界面优惠劵适配器
 * Created by song on 2016/9/13.
 */
public class GoodsDetailCouponAdapter extends RecyclerView.Adapter<GoodsDetailCouponAdapter.CouponHolder>{
    private Context context;
    private List<CouponBean> data = new ArrayList<>();

    public GoodsDetailCouponAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_goods_detail_coupon,null);
        return new CouponHolder(view);
    }

    @Override
    public void onBindViewHolder(final CouponHolder holder, int position) {
        CouponBean bean = data.get(position);
        holder.tvPrice.setText(bean.getDiscount());
        holder.tvUserPrice.setText(String.format(context.getString(R.string.use_price),bean.getMin()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rlCoupon.setBackgroundResource(R.drawable.grey_bg_goods_detail_coupon);
                Toast.makeText(context,"领取成功",Toast.LENGTH_LONG).show();
            }
        });
    }

    class CouponHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlCoupon;
        TextView tvPrice;
        TextView tvUserPrice;
        TextView tvGet;

        public CouponHolder(View itemView) {
            super(itemView);
            rlCoupon = (RelativeLayout)itemView.findViewById(R.id.rl_coupon);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvUserPrice = (TextView) itemView.findViewById(R.id.tv_user_price);
            tvGet = (TextView) itemView.findViewById(R.id.tv_get);
        }
    }
}
