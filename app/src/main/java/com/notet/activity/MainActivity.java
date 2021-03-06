package com.notet.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.adapter.NotesArrayAdapter;
import com.note.databasehandler.DabaseHandler;
import com.note.model.Notes;


import java.util.ArrayList;


public class MainActivity extends Activity {

    Activity activity;
    DabaseHandler myDabaseHandler;


    ArrayList<Notes> arrNote = null;
    NotesArrayAdapter adapter = null;
    //NotesAdapter adapterNote=null;
    GridView gvNotes;
    Notes note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BitmapDrawable background=new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.bg_actionbar));
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(background);// set background for action bar

        loadMain();
    }
    // laoding data (use when call onCreate and onResume)
    public void loadMain() {

       myDabaseHandler = new DabaseHandler(this);
//        myDabaseHandler.deleteNotes();
//        myDabaseHandler.dropTable();
//        myDabaseHandler.CreateTable();

        arrNote = myDabaseHandler.getAllNotesDESC();
        if (arrNote.size() > 0) {
            setContentView(R.layout.activity_main);
            gvNotes = (GridView) findViewById(R.id.gvNote);
            Log.d("MainActivity Notes size :", arrNote.size() + "");
            adapter = new NotesArrayAdapter(this, R.layout.custom_item_notes, arrNote);
            gvNotes.setAdapter(adapter);
        } else {
            setContentView(R.layout.activity_main_no_notes);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMain();
        Log.d("MainActivity", "Call event OnResume()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_addNote) {
            Intent i = new Intent(MainActivity.this, AddNote.class);
            startActivity(i);
            Log.d("MainActivity", "Call AddNote class");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
