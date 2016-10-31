package com.leyuan.aidong.ui.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    private boolean isCouponDescShow = false;
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
        View view = View.inflate(context,R.layout.item_coupon,null);
        return new CouponHolder(view);
    }

    @Override
    public void onBindViewHolder(final CouponHolder holder, int position) {
        CouponBean bean = data.get(position);

        //与优惠劵类型无关
        holder.tvCouponPrice.setText(bean.getDiscount());
        holder.tvUseMoney.setText(String.format(context.getString(R.string.user_condition),bean.getMin()));
        holder.tvCouponType.setText(bean.getName());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
       // holder.recyclerView.setAdapter(new CouponDescAdapter(bean.getDesc()));
        holder.couponTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.recyclerView.setVisibility(isCouponDescShow ? View.INVISIBLE : View.VISIBLE);
                holder.ivArrow.setImageResource(isCouponDescShow ? R.drawable.icon_arrow_up_coupon :
                        R.drawable.icon_arrow_down_coupon);
                isCouponDescShow = !isCouponDescShow;
            }
        });


        //与优惠劵类型有关 折扣劵,满减劵


        //与优惠劵使用状态有关 可使用,已使用,已过期
        if(TextUtils.isEmpty(type)) return;
        switch (type){
            case VALID:
                if(TextUtils.isEmpty(bean.getStart_date())){
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_expired),bean.getEnd_date()));
                } else {
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_time),
                            bean.getStart_date(),bean.getEnd_date()));
                }
                holder.itemView.setBackgroundResource(R.drawable.bg_coupon_fold);
                break;
            case USED:
                holder.tvTime.setText(String.format(context.getString(R.string.coupon_used),bean.getEnd_date()));
                holder.itemView.setBackgroundResource(R.drawable.bg_coupon_fold_gray);
                break;
            case EXPIRED:
                if(TextUtils.isEmpty(bean.getStart_date())){
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_expired),bean.getEnd_date()));
                } else {
                    holder.tvTime.setText(String.format(context.getString(R.string.coupon_time),
                            bean.getStart_date(),bean.getEnd_date()));
                }
                holder.itemView.setBackgroundResource(R.drawable.bg_coupon_fold_gray);
                break;
            default:
                break;
        }
    }

    class CouponHolder extends RecyclerView.ViewHolder {
        TextView tvRmbFlag;
        TextView tvCouponPrice;
        TextView tvDiscountFlag;
        TextView tvUseMoney;
        LinearLayout couponTypeLayout;
        TextView tvCouponType;
        ImageView ivArrow;
        TextView tvTime;
        RecyclerView recyclerView;

        public CouponHolder(View itemView) {
            super(itemView);
            tvRmbFlag = (TextView) itemView.findViewById(R.id.tv_rmb_flag);
            tvCouponPrice = (TextView) itemView.findViewById(R.id.tv_coupon_price);
            tvDiscountFlag = (TextView) itemView.findViewById(R.id.tv_discount_flag);
            tvUseMoney = (TextView) itemView.findViewById(R.id.tv_use_money);
            couponTypeLayout = (LinearLayout) itemView.findViewById(R.id.ll_coupon_type);
            tvCouponType = (TextView) itemView.findViewById(R.id.tv_coupon_type);
            ivArrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.rv_coupon_desc);
        }
    }
}
