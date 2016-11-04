package com.leyuan.aidong.ui.mvp.model.impl;

import android.database.sqlite.SQLiteDatabase;

import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.FoodData;
import com.leyuan.aidong.entity.data.UserData;
import com.leyuan.aidong.entity.data.VenuesData;
import com.leyuan.aidong.entity.greendao.SearchHistory;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.SearchService;
import com.leyuan.aidong.ui.mvp.model.SearchModel;

import java.util.List;

import rx.Subscriber;

//import com.leyuan.aidong.entity.greendao.DaoMaster;
//import com.leyuan.aidong.entity.greendao.DaoSession;
//import com.leyuan.aidong.entity.greendao.SearchHistoryDao;
//import org.greenrobot.greendao.query.QueryBuilder;
/**
 * 搜索
 * Created by song on 2016/9/18.
 */
public class SearchModelImpl implements SearchModel {
    private static final String SEARCH_VENUES = "venues";
    private static final String SEARCH_COURSE = "course";
    private static final String SEARCH_FOOD = "food";
    private static final String SEARCH_CAMPAIGN = "campaign";
    private static final String SEARCH_USER = "user";

    private SearchService searchService;

//    private SearchHistoryDao dao;

    public SearchModelImpl() {
        this.searchService = RetrofitHelper.createApi(SearchService.class);
    }

    public SearchModelImpl(SQLiteDatabase db){
        this.searchService = RetrofitHelper.createApi(SearchService.class);
//        DaoMaster daoMaster = new DaoMaster(db);
//        DaoSession daoSession = daoMaster.newSession();
//        dao = daoSession.getSearchHistoryDao();
    }

    @Override
    public void searchVenues(Subscriber<VenuesData> subscriber, String keyword, int page) {
        searchService.searchVenues(keyword, SEARCH_VENUES, page)
                .compose(RxHelper.<VenuesData>transform())
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
//        QueryBuilder qb = dao.queryBuilder();        //TODO 保存历史记录的容器可以优化
//        List list = qb.limit(10).orderDesc(SearchHistoryDao.Properties.Id).list();
//        if(!list.isEmpty()){
//            return (List<SearchHistory>)list;
//        }
        return null;
    }

    @Override
    public void insertSearchHistory(String keyword){
        SearchHistory history = new SearchHistory(null,keyword);
//        dao.insert(history);
    }

}
