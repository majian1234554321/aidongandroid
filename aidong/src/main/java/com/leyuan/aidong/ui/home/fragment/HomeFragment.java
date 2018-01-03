package com.leyuan.aidong.ui.home.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/25.
 */
public class HomeFragment extends BaseFragment implements SmartTabLayout.TabProvider {
    private ImageView ivLogo;
    private TextView tvLocation;
    private ImageView ivSearch;

    private List<View> allTabView = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container,false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivLogo = (ImageView) view.findViewById(R.id.iv_logo);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);

        final SmartTabLayout tabLayout = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout.setCustomTabView(this);

        FragmentPagerItems pages = new FragmentPagerItems(getContext());

        pages.add(FragmentPagerItem.of(null,HomeAttentionFragment.class));
        pages.add(FragmentPagerItem.of(null,HomeAttentionFragment.class));
        pages.add(FragmentPagerItem.of(null,HomeAttentionFragment.class));
        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_text_with_notification, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        TextView tip = (TextView) tabView.findViewById(R.id.tv_tab_tip);
        tip.setVisibility(View.GONE);
//        if(position == 0){
//            text.setText(R.string.tab_sport_circle);
//            text.setTypeface(Typeface.DEFAULT_BOLD);
//        }else {
//            text.setText(R.string.tab_discover);
//        }


        String[] campaignTab = getResources().getStringArray(R.array.homeTab);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

}
