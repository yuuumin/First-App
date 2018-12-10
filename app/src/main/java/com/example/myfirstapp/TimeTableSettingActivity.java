package com.example.myfirstapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTableSettingActivity extends AppCompatActivity {

    private EditText mSubjectText;
    private EditText mClassroomText;
    private EditText mTeacherText;
    private EditText mUnitText;
    private TextView mColorText;

    private TimeTableDBOpenHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private ContentValues mValues;

    private int mViewId;
    private int mColor = 0;

    private AlertDialog.Builder mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_setting);

        Intent intent = getIntent();
        // 時間曜日と色を受け取る
        mViewId = intent.getIntExtra("VIEW_ID", 0);
        mColor = intent.getIntExtra("COLOR_VIEW_ID", 0);

        settingDeleteDialog();
        createLayout();

        mHelper = new TimeTableDBOpenHelper(this);
        mDB = mHelper.getWritableDatabase();

        if(intent.getStringExtra("ACTIVITY") != null) {
            getTimeTableData(mViewId);
        }

    }

    /**
     * レイアウト各種設定
     * */
    public void createLayout() {
        mSubjectText = (EditText)findViewById(R.id.subject_text);
        mClassroomText = (EditText)findViewById(R.id.classroom_text);
        mTeacherText = (EditText)findViewById(R.id.teacher_text);
        mUnitText = (EditText)findViewById(R.id.credit_text);
        mColorText = (TextView)findViewById(R.id.color_text);
        if(mColor != 0) {
            mColorText.setBackgroundColor(mColor);
        } else {
            mColorText.setBackgroundColor(Color.GRAY);
        }
    }

    /**
     * 削除ボタン押下時のダイアログ設定
     * */
    public void settingDeleteDialog() {
        mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setTitle(Const.DELETE_MESSAGE);
        mAlertDialog.setPositiveButton(Const.DELETE_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 削除ボタン押下時の処理
                mHelper.deleteTimeTableData(mDB, mViewId);
                Intent intent = new Intent(TimeTableSettingActivity.this, TimeTableActivity.class);
                startActivity(intent);
            }
        });
        mAlertDialog.setNegativeButton(Const.DELETE_CANCEL,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // キャンセルボタン押下時の処理
                // 何もせず閉じる
            }
        });
    }




    /**
     * メニュー表示
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.time_table_menu, menu);
        return true;
    }

    /**
     * 保存ボタン押下
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mId = item.getItemId();

        if (mId == R.id.toolbar_storage) {
            if(String.valueOf(mSubjectText.getText()) != null) {
                saveTimeTableData(mViewId, String.valueOf(mSubjectText.getText()), String.valueOf(mClassroomText.getText()),
                        String.valueOf(mTeacherText.getText()), Integer.parseInt(String.valueOf(mUnitText.getText())), mColor);
            }

            Intent intent = new Intent(TimeTableSettingActivity.this, TimeTableActivity.class);
            startActivity(intent);
        }

        return true;
    }

    /**
     * 色選択押下
     * */
    public void onClickColorSetting(View view) {
//        Intent intent = new Intent(TimeTableSettingActivity.this, ColorSettingActivity.class);
//        intent.putExtra("VIEW_ID", mViewId);
//        startActivity(intent);
        DialogFragment newFragment = new ColorDialogFragment();
        newFragment.show(getFragmentManager(), "test");
    }


    /**
     * 削除ボタン押下
     * */
    public void onClickDeleteButton(View view) {
        mAlertDialog.show();
    }


    /**
     * 時間割データ取得
     * */
    public void getTimeTableData(int id) {

        String mSQLString = "select * from " + Const.TIME_TABLE_DB_NM + " where id=?";

        try {
            SQLiteCursor mCursor = (SQLiteCursor) mDB.rawQuery(mSQLString, new String[]{String.valueOf(id)});

            for(int i = 0 ; i < mCursor.getCount() ; i++) {
                mCursor.moveToFirst();
                mSubjectText.setText(mCursor.getString(1));
                mClassroomText.setText(mCursor.getString(2));
                mTeacherText.setText(mCursor.getString(3));
                mUnitText.setText(mCursor.getString(4));
                mColorText.setBackgroundColor(mCursor.getInt(5));
                mCursor.moveToNext();
            }
            mCursor.close();
        } catch(SQLException e) {
            Log.e("SQLERROR", e.toString());
        }
    }

    /**
     * 時間割データ保存
     * */
    public void saveTimeTableData(int id, String subject, String classroom, String teacher, int unit, int color) {
        mValues = new ContentValues();

        mValues.put(Const.ID_FIELD_NM, id);
        mValues.put(Const.SUBJECT_FIELD_NM, subject);
        mValues.put(Const.CLASSROOM_FIELD_NM, classroom);
        mValues.put(Const.TEACHER_FIELD_NM, teacher);
        mValues.put(Const.UNIT_FIELD_NM, unit);
        mValues.put(Const.COLOR_FIELD_NM, color);
        if (mDB.insert(Const.TIME_TABLE_DB_NM, null, mValues) < 0) {
            Toast.makeText(getApplicationContext(), Const.INSERT_ERROR_MSG, Toast.LENGTH_LONG);
        }
    }
}
