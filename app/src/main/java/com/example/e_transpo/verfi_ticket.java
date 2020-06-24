package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class verfi_ticket extends AppCompatActivity implements View.OnClickListener {
    public Button scanbtn;
    public static TextView scan_transpoid,scan_userid,scan_ticketid,scan_modeid,scan_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfi_ticket);
        scanbtn = (Button)findViewById(R.id.scancode_btn);
        scan_result = (TextView)findViewById(R.id.scan_result);
        scan_transpoid = (TextView)findViewById(R.id.scan_transpoid);
        scan_userid = (TextView)findViewById(R.id.scan_userid);
        scan_ticketid = (TextView)findViewById(R.id.scan_ticket_id);
        scan_modeid = (TextView)findViewById(R.id.scan_modeid);
        scanbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(verfi_ticket.this,main_QR_Scanner.class);
        startActivity(i);
    }
}