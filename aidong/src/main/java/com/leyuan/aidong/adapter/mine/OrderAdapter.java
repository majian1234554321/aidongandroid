package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ConfirmOrderGoodsAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.OrderBean;
import com.leyuan.aidong.entity.ParcelBean;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 订单适配器
 * Created by song on 2016/9/1.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private static final String UN_PAID = "pending";            //待付款
    private static final String PAID = "purchased";             //已支付
    private static final String FINISHED = "confirmed";         //已完成
    private static final String CLOSED = "canceled";            //已关闭
    private long orderCountdownMill;

    private Context context;
    private List<OrderBean> data = new ArrayList<>();
    private OrderListener orderListener;

    public OrderAdapter(Context context) {
        this.context = context;
        orderCountdownMill = SystemInfoUtils.getOrderCountdown(context) * 60 * 1000;
    }

    public void setData(List<OrderBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        final OrderBean bean = data.get(position);

        //与订单状态无关
        int count = 0;
        ArrayList<GoodsBean> goodsList = new ArrayList<>();
        ArrayList<ParcelBean> parcel = bean.getParcel();
        if(parcel != null && !parcel.isEmpty()){
            for (ParcelBean parcelBean : parcel) {
                goodsList.addAll(parcelBean.getItem());
                for (GoodsBean goodsBean : parcelBean.getItem()) {
                    count += FormatUtil.parseInt(goodsBean.getAmount());
                }
            }
        }
        ConfirmOrderGoodsAdapter goodAdapter = new ConfirmOrderGoodsAdapter(context);
        holder.recyclerView.setAdapter(goodAdapter);
        goodAdapter.setData(goodsList);
        holder.count.setText(String.format(context.getString(R.string.goods_count),count));
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPayAmount())));
        holder.tvOrderId.setText(String.format(context.getString(R.string.order_no),bean.getId()));
        holder.timer.start(DateUtils.getCountdown(bean.getCreated_at(), orderCountdownMill));

        //与订单状态有关
        if (TextUtils.isEmpty(bean.getStatus())) return;
        switch (bean.getStatus()) {
            case UN_PAID:           //待付款
                holder.state.setText(context.getString(R.string.un_paid));
                holder.tvOrderId.setVisibility(View.GONE);
                holder.timerLayout.setVisibility(View.VISIBLE);
                holder.payTip.setText(context.getString(R.string.need_pay));
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvReBuy.setVisibility(View.GONE);
                break;
            case PAID:              //已付款
                holder.state.setText(context.getString(R.string.paid));
                holder.tvOrderId.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                break;
            case FINISHED:            //已完成
                holder.state.setText(context.getString(R.string.order_finish));
                holder.tvOrderId.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                break;
            case CLOSED:              //已关闭
                holder.state.setText(context.getString(R.string.order_close));
                holder.tvOrderId.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvReBuy.setVisibility(View.VISIBLE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onPayOrder(bean.getId());
                }
            }
        });

        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onPayOrder(bean.getId());
                }
            }
        });

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onCancelOrder(bean.getId());
                }
            }
        });

        holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onConfirmOrder(bean.getId());
                }
            }
        });

        holder.tvReBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onBuyAgain(bean.getId());
                }
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onDeleteOrder(bean.getId());
                }
            }
        });

        holder.timer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if(orderListener != null){
                    orderListener.onRefreshOrderStatus();
                }
            }
        });
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView state;                 //订单状态
        TextView tvOrderId;             //订单号
        LinearLayout timerLayout;       //剩余支付时间
        CountdownView timer;    //剩余支付时间
        RecyclerView recyclerView;      //商品
        TextView count;                 //商品数量
        TextView payTip;                //实付款或者需付款
        TextView price;                 //商品价格
        TextView tvCancel;              //取消订单
        TextView tvPay;                 //立即支付
        TextView tvConfirm;             //确认收货
        TextView tvDelete;              //删除订单
        TextView tvReBuy;               //再次购买

        public OrderHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.tv_state);
            tvOrderId = (TextView) itemView.findViewById(R.id.tv_order_id);
            timerLayout = (LinearLayout) itemView.findViewById(R.id.ll_timer);
            timer = (CountdownView) itemView.findViewById(R.id.timer);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_good);
            count = (TextView) itemView.findViewById(R.id.tv_goods_count);
            payTip = (TextView) itemView.findViewById(R.id.tv_pay_tip);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel_join);
            tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
            tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvReBuy = (TextView) itemView.findViewById(R.id.tv_rebuy);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    public void setOrderListener(OrderListener orderListener) {
        this.orderListener = orderListener;
    }

    public interface OrderListener{
        void onPayOrder(String id);
        void onCancelOrder(String id);
        void onDeleteOrder(String id);
        void onConfirmOrder(String id);
        void onBuyAgain(String id);
        void onRefreshOrderStatus();
    }
}
