package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.CampaignDataBean;
import com.leyuan.support.entity.CampaignDetailBean;

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
    Observable<BaseBean<CampaignDataBean>> getCampaigns(@Query("page") int page);

    @GET("market/campaigns/{id}")
    Observable<BaseBean<CampaignDetailBean>> getCampaignDetail(@Path("id") int id);
}
