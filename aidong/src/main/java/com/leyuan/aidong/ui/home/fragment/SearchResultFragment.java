package com.leyuan.aidong.ui.home.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.home.activity.SearchActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果
 * Created by song on 2016/9/18.
 */
public class SearchResultFragment extends BaseFragment implements SmartTabLayout.TabProvider {
    private List<View> allTabView = new ArrayList<>();
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerItemAdapter adapter;
    private int index;
    private String keyword;

    public static SearchResultFragment newInstance(int index,String searchContent) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("keyword", searchContent);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt("index");
            keyword = bundle.getString("keyword");
        }
        return inflater.inflate(R.layout.fragment_search_result, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tabLayout = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.vp_search_result);

        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        SearchVenuesFragment venues = new SearchVenuesFragment();
        SearchCourseFragment course = new SearchCourseFragment();
        SearchNurtureFragment nurture = new SearchNurtureFragment();
        SearchEquipmentFragment equipment = new SearchEquipmentFragment();
        SearchCampaignFragment campaign = new SearchCampaignFragment();
        SearchUserFragment user = new SearchUserFragment();
        pages.add(FragmentPagerItem.of(null,venues.getClass(), new Bundler().putString("keyword", keyword)
                .putBoolean("needLoad",index == 0).get()));
        pages.add(FragmentPagerItem.of(null,course.getClass(), new Bundler().putString("keyword", keyword)
                .putBoolean("needLoad",index == 1).get()));
        pages.add(FragmentPagerItem.of(null,nurture.getClass(), new Bundler().putString("keyword", keyword)
                .putBoolean("needLoad",index == 2).get()));
        pages.add(FragmentPagerItem.of(null,equipment.getClass(),new Bundler().putString("keyword", keyword)
                .putBoolean("needLoad",index == 3).get()));
        pages.add(FragmentPagerItem.of(null,campaign.getClass(), new Bundler().putString("keyword", keyword)
                .putBoolean("needLoad",index == 4).get()));
        pages.add(FragmentPagerItem.of(null,user.getClass(), new Bundler().putString("keyword", keyword)
                .putBoolean("needLoad",index == 5).get()));
        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                index = position;
                ((SearchActivity)getActivity()).closeKeyboard();
                String keyword = ((SearchActivity)getActivity()).updateKeyword();
                refreshSelectedData(keyword);
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                }
            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_search_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] title = getResources().getStringArray(R.array.searchTab);
        text.setText(title[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    public void refreshSelectedData(String keyword){
        Fragment page = adapter.getPage(index);
        if(page instanceof SearchVenuesFragment){
            ((SearchVenuesFragment) page).refreshData(keyword);
        }else if(page instanceof SearchUserFragment){
            ((SearchUserFragment) page).refreshData(keyword);
        }else if(page instanceof SearchCourseFragment){
            ((SearchCourseFragment) page).refreshData(keyword);
        }else if(page instanceof SearchNurtureFragment){
            ((SearchNurtureFragment) page).refreshData(keyword);
        }else if(page instanceof SearchEquipmentFragment){
            ((SearchEquipmentFragment) page).refreshData(keyword);
        }else if(page instanceof SearchCampaignFragment){
            ((SearchCampaignFragment) page).refreshData(keyword);
        }
    }
}
