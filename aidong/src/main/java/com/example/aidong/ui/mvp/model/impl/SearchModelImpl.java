package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.SearchHistoryBean;
import com.example.aidong .entity.data.CampaignData;
import com.example.aidong .entity.data.CourseData;
import com.example.aidong .entity.data.EquipmentData;
import com.example.aidong .entity.data.SearchNurtureData;
import com.example.aidong .entity.data.UserData;
import com.example.aidong .entity.data.VenuesData;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.SearchService;
import com.example.aidong .ui.mvp.model.SearchModel;

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
    private static final String SEARCH_COURSE = "course";
    private static final String SEARCH_NURTURE = "nutrition";
    private static final String SEARCH_EQUIPMENT = "equipment";
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
    public void searchData(Subscriber<Object> subscriber,String keyword) {
        searchService.searchData(keyword)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchVenues(Subscriber<VenuesData> subscriber, String keyword, int page) {
        searchService.searchVenues(keyword, SEARCH_VENUES, page)
                .compose(RxHelper.<VenuesData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchNurture(Subscriber<SearchNurtureData> subscriber, String keyword, int page) {
        searchService.searchNurture(keyword, SEARCH_NURTURE, page)
                .compose(RxHelper.<SearchNurtureData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void searchCourse(Subscriber<CourseData> subscriber, String keyword, int page) {
        searchService.searchCourse(keyword, SEARCH_COURSE, page)
                .compose(RxHelper.<CourseData>transform())
                .subscribe(subscriber);
    }


    @Override
    public void searchEquipment(Subscriber<EquipmentData> subscriber, String keyword, int page) {
        searchService.searchEquipment(keyword, SEARCH_EQUIPMENT, page)
                .compose(RxHelper.<EquipmentData>transform())
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
    public void searchUserIt(Subscriber<UserData> subscriber, String keyword, int page) {
        searchService.searchUser(keyword, page)
                .compose(RxHelper.<UserData>transform())
                .subscribe(subscriber);
    }

    @Override
    public List<SearchHistoryBean> getSearchHistory() {
        return realm.where(SearchHistoryBean.class).findAll();
    }

    @Override
    public void insertSearchHistory(String keyword){
        boolean contain = false;
        realm.beginTransaction();
        RealmResults<SearchHistoryBean> all = realm.where(SearchHistoryBean.class).findAll();
        for (SearchHistoryBean searchHistoryBean : all) {
            if(searchHistoryBean.getKeyword().equals(keyword)){
                contain = true;
                break;
            }
        }
        if (contain) {
            SearchHistoryBean result = realm.where(SearchHistoryBean.class).equalTo("keyword",keyword).findFirst();
            result.deleteFromRealm();
        }

        SearchHistoryBean history = realm.createObject(SearchHistoryBean.class);
        history.setKeyword(keyword);
        realm.commitTransaction();
    }

    @Override
    public void deleteSearchHistory() {
        realm.beginTransaction();
        RealmResults<SearchHistoryBean> results = realm.where(SearchHistoryBean.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
