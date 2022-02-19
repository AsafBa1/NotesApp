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

import com.example.notesapp.Model.Note;
import com.example.notesapp.Model.NoteDB;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    EditText noteTitle;
    EditText noteDetails;
    Calendar c;
    String todayDate;
    String currentTime;
    Maps mMAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        getSupportActionBar().setTitle("New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        noteTitle = findViewById(R.id.add_Title);
        noteDetails = findViewById(R.id.add_notedet);


        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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

        c = Calendar.getInstance();
        todayDate = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
        currentTime = pad(c.get(Calendar.HOUR)+14)+":"+pad(c.get(Calendar.MINUTE));

        Log.d("calendar","Date and Time: " + todayDate +" and " + currentTime);
    }

    private String pad(int i) {
        if(i<10)
            return "0"+i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addnote_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            onBackPressed();
        }
        if(item.getItemId() == R.id.edit_save){
            Note note = new Note(noteTitle.getText().toString(),noteDetails.getText().toString(),todayDate,currentTime);
            NoteDB db = new NoteDB(this);
            db.addNote(note);
            //LatLng notepositon = new LatLng(mMAP.fusedLocationClient.la)
            goToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}