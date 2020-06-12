package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Dashboard extends AppCompatActivity {
    private CardView search,health,book_ticket,my_tickets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
         search = (CardView)findViewById(R.id.search);
         health = (CardView)findViewById(R.id.health);
        my_tickets = (CardView)findViewById(R.id.my_tickets);
        book_ticket =  (CardView)findViewById(R.id.book_tickets);
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
        my_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Dashboard.this, my_tickets.class);
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
}