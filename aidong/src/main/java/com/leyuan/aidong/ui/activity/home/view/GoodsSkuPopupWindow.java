package com.leyuan.aidong.ui.activity.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.entity.GoodsSkuValueBean;
import com.leyuan.aidong.entity.LocalGoodsSkuBean;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsSkuAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsSkuPopupWindowView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.widget.customview.BasePopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
public class GoodsSkuPopupWindow extends BasePopupWindow implements View.OnClickListener, GoodsSkuAdapter.SelectSkuListener,GoodsSkuPopupWindowView{
    private Context context;
    private SimpleDraweeView dvGoodsCover;
    private ImageView ivCancel;
    private TextView tvGoodName;
    private TextView tvGoodsPrice;
    private TextView tvStock;
    private TextView tvSkuTip;
    private RecyclerView skuRecyclerView;
    private GoodsSkuAdapter goodsSkuAdapter;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;
    private TextView tvConfirm;
    private TextView tvAdd;
    private TextView tvBuy;

    private int stock = Integer.MAX_VALUE;
    private GoodsDetailBean detailBean;
    private List<LocalGoodsSkuBean> localSkuBeanList;
    private List<String> allSelectedNodes = new ArrayList<>();
    private ConfirmSkuListener confirmSkuListener;

    private CartPresent present;

    public GoodsSkuPopupWindow(Context context, GoodsDetailBean detailBean) {
        super(context);
        this.context = context;
        this.detailBean = detailBean;
        present = new CartPresentImpl(context,this);
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
        tvSkuTip = (TextView) view.findViewById(R.id.tv_sku_tip);
        ivMinus = (ImageView) view.findViewById(R.id.iv_minus);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvAdd = (TextView) view.findViewById(R.id.tv_add_cart);
        tvBuy = (TextView) view.findViewById(R.id.tv_buy);
        skuRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sku);
        skuRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        goodsSkuAdapter = new GoodsSkuAdapter(context,localSkuBeanList,detailBean.spec.item);
        skuRecyclerView.setAdapter(goodsSkuAdapter);

        StringBuilder sb = new StringBuilder(context.getString(R.string.please_choose));
        for (String s : detailBean.spec.name) {
            sb.append(s).append(" ");
        }
        tvSkuTip.setText(sb);
        tvCount.setText("1");
        tvGoodName.setText(detailBean.name);
    }

    private void setListener() {
        ivCancel.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        goodsSkuAdapter.setSelectSkuListener(this);
    }

    @Override
    public void onClick(View v) {
        int count = Integer.parseInt(tvCount.getText().toString());
        StringBuilder result = new StringBuilder();
        switch (v.getId()){
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.iv_minus:
                count --;
                if(count <= 1){
                    count = 1;
                    ivMinus.setBackgroundResource(R.drawable.icon_minus_gray);
                }
                tvCount.setText(String.valueOf(count));
                break;
            case R.id.iv_add:
                count ++;
                if(count > stock){
                    count = stock;
                    Toast.makeText(context,context.getString(R.string.stock_out),Toast.LENGTH_LONG).show();
                }
                if(count > 1){
                    ivMinus.setBackgroundResource(R.drawable.icon_minus);
                }
                tvCount.setText(String.valueOf(count));
                break;
            case R.id.tv_confirm:
                if(allSelectedNodes.size() == detailBean.spec.name.size()){
                    for (String selectedNode : allSelectedNodes) {
                        result.append(selectedNode).append(" ");
                    }
                    if(confirmSkuListener != null){
                        confirmSkuListener.onConfirmSku(result.toString());
                        dismiss();
                    }
                }else {
                    tipUnSelectSku(result);
                }
                break;
            case R.id.tv_add_cart:
                if(allSelectedNodes.size() == detailBean.spec.name.size()) {
                    GoodsSkuBean line = getLine(allSelectedNodes);
                    String countStr = tvCount.getText().toString();
                    present.addCart(line.id,Integer.parseInt(countStr));
                }else {
                    tipUnSelectSku(result);
                }
                break;
            case R.id.tv_buy:
                if(allSelectedNodes.size() == detailBean.spec.name.size()){
                    //todo 确认订单
                }else {
                   tipUnSelectSku(result);
                }
                break;
            default:
                break;
        }
    }


    private void packageData() {
        localSkuBeanList = new ArrayList<>();
        for (int i = 0; i < detailBean.spec.name.size(); i++) {
            LocalGoodsSkuBean localGoodsSkuBean = new LocalGoodsSkuBean();
            String name = detailBean.spec.name.get(i);
            localGoodsSkuBean.setSkuName(name);
            List<String> temp = new ArrayList<>();
            for (GoodsSkuBean item : detailBean.spec.item) {
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

    //获选定规格值的Sku行 如颜色选中
    @Override
    public List<LocalGoodsSkuBean> onGetSelectSku() {
        List<LocalGoodsSkuBean> localSelectedSkuList = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
            if(localGoodsSkuBean.isSelected()) {
                localSelectedSkuList.add(localGoodsSkuBean);
            }
        }
        return localSelectedSkuList;
    }

    //获取未确定规格值的Sku行 如尺寸未选中
    @Override
    public List<LocalGoodsSkuBean> onGetUnSelectSku() {
        List<LocalGoodsSkuBean> localUnselectedSkuList = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
            if(!localGoodsSkuBean.isSelected()) {
                localUnselectedSkuList.add(localGoodsSkuBean);
            }
        }
        return localUnselectedSkuList;
    }

    @Override
    public void onSelectSkuChanged(List<String> allSelectedNodes) {
        this.allSelectedNodes = allSelectedNodes;
        StringBuilder result = new StringBuilder();
        if(allSelectedNodes.size() == detailBean.spec.name.size()){
            GoodsSkuBean line = getLine(allSelectedNodes);
            if(line != null){
                tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price),line.price));
                tvStock.setText(String.format(context.getString(R.string.stock_count),line.stock));
                dvGoodsCover.setImageURI(line.cover);
                stock = FormatUtil.parseInt(line.stock);
                if(Integer.parseInt(tvCount.getText().toString()) > stock){
                    tvCount.setText(line.stock);
                }
                ivMinus.setBackgroundResource(Integer.parseInt(tvCount.getText().toString()) > 1 ?
                        R.drawable.icon_minus : R.drawable.icon_minus_gray);
            }
            result.append(context.getString(R.string.selected));
            for (String selectedNode : allSelectedNodes) {
                result.append(selectedNode).append(" ");
            }
        }else {
            tvGoodsPrice.setText("默认价格范围");
            tvStock.setText("总库存");
            dvGoodsCover.setImageURI("");

            result.append(context.getString(R.string.please_choose));
            List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
            for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
                result.append(localGoodsSkuBean.getSkuName()).append(" ");
            }
        }
        tvSkuTip.setText(result);
    }

    @Override
    public void addCart(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            dismiss();
        }else {
            Toast.makeText(context,context.getString(R.string.add_cart_failed),Toast.LENGTH_LONG).show();
        }
    }

    //获取包含全部属性节点的唯一路线
    private GoodsSkuBean getLine(List<String> selectedValues){
        GoodsSkuBean usefulGoodsSkuBean = null;
        for (GoodsSkuBean goodsSkuBean : detailBean.spec.item) {
            if(goodsSkuBean.value.containsAll(selectedValues)){
                usefulGoodsSkuBean = goodsSkuBean;
                break;      //数据正常得情况下只可能有一条线路包含所有节点，不考虑数据录入异常情况
            }
        }
        return usefulGoodsSkuBean;
    }

    //提示sku没有全部选择
    private void tipUnSelectSku(StringBuilder result){
        result.append(context.getString(R.string.please_choose));
        List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
        for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
            result.append(localGoodsSkuBean.getSkuName()).append(" ");
        }
        Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }

    public void setConfirmSkuListener(ConfirmSkuListener l) {
        this.confirmSkuListener = l;
    }

    public interface ConfirmSkuListener {
        void onConfirmSku(String sku);
    }
}
