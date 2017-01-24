package com.leyuan.aidong.ui.activity.mine.adapter;

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

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵适配器
 * Created by song on 2016/8/31.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponHolder> {
    private static final String VALID = "valid";
    private static final String USED = "used";
    private static final String EXPIRED = "expired";

    private String type;
    private Context context;
    private List<CouponBean> data = new ArrayList<>();

    public CouponAdapter(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coupon,parent,false);
        return new CouponHolder(view);
    }

    @Override
    public void onBindViewHolder(final CouponHolder holder, int position) {
        CouponBean bean = data.get(position);

        //与优惠劵类型无关
        holder.tvName.setText(bean.getName());
        holder.tvCouponPrice.setText(bean.getDiscount());
        holder.tvUseMoney.setText(String.format(context.getString(R.string.user_condition),bean.getMin()));
        if(!TextUtils.isEmpty(bean.getIntroduce())){
            holder.tvDesc.setText(Html.fromHtml(bean.getIntroduce()));
        }

        //与优惠券类型有关 折扣劵,满减劵
        holder.tvRmbFlag.setVisibility(bean.getType().equals("0") ? View.VISIBLE : View.GONE);
        holder.tvDiscountFlag.setVisibility(bean.getType().equals("0") ? View.GONE : View.VISIBLE);

        //与优惠券类型有关 品类券,通用券,专用券
        if(bean.getLimitCategory().equals("0")){
            holder.tvCouponType.setText("通用劵");
            holder.tvProduce.setText("全场通用");
        }else {
            if(bean.getLimitExtId().equals("0")){
                holder.tvCouponType.setText("品类劵");
                holder.tvProduce.setText("指定品类产品可用");
            }else {
                holder.tvCouponType.setText("专用劵");
                holder.tvProduce.setText("指定特殊产品可用");
            }
        }

        //与优惠劵使用状态有关 可使用,已使用,已过期
        if(TextUtils.isEmpty(type)) return;
        switch (type){
            case VALID:
                if(TextUtils.isEmpty(bean.getStartDate())){
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_expired),bean.getEndDate()));
                } else {
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_time),
                            bean.getStartDate(),bean.getEndDate()));
                }
                holder.itemView.setBackgroundResource(R.drawable.bg_coupon_fold);
                break;
            case USED:
                holder.tvTime.setText(String.format(context.getString(R.string.coupon_used),bean.getEndDate()));
                holder.itemView.setBackgroundResource(R.drawable.bg_coupon_fold_gray);
                break;
            case EXPIRED:
                if(TextUtils.isEmpty(bean.getStartDate())){
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_expired),bean.getEndDate()));
                } else {
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_time),
                            bean.getStartDate(),bean.getEndDate()));
                }
                holder.itemView.setBackgroundResource(R.drawable.bg_coupon_fold_gray);
                break;
            default:
                break;
        }

        holder.couponTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.tvDesc.getVisibility() == View.VISIBLE){
                    holder.tvDesc.setVisibility(View.GONE);
                    holder.ivArrow.setImageResource(R.drawable.icon_arrow_up_coupon);

                }else {
                    holder.tvDesc.setVisibility(View.VISIBLE);
                    holder.ivArrow.setImageResource(R.drawable.icon_arrow_down_coupon);
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
        ImageView ivArrow;
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
        }
    }
}
