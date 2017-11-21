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
import com.leyuan.aidong.adapter.course.CourseStoreFilterAdapter;
import com.leyuan.aidong.adapter.home.RightFilterAdapter;
import com.leyuan.aidong.entity.course.CourseFilterBean;
import com.leyuan.aidong.utils.Logger;

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

    private RightFilterAdapter rightCircleAdapter;
    private CourseFilterBean courseFilterConfig;
    private CourseAreaFilterAdapter.OnLeftItemClickListener allAreaItemCLickLister = new CourseAreaFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
        }

    };
    private CourseStoreFilterAdapter.OnLeftItemClickListener allStoreItemClickListener = new CourseStoreFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {

        }
    };

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
        layoutStoreContentAll = (LinearLayout) view.findViewById(R.id.layout_store_content_all);
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
            case R.id.ll_brand:

                break;
            case R.id.ll_business_circle:

                break;
            case R.id.view_mask_bg:
                break;

            case R.id.ll_time_frame:

                break;

            case R.id.ll_filter:
                break;
            default:
                break;
        }
    }

    public void setData(CourseFilterBean courseFilterConfig) {
        this.courseFilterConfig = courseFilterConfig;
        Logger.i("Course",courseFilterConfig.toString());

//        adapterAllBrand = new CourseBrandFilterAdapter(context,courseFilterConfig.getCompany(),allBrandItemClickLister);
//        adapterAllArea = new CourseAreaFilterAdapter(context,courseFilterConfig.getCompany().get(0).getArea(),allAreaItemCLickLister);
//        adapterAllStore = new CourseStoreFilterAdapter(context,courseFilterConfig.getCompany().get(0).getArea().get(0).getStore(),allStoreItemClickListener);


    }

    private CourseBrandFilterAdapter.OnLeftItemClickListener allBrandItemClickLister = new CourseBrandFilterAdapter.OnLeftItemClickListener() {
        @Override
        public void onClick(int position) {
            //刷新区域adapter

        }
    };
}
