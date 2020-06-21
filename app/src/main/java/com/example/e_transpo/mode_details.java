package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class mode_details extends AppCompatActivity {

    TextView head_details,modeid_details,fare_details,stops_details,no_of_stops_details;
    Spinner modeid_spinner;
    String taken_from,taken_to,taken_mode;
    String main_modeid;
    Button bookticket;
    String fare,no_of_stopsss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_details);
        head_details = (TextView)findViewById(R.id.details_heading);
        modeid_details = (TextView)findViewById(R.id.main_details_modeid);
        fare_details = (TextView)findViewById(R.id.main_details_fare);
        stops_details = (TextView)findViewById(R.id.main_details_Startfrom);
        no_of_stops_details = (TextView)findViewById(R.id.main_details_stops);
        modeid_spinner = (Spinner)findViewById(R.id.detaisl_spinner);
        taken_from = getIntent().getStringExtra("passed_from");
        taken_to = getIntent().getStringExtra("passed_to");
        taken_mode = getIntent().getStringExtra("passed_modeid");
        bookticket = (Button)findViewById(R.id.bookticket__btn);

        final List<String> searchablespinner_list_train = new ArrayList<String>();
        searchablespinner_list_train.add("Select Train/Bus/Metro Number");
        searchablespinner_list_train.add("22439-Vande Bharat Express");
        searchablespinner_list_train.add("12049-Gatimaan Express");
        searchablespinner_list_train.add("12002-New Delhi Bhopal Shatabdi Express ");
        searchablespinner_list_train.add("12951-Mumbai New Delhi Rajdhani Express");

        final List<String> searchablespinner_list_bus = new ArrayList<String>();
        searchablespinner_list_bus.add("Bus ID 10001");
        searchablespinner_list_bus.add("Bus ID 10002");
        searchablespinner_list_bus.add("Bus ID 10003");
        searchablespinner_list_bus.add("Bus ID 10004");
        searchablespinner_list_bus.add("Bus ID 10005");

        final List<String> searchablespinner_list_Metro = new ArrayList<String>();
        searchablespinner_list_Metro.add("Metro ID 50001");
        searchablespinner_list_Metro.add("Metro ID 50002");
        searchablespinner_list_Metro.add("Metro ID 50003");
        searchablespinner_list_Metro.add("Metro ID 50004");
        searchablespinner_list_Metro.add("Metro ID 50005");


        if(taken_mode.contentEquals("Bus")){

            modeid_spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_bus));
            fare = "35Rs";
            no_of_stopsss = "10";
        }
        if(taken_mode.contentEquals("Train")){
            modeid_spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_train));
            fare = "150Rs";
            no_of_stopsss = "20";
        }
        if(taken_mode.contentEquals("Metro")){
            modeid_spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_Metro));
            fare = "100Rs";
            no_of_stopsss = "8";
        }

        modeid_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String modeid = parent.getItemAtPosition(position).toString();
                main_modeid = modeid ;
                putvalues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mode_details.this,ticket_booking.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void putvalues() {
        head_details.setText("Available "+taken_mode+" from "+ taken_from+ " to "+taken_to + " are:-");
        modeid_details.setText("Mode Id : "+main_modeid);
        fare_details.setText("Fare (Per Head) : "+fare);
        stops_details.setText("Starting Station A , Ending Station B ");
        no_of_stops_details.setText("No. Of Stops : "+no_of_stopsss);
    }
}