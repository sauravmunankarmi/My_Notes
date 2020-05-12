package com.example.mynotes.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mynotes.data.NoteContract.NoteEntry;

public class NoteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Notes.db";
    private static final int DATABASE_VERSION = 2;

    public NoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_NOTE_TABLE = "CREATE TABLE " + NoteEntry.TABLE_NAME + " ("
                + NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NoteEntry.COLUMN_TITLE + " TEXT DEFAULT 'TITLE', "
                + NoteEntry.COLUMN_DESC + " TEXT DEFAULT 'EMPTY',  "
                + NoteEntry.COLUMN_DATE + " TEXT );";

        db.execSQL(SQL_CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getTableData()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res1 = database.rawQuery("select * from "+NoteEntry.TABLE_NAME,null);
        return res1;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String idString = String.valueOf(id);

        return db.delete(NoteEntry.TABLE_NAME,NoteEntry._ID + " = ?" , new String[]{idString});

    }

    public void deleteAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + NoteEntry.TABLE_NAME );
    }
}
