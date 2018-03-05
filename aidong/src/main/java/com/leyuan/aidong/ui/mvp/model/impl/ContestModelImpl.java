package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.ContestData;
import com.leyuan.aidong.entity.data.ContestEnrolRecordData;
import com.leyuan.aidong.entity.data.ContestInfoData;
import com.leyuan.aidong.entity.data.ContestSchedulesData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.RankingData;
import com.leyuan.aidong.entity.data.RegisterData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.ContestService;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2018/2/23.
 */

public class ContestModelImpl {

    ContestService contestService;

    public ContestModelImpl() {
        contestService = RetrofitHelper.createApi(ContestService.class);
    }

    public void getContestDetail(Observer<ContestData> subscribe, String id) {

        contestService.getContestDetail(id)
                .compose(RxHelper.<ContestData>transform())
                .subscribe(subscribe);
    }


    public void contestEnrol(Observer<BaseBean> subscribe, String id, String name, String gender, String division) {
        contestService.contestEnrol(id, name, gender, division)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }

    public void postVideo(Observer<BaseBean> subscribe, String id, String video) {
        contestService.postVideo(id, video)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }

    public void scheduleEnrol(Observer<BaseBean> subscribe, String contestId, String scheduleId) {
        contestService.scheduleEnrol(contestId, scheduleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }


    public void scheduleCancel(Observer<BaseBean> subscribe, String contestId, String scheduleId) {
        contestService.scheduleCancel(contestId, scheduleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }


    public void invitationCodeEnrol(Observer<BaseBean> subscribe, String id, String name, String gender, String invitationCode) {
        contestService.invitationCodeEnrol(id, invitationCode, name, gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }


    public void checkInvitationCode(Observer<RegisterData> subscribe, String id, String code) {

        contestService.checkInvitationCode(id, code)
                .compose(RxHelper.<RegisterData>transform())
                .subscribe(subscribe);
    }


    public void getContestInfo(Observer<ContestInfoData> subscribe, String id) {

        contestService.getContestInfo(id)
                .compose(RxHelper.<ContestInfoData>transform())
                .subscribe(subscribe);
    }

    public void getContestDynamics(Observer<DynamicsData> subscribe, String id, int page) {

        contestService.getContestDynamics(id, page)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscribe);
    }

    public void getContestRanking(Observer<RankingData> subscribe, String id, String division, String type) {

        contestService.getContestRanking(id, division, type)
                .compose(RxHelper.<RankingData>transform())
                .subscribe(subscribe);
    }


    public void getContestSchedules(Observer<ContestSchedulesData> subscribe, String id, int page) {
        contestService.getContestSchedules(id, page)
                .compose(RxHelper.<ContestSchedulesData>transform())
                .subscribe(subscribe);
    }


    public void getContestEnrolRecord(Observer<ContestEnrolRecordData> subscribe, String id, int page) {
        contestService.getContestEnrolRecord(id, page)
                .compose(RxHelper.<ContestEnrolRecordData>transform())
                .subscribe(subscribe);
    }

}
