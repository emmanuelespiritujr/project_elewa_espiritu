package com.example.jespiritu.project_elewa_espiritu;

//  Created by Salim & Emmanuel on 2017-12-12.
//  Copyright © 2017 Salim Elewa & Emmanuel Espiritu. All rights reserved.
//
// This program allows a user to pick the train route they are taking,
// Then the program gives the user 3 stations to select from,
// that he or she will be departing from and arriving to
// After setting the stations, the user is taken to a separate page to
// Pick the time they will be departing at, and finally setting a time to
// be reminded at upon approaching that arrival station
// Limitations of the program include:
// - limited to 3 stations
// - only 8:30 am - 12:00 pm time
// - Timer intervals are only 5,10,15 mins

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    //declaring and initializing a constant variable for the second activity
    private static final int SECOND_ACTIVITY = 2;

    //declaring buttons that are in the UI
    Button btnDeparture;
    Button btnArrival;
    Button btnSeeSchedule;

    //spinner that has the routes
    Spinner spnRoutes;

    //list of strings of stations going west/east
    String[] stationsWest;
    String[] stationsEast;

    //the variable for the position that represents which route the user is taking
    int i;

    //declaring checkbox for the user to save their favourite station
    CheckBox chkSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the button to the UI's buttons
        btnDeparture = findViewById(R.id.btn_departure);
        btnArrival = findViewById(R.id.btn_arrival);
        btnSeeSchedule = findViewById(R.id.btn_seeSchedue);

        //setting the lists to the string arrays that we have created
        stationsWest = getResources().getStringArray(R.array.stations_west);
        stationsEast = getResources().getStringArray(R.array.stations_east);

        //initializing the checkbox to the UI's checkbox
        chkSave = findViewById(R.id.chkbox_Save);

        //setting a method to when the user checks the box
        chkSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //save the user's choice if the checkbox is checked
                savePrefs();
            }
        });

          //loading the checkbox preference
         loadPrefs();

        //setting the spinner to match the spinner in the UI
        spnRoutes = findViewById(R.id.spn_route);

        // creating an arraylist that will contain the routes the user is taking
        ArrayList<Route> routes = Route.getRoutes(this);

        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(
                this, android.R.layout.simple_spinner_item, routes);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        //setting the array adapter to display the routes
        spnRoutes.setAdapter(adapter);

        //setting a listener to when the user picks a route to take
        spnRoutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //set the button to display the pre-set message
                //btnDeparture.setText(R.string.Station_Departure);
                loadPrefs();
                btnArrival.setText(R.string.Station_Arrival);

                //setting the position of the route the user is taking
                i = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //setting an listener to when the user clicks the departure button to select a station
        btnDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the displayDialogListDep method
                displayDialogListDep();
            }
        });

        //setting an listener to when the user clicks the arrival button to select a station
        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the displayDialogListDep method
                displayDialogListArr();
            }
        });

        //setting an listener to when the user clicks the see schedule button
        btnSeeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is an error handling, if the user accidentally puts wrong order of stations/
                //forgets to pick stations, then it lets them know using a toast
                if(btnDeparture.getText().equals("Oakville Go") && btnArrival.getText().equals("Oakville Go")
                        || btnDeparture.getText().equals("Clarkson Go") && btnArrival.getText().equals("Clarkson Go")
                        || btnDeparture.getText().equals("Port Credit Go") && btnArrival.getText().equals("Port Credit Go")
                        || btnDeparture.getText().equals("Station of Departure")
                        || btnDeparture.getText().equals("Station of Departure") && btnArrival.getText().equals("Station of Arrival")
                        || btnArrival.getText().equals("Station of Arrival")
                        || i == 0 && btnDeparture.getText().equals("Oakville Go")
                        || i == 0 && btnArrival.getText().equals("Port Credit Go")
                        || i == 1 && btnDeparture.getText().equals("Port Credit Go")
                        || i == 1 && btnArrival.getText().equals("Oakville Go"))
                {
                    Toast.makeText(getApplicationContext(), "Invalid Selection for Travel!", Toast.LENGTH_LONG).show();
                }
                //Otherwise it takes the user to the second page
                else
                {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    //passing through the station of departure and arrival and the route taken
                    //to the second page
                    intent.putExtra("stationDepart", btnDeparture.getText().toString());
                    intent.putExtra("stationArrival", btnArrival.getText().toString());
                    intent.putExtra("routeTaken",spnRoutes.getSelectedItem().toString());
                    startActivityForResult(intent,SECOND_ACTIVITY);
                }
            }
        });
    }

    /**
     * Method that saves the user's preference and her/his station
     */
    private void savePrefs() {
        //calling shared preferences to edit it
        SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor editor = myPrefs.edit();

        //putting the value of the checkbox into saveStation
        editor.putBoolean("saveStation",chkSave.isChecked())
                .apply();

        //if the checkbox is checked, save the station
        if(chkSave.isChecked())
        {
            editor.putString("departingStation",btnDeparture.getText().toString())
                    .apply();
        }
    }

    /**
     * Method that loads the user's preference and sets his/her station
     */
    private void loadPrefs() {
        //loading the value of the checkbox and setting it
        SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
        chkSave.setChecked(myPrefs.getBoolean("saveStation", false));
        //if the check box is checked
        if(chkSave.isChecked())
        {
            String station = myPrefs.getString("departingStation",null);
            //set the text to the saved station
            btnDeparture.setText(station);
        }
        //otherwise, to the pre-set message
        else
        {
            btnDeparture.setText(R.string.Station_Departure);
        }

    }


    //when the user selects a station to depart from, the buttons text is set to that station
    DialogInterface.OnClickListener listListenerDep = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //depending on the route taken it displays the right station
            if(spnRoutes.getSelectedItemPosition() == 0)
            {
                btnDeparture.setText(stationsWest[i]);
                savePrefs();
            }
            else
            {
                btnDeparture.setText(stationsEast[i]);
                savePrefs();
            }
        }
    };

    //this will display the dialog box that will give the user the list of stations of departure
    private void displayDialogListDep() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //depending on the route the user picked, the order the stations changes
        //in this case, the list of stations is changed
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


    //when the user selects a station to depart from, the buttons text is set to that station
    DialogInterface.OnClickListener listListenerArr = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //depending on the route taken it displays the right station
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


    //this will display the dialog box that will give the user the list of stations of arrival
    private void displayDialogListArr() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //depending on the route the user picked, the order the stations changes
        //in this case, the list of stations is changed
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
