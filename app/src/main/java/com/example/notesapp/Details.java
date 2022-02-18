package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.Model.Note;
import com.example.notesapp.Model.NoteDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Details extends AppCompatActivity {
    FloatingActionButton editBtn;
    TextView deTb;
    NoteDB db;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatails);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Long id = intent.getLongExtra("ID",0);

        db = new NoteDB(this);
        note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        deTb = findViewById(R.id.det_body);
        deTb.setText(note.getContent());
        deTb.setMovementMethod(new ScrollingMovementMethod());


        Toast.makeText(this,"Title --> " + note.getTitle(),Toast.LENGTH_SHORT).show();

        editBtn = findViewById(R.id.det_edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this,Edit.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.det_delete){
            db.deleteNote(note.getID());
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}