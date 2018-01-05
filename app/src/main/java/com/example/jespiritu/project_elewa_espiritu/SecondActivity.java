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

    private static int OAKVILLE_TO_CLARKSON_INTERVAL = 8;
    private static int OAKVILLE_TO_PORTCREDIT_INTERVAL = 15;

    private static int PORTCREDIT_TO_CLARKSON_INTERVAL = 6;
    private static int PORTCREDIT_TO_OAKVILLE_INTERVAL = 14;

    private static int CLARKSON_TO_OAKVILLE_INTERVAL = 8;
    private static int CLARKSON_TO_PORTCREDIT_INTERVAL = 7;



    ListView lstDepartTimes;

    String stationDepart;
    String stationArrival;
    String routeTaken;
    String departTime;
    String newMins;

    TextView txtArrivalTime;
    TextView txtDepartureTime;

    int time;
    int hours;
    int mins;

    Spinner spnTimer;

    AlarmManager alarm;

    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

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

        if(intent != null) {
            stationDepart = intent.getStringExtra("stationDepart");
            stationArrival = intent.getStringExtra("stationArrival");
            routeTaken = intent.getStringExtra("routeTaken");

        }

        txtDepartureTime.setText("Select Departure Time From \n" + stationDepart + " Station:");

        if(stationDepart.equals("Oakville Go"))
        {
            ArrayList<TimesDeparture> times = TimesDeparture.getTimesOakville(this);

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
                        time = Integer.parseInt(departTime) + OAKVILLE_TO_CLARKSON_INTERVAL;
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

    private void ArrivalTimeCalculations() {
        String t = String.valueOf(time);
        t = t.substring(0, t.length() -2) + ":" + t.substring(t.length() - 2 , t.length());
        String [] timing = t.split(":");
        mins = Integer.parseInt(timing[1]);
        hours = Integer.parseInt(timing[0]);
        if(mins > 60)
        {
            mins = mins - 60;
            if(mins < 10)
            {
                newMins = "0" + String.valueOf(mins);
            }

            hours = hours + 1;
        }
        if(mins < 10)
        {
            newMins = "0" + String.valueOf(mins);
        }
        else
        {
            newMins = String.valueOf(mins);
        }
        txtArrivalTime.setText("Estimated Time of Arrival at \n" + stationArrival + " Station: " + String.valueOf(hours) + ":" + newMins);
    }

    public void setAlarm(View view) {

        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(SecondActivity.this, BootReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(SecondActivity.this, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, Integer.parseInt(newMins) - Integer.parseInt(spnTimer.getSelectedItem().toString()));

        alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(getApplicationContext(),0, myIntent, 0);


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
