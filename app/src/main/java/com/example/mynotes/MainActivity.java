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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mynotes.data.NoteDBHelper;

import java.util.ArrayList;

import com.example.mynotes.data.NoteContract.NoteEntry;

public class MainActivity extends AppCompatActivity {
    NoteDBHelper mDbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new NoteDBHelper(this);

        final ArrayList<Note> notesList = new ArrayList<>();

        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12"));


        NoteAdapter adapter = new NoteAdapter(this, notesList);
        ListView listView =  findViewById(R.id.list);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);

            }
        });


        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){

//        NoteDBHelper mDbHelper = new NoteDBHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + NoteEntry.TABLE_NAME, null);
        try{
            int num = cursor.getCount();

            Toast.makeText(MainActivity.this," "+ num,Toast.LENGTH_SHORT).show();

        }
        finally{
            cursor.close();
        }
        cursor.close();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(MainActivity.this,"All notes deleted.",Toast.LENGTH_SHORT).show();


        return super.onOptionsItemSelected(item);
    }
}
