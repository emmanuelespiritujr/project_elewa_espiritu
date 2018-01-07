package com.example.jespiritu.project_elewa_espiritu;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SecondActivity extends Activity {

    //constant variables that are the time interval between each station
    private static int OAKVILLE_TO_CLARKSON_INTERVAL = 8;
    private static int OAKVILLE_TO_PORTCREDIT_INTERVAL = 15;

    private static int PORTCREDIT_TO_CLARKSON_INTERVAL = 6;
    private static int PORTCREDIT_TO_OAKVILLE_INTERVAL = 14;

    private static int CLARKSON_TO_OAKVILLE_INTERVAL = 8;
    private static int CLARKSON_TO_PORTCREDIT_INTERVAL = 7;



    //declaring the list of departing times
    ListView lstDepartTimes;

    //declaring the stations depart, arrival, route taken, departTime, and the mins
    String stationDepart;
    String stationArrival;
    String routeTaken;
    String departTime;
    String newMins;

    //declaring the textview for the arrival time and departure time
    TextView txtArrivalTime;
    TextView txtDepartureTime;

    //declaring the time, hours and minutes
    int time;
    int hours;
    int mins;

    //declaring spinner the includes the timer times
    Spinner spnTimer;

    //alarm manager to set the alarm
    AlarmManager alarm;

    //intent that send to the alarm
    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //initializing the textviews, list and spinner to those in the UI
        txtArrivalTime = findViewById(R.id.txt_timeOfArr);

        txtDepartureTime = findViewById(R.id.txt_timeOfDep);

        lstDepartTimes = findViewById(R.id.lstDepartTimes);

        spnTimer = findViewById(R.id.spn_timer);

        List<Integer> setTimer = new ArrayList<Integer>();

        setTimer.add(5);
        setTimer.add(10);
        setTimer.add(15);

        ArrayAdapter<Integer> timerAdapter = new ArrayAdapter<Integer>
                (this, android.R.layout.simple_spinner_item, setTimer);

        timerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTimer.setAdapter(timerAdapter);

        Intent intent = getIntent();

        //getting information from the MainActivity intent
        if(intent != null) {
            stationDepart = intent.getStringExtra("stationDepart");
            stationArrival = intent.getStringExtra("stationArrival");
            routeTaken = intent.getStringExtra("routeTaken");

        }

        //setting the text to the departing station
        txtDepartureTime.setText("Select Departure Time From \n" + stationDepart + " Station:");

        //setting the departure time depending on the station
        if(stationDepart.equals("Oakville Go"))
        {
            ArrayList<TimesDeparture> times = TimesDeparture.getTimesOakville(this);

            ArrayAdapter<TimesDeparture> adapter = new ArrayAdapter<TimesDeparture>(
                    this, android.R.layout.simple_list_item_1, times);
            lstDepartTimes.setAdapter(adapter);

            //setting an on click listener if the user clicks on a specific time that they will be
            //departing at from that station
            lstDepartTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //getting the time the user selected
                    departTime = lstDepartTimes.getItemAtPosition(i).toString();
                    //taking out the : in the text so it can be converted to an integer
                    departTime = departTime.replace(":","");

                    //check which station the user is going to, based on that
                    //calculate the arrival time
                    if(stationArrival.equals("Clarkson Go"))
                    {
                        //add the time interval based on the station
                        time = Integer.parseInt(departTime) + OAKVILLE_TO_CLARKSON_INTERVAL;
                        //call the time calculating method
                        ArrivalTimeCalculations();
                    }
                    if(stationArrival.equals("Port Credit Go"))
                    {
                        time = Integer.parseInt(departTime) + OAKVILLE_TO_PORTCREDIT_INTERVAL;
                        ArrivalTimeCalculations();
                    }

                }
            });
        }

        //setting the departure time depending on the station and route
        if(stationDepart.equals("Clarkson Go") && routeTaken.equals("Lakeshore East"))
        {
            ArrayList<TimesDeparture> times = TimesDeparture.getTimesClarksonE(this);

            ArrayAdapter<TimesDeparture> adapter = new ArrayAdapter<TimesDeparture>(
                    this, android.R.layout.simple_list_item_1, times);

            lstDepartTimes.setAdapter(adapter);
            lstDepartTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    departTime = lstDepartTimes.getItemAtPosition(i).toString();
                    departTime = departTime.replace(":","");
                    time = Integer.parseInt(departTime) + CLARKSON_TO_PORTCREDIT_INTERVAL;
                    ArrivalTimeCalculations();
                }
            });
        }


        //setting the departure time depending on the station and route
        if(stationDepart.equals("Clarkson Go") && routeTaken.equals("Lakeshore West"))
        {
            ArrayList<TimesDeparture> times = TimesDeparture.getTimesClarksonW(this);

            ArrayAdapter<TimesDeparture> adapter = new ArrayAdapter<TimesDeparture>(
                    this, android.R.layout.simple_list_item_1, times);

            lstDepartTimes.setAdapter(adapter);
            lstDepartTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    departTime = lstDepartTimes.getItemAtPosition(i).toString();
                    departTime = departTime.replace(":","");
                    time = Integer.parseInt(departTime) + CLARKSON_TO_OAKVILLE_INTERVAL;
                    ArrivalTimeCalculations();
                }
            });
        }

        //setting the departure time depending on the station
        if(stationDepart.equals("Port Credit Go"))
        {
            ArrayList<TimesDeparture> times = TimesDeparture.getTimesPortCredit(this);

            ArrayAdapter<TimesDeparture> adapter = new ArrayAdapter<TimesDeparture>(
                    this, android.R.layout.simple_list_item_1, times);

            lstDepartTimes.setAdapter(adapter);
            lstDepartTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    departTime = lstDepartTimes.getItemAtPosition(i).toString();
                    departTime = departTime.replace(":","");
                    if(stationArrival.equals("Clarkson Go"))
                    {
                        time = Integer.parseInt(departTime) + PORTCREDIT_TO_CLARKSON_INTERVAL;
                        ArrivalTimeCalculations();
                    }
                    if(stationArrival.equals("Oakville Go"))
                    {
                        time = Integer.parseInt(departTime) + PORTCREDIT_TO_OAKVILLE_INTERVAL;
                        ArrivalTimeCalculations();
                    }
                }
            });
        }
    }

    /**
     * Method that calculates the time it will take for the user to arrive to the station
     */
    private void ArrivalTimeCalculations() {

        //getting the value of time
        String t = String.valueOf(time);

        //adding : to the time
        t = t.substring(0, t.length() -2) + ":" + t.substring(t.length() - 2 , t.length());

        //splitting it at that point
        String [] timing = t.split(":");

        //parsing mins and hours each to integers
        mins = Integer.parseInt(timing[1]);
        hours = Integer.parseInt(timing[0]);

        //checking if the mins are over 60, do the required math
        if(mins > 60)
        {
            //subtract the 60
            mins = mins - 60;
            //check if mins are less than 10
            if(mins < 10)
            {
                //add 0 infront of the number so it makes sense to the user
                newMins = "0" + String.valueOf(mins);
            }

            //in this case we need to add an hour to the house
            hours++;
        }
        //if the mins are less than 10
        if(mins < 10)
        {
            //add 0 infront of the number so it makes sense to the user
            newMins = "0" + String.valueOf(mins);
        }
        //otherwise just take the value as it is
        else
        {
            newMins = String.valueOf(mins);
        }
        //let the user know what the etimated arrival time is
        txtArrivalTime.setText("Estimated Time of Arrival at \n" + stationArrival + " Station: " + String.valueOf(hours) + ":" + newMins);
    }

    /**
     * This method is an onclick method for the button in the UI to set the alarm
     * @param view Button
     */
    public void setAlarm(View view) {

        //setting the alarm manager
        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        //intent to get to the reciever
        Intent myIntent = new Intent(SecondActivity.this, BootReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(SecondActivity.this, 0, myIntent, 0);

        //setting the calender time to the specified alarm time that the user picked
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, Integer.parseInt(newMins) - Integer.parseInt(spnTimer.getSelectedItem().toString()));

        //setting the alarm exactly to that
        alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(getApplicationContext(),0, myIntent, 0);

        //giving the user notification about his/her alarm that has been set
        int min =  Integer.parseInt(newMins) - Integer.parseInt(spnTimer.getSelectedItem().toString());
        Notification notify = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.dmi)
                .setTicker("Notification")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle("Alarm set by DMI")
                .setContentText("Your alarm has been set to " + String.valueOf(hours) + ":" + String.valueOf(min))
                .getNotification();

        NotificationManager manager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1,notify);

    }
}
