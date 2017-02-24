package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.entity.GoodsSkuValueBean;
import com.leyuan.aidong.entity.LocalGoodsSkuBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.home.activity.ConfirmOrderActivity;
import com.leyuan.aidong.adapter.home.GoodsSkuAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsSkuPopupWindowView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.BasePopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
//todo 欠缺逻辑 : 当包含该sku但是该点的所有路径库存都为0 初始化该sku点的状态
public class GoodsSkuPopupWindow extends BasePopupWindow implements View.OnClickListener, GoodsSkuAdapter.SelectSkuListener,GoodsSkuPopupWindowView {
    public static final String FROM_SKU = "1";
    public static final String FROM_BUY = "2";
    public static final String FROM_ADD_CART = "3";

    private static final String BLANK_SPACE = " ";
    private Context context;
    private ImageView dvGoodsCover;
    private ImageView ivCancel;
    private TextView tvGoodName;
    private TextView tvGoodsPrice;
    private TextView tvStock;
    private TextView tvSelect;
    private TextView tvSkuTip;
    private RecyclerView skuRecyclerView;
    private GoodsSkuAdapter goodsSkuAdapter;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;
    private TextView tvConfirm;
    private TextView tvAdd;
    private TextView tvBuy;

    private double minPrice = Integer.MAX_VALUE;    //最低价格
    private double maxPrice;                        //最高价格
    private int totalStock;                         //总库存
    private String unConfirmedSkuCover;             //未选中sku图片

    private double price;                           //具体sku的价格
    private int stock = Integer.MAX_VALUE;          //具体sku的库存
    private String confirmedSkuCover;               //具体sku的图片

    private String gymId;
    private String count;
    private StringBuilder skuTip;
    private GoodsDetailBean detailBean;
    private List<LocalGoodsSkuBean> localSkuBeanList;
    private boolean showConfirmStatus = false;
    private boolean isAddCart = true;
    private List<String> selectedSkuValues = new ArrayList<>();
    private SelectSkuListener selectSkuListener;
    private CartPresent cartPresent;

    public GoodsSkuPopupWindow(Context context, GoodsDetailBean detailBean,
                               List<String> selectedSkuValues,String gymId,String count,String from) {
        super(context);
        this.context = context;
        this.detailBean = detailBean;
        this.selectedSkuValues = selectedSkuValues;
        this.gymId = gymId;
        this.count = count;

        if(from.equals(FROM_SKU)){
            showConfirmStatus = false;
        }else if(from.equals(FROM_ADD_CART)){
            showConfirmStatus = true;
            isAddCart = true;
        }else {
            showConfirmStatus = true;
            isAddCart = false;
        }
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

                //初始化选中的sku值和确定了sku值的sku名
                if(!selectedSkuValues.isEmpty()){
                    for (String selectedSkuValue : selectedSkuValues) {
                        if(selectedSkuValue.equals(value)){
                            valueBean.setSelected(true);
                            localGoodsSkuBean.setSelected(true);
                        }
                    }
                }
                valuesList.add(valueBean);
            }

            localGoodsSkuBean.setSkuValues(valuesList);
            localSkuBeanList.add(localGoodsSkuBean);
        }

        //初始化其他信息:图片,价格,库存,sku提示
        if(isAllSkuConfirm()){      //已确定sku
            skuTip = new StringBuilder();
            List<String> selectedValues = new ArrayList<>();
            for (String selectedSkuValue : selectedSkuValues) {
                selectedValues.add(selectedSkuValue);
                skuTip.append(selectedSkuValue).append(BLANK_SPACE);
            }
            GoodsSkuBean line = getLine(selectedValues);
            price = FormatUtil.parseDouble(line.price);
            stock = FormatUtil.parseInt(line.stock);
            confirmedSkuCover = line.cover;
        }else {                                                 //未选中sku
            skuTip = new StringBuilder();
            for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
                if(!localGoodsSkuBean.isSelected()){
                    skuTip.append(localGoodsSkuBean.getSkuName()).append(BLANK_SPACE);
                }
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
        unConfirmedSkuCover = detailBean.image.get(0);
    }

    private void initView(View view) {
        dvGoodsCover = (ImageView) view.findViewById(R.id.dv_goods_cover);
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        tvGoodName = (TextView) view.findViewById(R.id.tv_good_name);
        tvGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
        tvStock = (TextView) view.findViewById(R.id.tv_stock);
        tvSelect = (TextView) view.findViewById(R.id.tv_select);
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
        tvSkuTip.setText(skuTip.toString());
        if(isAllSkuConfirm()){
            GlideLoader.getInstance().displayImage(confirmedSkuCover, dvGoodsCover);
            tvSelect.setText(context.getString(R.string.selected));
            tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price),String.valueOf(price)));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), stock));
        }else {
            GlideLoader.getInstance().displayImage(unConfirmedSkuCover, dvGoodsCover);
            tvSelect.setText(context.getString(R.string.please_select));
            tvGoodsPrice.setText(maxPrice == minPrice ? String.valueOf(maxPrice):
                    String.format(context.getString(R.string.rmb_price_scope),minPrice,maxPrice));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), totalStock));
        }
        tvCount.setText(TextUtils.isEmpty(count) ? "1" : count);
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
                if(selectSkuListener != null){
                    selectSkuListener.onSelectSkuChanged(selectedSkuValues,skuTip.toString(),tvCount.getText().toString());
                }
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
                if(isAllSkuConfirm()){
                    if(isAddCart){
                        addCart();
                    }else {
                        confirmOrder();
                    }
                    dismiss();
                }else {
                    tipUnSelectSku();
                }
                break;
            case R.id.tv_add_cart:
                if(isAllSkuConfirm()) {
                    dismiss();
                    addCart();
                }else {
                    tipUnSelectSku();
                }
                break;
            case R.id.tv_buy:
                if(isAllSkuConfirm()){
                    dismiss();
                    confirmOrder();
                }else {
                   tipUnSelectSku();
                }
                break;
            default:
                break;
        }
    }

    private void addCart() {
        GoodsSkuBean line = getLine(selectedSkuValues);
        String countStr = tvCount.getText().toString();
        cartPresent.addCart(line.code,Integer.parseInt(countStr),gymId);
    }

    private void confirmOrder() {
       /* if(nurturePresent == null) {
            nurturePresent = new NurturePresentImpl(this, context);
        }
        GoodsSkuBean line = getLine(selectedSkuValues);
        if(TextUtils.isEmpty(gymId)) {
            nurturePresent.buyNurtureImmediately(line.code,
                    FormatUtil.parseInt(tvCount.getText().toString()),"0",null);
        }else {
            nurturePresent.buyNurtureImmediately(line.code,
                    FormatUtil.parseInt(tvCount.getText().toString()),"1",gymId);
        }*/
        //todo 确认订单
        GoodsSkuBean line = getLine(selectedSkuValues);
        ArrayList<ShopBean> shopBeanList = new ArrayList<>();
        ShopBean shopBean = new ShopBean();
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setName(detailBean.name);
        goodsBean.setCover(detailBean.image.get(0));
        goodsBean.setPrice(line.price);
        goodsBean.setAmount(tvCount.getText().toString());
        goodsBean.setSpec_value((ArrayList<String>) line.value);
        goodsBeanList.add(goodsBean);
        shopBean.setItem(goodsBeanList);
        shopBean.setName(TextUtils.isEmpty(gymId) ? "仓库发货" : detailBean.pick_up.info.getName());
        shopBeanList.add(shopBean);
        ConfirmOrderActivity.start(context,ConfirmOrderActivity.ORDER_CART,shopBeanList,
                FormatUtil.parseDouble(line.price)* FormatUtil.parseInt(tvCount.getText().toString()));
    }

    @Override
    public void addCartResult(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            Toast.makeText(context,context.getString(R.string.add_cart_success),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,context.getString(R.string.add_cart_failed),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(selectSkuListener != null){
            selectSkuListener.onSelectSkuChanged(selectedSkuValues,skuTip.toString(),tvCount.getText().toString());
        }
    }

    @Override
    public void onSelectSkuChanged(List<String> allSelectedNodes) {
        this.selectedSkuValues = allSelectedNodes;
        skuTip = new StringBuilder();        //todo optimize
        if(allSelectedNodes.size() == detailBean.spec.name.size()){
            GoodsSkuBean line = getLine(allSelectedNodes);
            if(line != null){
                tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price),line.price));
                tvStock.setText(String.format(context.getString(R.string.stock_count),line.stock));
                GlideLoader.getInstance().displayImage(line.cover, dvGoodsCover);
                stock = FormatUtil.parseInt(line.stock);
                if(Integer.parseInt(tvCount.getText().toString()) > stock){
                    tvCount.setText(line.stock);
                }
                ivMinus.setBackgroundResource(Integer.parseInt(tvCount.getText().toString()) > 1 ?
                        R.drawable.icon_minus : R.drawable.icon_minus_gray);
            }
            for (String selectedNode : allSelectedNodes) {
                skuTip.append(selectedNode).append(BLANK_SPACE);
            }
            tvSelect.setText(context.getString(R.string.selected));
            tvSkuTip.setText(skuTip.toString());
        }else {
            GlideLoader.getInstance().displayImage(unConfirmedSkuCover, dvGoodsCover);
            tvGoodsPrice.setText(maxPrice==minPrice?String.valueOf(maxPrice):
                    String.format(context.getString(R.string.rmb_price_scope),minPrice,maxPrice));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), totalStock));
            List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
            for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
                skuTip.append(localGoodsSkuBean.getSkuName()).append(BLANK_SPACE);
            }
            tvSelect.setText(context.getString(R.string.please_select));
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

    private boolean isAllSkuConfirm(){
        return selectedSkuValues.size() == detailBean.spec.name.size();
    }

    public void setSelectSkuListener(SelectSkuListener l) {
        this.selectSkuListener = l;
    }

    public interface SelectSkuListener {
        void onSelectSkuChanged(List<String> selectedSkuValues,String skuTip,String count);
    }
}
