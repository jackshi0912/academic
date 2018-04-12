package cse110_group_13.ucsdconnect;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/*
 * Class Notes:
 * Date picker code is followed from https://www.youtube.com/watch?v=czKLAx750N0
 * The logic in some places is changed, but the skeleton is the same.
 * The code for the time picker dialog also mirrors the skeleton of the date picker,
 * but no tutorial was used to write it.
 *
 * Description: This class is the activity that enables the user to edit the events that they
 * have created. It provides the interface for the user to update their events in.
 *
 * Public Methods: showDialogOnStartDateClick()
 *                 showDialogOnEndDateClick()
 *                 showDialogOnStartTimeClick()
 *                 showDialogOnEndTimeClick()
 *
 */
public class EditEventActivity extends AppCompatActivity {

    private EditText eventName;
    private TextView eventStartDate;
    private TextView eventEndDate;
    private TextView eventStartTime;
    private TextView eventEndTime;
    private Spinner eventCategory;
    private EditText eventDescription;
    private Button createEvent;

    private int startEventYear = -1, startEventMonth = -1, startEventDay = -1, endEventYear = -1, endEventMonth = -1, endEventDay = -1;
    private int startEventHour = -1, startEventMinute = -1, endEventHour = -1, endEventMinute = -1;
    private static final int START_DATE_DIALOG_ID = 0;
    private static final int END_DATE_DIALOG_ID = 1;
    private static final int START_TIME_DIALOG_ID = 2;
    private static final int END_TIME_DIALOG_ID = 3;
    private static int currentDay, currentMonth, currentYear;
    static int currentHour, currentMinute;
    private static String startDate, endDate, startTime, endTime;

    public static Event createdEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        final Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        eventCategory = (Spinner) findViewById(R.id.create_event_category);
        Adapter categoryAdapter = eventCategory.getAdapter();

        int size = categoryAdapter.getCount();

        ArrayList<String> categories = new ArrayList<String>();

        for (int index = 0; index < size; index++) {

            categories.add((String) categoryAdapter.getItem(index));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_category, categories);
        eventCategory.setAdapter(arrayAdapter);


        showDialogOnStartDateClick();
        showDialogOnEndDateClick();
        showDialogOnStartTimeClick();
        showDialogOnEndTimeClick();

        // List<Event> createdEvents = User.currentUser.getCreatedEvents();

        Event editEvent = null;
        Map<String, String> currentEventInfo = User.currentUser.getEventInfo(editEvent);
        //populateFields(currentEventInfo);

        createEvent = (Button) findViewById(R.id.create_event_button);

        createEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getFields();
                Map<String, String> eventInfo = createMap();
                Event event = new Event(eventInfo);
                Log.d("CreateEvent", Boolean.valueOf(User.currentUser.checkInDatabase(event)).toString());

                if (User.currentUser.checkInDatabase(event)) {
                    Toast.makeText(EditEventActivity.this, "Event already exists!", Toast.LENGTH_LONG).show();
                }

                else if (isInputValid()) {
                    // User.currentUser.addEvent(event);
                    createdEvent = event;
                    startActivity(new Intent(EditEventActivity.this, InputMapsActivity.class));
                }
            }
        });
    }

    private void getFields() {

        eventName = (EditText) findViewById(R.id.create_event_name);
        eventDescription = (EditText) findViewById(R.id.create_event_description);
    }

    private Map<String, String> createMap() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("Title", eventName.getText().toString());
        Log.d("DATE AND TIME", startDate + " at " + startTime + " - " + endDate + " at " + endTime);
        if (startDate.equals(endDate)) {
            map.put("Date", startDate);
        }

        else {
            map.put("Date", startDate + " - " + endDate);
        }
        map.put("Time", startTime + " - " + endTime);
        map.put("Description", eventDescription.getText().toString());
        map.put("Category", eventCategory.getSelectedItem().toString());
        map.put("Location", null);
        map.put("Latitude", null);
        map.put("Longitude", null);

        return map;
    }

    public void showDialogOnStartDateClick() {
        eventStartDate = (TextView) findViewById(R.id.create_event_start_date);
        eventEndDate = (TextView) findViewById(R.id.create_event_end_date);

        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }

        });
    }

    public void showDialogOnEndDateClick() {
        eventEndDate = (TextView) findViewById(R.id.create_event_end_date);

        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }

        });
    }


    public void showDialogOnStartTimeClick() {
        eventStartTime = (TextView) findViewById(R.id.create_event_start_time);

        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_TIME_DIALOG_ID);
            }
        });
    }

    public void showDialogOnEndTimeClick() {

        eventEndTime = (TextView) findViewById(R.id.create_event_end_time);

        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == START_DATE_DIALOG_ID) return new DatePickerDialog(this, sdpickerListner, currentYear, currentMonth, currentDay);
        if (id == END_DATE_DIALOG_ID) return new DatePickerDialog(this, edpickerListner, currentYear, currentMonth, currentDay);
        if (id == START_TIME_DIALOG_ID) return new TimePickerDialog(this, stpickerListener, currentHour, currentMinute, false);
        if (id == END_TIME_DIALOG_ID) return new TimePickerDialog(this, etpickerListener, currentHour, currentMinute, false);
        return null;
    }

    private DatePickerDialog.OnDateSetListener sdpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startEventYear = year;
            startEventMonth = monthOfYear + 1;
            startEventDay = dayOfMonth;

            startDate = startEventMonth + "/" + startEventDay + "/" + startEventYear;

            eventStartDate.setText("Event Start Date: " + startDate);
            eventStartDate.setTextColor(Color.WHITE);
        }
    };

    private DatePickerDialog.OnDateSetListener edpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endEventYear = year;
            endEventMonth = monthOfYear + 1;
            endEventDay = dayOfMonth;

            endDate = endEventMonth + "/" + endEventDay + "/" + endEventYear;

            eventEndDate.setText("Event End Date: " + endDate);
            eventEndDate.setTextColor(Color.WHITE);
        }
    };

    private TimePickerDialog.OnTimeSetListener stpickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {

            startEventHour = hour;
            startEventMinute = minute;

            if (startEventMinute == 0) {
                startTime = ((startEventHour % 12 == 0) ? 12 : startEventHour % 12) + ":" + startEventMinute + "0" + ((startEventHour > 12) ? "pm" : "am");
            }

            else {
                startTime = ((startEventHour % 12 == 0) ? 12 : startEventHour % 12) + ":" + startEventMinute + ((startEventHour > 12) ? "pm" : "am");
            }

            eventStartTime.setText("Event Start Time: " + startTime);
            eventStartTime.setTextColor(Color.WHITE);
        }
    };

    private TimePickerDialog.OnTimeSetListener etpickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {

            endEventHour = hour;
            endEventMinute = minute;

            if (endEventMinute == 0) {
                endTime = ((startEventHour % 12 == 0) ? 12 : startEventHour % 12) + ":" + startEventMinute + "0" + ((startEventHour > 12) ? "pm" : "am");
            }

            else {
                endTime = ((startEventHour % 12 == 0) ? 12 : startEventHour % 12) + ":" + startEventMinute + ((startEventHour > 12) ? "pm" : "am");
            }

            eventEndTime.setText("Event End Time: " + endTime);
            eventEndTime.setTextColor(Color.WHITE);
        }
    };

    private boolean isInputValid() {

        if (eventName.getText().toString().equals("")) {
            Toast.makeText(EditEventActivity.this, "Please enter an event name", Toast.LENGTH_LONG).show();
            return false;
        }

        if (startEventYear == -1 || startEventMonth == -1 || startEventDay == -1) {
            Toast.makeText(EditEventActivity.this, "Please select a start date", Toast.LENGTH_LONG).show();
            return false;
        }

        if (endEventYear == -1 || endEventMonth == -1 || endEventDay == -1) {
            Toast.makeText(EditEventActivity.this, "Please select an end date", Toast.LENGTH_LONG).show();
            return false;
        }

        if (startEventHour == -1 || startEventMinute == -1) {
            Toast.makeText(EditEventActivity.this, "Please select a start time", Toast.LENGTH_LONG).show();
            return false;
        }

        if (endEventHour == -1 || endEventMinute == -1) {
            Toast.makeText(EditEventActivity.this, "Please select an end time", Toast.LENGTH_LONG).show();
            return false;
        }

        if (eventCategory.getSelectedItem().toString().equals("--Select Category--")) {
            Toast.makeText(EditEventActivity.this, "Please select an event category", Toast.LENGTH_LONG).show();
            return false;
        }

        if (eventDescription.getText().toString().equals("")) {
            Toast.makeText(EditEventActivity.this, "Please enter an event description", Toast.LENGTH_LONG).show();
            return false;
        }

        Calendar toDate = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();
        toDate.set(startEventYear, startEventMonth - 1, startEventDay, startEventHour, startEventMinute);

        if (toDate.before(nowDate)) {
            Toast.makeText(EditEventActivity.this, "Date and time already passed!", Toast.LENGTH_LONG).show();
            return false;
        }

        nowDate.set(endEventYear, endEventMonth - 1, endEventDay, endEventHour, endEventMinute);

        if (nowDate.before(toDate)) {
            Toast.makeText(EditEventActivity.this, "Start time is after end time", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}