package com.leyuan.aidong.entity.greendao;


import io.realm.RealmObject;

/**
 * 搜索历史记录实体
 * Created by song on 2016/10/19.
 */

public class SearchHistory extends RealmObject{


    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
