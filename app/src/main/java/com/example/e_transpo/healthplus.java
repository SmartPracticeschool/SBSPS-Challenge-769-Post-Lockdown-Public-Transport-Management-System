package com.example.e_transpo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class healthplus extends AppCompatActivity implements View.OnClickListener {
 private Spinner healthplus_spinner;
 String selected_health_issue;
 Button send_btn;
 FirebaseFirestore fbase;
    private ProgressBar loading;
 FirebaseAuth mauth;
 TextView name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthplus);
        healthplus_spinner = (Spinner) findViewById(R.id.spinner_health);
        name = (TextView)findViewById(R.id.editTextTextPersonName5);
        id = (TextView)findViewById(R.id.editTextTextPersonName6);
        loading = (ProgressBar)findViewById(R.id.loader);
        send_btn=(Button)findViewById(R.id.button3);
        mauth = FirebaseAuth.getInstance();
        fbase = FirebaseFirestore.getInstance();
        final List<String> health_issues = new ArrayList<String>();
        health_issues.add("Choose problem observed in patient");
        health_issues.add("Body-Pain");
        health_issues.add("Dehydration");
        health_issues.add("Weakness");
        health_issues.add("Excess-Vomitting");
        health_issues.add("Fever");
        healthplus_spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, health_issues));
        healthplus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String issue = parent.getItemAtPosition(position).toString();
                selected_health_issue = issue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        send_btn.setOnClickListener(this);

    }

    private void sumbit_details() {

        String name_input = name.getText().toString();
        String id_input = id.getText().toString();
        String current_user = mauth.getCurrentUser().getUid();
        if (name_input.isEmpty()) {
            name.setError("Please input name");
            name.requestFocus();
            return;
        }
        if (id_input.isEmpty()) {
            id.setError("Field mendatory");
            id.requestFocus();
            return;
        }
        if(selected_health_issue.contentEquals("Choose problem observed in patient")){
            Toast.makeText(getApplicationContext(),"Please select health issue",Toast.LENGTH_SHORT).show();
        }
        loading.setVisibility(View.VISIBLE);
        DocumentReference documentReference = fbase.collection("Health Plus Messages").document();
        Map<String,Object> usermap = new HashMap<>();
        usermap.put("Name_of_Patient",name_input);
        usermap.put("User_ID_of_Patient",id_input);
        usermap.put("User_ID_of_user",current_user);
        usermap.put("Heath_Issue",selected_health_issue);

        documentReference.set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loading.setVisibility(View.GONE);
                Intent i = new Intent(healthplus.this,Dashboard.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"Your request has been sent successfully !",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        sumbit_details();
    }
}