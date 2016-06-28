package com.example.aidong.activity.venues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.ViewFlipper;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.ArenaDetailsTimeAdapter;
import com.example.aidong.adapter.ShopCurriculumListViewAdapter;
import com.example.aidong.adapter.ShopPrivateEducationAdapter;
import com.example.aidong.model.Curriculum;
import com.example.aidong.model.PrivateEducation;
import com.example.aidong.view.InSListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class VenuesDetailsActivity extends BaseActivity implements View.OnClickListener ,View.OnTouchListener{
    private List<String> timeList = new ArrayList<>();
    private RecyclerView recycler_subject_time;
    private ArenaDetailsTimeAdapter arenaDetailsTimeAdapter;
    private InSListView listView_kc, listView_sj;
    private List<Curriculum> curriculumList = new ArrayList<>();
    private List<PrivateEducation> privateEducationList = new ArrayList<>();
    private Curriculum curriculum;
    private ShopCurriculumListViewAdapter shopCurriculumListViewAdapter;
    private ShopPrivateEducationAdapter shopPrivateEducationAdapter;
    private PrivateEducation privateEducation;
    private RadioButton kcRadioButton;
    private RadioButton sjRadioButton;
    private ViewFlipper viewFlipper;
    private boolean currentTabFlag = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_venues);
        initView();
        initData();
    }

    private void initView() {
        recycler_subject_time = (RecyclerView) findViewById(R.id.recycler_subject_time);
        listView_kc = (InSListView) findViewById(R.id.listView_kc);
        listView_sj = (InSListView) findViewById(R.id.listView_sj);
        recycler_subject_time.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VenuesDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_subject_time.setLayoutManager(layoutManager);

        kcRadioButton = (RadioButton) findViewById(R.id.kc_radio);
        kcRadioButton.setOnClickListener(this);
        kcRadioButton.setSelected(true);
        sjRadioButton = (RadioButton) findViewById(R.id.sj_radio);
        sjRadioButton.setOnClickListener(this);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        viewFlipper.setOnTouchListener(this);

    }

    private void initData() {
        for (int i = 0; i <7; i++) {
            timeList.add("asdasda");
        }
        arenaDetailsTimeAdapter = new ArenaDetailsTimeAdapter(VenuesDetailsActivity.this, timeList);
        recycler_subject_time.setAdapter(arenaDetailsTimeAdapter);


        for (int i = 0; i < 3; i++) {

            curriculum = new Curriculum();
            curriculum.setCourseChName("adas");
            curriculumList.add(curriculum);
        }
        shopCurriculumListViewAdapter = new ShopCurriculumListViewAdapter(VenuesDetailsActivity.this, curriculumList);
        listView_kc.setAdapter(shopCurriculumListViewAdapter);


        for (int i = 0; i < 3; i++) {

            privateEducation = new PrivateEducation();
            privateEducation.setChName("adas");
            privateEducation.setSex("M");
            privateEducationList.add(privateEducation);
        }
        shopPrivateEducationAdapter = new ShopPrivateEducationAdapter(VenuesDetailsActivity.this, privateEducationList);
        listView_sj.setAdapter(shopPrivateEducationAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.kc_radio:

                showPreviousView();
                kcRadioButton.setSelected(false);
                kcRadioButton.setSelected(true);
                break;
            case R.id.sj_radio:
                showNextView();
                sjRadioButton.setSelected(false);
                sjRadioButton.setSelected(true);
                break;
        }
    }


    void showPreviousView() {
        if (!currentTabFlag) {
            currentTabFlag = true;
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right));
            viewFlipper.showPrevious();
        }
    }

    void showNextView() {
        if (currentTabFlag) {
            currentTabFlag = false;
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
            viewFlipper.showNext();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
