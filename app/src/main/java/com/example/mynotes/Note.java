package com.example.mynotes;

import java.util.Date;

public class Note {

    private String mTitle;
    private String mSubTitle;
    private Date mDate;

    public Note(String title,String subTitle){

        mTitle = title;
        mSubTitle = subTitle;

    }

    public String getTitle(){
        return mTitle;
    }

    public String getSubTitle(){
        return mSubTitle;
    }

    public Date getDate(){
        return mDate;
    }

}
