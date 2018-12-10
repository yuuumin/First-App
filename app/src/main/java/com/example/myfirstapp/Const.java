package com.example.myfirstapp;

import android.graphics.Color;

/**
 * Created by akiyamayumi on 2018/11/18.
 */

public class Const {

    /**
     * 時間割のDB名
     * フィールド名
     */
    public static final String TIME_TABLE_DB_NM = "time_table";
    public static final String ID_FIELD_NM = "id";
    public static final String SUBJECT_FIELD_NM = "subject";
    public static final String CLASSROOM_FIELD_NM = "classroom";
    public static final String TEACHER_FIELD_NM = "teacher";
    public static final String UNIT_FIELD_NM = "unit";
    public static final String COLOR_FIELD_NM = "color";

    /**
     * 曜日名
     * */

    public static final String MONDAY = "月";
    public static final String TUESDAY = "火";
    public static final String WEDNESDAY = "水";
    public static final String THURSDAY = "木";
    public static final String FRIDAY = "金";
    public static final String SATURDAY = "土";
    public static final String SUNDAY = "日";


    /**
     * 削除ダイアログ
     * */
    public static final String DELETE_MESSAGE = "この授業を削除しますか";
    public static final String DELETE_OK = "削除";
    public static final String DELETE_CANCEL = "キャンセル";


    /**
     * 色設定ダイアログ
     * */
    public static final String COLOR_DIALOG_MESSAGE = "背景色設定";
    public static final String COLOR_DIALOG_OK = "保存";
    public static final String COLOR_DIALOG_CLOSE = "閉じる";

    /**
     * メッセージ
     * */
    public static final String INSERT_ERROR_MSG = "データの保存に失敗しました";
    public static final String NOT_CHECK_COLOR = "カラーを選択してください";
}
