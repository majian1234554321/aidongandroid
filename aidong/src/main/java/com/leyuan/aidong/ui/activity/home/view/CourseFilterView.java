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
import com.leyuan.aidong.entity.BusinessCircleBean;
import com.leyuan.aidong.entity.BusinessCircleDescBean;
import com.leyuan.aidong.ui.activity.home.adapter.LeftFilterAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.RightFilterAdapter;
import com.leyuan.aidong.widget.dropdownmenu.adapter.ListWithFlagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程列表筛选控件
 * Created by song on 2016/10/31.
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
    private ListView leftList;
    private ListView rightList;

    //分类
    private ListWithFlagAdapter categoryAdapter;
    private List<String> categoryList = new ArrayList<>();

    //商圈
    private LeftFilterAdapter leftCircleAdapter;
    private RightFilterAdapter rightCircleAdapter;
    private BusinessCircleBean leftSelectedCircle;
    private BusinessCircleDescBean rightSelectedCircle;
    private List<BusinessCircleBean> leftCircleList = new ArrayList<>();
    private List<BusinessCircleDescBean> rightCircleList = new ArrayList<>();

    private int panelHeight;
    private boolean isPopupShowing = false;

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

    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_course_filter, this);
        categoryLayout = (LinearLayout) view.findViewById(R.id.ll_category);
        tvCategory = (TextView) view.findViewById(R.id.tv_category);
        ivCategoryArrow = (ImageView) view.findViewById(R.id.iv_category_arrow);
        circleLayout = (LinearLayout) view.findViewById(R.id.ll_business_circle);
        tvCircle = (TextView) view.findViewById(R.id.tv_circle);
        ivCircleArrow = (ImageView) view.findViewById(R.id.iv_circle_arrow);
        maskBgView = view.findViewById(R.id.view_mask_bg);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        leftList = (ListView) view.findViewById(R.id.list_left);
        rightList = (ListView) view.findViewById(R.id.list_right);
    }

    private void setListener(){
        categoryLayout.setOnClickListener(this);
        circleLayout.setOnClickListener(this);
        maskBgView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_category:
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
        if (!isPopupShowing){
            isPopupShowing = true;
            showPopup();
        }
        tvCategory.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        rightList.setVisibility(GONE);
        if (categoryList.isEmpty()) {
            Log.d("CourseFilterView", "you need set categoryList data first");
        }
        if(categoryAdapter == null){
            categoryAdapter = new ListWithFlagAdapter(context, categoryList);
        }
        leftList.setAdapter(categoryAdapter);
        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setCheckItem(position);
                tvCategory.setText(categoryList.get(position));
                hidePopup();
                if (onFilterClickListener != null) {
                    onFilterClickListener.onCategoryItemClick(position);
                }
            }
        });
    }

    //设置商圈数据
    private void setBusinessCircleAdapter(){
        if (!isPopupShowing){
            isPopupShowing = true;
            showPopup();
        }
        tvCircle.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCircleArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        rightList.setVisibility(VISIBLE);

        if(leftSelectedCircle == null && leftCircleList.get(0) != null){
            leftSelectedCircle = leftCircleList.get(0);

        }

        // 左边列表
        leftCircleAdapter = new LeftFilterAdapter(context, leftCircleList);
        leftList.setAdapter(leftCircleAdapter);
        leftList.smoothScrollToPosition(8);
        leftCircleAdapter.setSelectedBean(leftSelectedCircle);

        //右边列表
        if(rightSelectedCircle == null){
            rightCircleList = leftCircleList.get(0).getDistrict();
            rightCircleAdapter = new RightFilterAdapter(context,rightCircleList);
            rightList.setAdapter(rightCircleAdapter);
        }



        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                leftSelectedCircle = leftCircleList.get(position);
                leftCircleAdapter.setSelectedBean(leftSelectedCircle);
                if(leftCircleList.get(position) != null){
                    rightCircleList = leftCircleList.get(position).getDistrict();
                    rightCircleAdapter.setCircleDescBeanList(rightCircleList);
                }
            }
        });

        rightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hidePopup();
                rightSelectedCircle = rightCircleList.get(position);
                //leftSelectedCircle.setSelectedRightCircleBean(rightSelectedCircle);
                rightCircleAdapter.setSelectedBean(rightSelectedCircle);
                String address = rightSelectedCircle.getAreaName();
                tvCircle.setText(address);
                if (onFilterClickListener != null) {
                    onFilterClickListener.onBusinessCircleItemClick(address);
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

    // 复位分类的显示状态
    public void resetCategoryStatus() {
        tvCategory.setTextColor(context.getResources().getColor(R.color.black));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow);
    }

    // 复位商圈的显示状态
    public void resetBusinessCircleStatus(){
        tvCircle.setTextColor(context.getResources().getColor(R.color.black));
        ivCircleArrow.setImageResource(R.drawable.icon_filter_arrow);
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface  OnFilterClickListener{
        void onCategoryItemClick(int position);
        void onBusinessCircleItemClick(String address);
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCircleList(List<BusinessCircleBean> circleList) {
        this.leftCircleList = circleList;
    }
}
