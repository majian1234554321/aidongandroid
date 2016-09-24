package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong.entity.data.CampaignData;
import com.example.aidong.entity.data.CourseData;
import com.example.aidong.entity.data.FoodData;
import com.example.aidong.entity.data.UserData;
import com.example.aidong.entity.data.VenuesData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.RxHelper;
import com.example.aidong.http.api.SearchService;
import com.example.aidong.ui.mvp.model.SearchModel;

import rx.Subscriber;

/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public class SearchModelImpl implements SearchModel{
    private static final String SEARCH_VENUES = "venues";
    private static final String SEARCH_COURSE = "course";
    private static final String SEARCH_FOOD = "food";
    private static final String SEARCH_CAMPAIGN = "campaign";
    private static final String SEARCH_USER = "user";

    private SearchService searchService;

    public SearchModelImpl() {
        this.searchService = RetrofitHelper.createApi(SearchService.class);
    }

    @Override
    public void searchVenues(Subscriber<VenuesData> subscriber, String keyword, int page) {
        searchService.searchVenues(keyword,SEARCH_VENUES,page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchCourse(Subscriber<CourseData> subscriber, String keyword, int page) {
        searchService.searchCourse(keyword,SEARCH_COURSE,page)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchFood(Subscriber<FoodData> subscriber, String keyword, int page) {
        searchService.searchFood(keyword,SEARCH_FOOD,page)
                .compose(RxHelper.<FoodData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchCampaign(Subscriber<CampaignData> subscriber, String keyword, int page) {
        searchService.searchCampaign(keyword,SEARCH_CAMPAIGN,page)
                .compose(RxHelper.<CampaignData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchUser(Subscriber<UserData> subscriber, String keyword, int page) {
        searchService.searchUser(keyword,SEARCH_USER,page)
                .compose(RxHelper.<UserData>transform())
                .subscribe(subscriber);
    }

}
