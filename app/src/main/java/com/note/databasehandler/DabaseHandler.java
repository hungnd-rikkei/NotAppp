package com.note.databasehandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.note.model.Notes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DUYHUNG on 16-06-2014.
 * class use to handle model and create db for app
 * create CRUD
 */
public class DabaseHandler extends SQLiteOpenHelper {

    Context context;
    Notes note;

    // db version
    public static final int DATABASE_VERSION = 1;
    // db name
    public static final String DATABASE_NAME = "NoteDB";
    //tbl name
    public static final String TABLE_NOTES = "NewNotes";
    public static final String TABLE_NEW_NOTES = "NewNotes";

    // all column of table notes
    public static final String KEY_ID = "Id";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_CONTENT = "Content";
    public static final String KEY_CREATED_DATE = "CreatedDate";
    public static final String KEY_ALARM = "Alarm";
    public static final String KEY_BACGROUND = "Backround";

    // string query create table notes
    public static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + "("
            + KEY_ID + " INTEGER  IDENTITY(1,1) PRIMARY KEY ,"
            + KEY_TITLE + " TEXT,"
            + KEY_CONTENT + " TEXT,"
            + KEY_CREATED_DATE + " TEXT,"
            + KEY_ALARM + " TEXT,"
            + KEY_BACGROUND + " TEXT" + ")";



    public void delDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    public DabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }

    public void CreateTable() {
        SQLiteDatabase db = getWritableDatabase();
        onCreate(db);
        Log.d("Create table ", TABLE_NOTES);

    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
        Log.d("drop table ", TABLE_NOTES);
    }

    // CRUD (create,read,update,delete)
    public void addNote(Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        //values.put(KEY_ID,note.getId());
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_CREATED_DATE, note.getCreatedDate());
        values.put(KEY_ALARM, note.getAlarm());
        values.put(KEY_BACGROUND, note.getBackground());

        // insert row
        db.insert(TABLE_NOTES, null, values);
        db.close();
        Log.d("Add Insert NOTE", note.toString() + " Id :" + (idMax() + 1));


    }

    //get note --------------
    public Notes getNote(int Id) {
        Notes notes = new Notes();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_CREATED_DATE, KEY_ALARM, KEY_BACGROUND}, KEY_ID + "=?", null, null, null, null);
        if (cursor.moveToFirst()) {

            notes.setId(Integer.parseInt(cursor.getString(0)));
            notes.setTitle(cursor.getString(1));
            notes.setContent(cursor.getString(2));
            notes.setCreatedDate(cursor.getString(3));
            notes.setAlarm(cursor.getString(4));
            notes.setBackground(cursor.getString(5));

        }
        Log.d("Get Note", notes.toString());

        return notes;
    }

    // get allnote
    public ArrayList<Notes> getAllNotes() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Notes> arrNotes = new ArrayList<Notes>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Get all nots", "Note size :" + cursor.getCount());
        if (cursor.moveToFirst()) {
            Log.d("GetAll NOTEdddddddddddddd", "MoveToFirst");
            do {
                Notes notes = new Notes();
                //notes.setId(Integer.parseInt(cursor.getString(0)));
                notes.setTitle(cursor.getString(1));
                notes.setContent(cursor.getString(2));
                notes.setCreatedDate(cursor.getString(3));
                notes.setAlarm(cursor.getString(4));
                notes.setBackground(cursor.getString(5));

                arrNotes.add(notes);

                Log.d("GetAll NOTEdddddddddddddd", notes.toString());
            } while (cursor.moveToNext());
        }


        return arrNotes;
    }

    public ArrayList<Notes> getAllNotesDESC() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Notes> arrNotes = new ArrayList<Notes>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Get all nots", "Note size :" + cursor.getCount());
        if (cursor.moveToLast()) {
            Log.d("GetAll NOTEdddddddddddddd", "MoveToFirst");
            do {
                Notes notes = new Notes();
                //notes.setId(Integer.parseInt(cursor.getString(0)));
                notes.setTitle(cursor.getString(1));
                notes.setContent(cursor.getString(2));
                notes.setCreatedDate(cursor.getString(3));
                notes.setAlarm(cursor.getString(4));
                notes.setBackground(cursor.getString(5));

                arrNotes.add(notes);

                Log.d("GetAll NOTEdddddddddddddd", notes.toString());
            } while (cursor.moveToPrevious());
        }


        return arrNotes;
    }

    // count note
    public int countNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        Cursor cursor = db.rawQuery(TABLE_NOTES, null);
        cursor.close();
        return cursor.getCount();
    }

    // get id notes max
    public int idMax() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT MAX(" + KEY_ID + ")" + " FROM " + TABLE_NOTES;

        Cursor cursor = db.rawQuery(select, null);
        int id = 0;
        if (cursor.getCount() > 0) {
            Log.d("item note count() :", "" +cursor.getCount());
            cursor.moveToFirst();
            id = cursor.getInt(0);
            Log.d("item note id :", "" +id);
        }
        Log.d("Id max :", "" + id);

        return id;
    }

    public int updateNotes(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_CREATED_DATE, note.getCreatedDate());
        values.put(KEY_ALARM, note.getAlarm());
        values.put(KEY_BACGROUND, note.getBackground());

        int i = db.update(TABLE_NOTES, values, KEY_ID + "=?", new String[]{String.valueOf(notes.getId())});
        db.close();
        Log.d("UPDATE NOTE", notes.toString());
        return i;
    }

    // delete note selected
    public void deleteNotes(Notes notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + "=?", new String[]{String.valueOf(notes.getId())});
        db.close();
        Log.d("DELETE NOTE", notes.toString());
    }

    // del table notes if need
    public void deleteNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NOTES);
        db.close();
        Log.d("DELETE NOTE", "delete all notes");
    }
}
