package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.content.Intent;
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
import com.leyuan.aidong.ui.mine.activity.OrderDetailActivity;
import com.leyuan.aidong.entity.OrderBean;
import com.leyuan.aidong.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单适配器
 * Created by song on 2016/9/1.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private static final String UN_PAID = "0";          //待付款
    private static final String UN_DELIVERY = "1";      //待发货
    private static final String DELIVERED = "2";        //已发货
    private static final String FINISH = "3";           //已完成
    private static final String CLOSE = "4";            //已关闭
    private static final String UN_SELF_DELIVERY = "5"; //待自提
    private static final String SELF_DELIVERED = "6";   //已自提


    private Context context;
    private List<OrderBean> data = new ArrayList<>();
    private OrderListener orderListener;

    public OrderAdapter(Context context) {
        this.context = context;
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
        holder.recyclerView.setAdapter(new OrderGoodAdapter(context, bean.getItem()));
        holder.count.setText(String.format(context.getString(R.string.goods_count),bean.getTotal()));
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPay_amount()));

        //与订单状态有关
        if (TextUtils.isEmpty(bean.getStatus())) return;
        switch (bean.getStatus()) {
            case UN_PAID:           //待付款
                holder.state.setText(context.getString(R.string.un_paid));
                holder.timeOrId.setText(bean.getStatus());
                holder.payTip.setText(context.getString(R.string.need_pay));
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                break;
            case UN_DELIVERY:       //待发货
                holder.state.setText(context.getString(R.string.un_delivery));
                holder.timeOrId.setText(bean.getId());
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.tvCancel.setVisibility(View.VISIBLE);
                break;
            case DELIVERED:         //已发货
                holder.state.setText(context.getString(R.string.delivered));
                holder.timeOrId.setText(bean.getId());
                holder.payTip.setText(context.getString(R.string.true_pay));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                        (DensityUtil.dp2px(context,168), LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = DensityUtil.dp2px(context,10);
                holder.tvConfirm.setLayoutParams(params);
                holder.tvExpress.setVisibility(View.VISIBLE);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                break;
            case FINISH:            //已完成
                holder.state.setText(context.getString(R.string.order_finish));
                holder.timeOrId.setText(bean.getId());
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvBuyAgain.setVisibility(View.VISIBLE);
                break;
            case CLOSE:             //已关闭
                holder.state.setText(context.getString(R.string.order_close));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvBuyAgain.setVisibility(View.VISIBLE);
                break;
            case UN_SELF_DELIVERY:  //待自提
                holder.state.setText(context.getString(R.string.un_self_delivery));
                holder.timeOrId.setText(bean.getId());
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.tvCancel.setVisibility(View.VISIBLE);
                break;
            case SELF_DELIVERED:    //已自提
                holder.state.setText(context.getString(R.string.self_delivered));
                holder.timeOrId.setText(bean.getId());
                holder.payTip.setText(context.getString(R.string.true_pay));
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams
                        (DensityUtil.dp2px(context,168), LinearLayout.LayoutParams.WRAP_CONTENT);
                param.leftMargin = 0;
                holder.tvConfirm.setLayoutParams(param);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                holder.tvBuyAgain.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                context.startActivity(intent);
            }
        });

        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onPayOrder();
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

        holder.tvExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onCheckExpressInfo(bean.getId());
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

        holder.tvBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderListener != null){
                    orderListener.onBuyAgain();
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
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView state;                 //订单状态
        TextView timeOrId;              //实际支付时间或者订单号
        RecyclerView recyclerView;      //商品
        TextView count;                 //商品数量
        TextView payTip;                //实付款或者需付款
        TextView price;                 //商品价格
        TextView tvCancel;              //取消订单
        TextView tvPay;                 //立即支付
        TextView tvExpress;             //查看物流
        TextView tvConfirm;           //确认收货
        TextView tvDelete;              //删除订单
        TextView tvBuyAgain;            //再次购买

        public OrderHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.tv_state);
            timeOrId = (TextView) itemView.findViewById(R.id.tv_id_or_time);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_good);
            count = (TextView) itemView.findViewById(R.id.tv_goods_count);
            payTip = (TextView) itemView.findViewById(R.id.tv_pay_tip);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);
            tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
            tvExpress = (TextView) itemView.findViewById(R.id.tv_express);
            tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvBuyAgain = (TextView) itemView.findViewById(R.id.tv_again_buy);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    public void setOrderListener(OrderListener orderListener) {
        this.orderListener = orderListener;
    }

    public interface OrderListener{
        void onPayOrder();
        void onCancelOrder(String id);
        void onDeleteOrder(String id);
        void onConfirmOrder(String id);
        void onCheckExpressInfo(String id);
        void onBuyAgain();
    }
}
