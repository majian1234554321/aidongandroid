package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.campaign.CompetitionAreaBean;
import com.leyuan.aidong.entity.campaign.ContestBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.competition.fragment.ContestRankingFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.txt_group;

/**
 * Created by user on 2018/2/9.
 */
public class ContestRankingListActivity extends BaseActivity implements SmartTabLayout.TabProvider, View.OnClickListener {

    private ImageView ivBack;
    private TextView txtTitle;

    private ViewPager viewPager;
    private FragmentPagerItemAdapter adapter;

    private LinearLayout layoutSelectGroup;
    private TextView txtGroup;
    private ImageView imgGroupArrow;
    private SmartTabLayout tabLayout;
    private LinearLayout layoutContestProgress;
    private TextView txtMassElection;
    private TextView txtQuarterFinal;
    private TextView txtFinal;

    FrameLayout layout_my_ranking;
    private TextView txtRank;
    private ImageView imgAvatar;
    private TextView txtCoachName;
    private TextView txtIntro;


    private List<View> allTabView = new ArrayList<>();
    private String category;
    ContestBean contest;
    public static String rankType = "preliminary";
    private int transprentColor;
    public static String gender;


    public static void start(Context context, ContestBean contest) {

        Intent intent = new Intent(context, ContestRankingListActivity.class);
        intent.putExtra("contest", contest);

        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rankType = "preliminary";
        contest = getIntent().getParcelableExtra("contest");

        CompetitionAreaBean[] beans = new CompetitionAreaBean[contest.division_info.length + 1];
        for (int i = 0; i < beans.length; i++) {
            if (i == 0) {
                beans[i] = new CompetitionAreaBean("全国");
            } else {
                beans[i] = contest.division_info[i - 1];
            }
        }


        contest.division_info = beans;

        setContentView(R.layout.activity_contest_ranking_list);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        layoutSelectGroup = (LinearLayout) findViewById(R.id.layout_select_group);
        txtGroup = (TextView) findViewById(txt_group);
        imgGroupArrow = (ImageView) findViewById(R.id.img_group_arrow);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        layoutContestProgress = (LinearLayout) findViewById(R.id.layout_contest_progress);
        txtMassElection = (TextView) findViewById(R.id.txt_mass_election);
        txtQuarterFinal = (TextView) findViewById(R.id.txt_quarter_final);
        txtFinal = (TextView) findViewById(R.id.txt_final);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

//        layout_my_ranking = (FrameLayout) findViewById(R.id.layout_my_ranking);
//        txtRank = (TextView) findViewById(R.id.txt_rank);
//        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
//        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);
//        txtIntro = (TextView) findViewById(R.id.txt_intro);

        findViewById(R.id.iv_back).setOnClickListener(this);
        txtMassElection.setOnClickListener(this);
        txtQuarterFinal.setOnClickListener(this);
        txtFinal.setOnClickListener(this);
        layoutSelectGroup.setOnClickListener(this);
        transprentColor = Color.parseColor("#00000000");
        if (App.getInstance().isLogin()) {
            txtGroup.setText(App.getInstance().getUser().getGender() == 0 ? "男子组" : "女子组");
            gender = App.getInstance().getUser().getGender() == 0 ? "男" : "女";


             genderint = "男".equals(gender)?0:1;
        }

        initFragments();
    }

    int  genderint = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.txt_mass_election:
                rankType = "preliminary";
                tabLayout.setVisibility(View.VISIBLE);
                txtMassElection.setBackgroundResource(R.drawable.shape_simi_circle_orange_solid);
                txtQuarterFinal.setBackgroundColor(transprentColor);
                txtFinal.setBackgroundColor(transprentColor);

                refreshFragmentData();

                break;
            case R.id.txt_quarter_final:
                rankType = "semi_finals";
                tabLayout.setVisibility(View.VISIBLE);
                txtMassElection.setBackgroundColor(transprentColor);
                txtQuarterFinal.setBackgroundResource(R.drawable.shape_simi_circle_orange_solid);
                txtFinal.setBackgroundColor(transprentColor);
                refreshFragmentData();
                break;

            case R.id.txt_final:
                rankType = "finals";
                tabLayout.setVisibility(View.GONE);

                txtMassElection.setBackgroundColor(transprentColor);
                txtQuarterFinal.setBackgroundColor(transprentColor);
                txtFinal.setBackgroundResource(R.drawable.shape_simi_circle_orange_solid);
                refreshFragmentData();

                break;

            case R.id.layout_select_group:
                showGenderDialog();

                break;

        }
    }


    public void refreshFragmentData() {
        for (int i = 0; i < contest.division_info.length; i++) {
            Fragment page = adapter.getPage(i);
            if (page != null)
                ((ContestRankingFragment) page).fetchData();
        }
    }

    private void showGenderDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.man_woman_group_select)
                .items(R.array.gender)
                .itemsCallbackSingleChoice(genderint, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        txtGroup.setText((which == 0) ? "男子组" : "女子组");
                        genderint = which;
                        gender = (which == 0) ? "男" : "女";
                        refreshFragmentData();
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }

    private void initFragments() {
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < contest.division_info.length; i++) {

            ContestRankingFragment fragment = new ContestRankingFragment();

            pages.add(FragmentPagerItem.of(null, fragment.getClass(),
                    new Bundler().putString("division", contest.division_info[i].name).putString("contestId", contest.id).get()
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
        text.setText(contest.division_info[position].name);
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }


    public void setMyRankingVisible(int visible) {
//        layout_my_ranking.setVisibility(visible);
    }
}
