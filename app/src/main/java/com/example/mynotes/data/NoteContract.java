package com.example.mynotes.data;

import android.provider.BaseColumns;

public final class NoteContract {

    NoteContract(){}

    public static final class NoteEntry implements BaseColumns{

        public final static String TABLE_NAME = "Notes_Table";

        public final static String _ID = BaseColumns._ID;
        public final static String COULUMN_NOTES = "Notes";

    }










}
