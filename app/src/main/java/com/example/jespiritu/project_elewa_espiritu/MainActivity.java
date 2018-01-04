package com.example.jespiritu.project_elewa_espiritu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final int SECOND_ACTIVITY = 2;

    Button btnDeparture;
    Button btnArrival;
    Button btnSeeSchedule;

    Spinner spnRoutes;

    String[] stationsWest;
    String[] stationsEast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDeparture = findViewById(R.id.btn_departure);
        btnArrival = findViewById(R.id.btn_arrival);
        btnSeeSchedule = findViewById(R.id.btn_seeSchedue);

        stationsWest = getResources().getStringArray(R.array.stations_west);
        stationsEast = getResources().getStringArray(R.array.stations_east);

        spnRoutes = findViewById(R.id.spn_route);

        ArrayList<Route> routes = Route.getRoutes(this);


        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(
                this, android.R.layout.simple_spinner_item, routes);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spnRoutes.setAdapter(adapter);

        spnRoutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                btnDeparture.setText("Station of Departure");
                btnArrival.setText("Station of Arrival");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialogListDep();
            }
        });

        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialogListArr();
            }
        });

        btnSeeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                intent.putExtra("stationDepart", btnDeparture.getText().toString());
                intent.putExtra("stationArrival", btnArrival.getText().toString());
                startActivityForResult(intent,SECOND_ACTIVITY);
            }
        });
    }


    DialogInterface.OnClickListener listListenerDep = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if(spnRoutes.getSelectedItemPosition() == 0)
            {
                btnDeparture.setText(stationsWest[i]);
            }
            else
            {
                btnDeparture.setText(stationsEast[i]);
            }
        }
    };

    private void displayDialogListDep() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(spnRoutes.getSelectedItemPosition() == 0)
        {
            builder.setTitle("Station of Departure")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setItems(stationsWest, listListenerDep)
                    .setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();

            dialog.show();
        }
        else
        {
            builder.setTitle("Station of Departure")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setItems(stationsEast, listListenerDep)
                    .setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();

            dialog.show();
        }

    }



    DialogInterface.OnClickListener listListenerArr = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            if(spnRoutes.getSelectedItemPosition() == 0)
            {
                btnArrival.setText(stationsWest[i]);
            }
            else
            {
               btnArrival.setText(stationsEast[i]);
            }

        }
    };

    private void displayDialogListArr() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(spnRoutes.getSelectedItemPosition() == 0)
        {
            builder.setTitle("Station of Arrival")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setItems(stationsWest, listListenerArr)
                    .setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();

            dialog.show();
        }
        else
        {
            builder.setTitle("Station of Arrival")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setItems(stationsEast, listListenerArr)
                    .setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();

            dialog.show();
        }

    }
}
