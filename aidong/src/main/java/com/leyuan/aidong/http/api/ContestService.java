package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.campaign.ContestBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by user on 2018/2/6.
 */

public interface ContestService {

    @GET("contest/{id}")
    Observable<BaseBean<ContestBean>> getContest();


}
