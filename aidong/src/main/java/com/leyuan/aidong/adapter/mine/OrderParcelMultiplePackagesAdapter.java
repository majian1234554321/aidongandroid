package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ConfirmOrderGoodsAdapter;
import com.leyuan.aidong.entity.ParcelBean;
import com.leyuan.aidong.ui.mine.activity.BarcodeActivity;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.ImageRectUtils;
import com.leyuan.aidong.utils.QRCodeUtil;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;

/**
 * 订单列表中包裹适配器
 * Created by song on 2016/9/8.
 */
public class OrderParcelMultiplePackagesAdapter extends RecyclerView.Adapter<OrderParcelMultiplePackagesAdapter.CartHolder> {
    private final int qrCodeWidth;
    private final int qrCodeHeight;
    private Context context;
    private List<ParcelBean> data = new ArrayList<>();
    private static final String UN_PAID = "pending";          //待付款
    private static final String PAID = "purchased";           //已支付
    private static final String FINISH = "confirmed";         //已确认
    private static final String CLOSE = "canceled";
    private String payStatus;

    public OrderParcelMultiplePackagesAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_parcel, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final ParcelBean bean = data.get(position);
        if (UN_PAID.equals(payStatus) || CLOSE.equals(payStatus)) {
            holder.rlQrCode.setVisibility(View.GONE);
        } else {
            holder.rlQrCode.setVisibility(View.VISIBLE);
            if (PAID.equals(payStatus)) {
                holder.tvQrNum.setText(bean.getId());
                holder.ivCode.setImageBitmap(QRCodeUtil.createBarcode(context, 0xFF000000, bean.getId(),
                        qrCodeWidth, qrCodeHeight, false));
                holder.ivCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BarcodeActivity.start(context, bean.getId(), ImageRectUtils.getDrawableBoundsInView( holder.ivCode));
                    }
                });
            } else {
                holder.tvQrNum.setText(bean.getId());
                holder.tvQrNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvQrNum.setTextColor(Color.parseColor("#ebebeb"));
                holder.ivCode.setImageBitmap(QRCodeUtil.createBarcode(context, 0xFFebebeb, bean.getId(),
                        qrCodeWidth, qrCodeHeight, false));
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
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    class CartHolder extends RecyclerView.ViewHolder {
        TextView tvShopName;
        TextView tvDeliveryType;
        RecyclerView rvShop;

        private RelativeLayout rlQrCode;   //自提条形码
        private TextView tvQrNum;
        private ImageView ivCode;

        public CartHolder(View itemView) {
            super(itemView);
            rlQrCode = (RelativeLayout) itemView.findViewById(R.id.rl_qr_code);
            tvQrNum = (TextView) itemView.findViewById(R.id.tv_qr_num);
            ivCode = (ImageView) itemView.findViewById(R.id.iv_qr);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvDeliveryType = (TextView) itemView.findViewById(R.id.tv_delivery_type);
            rvShop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
        }
    }
}
