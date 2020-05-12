package com.example.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotes.data.NoteContract.NoteEntry;
import com.example.mynotes.data.NoteDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    NoteDBHelper mDbHelper ;
    public int dbID;
    public boolean isEdit;

    private static String Title;
    private static String Desc;
    private static String Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mDbHelper = new NoteDBHelper(this);

        isEdit = Note.getEdit();

        if(isEdit){
            getNoteFromDataBase();

            EditText title_edit_text = findViewById(R.id.edit_title);
            title_edit_text.setText(Title);
            EditText desc_edit_text = findViewById(R.id.edit_desc);
            desc_edit_text.setText(Desc);

            Note.setEdit(false);
        }



        FloatingActionButton fab = findViewById(R.id.fab_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNote();
                Intent intent = new Intent(NoteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }

    private void getNoteFromDataBase(){
        dbID = Note.getID();
        Cursor res = mDbHelper.getTableData();
        if(res.getCount()==0)
        {
            Toast.makeText(NoteActivity.this,"NO NOTES!",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToFirst();
            do {
                int ID = res.getInt(0);
                String title = res.getString(1);
                String desc = res.getString(2);
                String date = res.getString(3);

                if ( ID == dbID ) {
                    Title = title;
                    Desc = desc;
                    Date = date;
                }
            } while (res.moveToNext());
        }
        res.close();
    }



    private void insertNote() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        EditText editText_title = (EditText) findViewById(R.id.edit_title);
        EditText editText_desc = (EditText) findViewById(R.id.edit_desc);

        String titleString = editText_title.getText().toString().trim();
        String descString = editText_desc.getText().toString().trim();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(new Date());

        if (!titleString.equals("") || !descString.equals("")) {

            //if both title and description are empty...it wont insert in database

            ContentValues values = new ContentValues();
            values.put(NoteEntry.COLUMN_TITLE, titleString);
            values.put(NoteEntry.COLUMN_DESC, descString);
            values.put(NoteEntry.COLUMN_DATE, date);

            long newRowID = db.insert(NoteEntry.TABLE_NAME, null, values);

            Log.v("MainActivity", "new row id = " + newRowID);

            if(newRowID == -1){
                Toast.makeText(NoteActivity.this,"Note insertion failed!",Toast.LENGTH_LONG).show();
            }

            else{

                Toast.makeText(NoteActivity.this,"Note added!",Toast.LENGTH_SHORT).show();
            }

        }



    }




}
