package com.leyuan.aidong.ui.home.view;

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
import com.leyuan.aidong.adapter.home.CategoryListAdapter;
import com.leyuan.aidong.adapter.home.LeftFilterAdapter;
import com.leyuan.aidong.adapter.home.RightFilterAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.DistrictDescBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程列表筛选控件
 * Created by song on 2016/10/31.
 * //todo 混乱.............
 */
public class CourseFilterView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private LinearLayout categoryLayout;
    private TextView tvCategory;
    private ImageView ivCategoryArrow;
    private LinearLayout circleLayout;
    private TextView tvCircle;
    private ImageView ivCircleArrow;
    private View maskBgView;
    private LinearLayout contentLayout;
    private ListView leftListView;
    private ListView rightListView;

    //分类
    private CategoryListAdapter categoryAdapter;
    private List<CategoryBean> categoryList = new ArrayList<>();

    //商圈
    private LeftFilterAdapter leftCircleAdapter;
    private RightFilterAdapter rightCircleAdapter;
    private int leftSelectedPosition = -1;  //左边列表实际选中的位置 ：只有当右边列表选中其中一个item时，此时的左边列表实际位置才确定，否则当临时选中位置处理，即只改变选中的效果
    private int rightSelectedPosition = -1; //右边列表实际选中的位置
    private List<DistrictBean> leftCircleList = new ArrayList<>();
    private List<DistrictDescBean> rightCircleList = new ArrayList<>();
    private int tempLeftPosition = 0;

    private int panelHeight;
    private boolean isPopupShowing = false;
    private boolean isCategoryShowing = false;
    private boolean isCircleShowing = false;


    public CourseFilterView(Context context) {
        this(context, null);
    }

    public CourseFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        setListener();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_course_filter, this);
        categoryLayout = (LinearLayout) view.findViewById(R.id.ll_brand);
        tvCategory = (TextView) view.findViewById(R.id.tv_brand);
        ivCategoryArrow = (ImageView) view.findViewById(R.id.iv_brand_arrow);
        circleLayout = (LinearLayout) view.findViewById(R.id.ll_business_circle);
        tvCircle = (TextView) view.findViewById(R.id.tv_circle);
        ivCircleArrow = (ImageView) view.findViewById(R.id.iv_circle_arrow);
        maskBgView = view.findViewById(R.id.view_mask_bg);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        leftListView = (ListView) view.findViewById(R.id.list_left);
        rightListView = (ListView) view.findViewById(R.id.list_right);
    }

    private void setListener() {
        categoryLayout.setOnClickListener(this);
        circleLayout.setOnClickListener(this);
        maskBgView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_brand:
                resetBusinessCircleStatus();
                setCategoryAdapter();
                break;
            case R.id.ll_business_circle:
                resetCategoryStatus();
                setBusinessCircleAdapter();
                break;
            case R.id.view_mask_bg:
                hidePopup();
                break;
            default:
                break;
        }
    }

    // 设置分类数据
    private void setCategoryAdapter() {
        if (!isPopupShowing) {
            isPopupShowing = true;
            showPopup();
        } else if (isCategoryShowing) {
            hidePopup();
            return;
        }
        isCategoryShowing = true;
        isCircleShowing = false;
        tvCategory.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        rightListView.setVisibility(GONE);
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryListAdapter(context, categoryList);
        }
        leftListView.setAdapter(categoryAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setCheckItem(position);
                tvCategory.setText(categoryList.get(position).getName());
                hidePopup();
                if (onFilterClickListener != null) {
                    //全部课程类型返回空
                    onFilterClickListener.onCategoryItemClick(context.getString(R.string.course_all_type).equals(categoryList.get(position).getId())
                            ? "" : categoryList.get(position).getId());
                }
            }
        });
    }

    //设置商圈数据
    private void setBusinessCircleAdapter() {
        if (!isPopupShowing) {
            isPopupShowing = true;
            showPopup();
        } else if (isCircleShowing) {
            hidePopup();
            return;
        }
        isCategoryShowing = false;
        isCircleShowing = true;

        tvCircle.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCircleArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        rightListView.setVisibility(VISIBLE);

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
            leftCircleAdapter.setSelectedBean(leftCircleList.get(0));
        }

        //右边列表
        rightCircleAdapter = new RightFilterAdapter(context, rightCircleList);
        rightListView.setAdapter(rightCircleAdapter);
        if (rightSelectedPosition != -1) {
            rightListView.setSelection(rightSelectedPosition);
            rightCircleAdapter.setSelectedBean(rightCircleList.get(rightSelectedPosition));
        } else {
            rightListView.setSelection(0);
            rightCircleAdapter.setSelectedBean(rightCircleList.get(0));
        }

        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int leftPosition, long id) {
                tempLeftPosition = leftPosition;
                leftCircleAdapter.setCheckItem(leftPosition);       //临时改变左边选中item的状态

                if (leftCircleList.get(leftPosition) != null) {       //更新右边列表数据
                    rightCircleList = leftCircleList.get(leftPosition).getDistrictValues();
                    rightCircleAdapter.setCircleDescBeanList(rightCircleList);
                }

                if (leftPosition != leftSelectedPosition) {           //若左边列表实际选中的左边列表不是当前点击位置 去掉之前有右边列表之前选中的item 并将右边列表滑至顶端
                    rightCircleAdapter.setSelectedBean(null);
                    rightListView.setSelection(0);
                } else {                                             //若左边列表实际选中的左边列表是当前点击位置 将右边列表滑至顶端
                    rightListView.setSelection(rightSelectedPosition);
                }
            }
        });

        rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int rightPosition, long id) {
                hidePopup();
                leftSelectedPosition = tempLeftPosition;        //改变左边选中item的状态
                leftCircleAdapter.setSelectedBean(leftCircleList.get(leftSelectedPosition));

                rightSelectedPosition = rightPosition;           //改变右边选中item
                rightCircleAdapter.setSelectedBean(rightCircleList.get(rightSelectedPosition));
                String address = rightCircleList.get(rightSelectedPosition).getArea();
                tvCircle.setText(address);
                if (onFilterClickListener != null) {
                    onFilterClickListener.onBusinessCircleItemClick(context.getString(R.string.all_circle).equals(address) ? "" : address);
                }
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
        resetCategoryStatus();
        resetBusinessCircleStatus();
        maskBgView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(contentLayout, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    public boolean isPopupShowing() {
        return isPopupShowing;
    }

    // 复位分类的显示状态
    public void resetCategoryStatus() {
        tvCategory.setTextColor(context.getResources().getColor(R.color.black));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow);
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
        void onCategoryItemClick(String category);

        void onBusinessCircleItemClick(String address);
    }

    public void setCategoryList(List<CategoryBean> categoryList) {
        if (categoryList != null)
            this.categoryList = categoryList;
    }

    public void setCircleList(List<DistrictBean> circleList) {
        if (circleList != null)
            this.leftCircleList = circleList;
    }

    public void selectCategory(String category) {
        if (categoryList == null || categoryList.isEmpty() || TextUtils.isEmpty(category)) {
            return;
        }
        for (int i = 0; i < categoryList.size(); i++) {
            if (category.equals(categoryList.get(i).getName())) {
                if (categoryAdapter == null) {
                    categoryAdapter = new CategoryListAdapter(context, categoryList);
                    leftListView.setAdapter(categoryAdapter);
                }
                tvCategory.setText(category);
                categoryAdapter.setCheckItem(i);
                break;
            }
        }
    }
}
