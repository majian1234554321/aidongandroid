package com.example.aidong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017/4/10.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mysql.db";
    public static final String TABLE_NAME = "default";
    private static final String TABLE_NAME_DYNAMIC = "dynamic";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists dynamic (_id integer primary key autoincrement, " +
                "kCMDMsgType integer,kDNPraiseAvatar varchar(64),kDNUserName varchar(64),kDNID varchar(64)" +
                ",kDNOccurTime varchar(64),kDNContent varchar(64),kDNContentUrl varchar(64),kDNCommentType integer" +
                ",kDNMsgID varchar(64),kDNContentType integer,kDNreplySiteNickName varchar(64))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
