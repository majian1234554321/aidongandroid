package com.example.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.ConfirmOrderGoodsAdapter;
import com.example.aidong .entity.ParcelBean;
import com.example.aidong .ui.mine.activity.BarcodeActivity;
import com.example.aidong .utils.DensityUtil;
import com.example.aidong .utils.ImageRectUtils;
import com.example.aidong .utils.QRCodeUtil;
import com.example.aidong .widget.ExtendTextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong .utils.Constant.DELIVERY_EXPRESS;

/**
 * 订单列表中自提包裹适配器
 * Created by song on 2016/9/8.
 */
public class OrderSelfDeliveryAdapter extends RecyclerView.Adapter<OrderSelfDeliveryAdapter.CartHolder> {
    private final int qrCodeWidth;
    private final int qrCodeHeight;
    private Context context;
    private List<ParcelBean> data = new ArrayList<>();
    private static final String UN_PAID = "pending";          //待付款
    private static final String PAID = "purchased";           //已支付
    private static final String FINISH = "confirmed";         //已确认
    private static final String CLOSE = "canceled";
    private String payStatus;
    private boolean isFood;
    private boolean isVirtual;

    public OrderSelfDeliveryAdapter(Context context) {
        this.context = context;
        qrCodeWidth = DensityUtil.dp2px(context, 294);
        qrCodeHeight = DensityUtil.dp2px(context, 73);
    }

    public void setData(List<ParcelBean> data) {
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
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_self_delivery, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final ParcelBean bean = data.get(position);
        if (isFood) {
            holder.tv_delivery_time.setLeftTextContent(context.getResources().getString(R.string.send_the_meal_time));
            holder.tv_delivery_time.setRightContent(bean.getPickUpDate() + " " + bean.getPick_up_period());
        } else {
            holder.tv_delivery_time.setLeftTextContent(context.getResources().getString(R.string.self_delivery_time));
            holder.tv_delivery_time.setRightContent(bean.getPickUpDate());
        }

        if (UN_PAID.equals(payStatus) || CLOSE.equals(payStatus)) {
            holder.rlQrCode.setVisibility(View.GONE);
        } else {
            holder.rlQrCode.setVisibility(View.VISIBLE);

            if (bean.isVerified()) {
                holder.tvQrNum.setText(bean.getVerify_no());
                holder.tvQrNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvQrNum.setTextColor(Color.parseColor("#ebebeb"));
                if (bean.getVerify_no() != null) {
                    holder.ivCode.setImageBitmap(QRCodeUtil.createBarcode(context, 0xFFebebeb, bean.getVerify_no(),
                            qrCodeWidth, qrCodeHeight, false));
                }

            } else {
                holder.tvQrNum.setText(bean.getVerify_no());
                if (bean.getVerify_no() != null) {
                    holder.ivCode.setImageBitmap(QRCodeUtil.createBarcode(context, 0xFF000000, bean.getVerify_no(),
                            qrCodeWidth, qrCodeHeight, false));
                }
                holder.ivCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BarcodeActivity.start(context, bean.getVerify_no(), ImageRectUtils.getDrawableBoundsInView(holder.ivCode));
                    }
                });
            }

        }

        if (DELIVERY_EXPRESS.equals(bean.getPickUpWay())) {
            holder.tvShopName.setText("仓库发货");
        } else {
            holder.tvShopName.setText(bean.getAddress());
        }
        String type = DELIVERY_EXPRESS.equals(bean.getPickUpWay()) ? "快递" : "自提";
        holder.tvDeliveryType.setText(type);
        holder.rvShop.setLayoutManager(new LinearLayoutManager(context));
        final ConfirmOrderGoodsAdapter goodsAdapter = new ConfirmOrderGoodsAdapter(context);
        holder.rvShop.setAdapter(goodsAdapter);
        goodsAdapter.setData(bean.getItem());

        if(isVirtual){
            holder.tv_delivery_time.setVisibility(View.GONE);
            holder.rlQrCode.setVisibility(View.GONE);
            holder.tvDeliveryType.setVisibility(View.GONE);
        }
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }

    public void setIsVirtual(boolean virtual) {
        this.isVirtual = virtual;
        notifyDataSetChanged();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        TextView tvShopName;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        private RelativeLayout rlQrCode;   //自提条形码
        private TextView tvQrNum;
        private ImageView ivCode;
        ExtendTextView tv_delivery_time;
        LinearLayout layout_delivery_type;

        public CartHolder(View itemView) {
            super(itemView);
            tv_delivery_time = (ExtendTextView) itemView.findViewById(R.id.tv_delivery_time);
            rlQrCode = (RelativeLayout) itemView.findViewById(R.id.rl_qr_code);
            tvQrNum = (TextView) itemView.findViewById(R.id.tv_qr_num);
            ivCode = (ImageView) itemView.findViewById(R.id.iv_qr);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
            layout_delivery_type = (LinearLayout)itemView.findViewById(R.id.layout_delivery_type);
        }
    }
}
