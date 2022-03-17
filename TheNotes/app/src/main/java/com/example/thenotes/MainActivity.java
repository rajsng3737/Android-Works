package com.example.thenotes;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        ArrayList<String[]> notes = new ArrayList<>();
        NoteDatabase nd = new NoteDatabase(this,null);
        notes.clear();
        Cursor cursor = nd.read();
        if(cursor.moveToFirst()) {
            do {
                notes.add(new String[]{cursor.getString(0), cursor.getString(1)});
            } while (cursor.moveToNext());
            GridView grd = findViewById(R.id.grdView);
            grd.setAdapter(new TicketsInfalter(notes));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(this, InsertNoteActivity.class);
            startActivity(intent);
            return true;
        }
        else
            Toast.makeText(this, "Unable to Add", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
    class TicketsInfalter extends BaseAdapter{
        ArrayList<String[]> notesAL;
        String[] temp;
        TicketsInfalter(ArrayList<String[]> notes ){
            this.notesAL=notes;
        }
        @Override
        public int getCount() {
            return notesAL.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = getLayoutInflater().inflate(R.layout.ticket_for_main,null);
            TextView title = myView.findViewById(R.id.TitleTV);
            TextView desc = myView.findViewById(R.id.NoteTV);
            temp = notesAL.get(position);
            title.setText(temp[0]);
            desc.setText(temp[1]);
            return myView;
        }
    }
}