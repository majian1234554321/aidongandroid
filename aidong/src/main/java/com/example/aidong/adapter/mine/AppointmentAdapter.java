package com.example.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.AppointmentBean;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.SystemInfoUtils;
import com.example.aidong .widget.dialog.BaseDialog;
import com.example.aidong .widget.dialog.ButtonCancelListener;
import com.example.aidong .widget.dialog.ButtonOkListener;
import com.example.aidong .widget.dialog.DialogDoubleButton;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 预约适配器
 * Created by song on 2016/9/1.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private static final String UN_PAID = "pending";        //待付款
    private static final String UN_JOIN = "purchased";      //待参加
    private static final String JOINED = "signed";          //已参加
    public static final String CLOSE = "canceled";          //已关闭
    private static final String REFUNDING = "refunding";    //退款中
    private static final String REFUNDED = "returned";      //已退款

    private long appointCountdownMill;

    private Context context;
    private List<AppointmentBean> data = new ArrayList<>();
    private AppointmentListener appointmentListener;

    public AppointmentAdapter(Context context) {
        this.context = context;
        appointCountdownMill = SystemInfoUtils.getAppointmentCountdown(context) * 60 * 1000;
    }

    public void setData(List<AppointmentBean> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment_campaign, parent, false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, final int position) {
        final AppointmentBean bean = data.get(position);

        //与订单状态无关
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.cover);
        holder.name.setText(bean.getName());
        holder.address.setText("共" + bean.amount + "张票");
        if (bean.spec_value != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bean.spec_value.length; i++) {
                sb.append(bean.spec_value[i]);
                sb.append(" ");
            }
            holder.tvtime.setText(sb.toString());
        }


        holder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPay_amount())));
        //与订单状态有关
        if (TextUtils.isEmpty(bean.getStatus())) return;
        switch (bean.getStatus()) {
            case UN_PAID:           //待付款
                holder.state.setText(context.getString(R.string.un_paid));
                holder.payTip.setText(context.getString(R.string.need_pay));
                holder.date.setVisibility(View.GONE);
                holder.timerLayout.setVisibility(View.VISIBLE);
                holder.tvCancelPay.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvCancelJoin.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);


                holder.timer.start(DateUtils.getCountdown(bean.getCreated_at(), appointCountdownMill) > appointCountdownMill ? appointCountdownMill : DateUtils.getCountdown(bean.getCreated_at(), appointCountdownMill)
                );
                break;

            case UN_JOIN:           //待参加
                holder.state.setText(context.getString(R.string.appointment_un_joined));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.date.setText("预约码: " + bean.getNo());

                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvCancelJoin.setVisibility(FormatUtil.parseDouble(bean.getPrice()) == 0d
                        ? View.VISIBLE : View.GONE);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvCancelPay.setVisibility(View.GONE);
                break;
            case JOINED:            //已参加
                holder.state.setText(context.getString(R.string.appointment_joined));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.date.setText("预约码: " + bean.getNo());
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelJoin.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvCancelPay.setVisibility(View.GONE);
                break;
            case CLOSE:             //已关闭
                holder.state.setText(context.getString(R.string.order_close));
                holder.payTip.setText(context.getString(R.string.true_pay));
                holder.date.setText("预约码: " + bean.getNo());
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelJoin.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvCancelPay.setVisibility(View.GONE);
                break;
            case REFUNDING:           //退款中
                holder.state.setText(context.getString(R.string.order_refunding));
                holder.date.setText("预约码: " + bean.getNo());
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelJoin.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvCancelPay.setVisibility(View.GONE);
                break;
            case REFUNDED:             //已退款
                holder.state.setText(context.getString(R.string.order_refunded));
                holder.date.setText("预约码: " + bean.getNo());
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelJoin.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.tvCancelPay.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentListener.onPayOrder(bean.getAppointmentType(), bean.getId(), bean.getCover());
            }
        });

        holder.tvCancelJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DialogDoubleButton(context).setContentDesc("确定取消当前预约")
                        .setBtnCancelListener(new ButtonCancelListener() {
                            @Override
                            public void onClick(BaseDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setBtnOkListener(new ButtonOkListener() {
                            @Override
                            public void onClick(BaseDialog dialog) {
                                dialog.dismiss();
                                if (appointmentListener != null) {
                                    appointmentListener.onCancelJoin(position);
                                }
                            }
                        }).show();


            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appointmentListener != null) {
                    appointmentListener.onDeleteOrder(bean.getId());
                }
            }
        });

        holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appointmentListener != null) {
                    appointmentListener.onConfirmJoin(position);
                }
            }
        });

        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appointmentListener != null) {
                    appointmentListener.onPayOrder(bean.getAppointmentType(), bean.getId(), bean.getCover());
                }
            }
        });

        holder.tvCancelPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appointmentListener != null) {
                    appointmentListener.onCancelPay(bean.getId());
                }
            }
        });

        holder.timer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if (appointmentListener != null) {
                    appointmentListener.onCountdownEnd(position);
                }
            }
        });
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
        TextView tvCancelJoin;
        TextView tvCancelPay;
        TextView tvConfirm;
        TextView tvDelete;
        TextView tvPay;
        public TextView tvtime;

        public AppointmentHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tv_state);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            timerLayout = (LinearLayout) itemView.findViewById(R.id.ll_timer);
            timer = (CountdownView) itemView.findViewById(R.id.timer);
            cover = (ImageView) itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            payTip = (TextView) itemView.findViewById(R.id.tv_price_tip);
            tvCancelJoin = (TextView) itemView.findViewById(R.id.tv_cancel_join);
            tvCancelPay = (TextView) itemView.findViewById(R.id.tv_cancel_pay);
            tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
            tvtime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    public void setAppointmentListener(AppointmentListener appointmentListener) {
        this.appointmentListener = appointmentListener;
    }

    public interface AppointmentListener {
        void onPayOrder(String type, String id, String imageUrl);

        void onDeleteOrder(String id);

        void onConfirmJoin(int position);

        void onCancelJoin(int position);

        void onCancelPay(String id);

        void onCountdownEnd(int position);
    }
}
