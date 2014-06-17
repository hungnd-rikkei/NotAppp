package com.notet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.note.databasehandler.DabaseHandler;
import com.note.model.Notes;


public class AddNote extends Activity {

    // request and result code
    public static final int REQUEST_CODE_ADD_NOTE = 111;

    public static final int RESULT_COLOR = 113;
    public static final int RESULT_PHOTO = 115;

    // my color
    public static final int RESULT_COLOR_WHITE = 200;
    public static final int RESULT_COLOR_YELLOW = 201;
    public static final int RESULT_COLOR_GREEN = 202;
    public static final int RESULT_COLOR_BLUE = 203;

    //
    public int color;
    public String strAlarm = null;
    public String strDay = null;
    public String strTime = null;

    // db
    DabaseHandler myHandler;
    Notes notes;

    // controls
    EditText txtTitle, txtContent;
    ImageView btnCancel;
    TextView txtCurrentDate;
    Spinner spDate, spTime;
    LinearLayout llAddNote;
    // dialog
    static final int TIME_DIALOG_ID = 155;
    static final int DATE_DIALOG_ID = 166;
    // time and date
    int Hour, Min, Second, Day, Month, Year;


    ArrayAdapter<String> adapterDay = null;
    ArrayAdapter<String> adapterTime = null;
    String arrDate[]=null;
    String arrTime[]=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        arrDate = getResources().getStringArray(R.array.arrdate);
        arrTime = getResources().getStringArray(R.array.arrtime);
        getControls();
        try {
            adapterDay = new ArrayAdapter<String>(AddNote.this, android.R.layout.simple_spinner_item, arrDate);
            adapterTime = new ArrayAdapter<String>(AddNote.this, android.R.layout.simple_spinner_item, arrTime);

            adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spDate.setAdapter(adapterDay);
            spTime.setAdapter(adapterTime);

            spDate.setOnItemSelectedListener(new myOnItemClickListener());
            spTime.setOnItemSelectedListener(new myOnItemClickListener2());

        } catch (Exception e) {
            Log.d("AddNote", "Load arrString Error...");
        }


    }

    public void getControls() {
        spDate = (Spinner) findViewById(R.id.spDate);
        spTime = (Spinner) findViewById(R.id.spTime);
        txtContent = (EditText) findViewById(R.id.txtAddContent);
        txtTitle = (EditText) findViewById(R.id.txtAddTitle);
//        txtCurrentDate = (TextView) findViewById(R.id.txtCreatedDate);
        btnCancel = (ImageView) findViewById(R.id.btnCancel);
        llAddNote = (LinearLayout) findViewById(R.id.llAddNote);


    }

    private class myOnItemClickListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if (i == 3) {
                showDialog(DATE_DIALOG_ID);
                strDay=Day+"/"+Month+"/"+Year;
                arrDate[3]=strDay;

            } else {
                arrDate[3]="Other...";
                strDay = spDate.getSelectedItem().toString();
            }
            adapterDay.notifyDataSetChanged();
           // spDate.setAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            strAlarm = "";
        }
    }

    private class myOnItemClickListener2 implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if (i == 3) {
                showDialog(TIME_DIALOG_ID);
                strTime=Hour+":"+Min;
                arrTime[3]=strTime;

            } else {
                strTime = spTime.getSelectedItem().toString();
                arrTime[3]="Other...";
            }
            adapterTime.notifyDataSetChanged();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            strAlarm = "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_insertphoto:
                InsertPhoto();
                break;
            case R.id.action_setbackground:
                SetBacground();
                break;
            case R.id.action_savenote:
                SaveNote();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void InsertPhoto() {
        Intent i = new Intent(AddNote.this, InsertPhoto.class);
        startActivityForResult(i, REQUEST_CODE_ADD_NOTE);
        Log.d("AddNote", "Call activity InertPhoto");
    }

    public void SetBacground() {
        Intent i = new Intent(AddNote.this, ChooseColor.class);
        startActivityForResult(i, REQUEST_CODE_ADD_NOTE);
        Log.d("AddNote", "Call activity ChooseColor");
    }

    public void SaveNote() {
        myHandler = new DabaseHandler(this);
        strAlarm = strDay + " " + strTime;
        notes = new Notes();
        notes.setTitle(txtTitle.getText() + "");
        notes.setContent(txtContent.getText() + "");
        // notes.setCreatedDate(txtCurrentDate.getText() + "");
        notes.setCreatedDate("16/06/2014");
        notes.setBackground(color + "");
        notes.setAlarm(strAlarm);
        try {
            myHandler.addNote(notes);
            myHandler.close();
            finish();
            Log.d("AddNote", "Call back to MainActivity class");
        } catch (Exception e) {
            Log.d("AddNote", "Add new a note errorr..." + e.toString());
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int[] arrColor = {getResources().getColor(R.color.color_white), getResources().getColor(R.color.color_yellow),
                getResources().getColor(R.color.color_green), getResources().getColor(R.color.color_blue)};
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_COLOR) {
            Bundle b = data.getBundleExtra("DATA");
            color = b.getInt("COLOR");
            Log.d("Add note", "Background " + color);

            switch (color) {
                case RESULT_COLOR_WHITE:
                    llAddNote.setBackgroundColor(arrColor[0]);
                    break;
                case RESULT_COLOR_YELLOW:
                    llAddNote.setBackgroundColor(arrColor[1]);
                    break;
                case RESULT_COLOR_GREEN:
                    llAddNote.setBackgroundColor(arrColor[2]);
                    break;
                case RESULT_COLOR_BLUE:
                    llAddNote.setBackgroundColor(arrColor[3]);
                    break;
                default:
                    llAddNote.setBackgroundColor(arrColor[4]);
                    break;
            }

        }

    }

    // datepicker and timepiker dialog

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
               return new TimePickerDialog(this, timePickerListener, Hour, Min, false);

            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, Year, Month, Day);

            default:
                break;

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                Hour=hourOfDay;
            Min=minutes;

        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Year=year;
            Month=month;
            Day=day;
        }
    };
}
