package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.lang.reflect.Method;

public class TimeTableActivity extends AppCompatActivity {

    private int mjikan;
    private int mWeek;
    private int mWindowWidth;
    private int mTitleWidth;

    private String[] mWeekText;

    private TimeTableDBOpenHelper mHelper = null;
    private SQLiteDatabase mDB = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mjikan = 6;
        mWeek = 5;
        mTitleWidth = 28;
        mWindowWidth = getRealSize(this).x - (5 * mWeek) - 30 - 10;

        mWeekText = new String[mWeek];

        mWeekText[0] = Const.MONDAY;
        mWeekText[1] = Const.TUESDAY;
        mWeekText[2] = Const.WEDNESDAY;
        mWeekText[3] = Const.THURSDAY;
        mWeekText[4] = Const.FRIDAY;

        mHelper = new TimeTableDBOpenHelper(this);
        mDB = mHelper.getWritableDatabase();


        // 親ビュー
        LayoutInflater mScrollInflater = LayoutInflater.from(this);
        View mScrollView = mScrollInflater.inflate(R.layout.scroll_layout, null);
        LinearLayout mScrollLayout = mScrollView.findViewById(R.id.scroll_view);

        // 曜日親ビュー
        LayoutInflater mWeekTitleInflater = LayoutInflater.from(this);
        View mWeekTitleView = mWeekTitleInflater.inflate(R.layout.week_title_layout, null);
        LinearLayout mWeekTitleLayout = mWeekTitleView.findViewById(R.id.week_title_view);
        mWeekTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTitleWidth));

        mScrollLayout.addView(mWeekTitleView);

        // 曜日子ビュー
        for(int i = 0 ; i < mWeek + 1 ; i++) {
            LayoutInflater mWeekTitleChildInflater = LayoutInflater.from(this);
            View mWeekTitleChildView = mWeekTitleChildInflater.inflate(R.layout.week_title_child_layout, null);
            LinearLayout mWeekTitleChildLayout = mWeekTitleChildView.findViewById(R.id.week_title_child_view);
            TextView mWeekTitleText = mWeekTitleChildView.findViewById(R.id.week_title_text);
            if(i == 0) {
                mWeekTitleChildLayout.setLayoutParams(new LinearLayout.LayoutParams(mTitleWidth, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                Space mSpace = new Space(this);
                mWeekTitleChildLayout.setLayoutParams(new LinearLayout.LayoutParams(mWindowWidth / mWeek - 2, ViewGroup.LayoutParams.MATCH_PARENT));
                mWeekTitleLayout.addView(mSpace, new ActionBar.LayoutParams(5, ViewGroup.LayoutParams.MATCH_PARENT));
                mWeekTitleText.setText(mWeekText[i-1]);
            }

            mWeekTitleLayout.addView(mWeekTitleChildView);
        }

        for(int i = 0 ; i < mjikan ; i++) {
            // 子ビュー
            LayoutInflater mWeekInflater = LayoutInflater.from(this);
            View mWeekView = mWeekInflater.inflate(R.layout.week_layout, null);
            LinearLayout mWeekLayout = mWeekView.findViewById(R.id.week_view);
            mWeekView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (mWindowWidth / mWeek) * 4 / 3));

            // 時間タイトルビュー
            LayoutInflater mTimeTitleInflater = LayoutInflater.from(this);
            View mTimeTitleView = mTimeTitleInflater.inflate(R.layout.time_title_layout, null);
            mTimeTitleView.setLayoutParams(new LinearLayout.LayoutParams(mTitleWidth, ViewGroup.LayoutParams.MATCH_PARENT));
            TextView mTimeTitleText = mTimeTitleView.findViewById(R.id.time_title_text);
            mTimeTitleText.setText(String.valueOf(i + 1));

            mWeekLayout.addView(mTimeTitleView);

            for (int j = 0; j < mWeek; j++) {
                // 孫ビュー
                int mViewId = (i + 1) * 10 + j + 1;
                TextView mDayLayout = new TextView(this);
                mDayLayout.setLayoutParams(new LinearLayout.LayoutParams(mWindowWidth / mWeek - 2, ViewGroup.LayoutParams.MATCH_PARENT));
                mDayLayout.setText(getSubjectData(mViewId));
                mDayLayout.setGravity(Gravity.CENTER);
                mDayLayout.setId((i + 1) * 10 + j + 1);
                if(getColorData(mViewId) != 0) {
                    mDayLayout.setBackgroundColor(getColorData(mViewId));
                } else {
                    mDayLayout.setBackgroundColor(Color.GRAY);
                }
                mDayLayout.setOnClickListener(new View.OnClickListener() {
                    public void onClick (View view) {
                        // 孫ビュー押下時の処理
                        Intent intent = new Intent(TimeTableActivity.this, TimeTableSettingActivity.class);
                        intent.putExtra("VIEW_ID", view.getId());
                        startActivity(intent);
                    }
                });

                Space mSpace = new Space(this);

                mWeekLayout.addView(mSpace, new ActionBar.LayoutParams(5, ViewGroup.LayoutParams.MATCH_PARENT));
                mWeekLayout.addView(mDayLayout);
            }
            Space mSpace = new Space(this);

            mScrollLayout.addView(mSpace, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5));
            mScrollLayout.addView(mWeekView);
        }

        setContentView(mScrollView);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    /**
     * 画面サイズ取得
     * */
    public static Point getRealSize(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point(0, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Android 4.2~
            display.getRealSize(point);
            return point;

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            // Android 3.2~
            try {
                Method getRawWidth = Display.class.getMethod("getRawWidth");
                Method getRawHeight = Display.class.getMethod("getRawHeight");
                int width = (Integer) getRawWidth.invoke(display);
                int height = (Integer) getRawHeight.invoke(display);
                point.set(width, height);
                return point;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return point;
    }


    /**
     * 時間割科目データ取得
     * */
    public String getSubjectData(int id) {

        String subject = null;
        String mSQLString = "select * from " + Const.TIME_TABLE_DB_NM + " where id=?";

        try {
            SQLiteCursor mCursor = (SQLiteCursor) mDB.rawQuery(mSQLString, new String[]{String.valueOf(id)});

            for(int i = 0 ; i < mCursor.getCount() ; i++) {
                mCursor.moveToFirst();
                subject =  mCursor.getString(1);
                mCursor.moveToNext();
            }
            mCursor.close();
        } catch(SQLException e) {
            Log.e("SQLERROR", e.toString());
        }

        return subject;
    }

    /**
     * 時間割カラーデータ取得
     * */
    public int getColorData(int id) {

        int color = 0;
        String mSQLString = "select * from " + Const.TIME_TABLE_DB_NM + " where id=?";

        try {
            SQLiteCursor mCursor = (SQLiteCursor) mDB.rawQuery(mSQLString, new String[]{String.valueOf(id)});

            for(int i = 0 ; i < mCursor.getCount() ; i++) {
                mCursor.moveToFirst();
                color = mCursor.getInt(5);
                mCursor.moveToNext();
            }
            mCursor.close();
        } catch(SQLException e) {
            Log.e("SQLERROR", e.toString());
        }

        return color;
    }


}
