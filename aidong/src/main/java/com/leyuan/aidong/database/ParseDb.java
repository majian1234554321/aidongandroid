package com.leyuan.aidong.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2017/4/10.
 */

public class ParseDb {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public ParseDb(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void insertParseId(int id) {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("videoId", id);
        db.insert(DataBaseHelper.TABLE_NAME, null, values);
        db.setTransactionSuccessful();

    }


}
