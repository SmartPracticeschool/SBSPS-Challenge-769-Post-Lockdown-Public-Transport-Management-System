package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class splashscreen extends AppCompatActivity {
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = (ProgressBar)findViewById(R.id.loadersplash);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashscreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);

    }
}