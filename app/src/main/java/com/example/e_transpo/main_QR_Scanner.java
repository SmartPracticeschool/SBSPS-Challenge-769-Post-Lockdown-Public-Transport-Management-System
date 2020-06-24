package com.example.e_transpo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.graphics.Color.rgb;

public class main_QR_Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public String scanned_result_ticketid ;
    ZXingScannerView scannerView;
    FirebaseFirestore fbase;
    FirebaseAuth fauth;

    public static Boolean result;
    List<String> Ticket_details = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        fbase = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();


        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        setContentView(scannerView);

    }

    @Override
    public void handleResult(Result result) {

        scanned_result_ticketid = result.getText();
        Toast.makeText(main_QR_Scanner.this, scanned_result_ticketid, Toast.LENGTH_SHORT).show();
        verification();

        onBackPressed();

    }

    private void verification() {
        CollectionReference gov_ticket_coll = fbase.collection("Govt_Ticket_detais");
        Query ticketquery = gov_ticket_coll.whereEqualTo("Ticket-Id",scanned_result_ticketid);
        ticketquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document:task.getResult()){
                        Ticket_details.add(document.getString("Ticket-Id"));
                        Ticket_details.add(document.getString("User-Id"));
                        Ticket_details.add(document.getString("Mode-Id"));
                        Ticket_details.add(document.getString("Transpo-Id"));
                        verfi_ticket.scan_ticketid.setText("Ticket-Id: "+Ticket_details.get(0));
                        verfi_ticket.scan_userid.setText("User-Id: "+Ticket_details.get(1));
                        verfi_ticket.scan_modeid.setText("Mode: "+Ticket_details.get(2));
                        verfi_ticket.scan_transpoid.setText("Transpo-Id: "+Ticket_details.get(3));
                        verfi_ticket.scan_result.setTextColor(rgb(0,255,0));
                        verfi_ticket.scan_result.setText("USER VERIFIED SUCESSFULLY");
                        }
                }
                else {
                    Toast.makeText(main_QR_Scanner.this, "Error Occured !!!!!!", Toast.LENGTH_SHORT).show();
                    verfi_ticket.scan_result.setTextColor(rgb(255,0,0));
                    verfi_ticket.scan_result.setText("USER NOT EXIST !!");

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}