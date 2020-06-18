package com.example.e_transpo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;

import java.util.function.Consumer;

public class Dashboard extends AppCompatActivity {
    private CardView search, health, book_ticket;
    private FirebaseAuth mAuth;
    private Button signout,profile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        search = (CardView) findViewById(R.id.search);
        health = (CardView) findViewById(R.id.health);
        book_ticket = (CardView) findViewById(R.id.book_tickets);
        signout =(Button)findViewById(R.id.das_signout);
        profile_btn = (Button)findViewById(R.id.das_userProfile);
        mAuth = FirebaseAuth.getInstance();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent m = new Intent(Dashboard.this, login.class);
                startActivity(m);
            }
        });
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent m = new Intent(Dashboard.this, User_Profile.class);
                startActivity(m);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Dashboard.this, search_option.class);
                        startActivity(i);

                    }
                }, 300);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Dashboard.this, healthplus.class);
                        startActivity(i);

                    }
                }, 300);
            }
        });
        book_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Dashboard.this, ticket_booking.class);
                        startActivity(i);

                    }
                }, 300);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            Intent i = new Intent(this, login.class);
            startActivity(i);
        }
    }




}
