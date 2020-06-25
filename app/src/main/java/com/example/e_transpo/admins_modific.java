package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class admins_modific extends AppCompatActivity {

    Spinner mode_of_transpo_admin,searchablespinner_admin,searchablespinner_what_admin;
    Button updation_btn;
    EditText mainupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_modific);
        mode_of_transpo_admin = (Spinner)findViewById(R.id.mode_of_transpo_admins);
        searchablespinner_admin = (Spinner)findViewById(R.id.searchablespinner_admin);
        searchablespinner_what_admin = (Spinner)findViewById(R.id.searchablespinner_what_admin);
        mainupdate= (EditText)findViewById(R.id.editText_mainupdte);
        updation_btn = (Button)findViewById(R.id.updataion_btn);


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

        final List<String> searchablespinner_whatupdate = new ArrayList<String>();
        searchablespinner_whatupdate.add("Select what to update");
        searchablespinner_whatupdate.add("Update No. of Stops");
        searchablespinner_whatupdate.add("Update Route Info");
        searchablespinner_whatupdate.add("Update Fare details");

        List<String> searchablespinnermode_list = new ArrayList<String>();
        searchablespinnermode_list.add("Select Mode Of Transport");
        searchablespinnermode_list.add("Bus");
        searchablespinnermode_list.add("Train");
        searchablespinnermode_list.add("Metro");

        mode_of_transpo_admin.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, searchablespinnermode_list));
        mode_of_transpo_admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    String smode = parent.getItemAtPosition(position).toString();
                    searchablespinner_admin.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_bus));
                }
                if (position == 2) {
                    String smode = parent.getItemAtPosition(position).toString();
                    searchablespinner_admin.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_train));
                }
                if (position == 3) {
                    String smode = parent.getItemAtPosition(position).toString();
                    searchablespinner_admin.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_Metro));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        searchablespinner_admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {String smode = parent.getItemAtPosition(position).toString();
                String modeid = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchablespinner_what_admin.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, searchablespinner_whatupdate));
        searchablespinner_what_admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    mainupdate.setHint("Type new no. of Stops  ");
                }
                if (position == 2) {
                    mainupdate.setHint("Upadte route info here ");
                }
                if (position == 3) {
                    mainupdate.setHint("Type new fare in Rs. ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        updation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        String input_mainupdate = mainupdate.getText().toString();
        if (input_mainupdate.isEmpty()){
            mainupdate.setError("Please input date");
            mainupdate.requestFocus();
            return;
        }
        Toast.makeText(getApplicationContext(),"Updated Successfully !!",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(admins_modific.this,administrationphasedashboard.class);
        finish();
        startActivity(i);
    }
}