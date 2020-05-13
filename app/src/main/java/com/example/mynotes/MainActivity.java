package com.example.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
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
import android.widget.Toast;
import com.example.mynotes.data.NoteDBHelper;
import com.example.mynotes.data.NoteContract.NoteEntry;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static boolean boolDelete;
    public static boolean flagAllDelete;
    public static Note undoNote;
    public boolean doubleBackToExitPressedOnce = false;

    NoteDBHelper mDbHelper ;
    final ArrayList<Note> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(flagAllDelete){
//            Toast.makeText(MainActivity.this,"All notes deleted",Toast.LENGTH_LONG).show();
//            flagAllDelete = false;
//        }

        mDbHelper = new NoteDBHelper(this);
        Cursor res = mDbHelper.getTableData();
        if(res.getCount()==0)
        {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),"Start adding notes!",Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
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

        mRecyclerView = findViewById(R.id.recycler_view);
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
                        undoNote = notesList.get(position);
                        notesList.get(position).setID();
                        mDbHelper.deleteNote(notesList.get(position).getID());
                        boolDelete = true;
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

        if(boolDelete){
            Snackbar.make(fab,"Note deleted! ",Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertUndoNote(undoNote);
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }).show();
            boolDelete = false;
        }
        //displayDatabaseInfo(); //to check data insertion in db
    }

    private void displayDatabaseInfo(){
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

    public void insertUndoNote(Note note){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String titleString = note.getTitle();
        String descString = note.getSubTitle();
        String date = note.getDate();
        String time = note.getTime();
        if (!titleString.equals("") || !descString.equals("")) {
            ContentValues values = new ContentValues();
            values.put(NoteEntry.COLUMN_TITLE, titleString);
            values.put(NoteEntry.COLUMN_DESC, descString);
            values.put(NoteEntry.COLUMN_DATE, date);
            values.put(NoteEntry.COLUMN_TIME, time);
            long newRowID = db.insert(NoteEntry.TABLE_NAME, null, values);
            Log.v("MainActivity", "new row id = " + newRowID);
            if(newRowID == -1){
                Toast.makeText(MainActivity.this,"Undo failed!",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Note restored!",Toast.LENGTH_SHORT).show();
            }
        }
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
        flagAllDelete = true;
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to leave", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
