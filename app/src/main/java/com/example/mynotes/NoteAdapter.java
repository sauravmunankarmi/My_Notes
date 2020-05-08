package com.example.mynotes;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter {

    public NoteAdapter(MainActivity context, ArrayList<Note> notes) {
        super(context,0, notes);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Note local_note = (Note) getItem(position);


        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_box);
        assert local_note != null;
        titleTextView.setText(local_note.getTitle());

        TextView subTitleTextView = (TextView) listItemView.findViewById(R.id.desc_box);
        subTitleTextView.setText(local_note.getSubTitle());

        return listItemView;
    }
}
