package com.example.myfirstapp;

/**
 * Created by akiyamayumi on 2018/11/16.
 */

public class TimeTableData {

    String mSubject;
    String mClassRoom;
    String mTeacher;

    int mUnit;
    int mSettingColor;

    public void setSubject(String subject){
        mSubject = subject;
    }

    public void setClassRoom(String classroom){
        mClassRoom = classroom;
    }

    public void setTeacher(String teacher){
        mTeacher = teacher;
    }

    public void setUnit(int unit){
        mUnit = unit;
    }

    public void setSettingColor(int color){
        mSettingColor = color;
    }

    public String getSubject(){
        return mSubject;
    }

    public String getClassRoom(){
        return mClassRoom;
    }

    public String getTeacher(){
        return mTeacher;
    }

    public int getUnit(){
        return mUnit;
    }

    public int getSettingColor(){
        return mSettingColor;
    }
}
