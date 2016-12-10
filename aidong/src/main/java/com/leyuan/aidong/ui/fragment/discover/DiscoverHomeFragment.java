package com.leyuan.aidong.ui.fragment.discover;

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
import com.leyuan.aidong.ui.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;


/**
 * 发现
 * Created by song on 2016/11/19.
 */
public class DiscoverHomeFragment extends BaseFragment implements SmartTabLayout.TabProvider{
    private List<View> allTabView = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_home,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView camera = (ImageView) view.findViewById(R.id.iv_camera);
        final SmartTabLayout tabLayout = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout.setCustomTabView(this);

        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        pages.add(FragmentPagerItem.of(null,FoundHomeFragment.class));
        pages.add(FragmentPagerItem.of(null,SportCircleFragment.class));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                camera.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

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
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_text_with_notification, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        TextView tip = (TextView) tabView.findViewById(R.id.tv_tab_tip);
        if(position == 0){
            text.setText(R.string.tab_discover);
            text.setTypeface(Typeface.DEFAULT_BOLD);
            tip.setVisibility(View.GONE);
        }else {
            text.setText(R.string.tab_sport_circle);
            tip.setVisibility(View.VISIBLE);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
