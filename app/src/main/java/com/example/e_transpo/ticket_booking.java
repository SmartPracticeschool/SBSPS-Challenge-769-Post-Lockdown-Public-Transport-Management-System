package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ticket_booking extends AppCompatActivity implements View.OnClickListener {
    EditText date, from, to, no_of_ticket;
    TextView tanspo_id;
    Spinner mode_of_transpo,searchablespinner;
    Button book_ticket;
    String spinner_seleection;
    String Transportid,modeid;
    String userid;
    private FirebaseAuth mAuth;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);
        date = (EditText) findViewById(R.id.date_pick);
        from = (EditText) findViewById(R.id.editText_from);
        to = (EditText) findViewById(R.id.editText_to);
        no_of_ticket = (EditText) findViewById(R.id.no_of_tickets);
        book_ticket = (Button) findViewById(R.id.booktickrt_btn);
        book_ticket.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        searchablespinner = (Spinner) findViewById(R.id.searchablespinner);
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


        mode_of_transpo = (Spinner) findViewById(R.id.mode_of_transpo);
        List<String> searchablespinnermode_list = new ArrayList<String>();
        searchablespinnermode_list.add("Select Mode Of Transport");
        searchablespinnermode_list.add("Bus");
        searchablespinnermode_list.add("Train");
        searchablespinnermode_list.add("Metro");
        mode_of_transpo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, searchablespinnermode_list));
        mode_of_transpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    String smode = parent.getItemAtPosition(position).toString();
                    modeid = smode;
                    searchablespinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_bus));
                }
                if (position == 2) {
                    String smode = parent.getItemAtPosition(position).toString();
                    modeid = smode;
                    searchablespinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_train));
                }
                if (position == 3) {
                    String smode = parent.getItemAtPosition(position).toString();
                    modeid = smode;
                    searchablespinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, searchablespinner_list_Metro));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        searchablespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {String smode = parent.getItemAtPosition(position).toString();
                String modeid = parent.getItemAtPosition(position).toString();
                Transportid = modeid ;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
        private void validation () {
            String date_string = date.getText().toString();
            String from_string = from.getText().toString();
            String to_string = to.getText().toString();
            String tickets_string = no_of_ticket.getText().toString();
            if (date_string.isEmpty()) {
                date.setError("Please input date");
                date.requestFocus();
                return;
            }
            if (from_string.isEmpty()) {
                from.setError("Field mendatory");
                from.requestFocus();
                return;
            }
            if (to_string.isEmpty()) {
                to.setError("Field mendatory");
                to.requestFocus();
                return;
            }
            if (tickets_string.isEmpty()) {
                no_of_ticket.setError("Field mendatory");
                no_of_ticket.requestFocus();
                return;
            }
            Intent i = new Intent(ticket_booking.this, ticket_finalisation.class);
            i.putExtra("passed_date", date_string);
            i.putExtra("passed_from", from_string);
            i.putExtra("passed_to", to_string);
            i.putExtra("passed_ticket", tickets_string);
            i.putExtra("passed_transpoid", Transportid);
            i.putExtra("passed_modeid", modeid);
            i.putExtra("passed_userid", userid);
            startActivity(i);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.booktickrt_btn:
                validation();
                break;
        }

    }
}
