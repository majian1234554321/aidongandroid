package com.leyuan.aidong.http.api;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseDetailData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.PayOrderData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程
 * Created by song on 2016/8/13.
 */
public interface CourseService {
    //@GET("market/courses")
    //Observable<BaseBean<CourseData>> getCourses(@Query("cat") int category, @Query("day") int day, @Query("page") int page);

    @GET("market/courses")
    Observable<BaseBean<CourseData>> getCourses(@Query("day") String day,@Query("cat") String cat,
                                                @Query("landmark") String landmark, @Query("page") int page);

    @GET("market/courses/{id}")
    Observable<BaseBean<CourseDetailData>> getCourseDetail(@Path("id") String id);

    @FormUrlEncoded
    @POST("market/courses/{id}")
    Observable<BaseBean<PayOrderData>> buyCourse(@Path("id") String id,
                                                 @Field("coupon") String couponId,
                                                 @Field("integral") String integral,
                                                 @Field("pay_type") String payType,
                                                 @Field("contact_name") String contactName,
                                                 @Field("contact_mobile") String contactMobile);
}
