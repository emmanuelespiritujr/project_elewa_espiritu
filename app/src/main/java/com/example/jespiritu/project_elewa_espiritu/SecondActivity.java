package com.example.jespiritu.project_elewa_espiritu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends Activity {

    private static int CLARKSON_DEP_TIME_INTERVAL = 8;
    private static int PORTCREDIT_DEP_TIME_INTERVAL = 15;

    private static int CLARKSON_ARR_TIME_INTERVAL = 6;
    private static int OAKVILLE_ARR_TIME_INTERVAL = 14;

    ListView lstDepartTimes;

    String stationDepart;
    String stationArrival;

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txt = findViewById(R.id.txt_timeOfDep);

        lstDepartTimes = findViewById(R.id.lstDepartTimes);
        ArrayList<TimesDeparture> times = TimesDeparture.getTimes(this);

        ArrayAdapter<TimesDeparture> adapter = new ArrayAdapter<TimesDeparture>(
                this, android.R.layout.simple_spinner_item, times);




        Intent intent = getIntent();

        if(intent != null) {
             stationDepart = intent.getStringExtra("stationDepart");
             stationArrival = intent.getStringExtra("stationArrival");

            txt.setText(stationDepart);

        }

        if(stationDepart.equals("Oakville Go"))
        {
            lstDepartTimes.setAdapter(adapter);
        }
    }
}
