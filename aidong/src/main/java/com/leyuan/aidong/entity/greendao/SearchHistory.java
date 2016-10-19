package com.leyuan.aidong.entity.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 搜索历史记录实体
 * Created by song on 2016/10/19.
 */
@Entity
public class SearchHistory {
    @Id(autoincrement=true)
    private Long id;


    private String keyword;


    @Generated(hash = 98012354)
    public SearchHistory(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }


    @Generated(hash = 1905904755)
    public SearchHistory() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getKeyword() {
        return this.keyword;
    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

 
}
