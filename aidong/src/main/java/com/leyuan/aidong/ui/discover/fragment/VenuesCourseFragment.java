package com.leyuan.aidong.ui.discover.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CourseDateAdapter;
import com.leyuan.aidong.adapter.home.CourseListAdapterNew;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.CourseListPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseListView;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.widget.CustomLayoutManager;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 场馆详情-课程
 * Created by song on 2016/8/27.
 */
public class VenuesCourseFragment extends BaseFragment implements CourseDateAdapter.ItemClickListener, CourseListView {
    private static final java.lang.String TAG = "VenuesCourseFragment";
    private SwitcherLayout switcherLayout;
//    private VenuesCourseAdapter courseAdapter;
//    private String id;
    private List<String> days;
//    private VenuesPresent venuesPresent;

    private RecyclerView dateView;
    private CourseDateAdapter dateAdapter;

    private CourseListAdapterNew courseAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ArrayList<CourseBeanNew> data = new ArrayList<>();
    private String date, store, course, time;

    private CourseListPresentImpl coursePresent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        days = DateUtils.getSevenDate();

        coursePresent = new CourseListPresentImpl(getContext(), this);

//        venuesPresent = new VenuesPresentImpl(getContext(), this);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            id = bundle.getString("id");
//        }
        initView(view);

        coursePresent.pullRefreshCourseList(store, null, null, days.get(0),null);
    }

    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_course);
        switcherLayout = new SwitcherLayout(getContext(), recyclerView);
        courseAdapter = new CourseListAdapterNew(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(courseAdapter);

        dateView = (RecyclerView) view.findViewById(R.id.rv_date);
        dateAdapter = new CourseDateAdapter();
        dateView.setLayoutManager(new CustomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dateView.setAdapter(dateAdapter);
        dateAdapter.setData(days);
        dateAdapter.setItemClickListener(this);
    }


    @Override
    public void onItemClick(int position) {
//        venuesPresent.getCourses(switcherLayout, id, days.get(position));
        coursePresent.pullRefreshCourseList(store, course, time, days.get(position),new HashMap());
    }

//    @Override
//    public void showEmptyView() {
//        View view = View.inflate(getContext(),R.layout.empty_venues_course,null);
//        switcherLayout.addCustomView(view,"empty");
//        switcherLayout.showCustomLayout("empty");
//    }

    @Override
    public void onGetRefreshCourseList(ArrayList<CourseBeanNew> courseList) {
        data.clear();
        if (courseList != null)
            data.addAll(courseList);
        courseAdapter.setData(data);
        courseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMoreCourseList(ArrayList<CourseBeanNew> courseList) {
        if (courseList != null)
            data.addAll(courseList);
        courseAdapter.setData(data);
        courseAdapter.notifyDataSetChanged();
    }

}
