package com.example.thenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class NoteDatabase {
    private static final String dbName="Notes Data";
    private final ContentValues contentValues;
    private final Context context;
    private SQLiteDatabase sqdb;
    NoteDatabase(Context context,ContentValues contentValues){
        this.contentValues = contentValues;
        this.context = context;
        sqdb = (new OpenDatabase()).getWritableDatabase();
    }
    class OpenDatabase extends SQLiteOpenHelper {
        OpenDatabase(){
            super(context,dbName,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE NotesTable ("+
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TITLE TEXT," +
                    "DESCRIPTION TEXT);");
            Toast.makeText(context, "Database Created Successfully", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO : Something will be Implemented later
        }
    }
    void insert() {
        long status = sqdb.insert("NotesTable", "", contentValues);
        if (status > 0) {
            Toast.makeText(context, "Note Added", (int) 1000).show();
        } else {
            Toast.makeText(context, "Failed to Add Note", (int) 1000).show();
        }
    }
    Cursor read(){
        Cursor cursor = sqdb.query("NotesTable",new String[]{"TITLE", "DESCRIPTION"},null,null,null,null,null);
        return cursor;
    }
}
