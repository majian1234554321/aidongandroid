package com.leyuan.aidong.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leyuan.aidong.entity.CircleDynamicBean;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;

/**
 * Created by user on 2017/7/25.
 */
public class DynamicDb {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DynamicDb(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void insertInto(String fromAvatar, String fromName,
                           String dynamicId, String time, String content, String imageUrl,
                           int commentType, String msgId, int dynamicType, String replySiteNickname) {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("insert into dynamic values(null,?,?,?,?,?,?,?,?,?,?,?)"
                    , new Object[]{1, fromAvatar, fromName, dynamicId, time, content, imageUrl,
                            commentType, msgId, dynamicType, replySiteNickname});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();

            Logger.i("DynamicDb insert into success");
//            db.close();
        }
    }

    public ArrayList<CircleDynamicBean> queryAll() {
        ArrayList<CircleDynamicBean> beanList = new ArrayList<>();
        try {
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from dynamic", null);
            while (cursor.moveToNext()) {
                CircleDynamicBean bean = new CircleDynamicBean();
                bean.setFromAvatar(cursor.getString(cursor.getColumnIndex(Constant.KDNPRAISEAVATAR)));
                bean.setFromName(cursor.getString(cursor.getColumnIndex(Constant.KDNUSERNAME)));
                bean.setDynamicId(cursor.getString(cursor.getColumnIndex(Constant.KDNID)));
                bean.setTime(cursor.getString(cursor.getColumnIndex(Constant.KDNOCCURTIME)));
                bean.setContent(cursor.getString(cursor.getColumnIndex(Constant.KDNCONTENT)));
                bean.setImageUrl(cursor.getString(cursor.getColumnIndex(Constant.KDNCONTENTURL)));
                bean.setCommentType(cursor.getInt(cursor.getColumnIndex(Constant.KDNCOMMENTTYPE)));
                bean.setKDNMSGID(cursor.getString(cursor.getColumnIndex(Constant.KDNMSGID)));
                bean.setDynamicType(cursor.getInt(cursor.getColumnIndex(Constant.KDNCONTENTTYPE)));
//                bean.setReplySiteNickname(cursor.getString(cursor.getColumnIndex(Constant.KDNREPLYSITENICKNAME)));
                beanList.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        finally {
//            if (db != null){
//                db.close();
//            }
//        }
        Logger.i("DynamicDb  queryAll  success");

        return beanList;
    }


    public void insertInto(CircleDynamicBean bean) {
        insertInto(bean.getFromAvatar(), bean.getFromName(), bean.getDynamicId(), bean.getTime(), bean.getContent(), bean.getImageUrl()
                , bean.getCommentType(), bean.getKDNMSGID(), bean.getDynamicType(), bean.getReplySiteNickname());
    }

    public void clear() {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("delete from dynamic");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();

            Logger.i("DynamicDb insert into success");
//            db.close();
        }
    }
}
