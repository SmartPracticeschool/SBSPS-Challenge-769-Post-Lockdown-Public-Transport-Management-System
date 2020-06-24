package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class administrationphasedashboard extends AppCompatActivity {
    TextView ticket_verf,health_feedbac,updatio;

    private Button signout,profile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrationphasedashboard);
        ticket_verf = (TextView)findViewById(R.id.ticket_verf);
        signout =(Button)findViewById(R.id.das_signout);
        profile_btn = (Button)findViewById(R.id.das_userProfile);
        health_feedbac = (TextView)findViewById(R.id.healthplus_fedback);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent m = new Intent(administrationphasedashboard.this, Administrationphase.class);
                startActivity(m);
            }
        });
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent m = new Intent(administrationphasedashboard.this, admin_profile.class);
                startActivity(m);
            }
        });
        ticket_verf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(administrationphasedashboard.this,verfi_ticket.class);
                startActivity(i);
            }
        });
        health_feedbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(administrationphasedashboard.this,feedback_Healthplus.class);
                startActivity(i);
            }
        });
    }
}