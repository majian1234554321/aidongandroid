package com.leyuan.aidong.ui.activity.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.entity.SpecificationBean;
import com.leyuan.aidong.ui.activity.home.adapter.SpecificationAdapter;
import com.leyuan.aidong.widget.customview.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
public class GoodsInfoPopupWindow extends BasePopupWindow implements View.OnClickListener{
    private Context context;
    private SimpleDraweeView dvGoodsCover;
    private ImageView ivCancel;
    private TextView tvGoodName;
    private TextView tvGoodsPrice;
    private TextView tvStock;
    private TextView tvSelectTip;

    private RecyclerView specificationView;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;

    private TextView tvConfirm;
    private TextView tvAdd;
    private TextView tvBuy;

    private boolean isConfirmDeliveryWay = false;   //是否确认配送方式
    private GoodsDetailBean bean;
    public GoodsInfoPopupWindow(Context context,boolean isConfirmDeliveryWay) {
        super(context);
        this.context = context;
        this.isConfirmDeliveryWay = isConfirmDeliveryWay;

        bean = new GoodsDetailBean();
        GoodsDetailBean.Spec spec =  bean.new Spec();
        List<String> name = new ArrayList<>();
        name.add("尺寸");
        name.add("颜色");
        spec.name = name;
        List<GoodsDetailBean.Spec.Item> itemList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            GoodsDetailBean.Spec.Item item = spec.new Item();
            if (i == 0) {
                List<String> value = new ArrayList<>();
                value.add("小");
                value.add("红色");
                item.value = value;
            } else if (i == 1) {
                List<String> value = new ArrayList<>();
                value.add("小");
                value.add("绿色");
                item.value = value;
            } else if (i == 2) {
                List<String> value = new ArrayList<>();
                value.add("小");
                value.add("蓝色");
                item.value = value;
            } else if (i == 3) {
                List<String> value = new ArrayList<>();
                value.add("中");
                value.add("红色");
                item.value = value;
            } else if (i == 4) {
                List<String> value = new ArrayList<>();
                value.add("中");
                value.add("绿色");
                item.value = value;
            } else if (i == 5) {
                List<String> value = new ArrayList<>();
                value.add("中");
                value.add("蓝色");
                item.value = value;
            } else if (i == 6) {
                List<String> value = new ArrayList<>();
                value.add("大");
                value.add("红色");
                item.value = value;
            } else if (i == 7) {
                List<String> value = new ArrayList<>();
                value.add("大");
                value.add("绿色");
                item.value = value;
            } else if (i == 8) {
                List<String> value = new ArrayList<>();
                value.add("大");
                value.add("蓝色");
                item.value = value;
            } else if (i == 9) {
                List<String> value = new ArrayList<>();
                value.add("超大");
                value.add("蓝色");
                item.value = value;
            } else {
                List<String> value = new ArrayList<>();
                value.add("超大");
                value.add("红色");
            }
            itemList.add(item);
        }

        spec.item = itemList;

        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popup_goods_detail,null);
        setContentView(view);
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

        specificationView = (RecyclerView) view.findViewById(R.id.rv_specification);
        specificationView.setLayoutManager(new LinearLayoutManager(context));
        SpecificationAdapter adapter = new SpecificationAdapter(context);
        specificationView.setAdapter(adapter);

        List<SpecificationBean> sBeanList = new ArrayList<>();
        for (int i = 0; i < bean.spec.name.size(); i++) {
            SpecificationBean sBean = new SpecificationBean();          //自己需要的规格名数据
            sBean.specificationName = bean.spec.name.get(i);

            for (int j = 0; j < bean.spec.item.size(); j++) {
                List<String> sBeanValueList = new ArrayList<>();        //自己需要的规格值数据
                GoodsDetailBean.Spec.Item item = bean.spec.item.get(i);


            }


            sBeanList.add(sBean);
        }






       // adapter.setData(bean.spec);

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
