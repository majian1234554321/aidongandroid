package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CoachData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.data.VenuesDetailData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 场馆
 */
public interface VenuesService {

    /**
     * 获取场馆列表
     *
     * @param page 当前页数
     * @return 场馆集合
     */
    @GET("gyms")
    Observable<BaseBean<VenuesData>> getVenues(@Query("page") int page, @Query("brand_id") String brand_id,
                                               @Query("landmark") String landmark,
                                               @Query("area") String area,
                                               @Query("gym_types") String gymTypes);
    /**
     * 获取场馆列表
     *
     * @param page 当前页数
     * @return 场馆集合
     */
    @GET("gyms/self_support")
    Observable<BaseBean<VenuesData>> getSlefSupportVenues(@Query("page") int page);

    /**
     * 获取场馆详情
     *
     * @param id 场馆id
     * @return 场馆详情实体
     */
    @GET("gyms/{id}")
    Observable<BaseBean<VenuesDetailData>> getVenuesDetail(@Path("id") String id);

    /**
     * 获取场馆中教练列表(暂不考虑分页)
     *
     * @param id 场馆id
     * @return
     */
    @GET("gyms/{id}/coaches")
    Observable<BaseBean<CoachData>> getCoaches(@Path("id") String id);

    /**
     * 获取场馆中课程列表(暂不考虑分页)
     *
     * @param id 场馆id
     * @return
     */
    @GET("gyms/{id}/courses")
    Observable<BaseBean<CourseData>> getCourses(@Path("id") String id, @Query("day") String day);

    /**
     * 预约场馆
     *
     * @param id     场馆id
     * @param date   预约日期
     * @param period 预约时段（0-上午 1-下午)
     * @param name   预约人
     * @param mobile 预约电话
     * @return
     */
    @FormUrlEncoded
    @POST("gyms/{id}")
    Observable<BaseBean> appointVenues(@Path("id") String id, @Field("date") String date,
                                       @Field("period") String period, @Field("name") String name,
                                       @Field("mobile") String mobile);

    /**
     * 预约私教
     *
     * @param id       场馆id
     * @param coach_id 私教id
     * @param date     预约日期
     * @param period   预约时段（0-上午 1-下午)
     * @param name     预约人
     * @param mobile   预约电话
     * @return
     */
    @FormUrlEncoded
    @POST("gyms/{id}/coaches/{coach_id}")
    Observable<BaseBean> appointCoach(@Path("id") String id, @Path("coach_id") String coach_id,
                                      @Field("date") String date, @Field("period") String period,
                                      @Field("name") String name, @Field("mobile") String mobile);
}
