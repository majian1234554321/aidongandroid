package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.CouponModel;
import com.leyuan.aidong.ui.mvp.model.impl.CouponModelImpl;
import com.leyuan.aidong.utils.Constant;

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
        if(data != null){
            this.data = data;
        }
        notifyDataSetChanged();
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
    public void onBindViewHolder(final CouponHolder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("0".equals(bean.getStatus())){
                    CouponModel model = new CouponModelImpl();
                    model.obtainCoupon(new ProgressSubscriber<BaseBean>(context) {
                        @Override
                        public void onNext(BaseBean baseBean) {
                            if (baseBean.getStatus() == Constant.OK) {
                                notifyDataSetChanged();
                                Toast.makeText(context, "领取成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "领取失败" + baseBean.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, bean.getId());
                }else {
                    Toast.makeText(context,"已领取过该优惠券",Toast.LENGTH_LONG).show();
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
}
