package com.example.mynotes;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> local_note_list;
    private OnItemClickListener mListener;


    public NoteAdapter(ArrayList<Note> notes) {
        local_note_list = notes;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView title_text_view;
        public TextView desc_text_view;
        public TextView date_text_view;
        public ImageButton delete_button;


        public NoteViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title_text_view = itemView.findViewById(R.id.title_box);
            desc_text_view = itemView.findViewById(R.id.desc_box);
            date_text_view = itemView.findViewById(R.id.date_box);
            delete_button = itemView.findViewById(R.id.delete_button);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }

            });

        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(view,mListener);
        return noteViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {

        Note local_note = local_note_list.get(i);

        noteViewHolder.title_text_view.setText(local_note.getTitle());
        noteViewHolder.desc_text_view.setText(local_note.getSubTitle());
        noteViewHolder.date_text_view.setText(local_note.getDate());


    }

    @Override
    public int getItemCount() {
        return local_note_list.size();
    }





}
