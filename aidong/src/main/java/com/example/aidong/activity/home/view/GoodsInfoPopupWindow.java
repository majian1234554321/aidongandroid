package com.example.aidong.activity.home.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.view.BasePopupWindow;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
public class GoodsInfoPopupWindow extends BasePopupWindow implements View.OnClickListener{
    private Context context;

    private ImageView ivCancel;
    private TextView tvGoodName;
    private TextView tvGoodsPrice;
    private SimpleDraweeView dvGoodsCover;

    private LinearLayout tasteLayout;
    private RecyclerView recyclerView;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;

    private TextView tvConfirm;
    private TextView tvAdd;
    private TextView tvBuy;

    private boolean isConfirmDeliveryWay = false;   //是否确认配送方式

    public GoodsInfoPopupWindow(Context context,boolean isConfirmDeliveryWay) {
        super(context);
        this.context = context;
        this.isConfirmDeliveryWay = isConfirmDeliveryWay;

        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popup_goods_detail,null);
        setContentView(view);
        initView(view);
        setListener();
    }

    private void initView(View view) {
        tvGoodName = (TextView) view.findViewById(R.id.tv_good_name);
        tvGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        dvGoodsCover = (SimpleDraweeView) view.findViewById(R.id.dv_goods_cover);
        tasteLayout = (LinearLayout) view.findViewById(R.id.ll_taste);
        recyclerView = (RecyclerView) view.findViewById(R.id.rl_goods_taste);
        ivMinus = (ImageView) view.findViewById(R.id.iv_minus);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvAdd = (TextView) view.findViewById(R.id.tv_add);
        tvBuy = (TextView) view.findViewById(R.id.tv_buy);

        tvConfirm.setVisibility( isConfirmDeliveryWay ? View.GONE :View.VISIBLE);
        tvAdd.setVisibility( isConfirmDeliveryWay ? View.VISIBLE :View.GONE);
        tvBuy.setVisibility( isConfirmDeliveryWay ? View.VISIBLE :View.GONE);
    }

    private void setListener() {
        ivCancel.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.iv_minus:
                break;
            case R.id.iv_add:
                break;
            case R.id.tv_confirm:
                break;
            case R.id.tv_add:
                break;
            case R.id.tv_buy:
                break;
            default:
                break;
        }
    }
}
