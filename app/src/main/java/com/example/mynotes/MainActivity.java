package com.example.mynotes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Note> notesList = new ArrayList<>();

        notesList.add(new Note("Title","Subtitle 1 \n subtitle 2"));


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
    }
}
