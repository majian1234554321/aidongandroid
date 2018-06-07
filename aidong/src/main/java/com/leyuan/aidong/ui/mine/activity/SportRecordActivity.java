package com.leyuan.aidong.ui.mine.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.SportRecordFragment;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.Logger;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/10.
 */
public class SportRecordActivity extends BaseActivity implements SmartTabLayout.TabProvider, View.OnClickListener {

    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();
    TextView txt_right;
    ImageView img_left;

    public static String year = "2018";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_record);
        img_left = (ImageView) findViewById(R.id.img_left);
        txt_right = (TextView) findViewById(R.id.txt_right);

        final SmartTabLayout tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);


        tabLayout.setCustomTabView(this);

        FragmentPagerItems pages = new FragmentPagerItems(this);
        year = "2018";
        for (int i = 0; i < 12; i++) {
            SportRecordFragment courseFragment = new SportRecordFragment();
            pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                    new Bundler().putInt("month", i + 1).putString("year", year).get()
            ));

        }

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);





        viewPager.setCurrentItem(DateUtils.getMonth()-1);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //reset tip dot

                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

                    if (i == position){
                        text.setTextColor(ContextCompat.getColor(SportRecordActivity.this, R.color.black));
                    }else {
                        text.setTextColor(ContextCompat.getColor(SportRecordActivity.this, R.color.white));
                    }

                }
            }
        });


        img_left.setOnClickListener(this);
        txt_right.setOnClickListener(this);
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_sport_record, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.months);
        text.setText(campaignTab[position]);
        if (position == DateUtils.getMonth()-1) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
            text.setTextColor(ContextCompat.getColor(SportRecordActivity.this, R.color.black));
        }
        allTabView.add(tabView);
        return tabView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.txt_right:
                showHeightDialog();


                break;
        }
    }


    private void showHeightDialog() {
        new MaterialDialog.Builder(this).title("请选择年份")
                .items(generateHeightData())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        year = text.toString();
                        txt_right.setText(year + "年");

                        for (int i = 0; i < 12; i++) {
                            SportRecordFragment page = (SportRecordFragment) adapter.getPage(i);

                            Logger.i("SportRecordFragment" + page);
                            if (page != null) {
//                                page.setyear(year);
                                page.fetchData();
                            }
                        }

                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }


    private List<String> generateHeightData() {
        List<String> height = new ArrayList<>();
        for (int i = 2017; i < 2019; i++) {
            height.add(String.valueOf(i));
        }
        return height;
    }

}
