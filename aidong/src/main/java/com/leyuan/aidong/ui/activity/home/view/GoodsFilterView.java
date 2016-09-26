package com.leyuan.aidong.ui.activity.home.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.widget.dropdownmenu.adapter.ListWithFlagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品和装备更多商品筛选控件
 * Created by song on 2016/9/19.
 */
public class GoodsFilterView extends LinearLayout implements View.OnClickListener {
    private Context context;

    private LinearLayout filterLayout;
    private LinearLayout categoryLayout;
    private TextView tvCategory;
    private ImageView ivCategoryArrow;
    private LinearLayout popularityLayout;
    private TextView tvPopularity;
    private LinearLayout saleLayout;
    private TextView tvSale;
    private LinearLayout priceLayout;
    private TextView tvPrice;
    private ImageView ivPriceArrow;

    private View maskBgView;
    private LinearLayout contentLayout;
    private ListView listView;
    private ListWithFlagAdapter categoryAdapter;
    private List<String> categoryList = new ArrayList<>();

    private String  category;
    private boolean isShowing = false;
    private boolean isLow2High = true;
    private int panelHeight;

    public GoodsFilterView(Context context) {
        this(context, null);
    }

    public GoodsFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        setListener();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_goods_filter, this);
        filterLayout = (LinearLayout) view.findViewById(R.id.ll_filter_layout);
        categoryLayout = (LinearLayout) view.findViewById(R.id.ll_category);
        tvCategory = (TextView) view.findViewById(R.id.tv_category);
        ivCategoryArrow = (ImageView) view.findViewById(R.id.iv_category_arrow);
        popularityLayout = (LinearLayout) view.findViewById(R.id.ll_popularity);
        tvPopularity = (TextView) view.findViewById(R.id.tv_popularity);
        saleLayout = (LinearLayout) view.findViewById(R.id.ll_sale);
        tvSale = (TextView) view.findViewById(R.id.tv_sale);
        priceLayout = (LinearLayout) view.findViewById(R.id.ll_price);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        ivPriceArrow = (ImageView) findViewById(R.id.iv_price_arrow);
        maskBgView = view.findViewById(R.id.view_mask_bg);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        listView = (ListView) view.findViewById(R.id.list);
    }

    private void setListener() {
        categoryLayout.setOnClickListener(this);
        popularityLayout.setOnClickListener(this);
        saleLayout.setOnClickListener(this);
        priceLayout.setOnClickListener(this);
        maskBgView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(onFilterClickListener == null){
           return;
        }

        switch (v.getId()) {
            case R.id.ll_category:
                isLow2High = true;
                setCategoryAdapter();
                break;
            case R.id.ll_popularity:
                isLow2High = true;
                if(isShowing){
                    hideCategoryList();
                }
                resetSortStatus();
                onFilterClickListener.onPopularityClick();
                tvPopularity.setTextColor(context.getResources().getColor(R.color.main_red));
                break;
            case R.id.ll_sale:
                isLow2High = true;
                if(isShowing){
                    hideCategoryList();
                }
                resetSortStatus();
                onFilterClickListener.onSaleClick();
                tvSale.setTextColor(context.getResources().getColor(R.color.main_red));
                break;
            case R.id.ll_price:
                if(isShowing){
                    hideCategoryList();
                }
                resetSortStatus();
                onFilterClickListener.onPriceClick(isLow2High);
                ivPriceArrow.setImageResource(isLow2High ?R.drawable.icon_red_arrow_up : R.drawable.icon_red_arrow_down);
                tvPrice.setTextColor(context.getResources().getColor(R.color.main_red));
                isLow2High = !isLow2High;
                break;
            case R.id.view_mask_bg:
                hideCategoryList();
                break;
            default:
                break;
        }
    }

    // 设置分类数据
    private void setCategoryAdapter() {
        if (isShowing){
            return;
        }
        isShowing = true;
        showCategoryList();
        tvCategory.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        if (categoryList.isEmpty()) {
            Log.d("GoodsFilterView", "you need set categoryList data first");
        }
        if(categoryAdapter == null){
            categoryAdapter = new ListWithFlagAdapter(context, categoryList);
        }
        listView.setAdapter(categoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = categoryList.get(position);
                categoryAdapter.setCheckItem(position);
                tvCategory.setText(category);
                hideCategoryList();
                if (onFilterClickListener != null) {
                    onFilterClickListener.onCategoryItemClick(category);
                }
            }
        });
    }

    //设置分类筛选数据
    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    // 动画显示
    private void showCategoryList() {
        isShowing = true;
        maskBgView.setVisibility(VISIBLE);
        contentLayout.setVisibility(VISIBLE);
        contentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contentLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = contentLayout.getHeight();
                ObjectAnimator.ofFloat(contentLayout, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
    }

    // 隐藏动画
    public void hideCategoryList() {
        isShowing = false;
        resetCategoryStatus();
        maskBgView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(contentLayout, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    public void resetAllStatus() {
        resetCategoryStatus();
        resetSortStatus();
        hideCategoryList();
    }

    // 复位分类的显示状态
    public void resetCategoryStatus() {
        tvCategory.setTextColor(context.getResources().getColor(R.color.black));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow);
    }

    // 复位三个排序按钮的显示状态
    public void resetSortStatus() {
        tvPopularity.setTextColor(context.getResources().getColor(R.color.black));
        tvSale.setTextColor(context.getResources().getColor(R.color.black));
        tvPrice.setTextColor(context.getResources().getColor(R.color.black));
        ivPriceArrow.setImageResource(R.drawable.icon_double_arrow);
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onCategoryItemClick(String category);

        void onPopularityClick();

        void onSaleClick();

        void onPriceClick(boolean low2High);
    }

}
