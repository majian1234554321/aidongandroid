package com.leyuan.support.http.api;


import com.leyuan.support.entity.DemoBean;
import com.leyuan.support.entity.DomeBaseBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by song on 2016/8/9.
 */
public interface DemoService {

    @GET("api/history/content/50/{page}")
    Observable<DomeBaseBean<List<DemoBean>>> getDomeNews(@Path("page") int page);
}
