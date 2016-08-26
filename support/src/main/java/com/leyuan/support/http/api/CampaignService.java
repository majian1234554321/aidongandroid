package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.CampaignData;
import com.leyuan.support.entity.data.CampaignDetailData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignService {

    @GET("market/campaigns")
    Observable<BaseBean<CampaignData>> getCampaigns(@Query("page") int page);

    @GET("market/campaigns/{id}")
    Observable<BaseBean<CampaignDetailData>> getCampaignDetail(@Path("id") int id);
}
