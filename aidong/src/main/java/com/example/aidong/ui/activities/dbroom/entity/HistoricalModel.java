package com.example.aidong.ui.activities.dbroom.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class HistoricalModel {

    @PrimaryKey(autoGenerate=true)
    public int uid;

    @ColumnInfo(name = "keyword")
    public String keyword;

    @ColumnInfo(name = "date")
    public String date;

    public HistoricalModel(String keyword, String date) {
        this.keyword = keyword;
        this.date = date;
    }
}
