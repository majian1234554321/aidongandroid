package com.example.aidong.adapter.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.course.CourseAppointBean;
import com.example.aidong .ui.mine.activity.AppointDetailCourseNewActivity;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.SystemInfoUtils;
import com.example.aidong .widget.dialog.BaseDialog;
import com.example.aidong .widget.dialog.ButtonCancelListener;
import com.example.aidong .widget.dialog.ButtonOkListener;
import com.example.aidong .widget.dialog.DialogDoubleButton;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 预约适配器
 * Created by song on 2016/9/1.
 */
public class AppointmentCourseListAdapter extends RecyclerView.Adapter<AppointmentCourseListAdapter.AppointmentHolder> {


    private final String appointType;
    private long appointCountdownMill;

    private Context context;
    private List<CourseAppointBean> data;
    private AppointmentListener appointmentListener;

    public AppointmentCourseListAdapter(Context context, String type) {
        this.context = context;
        appointCountdownMill = SystemInfoUtils.getAppointmentCountdown(context) * 60 * 1000;
        this.appointType = type;
    }

    public void setData(List<CourseAppointBean> data) {
        this.data = data;
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment_course, parent, false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppointmentHolder holder, final int position) {
        final CourseAppointBean bean = data.get(position);

        //与订单状态无关
        GlideLoader.getInstance().displayImage(bean.getTimetable().getCover(), holder.cover);
        holder.name.setText(bean.getTimetable().getName());
        holder.tv_time.setText(bean.getTimetable().getClass_time());
        holder.address.setText(bean.getTimetable().getClass_store() + "-" + bean.getTimetable().getClass_room());
        holder.price.setText(String.format(context.getString(R.string.rmb_price_double), FormatUtil.parseDouble(bean.getPay_amount())));

        //与订单状态有关
//        if(TextUtils.equals(appointType,CourseAppointBean.QUEUED)){
//
//        }else {
//
//        }

        switch (bean.getCourseFinalStatus()) {
            case CourseAppointBean.queued:
                holder.state.setText(context.getString(R.string.queueing));
                holder.txt_appoint_or_queue_number.setVisibility(View.VISIBLE);
                holder.txt_appoint_or_queue_number.setText("当前排队：第" + bean.getPosition() + "位");
                holder.timerLayout.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));

                holder.tvCancelAppoint.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);

                holder.tvCancelQueue.setVisibility(View.VISIBLE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.GONE);
                break;

            case CourseAppointBean.pending:

                holder.state.setText(context.getString(R.string.un_paid));
                holder.txt_appoint_or_queue_number.setVisibility(View.GONE);

                holder.timerLayout.setVisibility(View.VISIBLE);
                holder.timer.start(DateUtils.getCountdown(bean.getCreated_at(), appointCountdownMill));

                holder.payTip.setText(context.getString(R.string.need_pay));
                holder.tvCancelAppoint.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);

                holder.tvCancelQueue.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.GONE);

                break;

            case CourseAppointBean.paid:
                //逻辑与已预约是一样的
            case CourseAppointBean.appointed:
                holder.state.setText(context.getString(R.string.appointed));
                holder.txt_appoint_or_queue_number.setVisibility(View.VISIBLE);
                holder.txt_appoint_or_queue_number.setText("预约码：" + bean.getId());
                holder.timerLayout.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));

                holder.tvCancelAppoint.setVisibility(View.GONE);
                holder.tvPay.setVisibility(View.GONE);

                holder.tvCancelQueue.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.VISIBLE);

                break;

            case CourseAppointBean.canceled:

                holder.state.setText(context.getString(R.string.canceled));
                holder.txt_appoint_or_queue_number.setVisibility(View.VISIBLE);
                holder.txt_appoint_or_queue_number.setText("预约码：" + bean.getId());
                holder.timerLayout.setVisibility(View.GONE);

                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelQueue.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.GONE);
                holder.tvCancelAppoint.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));
                break;


            case CourseAppointBean.absent:
                holder.state.setText(context.getString(R.string.absent));
                holder.txt_appoint_or_queue_number.setVisibility(View.VISIBLE);
                holder.txt_appoint_or_queue_number.setText("预约码：" + bean.getId());
                holder.timerLayout.setVisibility(View.GONE);

                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelQueue.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.GONE);
                holder.tvCancelAppoint.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));


                break;
            case CourseAppointBean.signed:
                holder.state.setText(context.getString(R.string.signed));
                holder.txt_appoint_or_queue_number.setVisibility(View.VISIBLE);
                holder.txt_appoint_or_queue_number.setText("预约码：" + bean.getId());
                holder.timerLayout.setVisibility(View.GONE);

                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelQueue.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.GONE);
                holder.tvCancelAppoint.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));

                break;
            case CourseAppointBean.suspended:

                holder.state.setText(context.getString(R.string.suspended));
                holder.txt_appoint_or_queue_number.setVisibility(View.VISIBLE);
                holder.txt_appoint_or_queue_number.setText("预约码：" + bean.getId());
                holder.timerLayout.setVisibility(View.GONE);

                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancelQueue.setVisibility(View.GONE);
                holder.tvSignImmedialtely.setVisibility(View.GONE);
                holder.tvCancelAppoint.setVisibility(View.GONE);
                holder.payTip.setText(context.getString(R.string.true_pay));


                break;


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (appointmentListener != null) {
//                    appointmentListener.onPayOrder("course", bean.getId());
//                }

                AppointDetailCourseNewActivity.appointStart(context, bean.getId());
            }
        });

        holder.tvCancelQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DialogDoubleButton(context).setContentDesc("确定"+holder.tvCancelQueue.getText().toString().trim())
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
                                    appointmentListener.onCancelQueue(position);
                                }
                            }
                        }).show();


            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DialogDoubleButton(context).setContentDesc("确定"+holder.tvDelete.getText().toString().trim() + "?")
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
                                    appointmentListener.onDeleteOrder(bean.getId());
                                }
                            }
                        }).show();


            }
        });

        holder.tvSignImmedialtely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppointDetailCourseNewActivity.appointStart(context, bean.getId());
                if (appointmentListener != null) {
                    appointmentListener.onSignImmedialtely(position);
                }
            }
        });

        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointDetailCourseNewActivity.appointStart(context, bean.getId());
                if (appointmentListener != null) {
                    appointmentListener.onPayOrder("course", bean.getId());
                }
            }
        });

        holder.tvCancelAppoint.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {





                new DialogDoubleButton(context).setContentDesc("确定"+holder.tvCancelAppoint.getText().toString().trim() + "?")
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
                                    appointmentListener.onCancelAppoint(bean.getId());
                                }
                            }
                        }).show();




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
        TextView txt_appoint_or_queue_number;
        LinearLayout timerLayout;
        CountdownView timer;
        ImageView cover;
        TextView name;
        TextView address;
        TextView payTip;
        TextView price;
        TextView tvCancelQueue;
        TextView tvCancelAppoint;
        TextView tvSignImmedialtely;
        TextView tvDelete;
        TextView tvPay;
        TextView tv_time;

        public AppointmentHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tv_state);
            txt_appoint_or_queue_number = (TextView) itemView.findViewById(R.id.tv_date);
            timerLayout = (LinearLayout) itemView.findViewById(R.id.ll_timer);
            timer = (CountdownView) itemView.findViewById(R.id.timer);
            cover = (ImageView) itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

            price = (TextView) itemView.findViewById(R.id.tv_price);
            payTip = (TextView) itemView.findViewById(R.id.tv_price_tip);
            tvCancelQueue = (TextView) itemView.findViewById(R.id.tv_cancel_join);
            tvCancelAppoint = (TextView) itemView.findViewById(R.id.tv_cancel_pay);
            tvSignImmedialtely = (TextView) itemView.findViewById(R.id.tv_confirm);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
        }
    }

    public void setAppointmentListener(AppointmentListener appointmentListener) {
        this.appointmentListener = appointmentListener;
    }

    public interface AppointmentListener {
        void onPayOrder(String type, String id);

        void onDeleteOrder(String id);

        void onSignImmedialtely(int position);

        void onCancelQueue(int position);

        void onCancelAppoint(String id);

        void onCountdownEnd(int position);
    }
}
