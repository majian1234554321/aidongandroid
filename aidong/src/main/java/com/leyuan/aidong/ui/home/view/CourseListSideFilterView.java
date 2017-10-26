package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CourseSlideFilterAdapter;

/**
 * Created by user on 2017/10/25.
 */
public class CourseListSideFilterView implements View.OnClickListener {
    private final CourseSlideFilterListener listener;
    private LinearLayout llCourseFilter;
    private LinearLayout layoutMyBranch;
    private RecyclerView listMyBrand;
    private LinearLayout layoutOtherBranch;
    private RecyclerView listOtherBrand;
    private TextView tvResetFilter;
    private TextView tvFinishFilter;

    private CourseSlideFilterAdapter myBrandAdapter;
    private CourseSlideFilterAdapter otherBrandAdapter;

    public CourseListSideFilterView(Context context, View view, CourseSlideFilterListener listener) {
        this.listener = listener;

        llCourseFilter = (LinearLayout) view.findViewById(R.id.ll_course_filter);
        layoutMyBranch = (LinearLayout) view.findViewById(R.id.layout_my_branch);
        listMyBrand = (RecyclerView) view.findViewById(R.id.list_my_brand);
        layoutOtherBranch = (LinearLayout) view.findViewById(R.id.layout_other_branch);
        listOtherBrand = (RecyclerView) view.findViewById(R.id.list_other_brand);
        tvResetFilter = (TextView) view.findViewById(R.id.tv_reset_filter);
        tvFinishFilter = (TextView) view.findViewById(R.id.tv_finish_filter);

        tvResetFilter.setOnClickListener(this);
        tvFinishFilter.setOnClickListener(this);

        listMyBrand.setLayoutManager(new GridLayoutManager(context,3));
        myBrandAdapter = new CourseSlideFilterAdapter(context);
        listMyBrand.setAdapter(myBrandAdapter);

        listOtherBrand.setLayoutManager(new GridLayoutManager(context,3));
        otherBrandAdapter = new CourseSlideFilterAdapter(context);
        listOtherBrand.setAdapter(otherBrandAdapter);

    }

    public void setData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reset_filter:
                myBrandAdapter.getSelected().clear();
                myBrandAdapter.notifyDataSetChanged();

                otherBrandAdapter.getSelected().clear();
                otherBrandAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_finish_filter:

                break;
        }
    }

    public interface CourseSlideFilterListener {
        void onFinishClick();

        void onResetClick();

    }


}
