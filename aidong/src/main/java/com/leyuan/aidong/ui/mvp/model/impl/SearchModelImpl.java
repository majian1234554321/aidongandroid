package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.FoodData;
import com.leyuan.aidong.entity.data.SearchGoodsData;
import com.leyuan.aidong.entity.data.UserData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.greendao.SearchHistory;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.SearchService;
import com.leyuan.aidong.ui.mvp.model.SearchModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;

/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public class SearchModelImpl implements SearchModel {
    private static final String SEARCH_VENUES = "gym";
    private static final String SEARCH_GOODS = "product";
    private static final String SEARCH_COURSE = "course";
    private static final String SEARCH_FOOD = "food";
    private static final String SEARCH_CAMPAIGN = "campaign";
    private static final String SEARCH_USER = "user";

    private SearchService searchService;
    private Realm realm;


    public SearchModelImpl() {
        this.searchService = RetrofitHelper.createApi(SearchService.class);
    }

    public SearchModelImpl(Realm realm){
        this.searchService = RetrofitHelper.createApi(SearchService.class);
        this.realm = realm;
    }

    @Override
    public void searchVenues(Subscriber<VenuesData> subscriber, String keyword, int page) {
        searchService.searchVenues(keyword, SEARCH_VENUES, page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchGoods(Subscriber<SearchGoodsData> subscriber, String keyword, int page) {
        searchService.searchGoods(keyword, SEARCH_GOODS, page)
                .compose(RxHelper.<SearchGoodsData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchCourse(Subscriber<CourseData> subscriber, String keyword, int page) {
        searchService.searchCourse(keyword, SEARCH_COURSE, page)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchFood(Subscriber<FoodData> subscriber, String keyword, int page) {
        searchService.searchFood(keyword, SEARCH_FOOD, page)
                .compose(RxHelper.<FoodData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchCampaign(Subscriber<CampaignData> subscriber, String keyword, int page) {
        searchService.searchCampaign(keyword, SEARCH_CAMPAIGN, page)
                .compose(RxHelper.<CampaignData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchUser(Subscriber<UserData> subscriber, String keyword, int page) {
        searchService.searchUser(keyword, SEARCH_USER, page)
                .compose(RxHelper.<UserData>transform())
                .subscribe(subscriber);
    }

    @Override
    public List<SearchHistory> getSearchHistory() {
        return realm.where(SearchHistory.class).findAll();
    }

    @Override
    public void insertSearchHistory(String keyword){
        realm.beginTransaction();
        SearchHistory history = realm.createObject(SearchHistory.class);
        history.setKeyword(keyword);
        realm.commitTransaction();
    }

    @Override
    public void deleteSearchHistory() {
        realm.beginTransaction();
        RealmResults<SearchHistory> results = realm.where(SearchHistory.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
