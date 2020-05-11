package com.example.mynotes.data;

import android.provider.BaseColumns;

public final class NoteContract {

    public static Object NoteEntry;

    NoteContract(){}

    public static final class NoteEntry implements BaseColumns{

        public final static String TABLE_NAME = "Notes_Table";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "Title";
        public final static String COLUMN_DESC = "Description";
        public final static String COLUMN_DATE= "Date";


    }










}
