package com.leyuan.aidong.ui.home.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.CampaignFragment;
import com.leyuan.aidong.ui.home.fragment.CircleCourseListFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class CircleListActivity extends BaseActivity implements SmartTabLayout.TabProvider {

    private List<View> allTabView = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_list);

        final SmartTabLayout tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout.setCustomTabView(this);

        FragmentPagerItems pages = new FragmentPagerItems(this);

        pages.add(FragmentPagerItem.of(null,CircleCourseListFragment.class));
        pages.add(FragmentPagerItem.of(null,CampaignFragment.class));
        pages.add(FragmentPagerItem.of(null,CampaignFragment.class));
        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                //reset tip dot
                View tip = tabLayout.getTabAt(position).findViewById(R.id.tv_tab_tip);
                tip.setVisibility(View.GONE);

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
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_text_with_notification, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        TextView tip = (TextView) tabView.findViewById(R.id.tv_tab_tip);
        tip.setVisibility(View.GONE);


        String[] campaignTab = getResources().getStringArray(R.array.circleList);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}