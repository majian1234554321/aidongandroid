package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择优惠劵适配器
 * Created by song on 2016/8/31.
 */
public class SelectCouponAdapter extends RecyclerView.Adapter<SelectCouponAdapter.CouponHolder> {
    private Context context;
    private List<CouponBean> data = new ArrayList<>();
    private CouponListener couponListener;
    private String selectedCouponId;


    public SelectCouponAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CouponBean> data,String selectedCouponId) {
        if (data != null) {
            this.data = data;
            this.selectedCouponId = selectedCouponId;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_coupon, parent, false);
        return new CouponHolder(view);
    }

    @Override
    public void onBindViewHolder(final CouponHolder holder, final int position) {
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

        if (!TextUtils.isEmpty(bean.getIntroduce())) {
            holder.tvDesc.setText(Html.fromHtml(bean.getIntroduce()));
        }

        holder.tvCouponType.setText(bean.getCoupon_type());
        holder.tvProduce.setText(bean.getLimitCategory());

        if(TextUtils.equals( bean.getUser_coupon_id(),selectedCouponId)){
            Logger.i("coupon","TextUtils.equals selectedUserCouponId = " + selectedCouponId+", getUser_coupon_id = " + bean.getUser_coupon_id());
            holder.itemView.setBackgroundResource(R.drawable.bg_coupon_red_fold);
            holder.img_coupon_selected.setVisibility(View.VISIBLE);
        }else {
            holder.itemView.setBackgroundResource(R.drawable.bg_coupon_black_fold);
            holder.img_coupon_selected.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(bean.getStartDate())) {
            holder.tvTime.setText(String.format(context.getString(R.string.coupon_expired),
                    bean.getEndDate()));
        } else {
            holder.tvTime.setText(String.format(context.getString(R.string.coupon_time),
                    bean.getStartDate(), bean.getEndDate()));
        }

        holder.couponTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvDesc.getVisibility() == View.VISIBLE) {
                    holder.tvDesc.setVisibility(View.GONE);
                    holder.ivArrow.setImageResource(R.drawable.icon_arrow_down_coupon);
                } else {
                    holder.tvDesc.setVisibility(View.VISIBLE);
                    holder.ivArrow.setImageResource(R.drawable.icon_arrow_up_coupon);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (couponListener != null) {
                    couponListener.onCouponClicked(position);
                }
            }
        });
    }

    class CouponHolder extends RecyclerView.ViewHolder {
        TextView tvRmbFlag;
        TextView tvCouponPrice;
        TextView tvDiscountFlag;
        TextView tvName;
        TextView tvProduce;
        TextView tvUseMoney;
        LinearLayout couponTypeLayout;
        TextView tvCouponType;
        ImageView ivArrow,img_coupon_selected;
        TextView tvTime;
        TextView tvDesc;

        public CouponHolder(View itemView) {
            super(itemView);
            tvRmbFlag = (TextView) itemView.findViewById(R.id.tv_rmb_flag);
            tvCouponPrice = (TextView) itemView.findViewById(R.id.tv_coupon_price);
            tvDiscountFlag = (TextView) itemView.findViewById(R.id.tv_discount_flag);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvProduce = (TextView) itemView.findViewById(R.id.tv_produce_condition);
            tvUseMoney = (TextView) itemView.findViewById(R.id.tv_use_money);
            couponTypeLayout = (LinearLayout) itemView.findViewById(R.id.ll_coupon_type);
            tvCouponType = (TextView) itemView.findViewById(R.id.tv_coupon_type);
            ivArrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_coupon_desc);
            img_coupon_selected = (ImageView) itemView.findViewById(R.id.img_coupon_selected);
        }
    }

    public void setCouponListener(CouponListener couponListener) {
        this.couponListener = couponListener;
    }

    public interface CouponListener {
        void onCouponClicked(int position);
    }
}
