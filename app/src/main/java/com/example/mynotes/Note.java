package com.example.mynotes;

import java.util.Date;

public class Note {

    private String mTitle ;
    private String mSubTitle ;
    private String mDate ;
    private String mTime;
    private int mID;
    public static int exportID;
    public static boolean edit;


    public Note(String title,String subTitle,String date,String time,int id){

        mTitle = title;
        mSubTitle = subTitle;
        mDate = date;
        mTime = time;
        mID = id;

    }

    public void setID(){

        exportID = mID;
    }

    public static void setEdit(boolean bool){

        edit = bool;
    }

    public static boolean getEdit(){

        return edit;
    }

    public static int getID(){
        return exportID;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getSubTitle(){
        return mSubTitle;
    }

    public String getDate(){
        return mDate;
    }

    public String getTime() { return mTime; }

//    public int getID() { return mID; }

}
