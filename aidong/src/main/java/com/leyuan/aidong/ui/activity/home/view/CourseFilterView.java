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
 * 课程列表筛选控件
 * Created by song on 2016/10/31.
 */
public class CourseFilterView extends LinearLayout implements View.OnClickListener {
    private Context context;

    private LinearLayout filterLayout;
    private LinearLayout categoryLayout;
    private TextView tvCategory;
    private ImageView ivCategoryArrow;
    private LinearLayout circleLayout;
    private TextView tvCircle;
    private ImageView ivCircleArrow;
    private View maskBgView;
    private LinearLayout contentLayout;
    private ListView listLeft;
    private ListView listRight;

    private ListWithFlagAdapter categoryAdapter;
    private List<String> categoryList = new ArrayList<>();

    private boolean isPopupShowing = false;
    private int panelHeight;

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
        filterLayout = (LinearLayout) view.findViewById(R.id.ll_filter_layout);
        categoryLayout = (LinearLayout) view.findViewById(R.id.ll_category);
        tvCategory = (TextView) view.findViewById(R.id.tv_category);
        ivCategoryArrow = (ImageView) view.findViewById(R.id.iv_category_arrow);
        circleLayout = (LinearLayout) view.findViewById(R.id.ll_business_circle);
        tvCircle = (TextView) view.findViewById(R.id.tv_circle);
        ivCircleArrow = (ImageView) view.findViewById(R.id.iv_circle_arrow);
        maskBgView = view.findViewById(R.id.view_mask_bg);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        listLeft = (ListView) view.findViewById(R.id.list_left);
        listRight = (ListView) view.findViewById(R.id.list_right);
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
                break;
            case R.id.ll_business_circle:
                break;
            case R.id.view_mask_bg:
                break;
            default:
                break;
        }
    }

    // 设置分类数据
    private void setCategoryAdapter() {
        if (isPopupShowing){
            return;
        }
        isPopupShowing = true;
        showPopup();
        tvCategory.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        listRight.setVisibility(GONE);
        if (categoryList.isEmpty()) {
            Log.d("GoodsFilterView", "you need set categoryList data first");
        }
        if(categoryAdapter == null){
            categoryAdapter = new ListWithFlagAdapter(context, categoryList);
        }
        listLeft.setAdapter(categoryAdapter);
        listLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void setBusinessCircleAdapter(){
        if (isPopupShowing){
            return;
        }
        isPopupShowing = true;
        showPopup();
        tvCircle.setTextColor(context.getResources().getColor(R.color.main_red));
        ivCircleArrow.setImageResource(R.drawable.icon_filter_arrow_selected);
        contentLayout.setVisibility(VISIBLE);
        listRight.setVisibility(VISIBLE);

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
        maskBgView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(contentLayout, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 复位分类的显示状态
    public void resetCategoryStatus() {
        tvCategory.setTextColor(context.getResources().getColor(R.color.black));
        ivCategoryArrow.setImageResource(R.drawable.icon_filter_arrow);
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface  OnFilterClickListener{
        void onCategoryItemClick(int position);
        void onBusinessCircleItemClick(String address);
    }
}
