package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.GoodsSkuAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.entity.GoodsSkuValueBean;
import com.leyuan.aidong.entity.LocalGoodsSkuBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.home.activity.ConfirmOrderActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.CartPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsSkuPopupWindowView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.BasePopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
public class GoodsSkuPopupWindow extends BasePopupWindow implements View.OnClickListener,
        GoodsSkuAdapter.SelectSkuListener, GoodsSkuPopupWindowView {
    public enum GoodsStatus{
        SellOut,            //售罄状态
        ConfirmToAddCart,   //确定状态(点击确认加入购物车)
        ConfirmToBuy,       //确定状态（点击确认立即购买）
        Normal              //显示加入购物车和立即购买状态
    }

    private Context context;
    private ImageView dvGoodsCover;
    private ImageView ivCancel;
    private TextView tvGoodsPrice;
    private TextView tvStock;
    private TextView tvSelect;
    private TextView tvSkuTip;
    private GoodsSkuAdapter goodsSkuAdapter;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;
    private TextView tvConfirm;
    private TextView tvAdd;
    private LinearLayout buyLayout;
    private TextView tvStockTip;

    private double minPrice = Integer.MAX_VALUE;    //最低价格
    private double maxPrice;                        //最高价格
    private int totalStock;                         //总库存
    private String unConfirmedSkuCover;             //未选中sku图片

    private double price;                           //具体sku的价格
    private int stock = Integer.MAX_VALUE;          //具体sku的库存
    private String confirmedSkuCover;               //具体sku的图片

    private String goodsType;
    private String count;
    private String recommendId;
    private StringBuilder skuTip;
    private GoodsDetailBean detailBean;
    private List<LocalGoodsSkuBean> localSkuBeanList;
    private List<String> selectedSkuValues = new ArrayList<>();
    private SelectSkuListener selectSkuListener;
    private CartPresent cartPresent;
    private GoodsStatus status;

    public GoodsSkuPopupWindow(Context context,GoodsDetailBean detailBean,GoodsStatus status,
                               List<String> selectedSkuValues, String count, String recommendId, String goodsType) {
        super(context);
        this.context = context;
        this.detailBean = detailBean;
        this.status = status;
        this.selectedSkuValues = selectedSkuValues;
        this.recommendId = recommendId;
        this.count = count;
        this.goodsType = goodsType;
        cartPresent = new CartPresentImpl(context, this);
        init();
    }


    private void init() {
        View view = View.inflate(context, R.layout.popup_sku_detail, null);
        setContentView(view);
        initData();
        initView(view);
        setListener();
    }

    private void initData() {
        localSkuBeanList = new ArrayList<>();
        for (int i = 0; i < detailBean.spec.name.size(); i++) {
            LocalGoodsSkuBean localGoodsSkuBean = new LocalGoodsSkuBean();
            String name = detailBean.spec.name.get(i);
            localGoodsSkuBean.setSkuName(name);
            List<String> temp = new ArrayList<>();
            for (GoodsSkuBean item : detailBean.spec.item) {
                if (item != null && item.value != null) {
                    if(!item.value.isEmpty()&& item.value.size()>i) {
                        temp.add(item.value.get(i));
                    }
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
                if (!selectedSkuValues.isEmpty()) {
                    for (String selectedSkuValue : selectedSkuValues) {
                        if (selectedSkuValue.equals(value)) {
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
        if (isAllSkuConfirm()) {      //已确定sku
            skuTip = new StringBuilder();
            List<String> selectedValues = new ArrayList<>();
            for (String selectedSkuValue : selectedSkuValues) {
                selectedValues.add(selectedSkuValue);
                skuTip.append(selectedSkuValue).append(Constant.EMPTY_STR);
            }
            GoodsSkuBean line = getLine(selectedValues);
            price = FormatUtil.parseDouble(line.price);
            stock = line.getStock();
            confirmedSkuCover = line.cover;
        } else {                     //未选中sku
            skuTip = new StringBuilder();
            for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
                if (!localGoodsSkuBean.isSelected()) {
                    skuTip.append(localGoodsSkuBean.getSkuName()).append(Constant.EMPTY_STR);
                }
            }
        }
        for (GoodsSkuBean goodsSkuBean : detailBean.spec.item) {
            if (goodsSkuBean.price != null) {
                double price = FormatUtil.parseDouble(goodsSkuBean.price);
                if (price > maxPrice) {
                    maxPrice = price;
                }
                if (price < minPrice) {
                    minPrice = price;
                }
            }
            totalStock += goodsSkuBean.getStock();
        }
        unConfirmedSkuCover = detailBean.image.get(0);
    }

    private void initView(View view) {
        dvGoodsCover = (ImageView) view.findViewById(R.id.dv_goods_cover);
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        TextView tvGoodName = (TextView) view.findViewById(R.id.tv_good_name);
        tvGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
        tvStock = (TextView) view.findViewById(R.id.tv_stock);
        tvSelect = (TextView) view.findViewById(R.id.tv_select);
        tvSkuTip = (TextView) view.findViewById(R.id.tv_sku_tip);
        ivMinus = (ImageView) view.findViewById(R.id.iv_minus);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        ivAdd = (ImageView) view.findViewById(R.id.iv_add);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvAdd = (TextView) view.findViewById(R.id.tv_add_cart);
        buyLayout = (LinearLayout) view.findViewById(R.id.ll_buy);
        tvStockTip = (TextView) view.findViewById(R.id.tv_all_count);
        TextView tvSellOut = (TextView) view.findViewById(R.id.tv_sell_out);
        RecyclerView skuRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sku);
        skuRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        goodsSkuAdapter = new GoodsSkuAdapter(context, localSkuBeanList,
                detailBean.spec.item, selectedSkuValues);
        skuRecyclerView.setAdapter(goodsSkuAdapter);

        if (status == GoodsStatus.SellOut) {
            tvSellOut.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.GONE);
            tvAdd.setVisibility(View.GONE);
            buyLayout.setVisibility(View.GONE);
        } else if (status == GoodsStatus.Normal) {
            tvAdd.setVisibility(View.VISIBLE);
            buyLayout.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.GONE);
            tvSellOut.setVisibility(View.GONE);
        } else {
            tvConfirm.setVisibility(View.VISIBLE);
            tvAdd.setVisibility(View.GONE);
            buyLayout.setVisibility(View.GONE);
            tvSellOut.setVisibility(View.GONE);
        }

        tvGoodName.setText(detailBean.name);
        tvSkuTip.setText(skuTip.toString());
        if (isAllSkuConfirm()) {
            GlideLoader.getInstance().displayImage(confirmedSkuCover, dvGoodsCover);
            tvSelect.setText(context.getString(R.string.selected));
            tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price_double), price));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), stock));
            tvStockTip.setText(String.format(context.getString(R.string.surplus_goods_count),stock));
            tvStockTip.setVisibility(stock <= 10 ? View.VISIBLE : View.GONE);
        } else {
            GlideLoader.getInstance().displayImage(unConfirmedSkuCover, dvGoodsCover);
            tvSelect.setText(context.getString(R.string.please_select));
            tvGoodsPrice.setText(maxPrice == minPrice
                    ? String.format(context.getString(R.string.rmb_price_double), maxPrice)
                    : String.format(context.getString(R.string.rmb_price_scope), minPrice, maxPrice));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), totalStock));
        }
        tvCount.setText(TextUtils.isEmpty(count) ? "1" : count);
        ivMinus.setBackgroundResource(FormatUtil.parseInt(count) > 1 ? R.drawable.icon_minus
                : R.drawable.icon_minus_gray);
    }

    private void setListener() {
        ivCancel.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        buyLayout.setOnClickListener(this);
        goodsSkuAdapter.setSelectSkuListener(this);
    }

    @Override
    public void onClick(View v) {
        int count = Integer.parseInt(tvCount.getText().toString());
        switch (v.getId()) {
            case R.id.iv_cancel:
                if (selectSkuListener != null) {
                    selectSkuListener.onSelectSkuChanged(selectedSkuValues, skuTip.toString(),
                            tvCount.getText().toString(),stock);
                }
                dismiss();
                break;
            case R.id.iv_minus:
                count--;
                if (count <= 1) {
                    count = 1;
                    ivMinus.setBackgroundResource(R.drawable.icon_minus_gray);
                }
                tvCount.setText(String.valueOf(count));
                break;
            case R.id.iv_add:
                count++;
                if (count > stock) {
                    count = stock;
                    Toast.makeText(context, context.getString(R.string.stock_out), Toast.LENGTH_LONG).show();
                }
                if (count > 1) {
                    ivMinus.setBackgroundResource(R.drawable.icon_minus);
                }
                tvCount.setText(String.valueOf(count));
                break;
            case R.id.tv_confirm:
                if (App.mInstance.isLogin()) {
                    if (isAllSkuConfirm()) {
                        dismiss();
                        confirm();
                    } else {
                        tipUnSelectSku();
                    }
                } else {
                    ((GoodsDetailActivity) context).startActivityForResult(
                            new Intent(context, LoginActivity.class), Constant.REQUEST_CONFIRM);
                }
                break;
            case R.id.tv_add_cart:
                if (App.mInstance.isLogin()) {
                    if (isAllSkuConfirm()) {
                        dismiss();
                        addCart();
                    } else {
                        tipUnSelectSku();
                    }
                } else {
                    ((GoodsDetailActivity) context).startActivityForResult(
                            new Intent(context, LoginActivity.class), Constant.REQUEST_ADD_CART);
                }
                break;
            case R.id.ll_buy:
                if (App.mInstance.isLogin()) {
                    if (isAllSkuConfirm()) {
                        dismiss();
                        buyImmediately();
                    } else {
                        tipUnSelectSku();
                    }
                } else {
                    ((GoodsDetailActivity) context).startActivityForResult(
                            new Intent(context, LoginActivity.class), Constant.REQUEST_BUY_IMMEDIATELY);
                }
                break;
            default:
                break;
        }
    }

    public void confirm() {
        if (status == GoodsStatus.ConfirmToAddCart) {
            addCart();
        } else if(status == GoodsStatus.ConfirmToBuy){
            buyImmediately();
        }
    }

    public void addCart() {
        GoodsSkuBean line = getLine(selectedSkuValues);
        String countStr = tvCount.getText().toString();
        String id = detailBean.pick_up.getInfo().getId();
        cartPresent.addCart(line.code, Integer.parseInt(countStr), id, recommendId);
    }

    public void buyImmediately() {
        GoodsSkuBean line = getLine(selectedSkuValues);
        ShopBean shopBean = new ShopBean();
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        GoodsBean goodsBean = new GoodsBean();

        //结算界面修改自提信息时需要用到
        goodsBean.setProductId(detailBean.id);
        goodsBean.setProductType(goodsType);

        goodsBean.setName(detailBean.name);
        goodsBean.setCode(line.code);
        goodsBean.setCover(detailBean.image.get(0));
        goodsBean.setPrice(line.price);
        goodsBean.setType(goodsType);
        goodsBean.setAmount(tvCount.getText().toString());
        goodsBean.setSpec_value((ArrayList<String>) line.value);
        goodsBeanList.add(goodsBean);
        shopBean.setItem(goodsBeanList);
        shopBean.setPickUp(detailBean.pick_up);
        ConfirmOrderActivity.start(context, shopBean);
    }

    @Override
    public void addCartResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            ToastGlobal.showLong(context.getString(R.string.add_cart_success));
        } else {
            ToastGlobal.showLong(context.getString(R.string.add_cart_failed));
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (selectSkuListener != null) {
            selectSkuListener.onSelectSkuChanged(selectedSkuValues, skuTip.toString(), tvCount.getText().toString(),stock);
        }
    }

    @Override
    public void onSelectSkuChanged(List<String> allSelectedNodes) {
        this.selectedSkuValues = allSelectedNodes;
        skuTip = new StringBuilder();        //todo optimize
        if (allSelectedNodes.size() == detailBean.spec.name.size()) {
            GoodsSkuBean line = getLine(allSelectedNodes);
            if (line != null) {
                tvGoodsPrice.setText(String.format(context.getString(R.string.rmb_price_double),
                        FormatUtil.parseDouble(line.price)));
                tvStock.setText(String.format(context.getString(R.string.stock_count), line.getStock() + ""));
                GlideLoader.getInstance().displayImage(line.cover, dvGoodsCover);
                stock = line.getStock();
                tvStockTip.setText(String.format(context.getString(R.string.surplus_goods_count),stock));
                tvStockTip.setVisibility(stock <= 10 ? View.VISIBLE : View.GONE);
                if (Integer.parseInt(tvCount.getText().toString()) > stock) {
                    tvCount.setText(String.valueOf(line.getStock()));
                }
                ivMinus.setBackgroundResource(Integer.parseInt(tvCount.getText().toString()) > 1 ?
                        R.drawable.icon_minus : R.drawable.icon_minus_gray);
            }
            for (String selectedNode : allSelectedNodes) {
                skuTip.append(selectedNode).append(Constant.EMPTY_STR);
            }
            tvSelect.setText(context.getString(R.string.selected));
            tvSkuTip.setText(skuTip.toString());
        } else {
            GlideLoader.getInstance().displayImage(unConfirmedSkuCover, dvGoodsCover);
            tvGoodsPrice.setText(maxPrice == minPrice ? String.valueOf(maxPrice) :
                    String.format(context.getString(R.string.rmb_price_scope), minPrice, maxPrice));
            tvStock.setText(String.format(context.getString(R.string.int_stock_count), totalStock));
            List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
            for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
                skuTip.append(localGoodsSkuBean.getSkuName()).append(Constant.EMPTY_STR);
            }
            tvSelect.setText(context.getString(R.string.please_select));
            tvSkuTip.setText(skuTip.toString());
            tvStockTip.setVisibility(View.GONE);
        }
    }

    //获选定规格值的Sku行 如颜色选中
    @Override
    public List<LocalGoodsSkuBean> onGetSelectSku() {
        List<LocalGoodsSkuBean> localSelectedSkuList = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuBeanList) {
            if (localGoodsSkuBean.isSelected()) {
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
            if (!localGoodsSkuBean.isSelected()) {
                localUnselectedSkuList.add(localGoodsSkuBean);
            }
        }
        return localUnselectedSkuList;
    }

    //获取包含全部属性节点的唯一路线
    private GoodsSkuBean getLine(List<String> selectedValues) {
        GoodsSkuBean usefulGoodsSkuBean = null;
        for (GoodsSkuBean goodsSkuBean : detailBean.spec.item) {
            if (goodsSkuBean.value.containsAll(selectedValues)) {
                usefulGoodsSkuBean = goodsSkuBean;
                break;      //数据正常得情况下只可能有一条线路包含所有节点，不考虑数据录入异常情况
            }
        }
        return usefulGoodsSkuBean;
    }

    //提示sku没有全部选择
    private void tipUnSelectSku() {
        StringBuilder result = new StringBuilder();
        result.append(context.getString(R.string.please_choose));
        List<LocalGoodsSkuBean> unSelectedSkuBeanList = onGetUnSelectSku();
        for (LocalGoodsSkuBean localGoodsSkuBean : unSelectedSkuBeanList) {
            result.append(localGoodsSkuBean.getSkuName()).append(Constant.EMPTY_STR);
        }
        ToastGlobal.showLong(result.toString());
    }

    private boolean isAllSkuConfirm() {
        return selectedSkuValues.size() == detailBean.spec.name.size();
    }

    public void setSelectSkuListener(SelectSkuListener l) {
        this.selectSkuListener = l;
    }

    public interface SelectSkuListener {
        void onSelectSkuChanged(List<String> selectedSkuValues, String skuTip, String selectCount,int stock);
    }
}
