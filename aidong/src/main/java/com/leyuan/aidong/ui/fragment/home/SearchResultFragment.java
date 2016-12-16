package com.leyuan.aidong.ui.fragment.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
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
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private String keyword;
    private List<View> allTabView = new ArrayList<>();

    public static SearchResultFragment newInstance(String searchContent) {
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchContent);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            keyword = bundle.getString("keyword");
        }
        return inflater.inflate(R.layout.fragment_search_result, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tabLayout = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.vp_search_result);

        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        SearchVenuesFragment venues = new SearchVenuesFragment();
        SearchGoodsFragment goods = new SearchGoodsFragment();
        SearchCourseFragment course = new SearchCourseFragment();
        SearchFoodFragment food = new SearchFoodFragment();
        SearchCampaignFragment campaign = new SearchCampaignFragment();
        SearchUserFragment user = new SearchUserFragment();
        pages.add(FragmentPagerItem.of(null,venues.getClass(), new Bundler().putString("keyword", keyword).get()));
        pages.add(FragmentPagerItem.of(null,goods.getClass(), new Bundler().putString("keyword", keyword).get()));
        pages.add(FragmentPagerItem.of(null,course.getClass(), new Bundler().putString("keyword", keyword).get()));
        pages.add(FragmentPagerItem.of(null,food.getClass(), new Bundler().putString("keyword", keyword).get()));
        pages.add(FragmentPagerItem.of(null,campaign.getClass(), new Bundler().putString("keyword", keyword).get()));
        pages.add(FragmentPagerItem.of(null,user.getClass(), new Bundler().putString("keyword", keyword).get()));

        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);
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
}
