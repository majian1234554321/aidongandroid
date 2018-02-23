package com.leyuan.aidong.ui.competition.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.competition.fragment.ContestRankingFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/2/9.
 */
public class ContestRankingListActivity extends BaseActivity implements SmartTabLayout.TabProvider {

    private ImageView ivBack;
    private TextView txtTitle;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerItemAdapter adapter;

    private List<String> days = new ArrayList<String>();
    private List<View> allTabView = new ArrayList<>();
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_ranking_list);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        days.add("华东赛区");
        days.add("华南赛区");
        days.add("华东赛区");

        days.add("华东赛区");
        days.add("华东赛区");
        days.add("华东赛区");
        days.add("华东赛区");

        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < days.size(); i++) {
            ContestRankingFragment fragment = new ContestRankingFragment();

            pages.add(FragmentPagerItem.of(null, fragment.getClass(),
                    new Bundler().putString("date", days.get(i)).putString("category", "all").get()
            ));

            adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
            viewPager.setOffscreenPageLimit(6);
            viewPager.setAdapter(adapter);
            tabLayout.setCustomTabView(this);
            tabLayout.setViewPager(viewPager);
//            tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//                @Override
//                public void onPageSelected(int position) {
////                    for (int i = 0; i < allTabView.size(); i++) {
////                        View tabAt = tabLayout.getTabAt(i);
////                        TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
////                        text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
////                    }
//                }
//            });

            tabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
                @Override
                public void onTabClicked(int position) {




                }
            });

        }
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_contest_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        text.setText(days.get(position));
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }


}
