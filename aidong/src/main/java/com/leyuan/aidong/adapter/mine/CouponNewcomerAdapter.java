package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CouponBean;

import java.util.List;

/**
 * 优惠劵适配器
 * Created by song on 2016/8/31.
 */
public class CouponNewcomerAdapter extends RecyclerView.Adapter<CouponNewcomerAdapter.CouponHolder> {

    private Context context;
    private List<CouponBean> data;

    public CouponNewcomerAdapter(Context context) {
        this.context = context;
    }

    public CouponNewcomerAdapter(Context context, List<CouponBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coupon_newcumer, parent, false);
        return new CouponHolder(view);
    }

    @Override
    public void onBindViewHolder(final CouponHolder holder, int position) {
        CouponBean bean = data.get(position);

        //与优惠劵类型无关
        holder.tvName.setText(bean.getName());
        holder.tvRmbFlag.setText(bean.getDiscountSign());
        holder.tvCouponPrice.setText(bean.getDiscountNumber());
//        if (TextUtils.equals(bean.getMin(), Constant.NEGATIVE_ONE)) {
//            holder.tvUseMoney.setText("指定支付价格");
//        } else {
//            holder.tvUseMoney.setText(String.format(context.getString(R.string.user_condition), bean.getMin()));
//        }
        holder.tvUseMoney.setText(bean.getCouponDesc());
//        holder.tvCouponType.setText(bean.getCoupon_type());
        holder.tvProduce.setText(bean.getLimitCategory());
    }

    class CouponHolder extends RecyclerView.ViewHolder {
        TextView tvRmbFlag;
        TextView tvCouponPrice;
        TextView tvDiscountFlag;
        TextView tvName;
        TextView tvProduce;
        TextView tvUseMoney;
//        TextView tvCouponType;

        public CouponHolder(View itemView) {
            super(itemView);
            tvRmbFlag = (TextView) itemView.findViewById(R.id.tv_rmb_flag);
            tvCouponPrice = (TextView) itemView.findViewById(R.id.tv_coupon_price);
            tvDiscountFlag = (TextView) itemView.findViewById(R.id.tv_discount_flag);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvProduce = (TextView) itemView.findViewById(R.id.tv_produce_condition);
            tvUseMoney = (TextView) itemView.findViewById(R.id.tv_use_money);
//            tvCouponType = (TextView) itemView.findViewById(R.id.tv_coupon_type);
        }
    }
}
