package com.example.aidong.http.api;

import com.example.aidong.entity.BaseBean;
import com.example.aidong.entity.data.CampaignData;
import com.example.aidong.entity.data.CourseData;
import com.example.aidong.entity.data.FoodData;
import com.example.aidong.entity.data.UserData;
import com.example.aidong.entity.data.VenuesData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public interface SearchService {

    @FormUrlEncoded
    @POST("search")
    Observable<BaseBean<VenuesData>> searchVenues(@Field("keyword") String keyword,@Field("cat") String category, @Field("page") int page);

    @FormUrlEncoded
    @POST("search")
    Observable<BaseBean<CourseData>> searchCourse(@Field("keyword") String keyword,@Field("cat") String category,  @Field("page") int page);

    @FormUrlEncoded
    @POST("search")
    Observable<BaseBean<FoodData>> searchFood(@Field("keyword") String keyword,@Field("cat") String category,  @Field("page") int page);

    @FormUrlEncoded
    @POST("search")
    Observable<BaseBean<CampaignData>> searchCampaign(@Field("keyword") String keyword, @Field("cat") String category, @Field("page") int page);

    @FormUrlEncoded
    @POST("search")
    Observable<BaseBean<UserData>> searchUser(@Field("keyword") String keyword, @Field("cat") String category, @Field("page") int page);
}
