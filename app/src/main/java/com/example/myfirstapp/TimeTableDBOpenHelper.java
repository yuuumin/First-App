package com.example.myfirstapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by akiyamayumi on 2018/11/20.
 */

public class TimeTableDBOpenHelper extends SQLiteOpenHelper {

    public TimeTableDBOpenHelper(Context context) {
        super(context, "TIMETABLE_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Const.TIME_TABLE_DB_NM + " ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, subject TEXT, " +
                "classroom TEXT, teacher TEXT, unit TEXT, color INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public void saveTimeTableData() {

    }

    public void getTimeTableData(SQLiteDatabase db, int id) {

        String mSQLString = "select * from " + Const.TIME_TABLE_DB_NM + " where id=?";

        String data = null;
        try {
            SQLiteCursor mCursor = (SQLiteCursor) db.rawQuery(mSQLString, new String[]{String.valueOf(id)});

            for(int i = 0 ; i < mCursor.getCount() ; i++) {
                mCursor.moveToFirst();
                if(mCursor.getString(3) != null) {
                    data = mCursor.getString(3);
                }
                mCursor.moveToNext();
            }
            mCursor.close();
        } catch(SQLException e) {
            Log.e("SQLERROR", e.toString());
        }
    }

    public void deleteTimeTableData(SQLiteDatabase db, int id) {
        db.delete(Const.TIME_TABLE_DB_NM, "id = " + String.valueOf(id), null);
    }
}
