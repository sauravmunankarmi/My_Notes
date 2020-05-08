package com.example.mynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Note> notesList = new ArrayList<Note>();

        notesList.add(new Note("title1", "This is LIne 1 \n this is line 2  \n this isn line 3 \n this is aline 4"));
        notesList.add(new Note("title2", "subtitle2"));
        notesList.add(new Note("title3", "subtitle3"));
        notesList.add(new Note("title4", "subtitle4"));
        notesList.add(new Note("title5", "subtitle5"));
        notesList.add(new Note("title6", "subtitle6"));
        notesList.add(new Note("title7", "subtitle7"));
        notesList.add(new Note("title8", "subtitle8"));
        notesList.add(new Note("title9", "subtitle9"));


        NoteAdapter adapter = new NoteAdapter(this, notesList);
        ListView listView =  findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
