package com.example.jespiritu.project_elewa_espiritu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button btnDeparture;
    Button btnArrival;
    Button btnSeeSchedule;

    Spinner spnRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDeparture = findViewById(R.id.btn_departure);
        btnArrival = findViewById(R.id.btn_arrival);
        btnSeeSchedule = findViewById(R.id.btn_seeSchedue);

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
                Route route = (Route)adapterView.getAdapter().getItem(position);

                String format = "position is %d and value is %s";

                Toast.makeText(MainActivity.this, String.format(format, position, route), Toast.LENGTH_LONG).show();
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
                startActivity(intent);
            }
        });
    }

    private String[] itemListDep = {"D1", "D2", "D3", "D4", "D5"};

    DialogInterface.OnClickListener listListenerDep = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            btnDeparture.setText(itemListDep[i]);

            Toast.makeText(MainActivity.this,
                    "You Selected " + itemListDep[i],
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void displayDialogListDep() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Station of Departure")
                .setIcon(R.mipmap.ic_launcher_round)
                .setItems(itemListDep, listListenerDep)
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private String[] itemListArr = {"A1", "A2", "A3", "A4", "A5"};

    DialogInterface.OnClickListener listListenerArr = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            btnArrival.setText(itemListArr[i]);

            Toast.makeText(MainActivity.this,
                    "You Selected " + itemListArr[i],
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void displayDialogListArr() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Station of Arrival")
                .setIcon(R.mipmap.ic_launcher_round)
                .setItems(itemListArr, listListenerArr)
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
