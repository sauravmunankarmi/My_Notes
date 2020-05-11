package com.example.mynotes;

import java.util.Date;

public class Note {

    private String mTitle ;
    private String mSubTitle ;
    private String mDate ;

    public Note(String title,String subTitle,String date){

        mTitle = title;
        mSubTitle = subTitle;
        mDate = date;

    }

    public String getTitle(){
        return mTitle;
    }

    public String getSubTitle(){
        return mSubTitle;
    }

    public  String getDate(){
        return mDate;
    }

}
