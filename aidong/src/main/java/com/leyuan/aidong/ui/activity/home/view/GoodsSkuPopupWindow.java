package com.leyuan.aidong.ui.activity.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.entity.GoodsSkuValueBean;
import com.leyuan.aidong.entity.GoodsSpecBean;
import com.leyuan.aidong.entity.LocalGoodsSkuBean;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsSkuAdapter;
import com.leyuan.aidong.widget.customview.BasePopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
public class GoodsSkuPopupWindow extends BasePopupWindow implements View.OnClickListener {
    public Context context;
    private SimpleDraweeView dvGoodsCover;
    private ImageView ivCancel;
    private TextView tvGoodName;
    private TextView tvGoodsPrice;
    private TextView tvStock;
    private TextView tvSelectTip;

    private RecyclerView skuRecyclerView;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;

    private TextView tvConfirm;
    private TextView tvAdd;
    private TextView tvBuy;

    private GoodsSpecBean specBean;
    private List<LocalGoodsSkuBean> localSkuBeanList;
    private boolean isConfirmDeliveryWay = false;   //是否确认配送方式

    public GoodsSkuPopupWindow(Context context,GoodsSpecBean specBean,boolean isConfirmDeliveryWay) {
        super(context);
        this.context = context;
        this.specBean = specBean;
        this.isConfirmDeliveryWay = isConfirmDeliveryWay;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popup_sku_detail,null);
        setContentView(view);
        packageData();
        initView(view);
        setListener();
    }

    private void initView(View view) {
        dvGoodsCover = (SimpleDraweeView) view.findViewById(R.id.dv_goods_cover);
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        tvGoodName = (TextView) view.findViewById(R.id.tv_good_name);
        tvGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
        tvStock = (TextView) view.findViewById(R.id.tv_stock);
        tvSelectTip = (TextView) view.findViewById(R.id.tv_select_tip);

        ivMinus = (ImageView) view.findViewById(R.id.iv_minus);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvAdd = (TextView) view.findViewById(R.id.tv_add);
        tvBuy = (TextView) view.findViewById(R.id.tv_buy);

        skuRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sku);
        skuRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        GoodsSkuAdapter goodsSkuAdapter = new GoodsSkuAdapter(this,localSkuBeanList,specBean.item);
        skuRecyclerView.setAdapter(goodsSkuAdapter);

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

    private void packageData() {
        localSkuBeanList = new ArrayList<>();
        for (int i = 0; i < specBean.name.size(); i++) {
            LocalGoodsSkuBean localGoodsSkuBean = new LocalGoodsSkuBean();
            String name = specBean.name.get(i);
            localGoodsSkuBean.setSkuName(name);
            List<String> temp = new ArrayList<>();
            for (GoodsSkuBean item : specBean.item) {
                if(item != null && item.value != null){
                    temp.add(item.value.get(i));
                }
            }
            //去重
            List<String> values = new ArrayList<>();
            for (String s : temp) {
                if (Collections.frequency(values, s) < 1) {
                    values.add(s);
                }
            }
            //将String类型的规格值转换成带状态的规格实体
            List<GoodsSkuValueBean> valuesList = new ArrayList<>();
            for (String value : values) {
                GoodsSkuValueBean valueBean = new GoodsSkuValueBean();
                valueBean.setValue(value);
                valuesList.add(valueBean);
            }
            localGoodsSkuBean.setSkuValues(valuesList);
            localSkuBeanList.add(localGoodsSkuBean);
        }
    }

    //获取除最后选定规格值的Sku
    public List<LocalGoodsSkuBean> getExceptLastSelectedSku(){
        List<LocalGoodsSkuBean> localExceptLastSelectedSkuList = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
            if(localGoodsSkuBean.isSelected() && !localGoodsSkuBean.isLastSelected()){
                localExceptLastSelectedSkuList.add(localGoodsSkuBean);
            }
        }
        return localExceptLastSelectedSkuList;
    }

    //获取未确定规格值的Sku 如尺寸未选中
    public List<LocalGoodsSkuBean> getUnSelectedSku(){
        List<LocalGoodsSkuBean> localUnselectedSkuList = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
            if(!localGoodsSkuBean.isSelected()) {
                localUnselectedSkuList.add(localGoodsSkuBean);
            }
        }
        return localUnselectedSkuList;
    }
}
