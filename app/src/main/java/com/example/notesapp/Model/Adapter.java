package com.example.notesapp.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Details;
import com.example.notesapp.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    LayoutInflater inflater;
    static List<Note> notes;

    public Adapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_lsit_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String title = notes.get(position).getTitle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();
        long id  = notes.get(position).getID();


        ViewHolder.nTitle.setText(title);
        ViewHolder.nDate.setText(date);
        ViewHolder.nTime.setText(time);
        ViewHolder.nID.setText(String.valueOf(notes.get(position).getID()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        static TextView nTitle;
        static TextView nDate;
        static TextView nTime;
        static TextView nID;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.list_title);
            nDate = itemView.findViewById(R.id.list_date);
            nTime = itemView.findViewById(R.id.list_time);
            nID = itemView.findViewById(R.id.list_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Details.class);
                    intent.putExtra("ID",notes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
