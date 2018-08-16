package com.example.aidong.http;

import com.example.aidong.entity.SearchCoachModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface APIService {

    @GET("discoveries/search_coach")
    Observable<SearchCoachModel> search_coach(@Query("keyword") String keyword);
}
