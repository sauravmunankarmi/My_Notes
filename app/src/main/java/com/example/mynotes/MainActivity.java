package com.example.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mynotes.data.NoteDBHelper;

import java.util.ArrayList;

import com.example.mynotes.data.NoteContract.NoteEntry;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    NoteDBHelper mDbHelper ;
    final ArrayList<Note> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new NoteDBHelper(this);


        Cursor res = mDbHelper.getTableData();
        if(res.getCount()==0)
        {
            Toast.makeText(MainActivity.this,"NOTES HERE",Toast.LENGTH_SHORT).show();
        }
        else {
            res.moveToLast();

            do {

                String title = res.getString(1);
                String desc = res.getString(2);
                String date = res.getString(3);
                String time = res.getString(4);
                int id = res.getInt(0);


                notesList.add(new Note(title,desc,date,time,id));

            } while (res.moveToPrevious());
        }
        res.close();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NoteAdapter(notesList);
        mRecyclerView.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(
                new NoteAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        notesList.get(position).setID();
                        notesList.get(position).setEdit(true);
                        Intent in = new Intent(MainActivity.this, NoteActivity.class);
                        startActivity(in);
                    }

                    @Override
                    public void onDeleteClick(int position) {

                        notesList.get(position).setID();
                        int dbID = notesList.get(position).getID();

                        mDbHelper.deleteNote(dbID);
                        Toast.makeText(MainActivity.this,dbID + "Note deleted!",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(in);

                    }
                });



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


        mDbHelper = new NoteDBHelper(this);
        mDbHelper.deleteAllNotes();
        Toast.makeText(MainActivity.this,"All notes deleted.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }



}
