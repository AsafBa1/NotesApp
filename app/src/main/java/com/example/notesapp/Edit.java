package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.Model.Note;
import com.example.notesapp.Model.NoteDB;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    EditText noteTitle;
    EditText noteDetails;
    Calendar c;
    String todayDate;
    String currentTime;
    Note note;
    long nId;
    NoteDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        nId = i.getLongExtra("ID",0);
        db = new NoteDB(this);
        note = db.getNote(nId);

        getSupportActionBar().setTitle(note.getTitle());
        final String title = note.getTitle();
        String content = note.getContent();
        noteTitle = findViewById(R.id.edit_title);
        noteDetails = findViewById(R.id.edit_body);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        noteTitle.setText(title);
        noteDetails.setText(content);

        // set current date and time
        c = Calendar.getInstance();
        todayDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: "+todayDate);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+currentTime);
    }
    private String pad(int i) {
        if(i<10)
            return "0"+i;
        return String.valueOf(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.edit_delete){
            db.deleteNote(note.getID());
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        if(item.getItemId() == R.id.edit_save){
            if(noteTitle.getText().length() != 0){
                note.setTitle(noteTitle.getText().toString());
                note.setContent(noteDetails.getText().toString());
                int id = db.editNote(note);

                Intent intent = new Intent(getApplicationContext(),Details.class);
                intent.putExtra("ID",note.getID());
                startActivity(intent);
            }else
                noteTitle.setError("Title Can Not be Blank");
        }
        return super.onOptionsItemSelected(item);
    }
}