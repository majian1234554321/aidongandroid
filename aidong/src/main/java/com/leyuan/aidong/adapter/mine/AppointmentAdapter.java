package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AppointmentBean;
import com.leyuan.aidong.ui.mine.activity.AppointCampaignDetailActivity;
import com.leyuan.aidong.ui.mine.activity.AppointCourseDetailActivity;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 预约适配器
 * Created by song on 2016/9/1.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>
        implements View.OnClickListener{
    private static final String UN_PAID = "pending";         //待付款
    private static final String UN_JOIN= "purchased";        //待参加
    private static final String JOINED = "signed";           //已参加
    private static final String CLOSE = "canceled";          //已关闭
    private static final String REFUNDING = "4";             //退款中
    private static final String REFUNDED = "5";              //已退款

    private Context context;
    private List<AppointmentBean> data = new ArrayList<>();

    public AppointmentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AppointmentBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment,parent,false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, int position) {
        final AppointmentBean bean = data.get(position);

        //与订单状态无关
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getSubName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
       // holder.timer.start(Long.parseLong(bean.getLittleTime()) * 1000);

        //与订单状态有关
        if (TextUtils.isEmpty(bean.getStatus())) return;
        switch (bean.getStatus()) {
            case UN_PAID:           //待付款
                holder.state.setText(context.getString(R.string.un_paid));
                holder.payTip.setText(context.getString(R.string.need_pay));
                holder.date.setVisibility(View.GONE);
                holder.timerLayout.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case UN_JOIN:           //待参加
                holder.state.setText(context.getString(R.string.appointment_un_joined));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(), bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(FormatUtil.parseDouble(bean.getPayAmount()) == 0 ?
                        View.VISIBLE : View.GONE);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                break;
            case JOINED:            //已参加
                holder.state.setText(context.getString(R.string.appointment_joined));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(), bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case CLOSE:             //已关闭
                holder.state.setText(context.getString(R.string.order_close));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(), bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case REFUNDING:           //退款中
                holder.state.setText(context.getString(R.string.order_refunding));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(), bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case REFUNDED:             //已退款
                holder.state.setText(context.getString(R.string.order_refunded));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(), bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        holder.tvCancel.setOnClickListener(this);
        holder.tvPay.setOnClickListener(this);
        holder.tvDelete.setOnClickListener(this);
        holder.tvConfirm.setOnClickListener(this);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("course".equals(bean.getAppointmentType())){
                    AppointCourseDetailActivity.start(context,bean.getId());
                }else {
                    AppointCampaignDetailActivity.start(context,bean.getId());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                break;
            case R.id.tv_confirm:
                break;
            case R.id.tv_delete:
                break;
            case R.id.tv_pay:
                break;
            default:
                break;
        }
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {
        TextView state;
        TextView date;
        LinearLayout timerLayout;
        CountdownView timer;
        ImageView cover;
        TextView name;
        TextView address;
        TextView payTip;
        TextView price;
        TextView tvCancel;
        TextView tvConfirm;
        TextView tvDelete;
        TextView tvPay;

        public AppointmentHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tv_state);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            timerLayout = (LinearLayout) itemView.findViewById(R.id.ll_timer);
            timer = (CountdownView)itemView.findViewById(R.id.timer);
            cover = (ImageView) itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            payTip = (TextView) itemView.findViewById(R.id.tv_price_tip);
            tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);
            tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
        }
    }

    public interface OrderHandleListener{
        void onPayOrder();
        void onDeleteOrder();
        void onConfirmJoin();
        void onCancelJoin();
    }
}
