package com.leyuan.aidong.ui.discover.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.DistrictDescBean;
import com.leyuan.aidong.adapter.home.LeftFilterAdapter;
import com.leyuan.aidong.adapter.home.RightFilterAdapter;
import com.leyuan.aidong.adapter.home.CategoryListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆列表筛选控件
 * Created by song on 2016/10/31.
 */
//todo 逻辑过于混乱,通用性不够,需要重构.
public class VenuesFilterView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private LinearLayout brandLayout;
    private TextView tvBrand;
    private ImageView ivBrandArrow;
    private LinearLayout circleLayout;
    private TextView tvCircle;
    private ImageView ivCircleArrow;
    private View maskBgView;
    private LinearLayout contentLayout;
    private ListView leftListView;
    private ListView rightListView;

    //品牌
    private CategoryListAdapter brandAdapter;
    private List<CategoryBean> brandList = new ArrayList<>();

    //商圈
    private LeftFilterAdapter leftCircleAdapter;
    private RightFilterAdapter rightCircleAdapter;
    private int leftSelectedPosition = -1;  //左边列表实际选中的位置
    private int rightSelectedPosition = -1; //右边列表实际选中的位置
    private List<DistrictBean> leftCircleList = new ArrayList<>();
    private List<DistrictDescBean> rightCircleList = new ArrayList<>();

    private int panelHeight;
    private boolean isPopupShowing = false;
    private boolean isCategoryShowing = false;
    private boolean isCircleShowing = false;

    public VenuesFilterView(Context context) {
        this(context, null);
    }

    public VenuesFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VenuesFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        setListener();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_course_filter, this);
        brandLayout = (LinearLayout) view.findViewById(R.id.ll_brand);
        tvBrand = (TextView) view.findViewById(R.id.tv_brand);
        ivBrandArrow = (ImageView) view.findViewById(R.id.iv_brand_arrow);
        circleLayout = (LinearLayout) view.findViewById(R.id.ll_business_circle);
        tvCircle = (TextView) view.findViewById(R.id.tv_circle);
        ivCircleArrow = (ImageView) view.findViewById(R.id.iv_circle_arrow);
        maskBgView = view.findViewById(R.id.view_mask_bg);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        leftListView = (ListView) view.findViewById(R.id.list_left);
        rightListView = (ListView) view.findViewById(R.id.list_right);
    }

    private void setListener() {
        brandLayout.setOnClickListener(this);
        circleLayout.setOnClickListener(this);
        maskBgView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_brand:
                resetBusinessCircleStatus();
                setBrandAdapter();
                break;
            case R.id.ll_business_circle:
                resetBrandStatus();
                setBusinessCircleAdapter();
                break;
            case R.id.view_mask_bg:
                hidePopup();
                break;
            default:
                break;
        }
    }

    // 设置品牌数据
    private void setBrandAdapter() {
        if (!isPopupShowing) {
            isPopupShowing = true;
            showPopup();
        } else {
            if (isCategoryShowing) {
                hidePopup();
                return;
            }
        }
        isCategoryShowing = true;
        isCircleShowing = false;
        tvBrand.setTextColor(context.getResources().getColor(R.color.main_red));
        ivBrandArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        rightListView.setVisibility(GONE);
        if (brandAdapter == null) {
            brandAdapter = new CategoryListAdapter(context, brandList);
        }
        leftListView.setAdapter(brandAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                brandAdapter.setCheckItem(position);
                tvBrand.setText(brandList.get(position).getName());
                hidePopup();
                if (onFilterClickListener != null) {
                    onFilterClickListener.onBrandItemClick(brandList.get(position).getId());
                }
            }
        });
    }

    //设置商圈数据
    private void setBusinessCircleAdapter() {
        if (!isPopupShowing) {
            isPopupShowing = true;
            showPopup();
        } else {
            if (isCircleShowing) {
                hidePopup();
                return;
            }
        }
        isCategoryShowing = false;
        isCircleShowing = true;

        tvCircle.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCircleArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        rightListView.setVisibility(VISIBLE);

        if (leftCircleList.isEmpty()) {
            return;
        }

        //初始化右边列表值
        if (leftSelectedPosition == -1) {
            if (leftCircleList.get(0) != null) {
                rightCircleList = leftCircleList.get(0).getDistrictValues();
            }
        } else {
            rightCircleList = leftCircleList.get(leftSelectedPosition).getDistrictValues();
        }

        // 左边列表
        leftCircleAdapter = new LeftFilterAdapter(context, leftCircleList);
        leftListView.setAdapter(leftCircleAdapter);
        if (leftSelectedPosition != -1) {
            leftListView.setSelection(leftSelectedPosition);
            leftCircleAdapter.setSelectedBean(leftCircleList.get(leftSelectedPosition));
        } else {
            leftListView.setSelection(0);
//            leftCircleAdapter.setSelectedBean(leftCircleList.get(0));
        }

        //右边列表
        rightCircleAdapter = new RightFilterAdapter(context, rightCircleList);
        rightListView.setAdapter(rightCircleAdapter);
        if (rightSelectedPosition != -1) {
            rightListView.setSelection(rightSelectedPosition);
            rightCircleAdapter.setSelectedBean(rightCircleList.get(rightSelectedPosition));
        }

        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int leftPosition, long id) {
                leftCircleAdapter.setCheckItem(leftPosition);       //临时改变左边选中item的状态

                if (leftCircleList.get(leftPosition) != null) {       //更新右边列表数据
                    rightCircleList = leftCircleList.get(leftPosition).getDistrictValues();
                    rightCircleAdapter.setCircleDescBeanList(rightCircleList);
                }

                if (leftPosition != leftSelectedPosition) {           //若左边列表实际选中的位置不是当前点击位置 去掉右边列表之前选中的item 并将右边列表滑至顶端
                    rightCircleAdapter.setSelectedBean(null);
                    rightListView.setSelection(0);
                } else {                                             //若左边列表实际选中的位置是当前点击位置 将右边列表之前选中的item滑至顶端
                    rightListView.setSelection(rightSelectedPosition);
                }

                rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int rightPosition, long id) {
                        hidePopup();
                        leftSelectedPosition = leftPosition;             //改变左边选中item的状态
                        leftCircleAdapter.setSelectedBean(leftCircleList.get(leftSelectedPosition));

                        rightSelectedPosition = rightPosition;           //改变右边选中item
                        rightCircleAdapter.setSelectedBean(rightCircleList.get(rightSelectedPosition));
                        String address = rightCircleList.get(rightSelectedPosition).getArea();
                        tvCircle.setText(address);
                        if (onFilterClickListener != null) {
                            onFilterClickListener.onBusinessCircleItemClick(address);
                        }
                    }
                });
            }
        });
    }

    // 动画显示
    private void showPopup() {
        isPopupShowing = true;
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
    public void hidePopup() {
        isPopupShowing = false;
        resetBrandStatus();
        resetBusinessCircleStatus();
        maskBgView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(contentLayout, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 复位品牌的显示状态
    public void resetBrandStatus() {
        tvBrand.setTextColor(context.getResources().getColor(R.color.black));
        ivBrandArrow.setImageResource(R.drawable.icon_filter_arrow);
    }

    // 复位商圈的显示状态
    public void resetBusinessCircleStatus() {
        tvCircle.setTextColor(context.getResources().getColor(R.color.black));
        ivCircleArrow.setImageResource(R.drawable.icon_filter_arrow);
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onBrandItemClick(String brandId);

        void onBusinessCircleItemClick(String address);
    }

    public void setBrandList(List<CategoryBean> brandList) {
        if (brandList != null)
            this.brandList = brandList;
    }

    public void setCircleList(List<DistrictBean> circleList) {
        if (circleList != null)
            this.leftCircleList = circleList;
    }

    public boolean isPopupShowing() {
        return isPopupShowing;
    }

    public void dismissPopup() {
        hidePopup();
    }

    public void selectCategory(String id) {
        if (brandList == null || brandList.isEmpty()) {
            return;
        }

        for (int i = 0; i < brandList.size(); i++) {
            if (TextUtils.equals(brandList.get(i).getId(), id)) {
                if (brandAdapter == null) {
                    brandAdapter = new CategoryListAdapter(context, brandList);
                    leftListView.setAdapter(brandAdapter);
                }
                brandAdapter.setCheckItem(i);
                tvBrand.setText(brandList.get(i).getName());
                break;
            }
        }
    }
}
