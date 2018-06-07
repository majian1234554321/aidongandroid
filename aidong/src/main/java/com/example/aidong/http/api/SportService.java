package com.example.aidong.http.api;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.SportRecordData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程
 * Created by huyushuai on 2017/8/13.
 */
public interface SportService {

    @GET("app/api/athletic")
    Observable<BaseBean<SportRecordData>> getSportRecord(@Query("year") String year, @Query("month") String month);

}
