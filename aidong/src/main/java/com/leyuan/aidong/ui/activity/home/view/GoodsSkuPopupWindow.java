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
//todo 欠缺逻辑 : 当包含该sku但是该点的所有路径库存都为0 初始化该sku点的状态
public class GoodsSkuPopupWindow extends BasePopupWindow implements View.OnClickListener, GoodsSkuAdapter.SelectSkuListener,GoodsSkuPopupWindowView{
    private static final String BLANK_SPACE = " ";
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

    private double minPrice = Integer.MAX_VALUE;   //最低价格
    private double maxPrice;                       //最高价格
    private int totalStock;                        //总库存
    private String unSelectedSkuCover;             //未选中sku图片
    private StringBuilder unSelectedSkuTip;        //请选择sku提示

    private double price;                          //具体sku的价格
    private int stock = Integer.MAX_VALUE;         //具体sku的库存
    private String selectedSkuCover;               //具体sku的图片
    private StringBuilder selectedSkuTip;          //已选择sku提示

    private GoodsDetailBean detailBean;
    private List<LocalGoodsSkuBean> localSkuBeanList;
    private boolean showConfirmStatus = false;
    private List<String> selectedSkuValues = new ArrayList<>();
    private ConfirmSkuListener confirmSkuListener;
    private CartPresent cartPresent;


    public GoodsSkuPopupWindow(Context context, GoodsDetailBean detailBean,boolean showConfirmStatus,List<String> selectedSkuValues) {
        super(context);
        this.context = context;
        this.detailBean = detailBean;
        this.showConfirmStatus = showConfirmStatus;
        this.selectedSkuValues = selectedSkuValues;
        cartPresent = new CartPresentImpl(context,this);
        init();
    }


    private void init() {
        View view = View.inflate(context, R.layout.popup_sku_detail,null);
        setContentView(view);
        initData();
        initView(view);
        setListener();
    }

    private void initData(){
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

                /**************初始化选中的sku值**************/
                if(!selectedSkuValues.isEmpty()){
                    for (String selectedSkuValue : selectedSkuValues) {
                        if(selectedSkuValue.equals(value)){
                            valueBean.setSelected(true);
                        }
                    }
                }
                valuesList.add(valueBean);
            }

            /**************初始化选中sku值的规格名*************/
            if(!selectedSkuValues.isEmpty()){
                localGoodsSkuBean.setSelected(true);
            }

            localGoodsSkuBean.setSkuValues(valuesList);
            localSkuBeanList.add(localGoodsSkuBean);
        }

        /**************初始化其他信息:图片,价格,库存,sku提示*************/
        if(!selectedSkuValues.isEmpty()){                       //已有选中sku
            selectedSkuTip = new StringBuilder(context.getString(R.string.selected));
            List<String> selectedValues = new ArrayList<>();
            for (String selectedSkuValue : selectedSkuValues) {
                selectedValues.add(selectedSkuValue);
                selectedSkuTip.append(selectedSkuValue);
            }
            GoodsSkuBean line = getLine(selectedValues);
            price = FormatUtil.parseDouble(line.price);
            stock = FormatUtil.parseInt(line.stock);
            selectedSkuCover = line.cover;
        }else {                                                 //未选中sku
            unSelectedSkuTip = new StringBuilder(context.getString(R.string.please_choose));
            for (int i = 0; i < detailBean.spec.name.size(); i++) {
                unSelectedSkuTip.append(detailBean.spec.name.get(i)).append(BLANK_SPACE);
            }
        }
        for (GoodsSkuBean goodsSkuBean : detailBean.spec.item) {
            if(goodsSkuBean.price != null ){
                double price = FormatUtil.parseDouble(goodsSkuBean.price);
                if(price > maxPrice){
                    maxPrice = price;
                }
                if(price < minPrice){
                    minPrice = price;
                }
            }
            totalStock += FormatUtil.parseInt(goodsSkuBean.stock);
        }
        unSelectedSkuCover = detailBean.image.get(0);
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
        goodsSkuAdapter = new GoodsSkuAdapter(context,localSkuBeanList,detailBean.spec.item,selectedSkuValues);
        skuRecyclerView.setAdapter(goodsSkuAdapter);

        tvConfirm.setVisibility(showConfirmStatus ? View.VISIBLE : View.GONE);
        tvAdd.setVisibility(showConfirmStatus ? View.GONE : View.VISIBLE);
        tvBuy.setVisibility(showConfirmStatus ? View.GONE : View.VISIBLE);
        tvGoodName.setText(detailBean.name);
        if(selectedSkuValues.isEmpty()){
            dvGoodsCover.setImageURI(unSelectedSkuCover);
            tvGoodsPrice.setText(maxPrice == minPrice ? String.valueOf(maxPrice):
                    String.format(context.getString(R.string.rmb_price_scope),minPrice,maxPrice));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), totalStock));
            tvSkuTip.setText(unSelectedSkuTip.toString());
        }else {
            dvGoodsCover.setImageURI(selectedSkuCover);
            tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price),String.valueOf(price)));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), stock));
            tvSkuTip.setText(selectedSkuTip.toString());
        }
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
                if(selectedSkuValues.size() == detailBean.spec.name.size()){
                    if(confirmSkuListener != null){
                        confirmSkuListener.onSelectSku(selectedSkuValues);
                        dismiss();
                    }
                }else {
                    tipUnSelectSku();
                }
                break;
            case R.id.tv_add_cart:
                if(selectedSkuValues.size() == detailBean.spec.name.size()) {
                    GoodsSkuBean line = getLine(selectedSkuValues);
                    String countStr = tvCount.getText().toString();
                    cartPresent.addCart(line.id,Integer.parseInt(countStr));
                }else {
                    tipUnSelectSku();
                }
                break;
            case R.id.tv_buy:
                if(selectedSkuValues.size() == detailBean.spec.name.size()){
                    //todo 确认订单
                }else {
                   tipUnSelectSku();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void addCart(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            dismiss();
        }else {
            Toast.makeText(context,context.getString(R.string.add_cart_failed),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSelectSkuChanged(List<String> allSelectedNodes) {
        this.selectedSkuValues = allSelectedNodes;
        StringBuilder skuTip = new StringBuilder();
        if(allSelectedNodes.size() == detailBean.spec.name.size()){
            GoodsSkuBean line = getLine(allSelectedNodes);
            if(line != null){
                tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price),line.price));
                tvStock.setText(String.format(context.getString(R.string.stock_count),line.stock));
                dvGoodsCover.setImageURI(line.cover);
                stock = FormatUtil.parseInt(line.stock);
                tvSkuTip.setText(selectedSkuTip);
                if(Integer.parseInt(tvCount.getText().toString()) > stock){
                    tvCount.setText(line.stock);
                }
                ivMinus.setBackgroundResource(Integer.parseInt(tvCount.getText().toString()) > 1 ?
                        R.drawable.icon_minus : R.drawable.icon_minus_gray);
            }
            skuTip.append(context.getString(R.string.selected));
            for (String selectedNode : allSelectedNodes) {
                skuTip.append(selectedNode).append(BLANK_SPACE);
            }
            tvSkuTip.setText(skuTip.toString());
        }else {
            dvGoodsCover.setImageURI(unSelectedSkuCover);
            tvGoodsPrice.setText(maxPrice==minPrice?String.valueOf(maxPrice):
                    String.format(context.getString(R.string.rmb_price_scope),minPrice,maxPrice));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), totalStock));
            skuTip.append(context.getString(R.string.please_choose));
            List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
            for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
                skuTip.append(localGoodsSkuBean.getSkuName()).append(BLANK_SPACE);
            }
            tvSkuTip.setText(skuTip.toString());
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
    private void tipUnSelectSku(){
        StringBuilder result = new StringBuilder();
        result.append(context.getString(R.string.please_choose));
        List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
        for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
            result.append(localGoodsSkuBean.getSkuName()).append(BLANK_SPACE);
        }
        Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }

    public void setConfirmSkuListener(ConfirmSkuListener l) {
        this.confirmSkuListener = l;
    }

    public interface ConfirmSkuListener {
        void onSelectSku(List<String> selectedSkuValues);
    }
}
