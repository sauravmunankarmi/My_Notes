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
        setContentView(R.layout.test);

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
                int id = res.getInt(0);


                notesList.add(new Note(title,desc,date,id));

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
                });





//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",1));
//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",2));
//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",3));
//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",4));
//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",5));
//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",6));
//        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2","JAN 12",7));




//        NoteAdapter adapter = new NoteAdapter(this, notesList);
//        ListView listView =  findViewById(R.id.list);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Note note = notesList.get(position);
//                Toast.makeText(MainActivity.this,position + " ",Toast.LENGTH_SHORT).show();
//            }
//        });





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


//    public void delete_button_action(View view) {
//
//        Toast.makeText(MainActivity.this,"Tapped del",Toast.LENGTH_SHORT).show();
//
//
//    }


//    public void test_action(View view) {
//
//        Toast.makeText(MainActivity.this,"Tapped Notes",Toast.LENGTH_SHORT).show();
//
//    }
}
