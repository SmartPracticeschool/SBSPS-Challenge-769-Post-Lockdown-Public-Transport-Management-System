package com.example.e_transpo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class user_activity extends AppCompatActivity {
    private Button login_btn,signin_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity);
        login_btn = (Button) findViewById(R.id.button);
        signin_btn = (Button) findViewById(R.id.button2);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(user_activity.this, login.class);
                        startActivity(i);
                        finish();
                    }
                }, 500);
            }
        });
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(user_activity.this, healthplus.class);
                        startActivity(i);

                    }
                }, 500);
            }
        });


    }
}