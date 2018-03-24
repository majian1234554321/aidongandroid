package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.SportRecordData;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.SportModelImpl;
import com.leyuan.aidong.ui.mvp.view.SportRecordView;

/**
 * Created by user on 2018/2/23.
 */

public class SportPresentImpl {

    private Context context;
    private SportModelImpl sportModel;
    private SportRecordView sportRecordView;

    public SportPresentImpl(Context context) {
        this.context = context;
        sportModel = new SportModelImpl();
    }

    public void setSportRecordView(SportRecordView sportRecordView) {
        this.sportRecordView = sportRecordView;
    }

    public void getSportRecord(String year, String month) {
        sportModel.getSportRecord(new ProgressSubscriber<SportRecordData>(context) {
            @Override
            public void onNext(SportRecordData sportRecordData) {
                if (sportRecordView != null) {
                    sportRecordView.onGetSportRecordData(sportRecordData.athletic);
                }

            }
        }, year, month);
    }

    public void getSportRecordNoProgress(String year, String month) {
        sportModel.getSportRecord(new BaseSubscriber<SportRecordData>(context) {
            @Override
            public void onNext(SportRecordData sportRecordData) {
                if (sportRecordView != null) {
                    sportRecordView.onGetSportRecordData(sportRecordData.athletic);
                }

            }
        }, year, month);
    }


}
