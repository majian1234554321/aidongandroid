package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.activity.mine.OrderDetailActivity;
import com.leyuan.support.entity.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单适配器
 * Created by song on 2016/9/1.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private static final String UN_PAID = "0";          //待付款
    private static final String UN_DELIVERY = "1";      //待发货
    private static final String DELIVERIED = "2";       //已发货
    private static final String FINISH = "3";           //已完成
    private static final String CLOSE = "4";            //已关闭
    private static final String UN_SELF_DELIVETY = "5"; //待自提
    private static final String SELF_DELIVETIED = "6";  //已自提


    private Context context;
    private List<OrderBean> data = new ArrayList<>();

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
        View view = View.inflate(context, R.layout.item_order, null);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        OrderBean bean = data.get(position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new OrderGoodAdapter(context, bean.getItem()));

        if (UN_PAID.equals(bean.getStatus())) {

            holder.payTip.setText(context.getString(R.string.need_pay));
        } else {

            holder.payTip.setText(context.getString(R.string.true_pay));
        }

        holder.state.setText(bean.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView state;                 //订单状态
        TextView timeOrNum;             //实际支付时间或者订单号
        RecyclerView recyclerView;      //商品
        TextView count;                 //商品数量
        TextView payTip;                //实付款或者需付款
        TextView price;                 //商品价格
        TextView leftButton;            //底部左按钮
        TextView rightButton;           //底部右按钮

        public OrderHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.tv_state);
            timeOrNum = (TextView) itemView.findViewById(R.id.tv_id_or_time);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_good);
            count = (TextView) itemView.findViewById(R.id.tv_count);
            payTip = (TextView) itemView.findViewById(R.id.tv_pay_tip);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            leftButton = (TextView) itemView.findViewById(R.id.tv_left_button);
            rightButton = (TextView) itemView.findViewById(R.id.tv_right_button);
            itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }
}
