package com.example.thenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Objects;

public class InsertNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_note);
        Toolbar toolbar = findViewById(R.id.insertNoteToolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveNote)
        {
            insertData();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertData(){
            EditText title = findViewById(R.id.TitleET);
            EditText note = findViewById(R.id.NoteET);
            ContentValues contentValues = new ContentValues();
            contentValues.put("TITLE", String.valueOf(title.getText()));
            contentValues.put("DESCRIPTION", String.valueOf(note.getText()));
            new NoteDatabase(this,contentValues).insert();
    }
}