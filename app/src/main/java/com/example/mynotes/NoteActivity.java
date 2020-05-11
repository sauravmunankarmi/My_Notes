package com.example.mynotes;

import android.content.ContentValues;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mDbHelper = new NoteDBHelper(this);




        FloatingActionButton fab = findViewById(R.id.fab_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "FAB clicked!!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                insertNote();

                Intent intent = new Intent(NoteActivity.this,MainActivity.class);
                startActivity(intent);


            }
        });


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

        }



    }




}
