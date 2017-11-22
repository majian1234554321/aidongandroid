package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.course.CourseAreaFilterAdapter;
import com.leyuan.aidong.adapter.course.CourseBrandFilterAdapter;
import com.leyuan.aidong.adapter.course.CourseCategoryFilterAdapter;
import com.leyuan.aidong.adapter.course.CourseStoreFilterAdapter;
import com.leyuan.aidong.adapter.course.CourseTypePriceFilterAdapter;
import com.leyuan.aidong.adapter.home.RightFilterAdapter;
import com.leyuan.aidong.entity.course.CourseArea;
import com.leyuan.aidong.entity.course.CourseBrand;
import com.leyuan.aidong.entity.course.CourseFilterBean;
import com.leyuan.aidong.entity.course.CourseName;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;

import static com.leyuan.aidong.R.id.layout_store_content_all;

/**
 * 课程列表筛选控件
 * Created by song on 2016/10/31.
 * //todo 混乱.............
 */
public class CourseListFilterNew extends LinearLayout implements View.OnClickListener {
    Context context;
    private LinearLayout llStore;
    private TextView tvStore;
    private ImageView ivStoreArrow;
    private LinearLayout llCourseName;
    private TextView tvCourseName;
    private ImageView ivCourseNameArrow;
    private LinearLayout llTimeFrame;
    private TextView tvTimeFrame;
    private ImageView ivTimeArrow;
    private LinearLayout llFilter;
    private TextView tvFilter;
    private ImageView ivFilterArrow;
    private RelativeLayout layoutStoreContentRoot;
    private LinearLayout layoutStoreBelong;
    private TextView tvStoreAll;
    private TextView tvStoreMine;
    private LinearLayout layoutStoreContentAll;
    private ListView listStoreLeftAll;
    private ListView listStoreMiddleAll;
    private ListView listStoreRightAll;
    private LinearLayout layoutStoreContentMine;
    private ListView listStoreLeftMine;
    private ListView listStoreRightMine;
    private LinearLayout layoutCourseContent;
    private ListView listCourseLeft;
    private ListView listCourseRight;
    View view_mask_bg;

    private CourseBrandFilterAdapter adapterAllBrand;
    private CourseAreaFilterAdapter adapterAllArea;
    private CourseStoreFilterAdapter adapterAllStore;

    private CourseTypePriceFilterAdapter adapterTypePrice;
    private CourseCategoryFilterAdapter adapterCategoty;

    private RightFilterAdapter rightCircleAdapter;
    private ArrayList<CourseBrand> courseBrands;
    private CourseBrand mineCourseBrand;

    private boolean storeContentAllIsShowing, storeContentMineIsShowing, courseIsShowing, timeIsShowing;

    private CourseArea currentArea;
    private CourseStore currentStore;
    private CourseBrand currentBrand;

    private ArrayList<CourseStore> currentStoreList;
    private ArrayList<CourseArea> currentAreaList;
    private CourseName courseType;
    private String currentCoursePriceType;
    private ArrayList<String> currentCourseCategoryList;
    private String currentCourseCategory;

    public CourseListFilterNew(Context context) {
        this(context, null);
    }

    public CourseListFilterNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseListFilterNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        setListener();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_course_list_filter_new, this);
        llStore = (LinearLayout) view.findViewById(R.id.ll_store);
        tvStore = (TextView) view.findViewById(R.id.tv_store);
        ivStoreArrow = (ImageView) view.findViewById(R.id.iv_store_arrow);
        llCourseName = (LinearLayout) view.findViewById(R.id.ll_course_name);
        tvCourseName = (TextView) view.findViewById(R.id.tv_course_name);
        ivCourseNameArrow = (ImageView) view.findViewById(R.id.iv_course_name_arrow);
        llTimeFrame = (LinearLayout) view.findViewById(R.id.ll_time_frame);
        tvTimeFrame = (TextView) view.findViewById(R.id.tv_time_frame);
        ivTimeArrow = (ImageView) view.findViewById(R.id.iv_time_arrow);
        llFilter = (LinearLayout) view.findViewById(R.id.ll_filter);
        tvFilter = (TextView) view.findViewById(R.id.tv_filter);
        ivFilterArrow = (ImageView) view.findViewById(R.id.iv_filter_arrow);
        layoutStoreContentRoot = (RelativeLayout) view.findViewById(R.id.layout_store_content_root);
        layoutStoreBelong = (LinearLayout) view.findViewById(R.id.layout_store_belong);
        tvStoreAll = (TextView) view.findViewById(R.id.tv_store_all);
        tvStoreMine = (TextView) view.findViewById(R.id.tv_store_mine);
        layoutStoreContentAll = (LinearLayout) view.findViewById(layout_store_content_all);
        listStoreLeftAll = (ListView) view.findViewById(R.id.list_store_left_all);
        listStoreMiddleAll = (ListView) view.findViewById(R.id.list_store_middle_all);
        listStoreRightAll = (ListView) view.findViewById(R.id.list_store_right_all);
        layoutStoreContentMine = (LinearLayout) view.findViewById(R.id.layout_store_content_mine);
        listStoreLeftMine = (ListView) view.findViewById(R.id.list_store_left_mine);
        listStoreRightMine = (ListView) view.findViewById(R.id.list_store_right_mine);
        layoutCourseContent = (LinearLayout) view.findViewById(R.id.layout_course_content);
        listCourseLeft = (ListView) view.findViewById(R.id.list_course_left);
        listCourseRight = (ListView) view.findViewById(R.id.list_course_right);

        view_mask_bg = view.findViewById(R.id.view_mask_bg);
    }

    private void setListener() {
        llStore.setOnClickListener(this);
        llCourseName.setOnClickListener(this);
        llTimeFrame.setOnClickListener(this);
        view_mask_bg.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_store:
                if (isPopupShowing()) {
                    hidePopup();
                } else {
                    layoutStoreContentAll.setVisibility(VISIBLE);
                    storeContentAllIsShowing = true;
                }

                break;
            case R.id.ll_course_name:

                if (isPopupShowing()) {
                    hidePopup();
                } else {
                    layoutCourseContent.setVisibility(VISIBLE);
                    courseIsShowing = true;
                }

                break;
            case R.id.ll_time_frame:



                break;

            case R.id.view_mask_bg:
                break;
            default:
                break;
        }
    }

    public void setData(CourseFilterBean courseFilterConfig) {
        if (courseFilterConfig == null) return;
        this.courseBrands = courseFilterConfig.getCompany();
        this.mineCourseBrand = courseFilterConfig.getMine();
        this.courseType = courseFilterConfig.getCourse();
        Logger.i("Course", courseFilterConfig.toString());
        resetCurrentCategorySate(0, 0);

        adapterTypePrice = new CourseTypePriceFilterAdapter(context, courseType.getCourseTypePrice(), courseTypeItemClickListener);
        adapterCategoty = new CourseCategoryFilterAdapter(context, currentCourseCategoryList, courseCategoryListener);
        listCourseLeft.setAdapter(adapterTypePrice);
        listCourseRight.setAdapter(adapterCategoty);

        resetCurrentStoreState(0, 0, 0);

        adapterAllBrand = new CourseBrandFilterAdapter(context, courseBrands, allBrandItemClickLister);
        adapterAllArea = new CourseAreaFilterAdapter(context, currentAreaList, allAreaItemCLickLister);
        adapterAllStore = new CourseStoreFilterAdapter(context, currentStoreList, allStoreItemClickListener);

        listStoreLeftAll.setAdapter(adapterAllBrand);
        listStoreMiddleAll.setAdapter(adapterAllArea);
        listStoreRightAll.setAdapter(adapterAllStore);
    }

    private CourseTypePriceFilterAdapter.OnLeftItemClickListener courseTypeItemClickListener = new CourseTypePriceFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            resetCurrentCategorySate(position, 0);
            adapterCategoty.refreshData(currentCourseCategoryList);
        }
    };


    private CourseCategoryFilterAdapter.OnLeftItemClickListener courseCategoryListener = new CourseCategoryFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            resetCurrentCategorySate(-1, position);
            if (listener != null) {
                listener.onCourseCategoryItemClick(currentCoursePriceType, currentCourseCategory);
            }
            hidePopup();
        }
    };

    private void resetCurrentCategorySate(int pricePostion, int categoryPostion) {
        if (courseType != null && pricePostion > -1) {

            currentCoursePriceType = courseType.getCourseTypePrice().get(pricePostion);
            switch (pricePostion) {
                case 0:
                    currentCourseCategoryList = courseType.getAll();
                    break;
                case 1:
                    currentCourseCategoryList = courseType.getFree();
                    break;
                case 2:
                    currentCourseCategoryList = courseType.getTuition();
                    break;
            }
        }

        if (currentCourseCategoryList != null && categoryPostion > -1 && categoryPostion < currentCourseCategoryList.size()) {
            currentCourseCategory = currentCourseCategoryList.get(categoryPostion);
        }

    }

    private void resetCurrentStoreState(int brandPostion, int areaPostion, int storePostion) {

        if (courseBrands != null && brandPostion > -1 && brandPostion < courseBrands.size()) {
            currentBrand = courseBrands.get(brandPostion);
            currentAreaList = currentBrand.getArea();
        }

        if (currentAreaList != null && areaPostion > -1 && areaPostion < currentAreaList.size()) {
            currentArea = currentAreaList.get(areaPostion);
            currentStoreList = currentArea.getStore();
        }

        if (currentStoreList != null && storePostion > -1 && storePostion < currentStoreList.size()) {
            currentStore = currentStoreList.get(storePostion);
        }

    }


    private CourseBrandFilterAdapter.OnLeftItemClickListener allBrandItemClickLister = new CourseBrandFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            resetCurrentStoreState(position, 0, 0);

            //刷新区域adapter
            adapterAllArea.refreshData(currentAreaList);
            //区域刷新 门店必须刷新 选区域第0个
            adapterAllStore.refreshData(currentStoreList);

        }
    };


    private CourseAreaFilterAdapter.OnLeftItemClickListener allAreaItemCLickLister = new CourseAreaFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            //刷新门店adapter
            resetCurrentStoreState(-1, position, 0);
            adapterAllStore.refreshData(currentStoreList);
        }

    };


    private CourseStoreFilterAdapter.OnLeftItemClickListener allStoreItemClickListener = new CourseStoreFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            resetCurrentStoreState(-1, -1, position);
            if (listener != null) {
                listener.onAllStoreItemClick(currentBrand, currentArea, currentStore);
            }
            hidePopup();
        }
    };

    public boolean isPopupShowing() {
        return storeContentAllIsShowing || storeContentMineIsShowing || courseIsShowing || timeIsShowing;
    }

    public void hidePopup() {
        layoutStoreBelong.setVisibility(GONE);
        layoutStoreContentMine.setVisibility(GONE);
        layoutCourseContent.setVisibility(GONE);
        layoutStoreContentAll.setVisibility(GONE);

        storeContentAllIsShowing = false;
        storeContentMineIsShowing = false;
        courseIsShowing = false;
        timeIsShowing = false;
    }

    private OnCourseListFilterListener listener;

    public void setListener(OnCourseListFilterListener listener) {
        this.listener = listener;
    }

    public interface OnCourseListFilterListener {
        void onAllStoreItemClick(CourseBrand currentBrand, CourseArea currentArea, CourseStore currentStore);

        void onCourseCategoryItemClick(String currentCoursePriceType, String currentCourseCategory);
    }
}
