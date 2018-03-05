package com.leyuan.aidong.ui.mine.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.data.SportRecordMonthData;

/**
 * Created by user on 2018/1/29.
 */
public class SportRecordHeaderView extends RelativeLayout {

    private TextView txtCurrentMonthTitle;
    private TextView txtSportRecord;
    private TextView txtClockNum;
    private TextView txtGoCourseNum;
    private TextView txtClassTotalTime;
    private TextView txtExpendCal;

    public SportRecordHeaderView(Context context) {
        this(context, null, 0);
    }

    public SportRecordHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportRecordHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.inc_month_clock_info, this, true);

        txtCurrentMonthTitle = (TextView) view.findViewById(R.id.txt_current_month_title);
        txtSportRecord = (TextView) view.findViewById(R.id.txt_sport_record);
        txtClockNum = (TextView) view.findViewById(R.id.txt_clock_num);
        txtGoCourseNum = (TextView) view.findViewById(R.id.txt_go_course_num);
        txtClassTotalTime = (TextView) view.findViewById(R.id.txt_class_total_time);
        txtExpendCal = (TextView) view.findViewById(R.id.txt_expend_cal);

        txtSportRecord.setVisibility(GONE);
    }

    public void setData(SportRecordMonthData athletic) {
        if (athletic == null) return;
        txtClockNum.setText(athletic.days);
        txtGoCourseNum.setText(athletic.frequency);
        txtClassTotalTime.setText(athletic.during);
    }
}
