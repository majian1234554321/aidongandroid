package com.example.aidong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.adapter.MyFragmentPagerAdapter;
import com.example.aidong.view.CustomViewPager;

import java.util.ArrayList;

public class FoundFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private Button button_cg, button_people;
    private VenuesFragment arenaFragment;
    private PeoPleFragment peoPleFragment;
    private ArrayList<android.support.v4.app.Fragment> mFragments;
    private CustomViewPager viewpager;

    private int currIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_found, container, false);
        initView(rootView);
        initData();
        return rootView;
    }

    private void initView(View veiw) {
        button_cg = (Button) veiw.findViewById(R.id.button_cg);
        button_people = (Button) veiw.findViewById(R.id.button_people);
        viewpager = (CustomViewPager) veiw.findViewById(R.id.viewpager);
        button_cg.setOnClickListener(this);
        button_people.setOnClickListener(this);
        arenaFragment = new VenuesFragment();
        peoPleFragment = new PeoPleFragment();
        mFragments = new ArrayList<>();
        mFragments.add(arenaFragment);
        mFragments.add(peoPleFragment);

        //给ViewPager设置适配器
        viewpager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments));
        viewpager.setCurrentItem(0);//设置当前显示标签页为第一页
        viewpager.setOffscreenPageLimit(3);//预先加载三页
        viewpager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器

    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            currIndex = arg0;
            switch (currIndex) {
                case 0:
                    viewpager.setCurrentItem(currIndex);
                    break;
                case 1:
                    viewpager.setCurrentItem(currIndex);
                    break;

                default:
                    break;
            }

        }
    }


    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cg:
                viewpager.setCurrentItem(0);
                break;
            case R.id.button_people:
                viewpager.setCurrentItem(1);
                break;
        }


    }


    private void resetTabBtn() {
    }
}
