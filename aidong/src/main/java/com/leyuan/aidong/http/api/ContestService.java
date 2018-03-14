package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.ContestData;
import com.leyuan.aidong.entity.data.ContestEnrolRecordData;
import com.leyuan.aidong.entity.data.ContestInfoData;
import com.leyuan.aidong.entity.data.ContestSchedulesData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.RankingData;
import com.leyuan.aidong.entity.data.RegisterData;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 2018/2/6.
 */

public interface ContestService {

    @GET("contest/{id}")
    Observable<BaseBean<ContestData>> getContestDetail(@Path("id") String id);


    @FormUrlEncoded
    @POST("contest/{id}/sign_up")
    Observable<BaseBean> contestEnrol(@Path("id") String id, @Field("name") String name,
                                      @Field("gender") String gender, @Field("division") String division);


    @FormUrlEncoded
    @POST("contest/{id}/invitation")
    Observable<BaseBean> invitationCodeEnrol(@Path("id") String id,
                                             @Field("code") String invitationCode,
                                             @Field("name") String name,
                                             @Field("gender") String gender);


    @POST("contest/{id}/invitation/{code}")
    Observable<BaseBean<RegisterData>> checkInvitationCode(@Path("id") String id, @Path("code") String code);


    @FormUrlEncoded
    @POST("contest/{id}/preliminary")
    Observable<BaseBean> postVideo(@Path("id") String id, @Field("video") String video,@Field("content") String content);

    @POST("contest/{contestId}/schedules/{scheduleId}")
    Observable<BaseBean> scheduleEnrol(@Path("contestId") String contestId, @Path("scheduleId") String scheduleId);

    @DELETE("contest/{contestId}/schedules/{scheduleId}")
    Observable<BaseBean> scheduleCancel(@Path("contestId") String contestId, @Path("scheduleId") String scheduleId);


    @GET("contest/{id}/info")
    Observable<BaseBean<ContestInfoData>> getContestInfo(@Path("id") String id);


    @GET("contest/{id}/dynamics")
    Observable<BaseBean<DynamicsData>> getContestDynamics(@Path("id") String id, @Query("page") int page);


    /**
     *
     * @param id
     * @param division
     * @param type  (preliminary-海选　semi_finals-复赛 finals-决赛)
     * @return
     */
    @GET("contest/{id}/ranking")
    Observable<BaseBean<RankingData>> getContestRanking(@Path("id") String id,
                                                        @Query("division") String division,
                                                        @Query("list") String type,
                                                        @Query("gender") String gender);


    @GET("contest/{id}/schedules")
    Observable<BaseBean<ContestSchedulesData>> getContestSchedules(@Path("id") String id, @Query("page") int page);


    @GET("contest/{id}/semi_finals")
    Observable<BaseBean<ContestEnrolRecordData>> getContestEnrolRecord(@Path("id") String id, @Query("page") int page);


}
