package com.example.aidong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by song on 2016/9/20.
 */
public class SQLHelper extends SQLiteOpenHelper {
    private static final String NAME = "AiDong.db";     // .db可有可无
    private static final int VERSION = 1;               // 版本号不能为0

    // 用户信息表
    private static final String CREATE_USER_TABLE = "create table user(id integer primary key autoincrement not null,)";

    // 收货地址表
    private static final String CREATE_ADDRESS_TABLE = "create table address(id integer primary key autoincrement not null,name varchar(10)," +
            "phone varchar(30),address varchar(50) address_desc varchar(100))";

    // 历史搜索表(SEARCH_HISTORY)
    private static final String CREATE_SEARCH_HISTORY_TABLE = "create table search_history(id integer primary key autoincrement not null, "
            + "keyword varchar(100) not null)";


    public SQLHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ADDRESS_TABLE);
        db.execSQL(CREATE_SEARCH_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
