package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.entity.data.CoachData;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.VenuesModel;
import com.leyuan.support.mvp.model.impl.VenuesModelImpl;
import com.leyuan.support.mvp.presenter.VenuesCoachFragmentPresent;
import com.leyuan.support.mvp.view.VenuesCoachFragmentView;

/**
 * 场馆 -教练
 * Created by song on 2016/8/30.
 */
public class VenuesCoachFragmentPresentImpl implements VenuesCoachFragmentPresent{

    private Context context;
    private VenuesModel venuesModel;
    private VenuesCoachFragmentView venuesCoachFragmentView;

    public VenuesCoachFragmentPresentImpl(Context context, VenuesCoachFragmentView venuesCoachFragmentView) {
        this.context = context;
        this.venuesCoachFragmentView = venuesCoachFragmentView;
        venuesModel = new VenuesModelImpl();
    }

    @Override
    public void getCoaches(int id) {
        venuesModel.getCoaches(new ProgressSubscriber<CoachData>(context,false) {
            @Override
            public void onNext(CoachData coachData) {
                if(coachData != null && coachData.getCoach() != null && !coachData.getCoach().isEmpty()){
                    venuesCoachFragmentView.setCoaches(coachData.getCoach());
                }else {
                    venuesCoachFragmentView.showNoCoachView();
                }
            }
        },id);
    }
}
