package com.example.aidong.ui.activities.dbroom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.aidong.ui.activities.dbroom.dao.HistoricalDao;
import com.example.aidong.ui.activities.dbroom.entity.HistoricalModel;

@Database(entities = {HistoricalModel.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "UserDatabase.db";
    private static volatile AppDataBase instance;

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDataBase.class,
                DB_NAME).build();
    }

    public abstract HistoricalDao getHistoricalDao();


}
