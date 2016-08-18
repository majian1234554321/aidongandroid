package com.leyuan.support.http.api;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.entity.CampaignDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public interface CampaignService {

    @GET("market/campaigns")
    Observable<BaseBean<List<CampaignBean>>> getCampaigns(int page);

    @GET("market/campaigns/{id}")
    Observable<BaseBean<CampaignDetailBean>>  getgetCampaignDetail(@Path("id") int id);
}
