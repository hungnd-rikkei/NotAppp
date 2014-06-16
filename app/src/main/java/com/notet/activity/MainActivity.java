package com.notet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.adapter.NotesAdapter;
import com.note.databasehandler.DabaseHandler;
import com.note.model.Notes;

import java.util.ArrayList;


public class MainActivity extends Activity {

    Context context;
    DabaseHandler myDabaseHandler;

    ArrayList<Notes> arrNotes=null;
    NotesAdapter adapterNote=null;
    GridView gvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMain();

    }

    public void loadMain(){
        myDabaseHandler=new DabaseHandler(this);

        gvNotes = (GridView) findViewById(R.id.gvNote);
        try {
            arrNotes = myDabaseHandler.getAllNotes();
            adapterNote=new NotesAdapter(context,arrNotes);
            gvNotes.setAdapter(adapterNote);

        }catch (Exception e){
            Log.d("MainActivity","Error Loading...DB(db null)");
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadMain();
        Log.d("MainActivity","Call event OnResume()");

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
            Intent i = new Intent(MainActivity.this,AddNote.class);
            startActivity(i);
            Log.d("MainActivity","Call AddNote class");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}