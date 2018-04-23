package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.leyuan.aidong.widget.RangeSeekBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.leyuan.aidong.R.id.layout_store_content_all;
import static com.leyuan.aidong.R.id.left;
import static com.leyuan.aidong.R.id.switch1;
import static com.leyuan.aidong.R.id.tv_store_all;

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
    private TextView tvFilter, tv_reset003, tv_true003;
    private ImageView ivFilterArrow;
    private RelativeLayout layoutStoreContentRoot;
    private LinearLayout layoutStoreBelong;
    private TextView tvStoreAll, tv_time;
    private TextView tvStoreMine;
    private LinearLayout layoutStoreContentAll;
    private ListView listStoreLeftAll;
    private ListView listStoreMiddleAll;
    private ListView listStoreRightAll;
    private LinearLayout layoutStoreContentMine, ll003;

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

    private boolean storeContentRootIsShowing, storeContentAllIsShowing, storeContentMineIsShowing, courseIsShowing, timeIsShowing;

    private CourseArea currentArea;
    private CourseStore currentStore;
    private CourseBrand currentBrand;

    private ArrayList<CourseStore> currentStoreList;
    private ArrayList<CourseArea> currentAreaList;
    private CourseName courseType;
    private String currentCoursePriceType;

    private String currentCourseCategory;

    private ArrayList<CourseArea> currentMineAreaList;
    private CourseArea currentMineArea;
    private ArrayList<CourseStore> currentMineStoreList;
    private CourseStore currentMineStore;
    private CourseAreaFilterAdapter adapterMineArea;
    private CourseStoreFilterAdapter adapterMineStore;
    private List<List<CourseName.CategoryModelItem>> rightlist;
    private ArrayList<String> leftlist;
    private TextView rb1;
    private TextView rb2;
    private TextView rb3;
    private TextView rb4;
    private RangeSeekBar seekbar3;
    public String startTime = "0", endTime = "24";

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
        tvStoreAll = (TextView) view.findViewById(tv_store_all);
        tvStoreMine = (TextView) view.findViewById(R.id.tv_store_mine);
        layoutStoreContentAll = (LinearLayout) view.findViewById(layout_store_content_all);
        listStoreLeftAll = (ListView) view.findViewById(R.id.list_store_left_all);
        listStoreMiddleAll = (ListView) view.findViewById(R.id.list_store_middle_all);
        listStoreRightAll = (ListView) view.findViewById(R.id.list_store_right_all);
        layoutStoreContentMine = (LinearLayout) view.findViewById(R.id.layout_store_content_mine);
        listStoreLeftMine = (ListView) view.findViewById(R.id.list_store_left_mine);
        listStoreRightMine = (ListView) view.findViewById(R.id.list_store_right_mine);
        layoutCourseContent = (LinearLayout) view.findViewById(R.id.layout_course_content);

        ll003 = (LinearLayout) view.findViewById(R.id.ll003);
        LinearLayout lls = (LinearLayout) view.findViewById(R.id.lls);

        lls.setOnClickListener(this);
        listCourseLeft = (ListView) view.findViewById(R.id.list_course_left);
        listCourseRight = (ListView) view.findViewById(R.id.list_course_right);

        view_mask_bg = view.findViewById(R.id.view_mask_bg);
        LinearLayout rg = view.findViewById(R.id.rg);
        rb1 = view.findViewById(R.id.rb1);
        rb2 = view.findViewById(R.id.rb2);
        rb3 = view.findViewById(R.id.rb3);
        rb4 = view.findViewById(R.id.rb4);

        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_reset003 = (TextView) view.findViewById(R.id.tv_reset003);
        tv_true003 = (TextView) view.findViewById(R.id.tv_true003);
        tv_true003.setOnClickListener(this);
        tv_reset003.setOnClickListener(this);

        seekbar3 = (RangeSeekBar) view.findViewById(R.id.seekbar3);


        seekbar3.setValue(0, 24);

        seekbar3.setLeftProgressDescription(startTime + ":00");
        seekbar3.setRightProgressDescription(endTime + ":00");
        tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");

        seekbar3.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    //  tv2.setText(min + "-" + max);


                    rb1.setTextColor(ContextCompat.getColor(context, R.color.gray));
                    rb2.setTextColor(ContextCompat.getColor(context, R.color.gray));
                    rb3.setTextColor(ContextCompat.getColor(context, R.color.gray));
                    rb4.setTextColor(ContextCompat.getColor(context, R.color.gray));

                    seekbar3.setLeftProgressDescription((int) min + ":00");

                    seekbar3.setRightProgressDescription((int) max + ":00");

                    startTime = (int) min + "";
                    endTime = (int) max + "";
                    tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");

                    if ((int) min == 0 && (int) max == 24) {

                        rb1.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                        rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                        rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                    } else if ((int) min >= 0 && (int) max <= 12) {

                        rb2.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                        rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                        rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                    } else if ((int) min >= 12 && (int) max <= 18) {

                        rb3.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                        rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                        rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                    } else if ((int) min >= 18 && (int) max <= 24) {

                        rb4.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                        rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                    } else {


                        rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                        rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                    }


                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }
        });


        rb1.setOnClickListener(new MyOnClickListener());
        rb2.setOnClickListener(new MyOnClickListener());
        rb3.setOnClickListener(new MyOnClickListener());
        rb4.setOnClickListener(new MyOnClickListener());


    }

    private void setListener() {
        llStore.setOnClickListener(this);
        llCourseName.setOnClickListener(this);
        llTimeFrame.setOnClickListener(this);
        view_mask_bg.setOnClickListener(this);
        tvStoreAll.setOnClickListener(this);
        tvStoreMine.setOnClickListener(this);
    }


    public class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            rb1.setTextColor(ContextCompat.getColor(context, R.color.gray));
            rb2.setTextColor(ContextCompat.getColor(context, R.color.gray));
            rb3.setTextColor(ContextCompat.getColor(context, R.color.gray));
            rb4.setTextColor(ContextCompat.getColor(context, R.color.gray));
            rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
            rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
            rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
            rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));

            switch (v.getId()) {
                case R.id.rb1:
                    rb1.setTextColor(ContextCompat.getColor(context, R.color.red_price));

                    seekbar3.setValue(0, 24);
                    seekbar3.setLeftProgressDescription(0 + ":00");
                    seekbar3.setRightProgressDescription(24 + ":00");
                    rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));

                    startTime = "0";
                    endTime = "24";
                    tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");

                    break;
                case R.id.rb2:

                    rb2.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                    seekbar3.setValue(0, 12);
                    seekbar3.setLeftProgressDescription(0 + ":00");
                    seekbar3.setRightProgressDescription(12 + ":00");
                    rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                    startTime = "0";
                    endTime = "12";
                    tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");
                    break;
                case R.id.rb3:

                    rb3.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                    seekbar3.setValue(12, 18);
                    seekbar3.setLeftProgressDescription(12 + ":00");
                    seekbar3.setRightProgressDescription(18 + ":00");
                    rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                    startTime = "12";
                    endTime = "18";
                    tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");
                    break;
                case R.id.rb4:
                    rb4.setTextColor(ContextCompat.getColor(context, R.color.red_price));
                    seekbar3.setValue(18, 24);
                    seekbar3.setLeftProgressDescription(18 + ":00");
                    seekbar3.setRightProgressDescription(24 + ":00");
                    rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));
                    startTime = "18";
                    endTime = "24";
                    tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_store:

                if (storeContentRootIsShowing) {
                    hidePopup();
                } else if (isPopupShowing()) {
                    hidePopup();
                    view_mask_bg.setVisibility(VISIBLE);
                    layoutStoreContentRoot.setVisibility(VISIBLE);
                    storeContentRootIsShowing = true;
                } else {
                    view_mask_bg.setVisibility(VISIBLE);
                    layoutStoreContentRoot.setVisibility(VISIBLE);
                    storeContentRootIsShowing = true;
                }

                break;
            case R.id.tv_store_all:
                tvStoreAll.setTextColor(getResources().getColor(R.color.main_red));
                tvStoreMine.setTextColor(getResources().getColor(R.color.c3));
                layoutStoreContentAll.setVisibility(VISIBLE);
                layoutStoreContentMine.setVisibility(GONE);
                break;
            case R.id.tv_store_mine:

                tvStoreAll.setTextColor(getResources().getColor(R.color.c3));
                tvStoreMine.setTextColor(getResources().getColor(R.color.main_red));
                layoutStoreContentAll.setVisibility(GONE);
                layoutStoreContentMine.setVisibility(VISIBLE);

                break;

            case R.id.ll_course_name:
                if (courseIsShowing) {
                    hidePopup();
                } else if (isPopupShowing()) {
                    hidePopup();
                    view_mask_bg.setVisibility(VISIBLE);
                    layoutCourseContent.setVisibility(VISIBLE);
                    courseIsShowing = true;
                } else {
                    view_mask_bg.setVisibility(VISIBLE);
                    layoutCourseContent.setVisibility(VISIBLE);
                    courseIsShowing = true;
                }

                break;
            case R.id.ll_time_frame:
                if (timeIsShowing) {
                    hidePopup();
                } else if (isPopupShowing()) {

                    hidePopup();
                    ll003.setVisibility(VISIBLE);
                    view_mask_bg.setVisibility(VISIBLE);
                    timeIsShowing = true;
                } else {

                    hidePopup();
                    view_mask_bg.setVisibility(VISIBLE);
                    ll003.setVisibility(VISIBLE);
                    timeIsShowing = true;
                }

                break;

            case R.id.view_mask_bg:
                if (isPopupShowing()) {
                    hidePopup();
                }
                break;

            case R.id.tv_true003:

                if (listener != null) {
                    listener.onTimeItemClick(startTime + ":00," + endTime + ":00");
                }
                hidePopup();

                break;


            case R.id.tv_reset003:


                rb1.setTextColor(ContextCompat.getColor(context, R.color.gray));
                rb2.setTextColor(ContextCompat.getColor(context, R.color.gray));
                rb3.setTextColor(ContextCompat.getColor(context, R.color.gray));
                rb4.setTextColor(ContextCompat.getColor(context, R.color.gray));
                rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                rb2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                rb3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));
                rb4.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_gray_button));

                rb1.setTextColor(ContextCompat.getColor(context, R.color.red_price));

                seekbar3.setValue(0, 24);
                seekbar3.setLeftProgressDescription(0 + ":00");
                seekbar3.setRightProgressDescription(24 + ":00");
                startTime = "0";
                endTime = "24";
                tv_time.setText("当前时间段选择为:" + startTime + ":00-" + endTime + ":00");
                rb1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_stroke_red_button));

                break;

            default:
                break;
        }
    }

    public void setData(CourseFilterBean courseFilterConfig, String category) {
        if (courseFilterConfig == null) return;
        this.courseBrands = courseFilterConfig.getCompany();
        this.mineCourseBrand = courseFilterConfig.getMine();
        this.courseType = courseFilterConfig.getCourse();
        Logger.i("Course", courseFilterConfig.toString());


//        if ("全部课程".equals(category)) {
//            tvCourseName.setTextColor(category);
//        }else {
//            tvCourseName.setTextColor(ContextCompat.getColor(context,R.color.red_price));
//        }


        if (courseType != null) {
            //int startPosition = courseType.getCategoryByCategoryName(category);

            int startPosition = 0;
            leftlist = new ArrayList<>();
            rightlist = new ArrayList<>();
            if (courseType.category != null && courseType.category.size() > 0) {
                for (int i = 0; i < courseType.category.size(); i++) {
                    leftlist.add(courseType.category.get(i).name);
                    rightlist.add(courseType.category.get(i).item);
                }
            }


            adapterTypePrice = new CourseTypePriceFilterAdapter(context, leftlist, courseTypeItemClickListener);

            if (rightlist.size() > 0) {
                adapterCategoty = new CourseCategoryFilterAdapter(context, rightlist.get(0), courseCategoryListener);
                listCourseRight.setAdapter(adapterCategoty);
            }

            listCourseLeft.setAdapter(adapterTypePrice);

            if (leftlist.contains(category)){

              int index =    leftlist.indexOf(category);
                resetCurrentCategorySate(index, startPosition);

            }else {
                resetCurrentCategorySate(0, startPosition);
            }




            refreshtCategoryAdapater(0, startPosition);



                if (TextUtils.isEmpty(category) && leftlist != null && leftlist.size() > 0 && leftlist.contains(category))
                    tvCourseName.setText(category);
                else
                    tvCourseName.setText("全部课程");

        }else {
            tvCourseName.setText("全部课程");
        }


        if (mineCourseBrand != null) {
            resetCurrentMineStoreState(0, 0);
            adapterMineArea = new CourseAreaFilterAdapter(context, currentMineAreaList, mineAreaItemCLickLister);
            adapterMineStore = new CourseStoreFilterAdapter(context, currentMineStoreList, mineStoreItemClickListener);
            listStoreLeftMine.setAdapter(adapterMineArea);
            listStoreRightMine.setAdapter(adapterMineStore);
        } else {
            layoutStoreBelong.setVisibility(GONE);
        }

        if (courseBrands != null) {
            resetCurrentStoreState(0, 0, 0);
            adapterAllBrand = new CourseBrandFilterAdapter(context, courseBrands, allBrandItemClickLister);
            adapterAllArea = new CourseAreaFilterAdapter(context, currentAreaList, allAreaItemCLickLister);
            adapterAllStore = new CourseStoreFilterAdapter(context, currentStoreList, allStoreItemClickListener);

            listStoreLeftAll.setAdapter(adapterAllBrand);
            listStoreMiddleAll.setAdapter(adapterAllArea);
            listStoreRightAll.setAdapter(adapterAllStore);
        }


    }

    private void resetCurrentMineStoreState(int areaPostion, int storePostion) {

        if (mineCourseBrand != null) {
            currentMineAreaList = mineCourseBrand.getArea();
        }

        if (currentMineAreaList != null && areaPostion > -1 && areaPostion < currentMineAreaList.size()) {
            currentMineArea = currentMineAreaList.get(areaPostion);
            currentMineStoreList = currentMineArea.getStore();
        }

        if (currentMineStoreList != null && storePostion > -1 && storePostion < currentMineStoreList.size()) {
            currentMineStore = currentMineStoreList.get(storePostion);
        }

    }


    private CourseTypePriceFilterAdapter.OnLeftItemClickListener courseTypeItemClickListener = new CourseTypePriceFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            resetCurrentCategorySate(position, 0);
            refreshtCategoryAdapater(position, 0);
        }
    };


    private CourseCategoryFilterAdapter.OnLeftItemClickListener courseCategoryListener = new CourseCategoryFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            resetCurrentCategorySate(leftPostion, position);
            refreshtCategoryAdapater(leftPostion, position);
            if (listener != null) {
                listener.onCourseCategoryItemClick(leftlist.get(leftPostion), rightlist.get(leftPostion).get(position).id);
            }
            //   tvCourseName.setText(currentCourseCategory);
            hidePopup();
        }
    };

    private void refreshtCategoryAdapater(int pricePostion, int categoryPostion) {
        if (pricePostion > -1) {
            adapterTypePrice.refreshData(pricePostion);
        }
        if (categoryPostion > -1) {
            if (adapterCategoty != null)
                adapterCategoty.refreshData(rightlist.get(pricePostion), categoryPostion);
        }

    }


    public int leftPostion = 0;

    private void resetCurrentCategorySate(int pricePostion, int categoryPostion) {
        if (courseType != null && pricePostion > -1) {

            // currentCoursePriceType = courseType.getCourseTypePrice().get(pricePostion);
            if (adapterCategoty != null)
                adapterCategoty.refreshData(rightlist.get(leftPostion), categoryPostion);

            leftPostion = pricePostion;
            if (rightlist != null && rightlist.size() > 0)
                tvCourseName.setText(rightlist.get(leftPostion).get(categoryPostion).name);
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

    private void refreshAllStoreAdapater(int brandPostion, int areaPostion, int storePostion) {
        if (brandPostion > -1) {
            adapterAllBrand.refreshData(brandPostion);
        }
        if (areaPostion > -1) {
            adapterAllArea.refreshData(currentAreaList, areaPostion);
        }

        if (storePostion > -1) {
            adapterAllStore.refreshData(currentStoreList, storePostion);
        }
    }


    private void refreshMineStoreAdapater(int areaPostion, int storePostion) {
        if (areaPostion > -1) {
            adapterMineArea.refreshData(currentMineAreaList, areaPostion);
        }

        if (storePostion > -1) {
            adapterMineStore.refreshData(currentMineStoreList, storePostion);
        }
    }


    private CourseAreaFilterAdapter.OnLeftItemClickListener mineAreaItemCLickLister = new CourseAreaFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            //刷新门店adapter
            resetCurrentMineStoreState(position, 0);
            refreshMineStoreAdapater(position, 0);

//            resetCurrentStoreState(-1, position, 0);
//            refreshAllStoreAdapater(-1, position, 0);
        }

    };

    private CourseStoreFilterAdapter.OnLeftItemClickListener mineStoreItemClickListener = new CourseStoreFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {

            resetCurrentMineStoreState(-1, position);
            refreshMineStoreAdapater(-1, position);
            if (listener != null) {
                listener.onAllStoreItemClick(mineCourseBrand, currentMineArea, currentMineStore);
            }

            if (TextUtils.equals(currentMineStore.getName(), "全部门店")) {
                if (TextUtils.equals(currentMineArea.getName(), "全部区域")) {
                    tvStore.setText("我的" + currentMineStore.getName());
                } else {
                    tvStore.setText("我的" + currentMineArea.getName() + currentMineStore.getName());
                }
            } else {
                tvStore.setText(currentMineStore.getName());
            }

            hidePopup();
        }
    };


    private CourseBrandFilterAdapter.OnLeftItemClickListener allBrandItemClickLister = new CourseBrandFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {

            resetCurrentStoreState(position, 0, 0);
            refreshAllStoreAdapater(position, 0, 0);

        }
    };


    private CourseAreaFilterAdapter.OnLeftItemClickListener allAreaItemCLickLister = new CourseAreaFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            //刷新门店adapter
            resetCurrentStoreState(-1, position, 0);
            refreshAllStoreAdapater(-1, position, 0);
        }

    };

    private CourseStoreFilterAdapter.OnLeftItemClickListener allStoreItemClickListener = new CourseStoreFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {

            resetCurrentStoreState(-1, -1, position);

            refreshAllStoreAdapater(-1, -1, position);

            if (listener != null) {
                listener.onAllStoreItemClick(currentBrand, currentArea, currentStore);
            }

            if (TextUtils.equals(currentStore.getName(), "全部门店")) {
                if (TextUtils.equals(currentArea.getName(), "全部区域")) {
                    tvStore.setText(currentBrand.getName() + currentStore.getName());
                } else {
                    tvStore.setText(currentArea.getName() + currentStore.getName());
                }
            } else {
                tvStore.setText(currentStore.getName());
            }


            hidePopup();

        }
    };

    public boolean isPopupShowing() {


        return storeContentRootIsShowing || courseIsShowing || timeIsShowing;
//        storeContentAllIsShowing || storeContentMineIsShowing ||
    }

    public void hidePopup() {
        layoutStoreContentRoot.setVisibility(GONE);
//        layoutStoreBelong.setVisibility(GONE);
//        layoutStoreContentMine.setVisibility(GONE);
//        layoutCourseContent.setVisibility(GONE);
        layoutCourseContent.setVisibility(GONE);
        view_mask_bg.setVisibility(GONE);
        ll003.setVisibility(GONE);
        storeContentRootIsShowing = false;
//        storeContentAllIsShowing = false;
//        storeContentMineIsShowing = false;
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

        void onTimeItemClick(String timeValue);
    }
}
