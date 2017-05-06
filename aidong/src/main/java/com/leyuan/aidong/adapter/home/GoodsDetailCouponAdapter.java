package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.ToastGlobal;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情界面优惠劵适配器
 * Created by song on 2016/9/13.
 */
public class GoodsDetailCouponAdapter extends RecyclerView.Adapter<GoodsDetailCouponAdapter.CouponHolder>{
    private Context context;
    private List<CouponBean> data = new ArrayList<>();
    private CouponListener listener;

    public GoodsDetailCouponAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CouponBean> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_detail_coupon,parent,false);
        return new CouponHolder(view);
    }

    @Override
    public void onBindViewHolder(final CouponHolder holder, final int position) {
        final CouponBean bean = data.get(position);
        holder.tvPrice.setText(bean.getDiscount());
        holder.tvUserPrice.setText(String.format(context.getString(R.string.use_price),bean.getMin()));
        if("0".equals(bean.getStatus())){   //未领
            holder.tvGet.setText("点击领取");
            holder.rlCoupon.setBackgroundResource(R.drawable.bg_goods_coupon);
        }else {
            holder.tvGet.setText("已领取");
            holder.rlCoupon.setBackgroundResource(R.drawable.bg_goods_coupon_gray);
        }

        if(FormatUtil.parseInt(bean.getDiscount()) > 99){
            holder.tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.sp_30));
        }else {
            holder.tvPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.sp_40));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("0".equals(bean.getStatus())){
                    if(listener != null){
                        listener.onCouponClick(position);
                    }
                }else {
                    ToastGlobal.showLong("已领取过该优惠券");
                }
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

    public void setListener(CouponListener listener) {
        this.listener = listener;
    }

    public interface CouponListener{
        void onCouponClick(int position);
    }
}
