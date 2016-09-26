package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CoachData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.data.VenuesDetailData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 场馆
 */
public interface VenuesService {

    /**
     * 获取场馆列表
     * @param page 当前页数
     * @return 场馆集合
     */
    @GET("gyms")
    Observable<BaseBean<VenuesData>> getVenues(@Query("page") int page);

    /**
     * 获取场馆详情
     * @param id 场馆id
     * @return 场馆详情实体
     */
    @GET("gyms/{id}")
    Observable<BaseBean<VenuesDetailData>> getVenuesDetail(@Path("id") int id);

    /**
     * 获取场馆中教练列表(暂不考虑分页)
     * @param id 场馆id
     * @return
     */
    @GET("gyms/{id}/coaches")
    Observable<BaseBean<CoachData>> getCoaches(@Path("id") int id);

    /**
     * 获取场馆中课程列表(暂不考虑分页)
     * @param id 场馆id
     * @return
     */
    @GET("gyms/{id}/courses")
    Observable<BaseBean<CourseData>> getCourses(@Path("id") int id);
}
