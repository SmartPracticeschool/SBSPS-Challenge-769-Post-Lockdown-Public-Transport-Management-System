package com.example.e_transpo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ticket_finalisation extends AppCompatActivity implements View.OnClickListener {
    Button pay_button;
    TextView transpo_name,date_textview,userid_textview,cost_textview;
    ImageView barcode_view;
    private ProgressBar loading;
    Integer Cost_value;
    Integer cost_metro=35,cost_bus=100,cost_train=150;
    FirebaseAuth mauth;
    FirebaseFirestore fbase;
    String USER_id,mainticketid;
    Bitmap btm;
    String _transpo_id,_mode_id,_to,_from,_date,_no_ticket_string;
    Integer _ticket_number;
    Integer ticket_avail_bus = 30;
    Integer ticket_avail_metro = 1020;
    Integer ticket_avail_train = 1224;
    Long database_ticket ;
    Integer update_value;
    String update_value_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_finalisation);
        pay_button = (Button)findViewById(R.id.pay_btn);
        transpo_name = (TextView) findViewById(R.id.transpo_name);
        date_textview = (TextView) findViewById(R.id.date_finalticket);
        userid_textview = (TextView) findViewById(R.id.finalticket_userid);
        cost_textview = (TextView) findViewById(R.id.cost);
        barcode_view = (ImageView)findViewById(R.id.bcode_img) ;
        loading = (ProgressBar)findViewById(R.id.loader);
        pay_button.setOnClickListener(this);
        mauth = FirebaseAuth.getInstance();
        fbase = FirebaseFirestore.getInstance();
        ticket_generate();
    }

    private void ticket_check() {
        DocumentReference documentReference = fbase.collection("ticket_counter").document(_transpo_id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("nnn", "DocumentSnapshot data: " + document.getData());
                        Long my_data = (Long) document.getLong("tickets");
                        if(_mode_id.contentEquals("Metro")) {
                            assert my_data != null;
                            if (ticket_avail_metro-my_data<=0){
                        Toast.makeText(getApplicationContext(),"Sorry , Ticket are not available !",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(ticket_finalisation.this, ticket_booking.class);
                                startActivity(i);
                                finish();
                            }
                        }, 2000);
                        return;
                    }
                }
                if(_mode_id.contentEquals("Bus")) {
                    assert my_data != null;
                    if (ticket_avail_bus-my_data<=0){
                        Toast.makeText(getApplicationContext(),"Sorry , Ticket are not available !",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(ticket_finalisation.this, ticket_booking.class);
                                startActivity(i);
                                finish();
                            }
                        }, 2000);
                        return;
                    }
                }
                if(_mode_id.contentEquals("Train")) {
                    assert my_data != null;
                    if (ticket_avail_train-my_data<=0){
                        Toast.makeText(getApplicationContext(),"Sorry , Ticket are not available !",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(ticket_finalisation.this, ticket_booking.class);
                                startActivity(i);
                                finish();
                            }
                        }, 2000);
                            return;
                    }
                }

                    } else {
                        Log.d("nnn", "No such document");
                    }
                } else {
                    Log.d("nnn", "get failed with ", task.getException());
                }
            }
        });
 }

    private void ticket_generate() {
        Calendar calendar = Calendar.getInstance();
        _transpo_id = getIntent().getStringExtra("passed_transpoid");
        _mode_id = getIntent().getStringExtra("passed_modeid");
        String _user_id = getIntent().getStringExtra("passed_userid");
        Integer _no_ticket = Integer.parseInt(getIntent().getStringExtra("passed_ticket"));
         _to = getIntent().getStringExtra("passed_to");
        _from = getIntent().getStringExtra("passed_from");
        _date = getIntent().getStringExtra("passed_date");
        _ticket_number = Integer.parseInt(String.valueOf(_no_ticket));
        ticket_check();
        transpo_name.setText("Passenger Ticket For" + _transpo_id);
        date_textview.setText("Date :   "+_date);
        userid_textview.setText("User Id :  "+_user_id);
        mainticketid = _mode_id+_user_id+calendar.getTimeInMillis();
        if(_mode_id.contentEquals("Metro")) {
            Cost_value = _no_ticket * cost_metro;
            cost_textview.setText("Total cost = "+Cost_value+"Rs,"+cost_metro+"Rs. per head");
        }
        if(_mode_id.contentEquals("Bus")) {
            Cost_value = _no_ticket  * cost_bus;
            cost_textview.setText("Total cost = "+Cost_value+"Rs,"+cost_bus+"Rs. per head");
        }
        if(_mode_id.contentEquals("Train")) {
            Cost_value =  _no_ticket  * cost_train;
            cost_textview.setText("Total cost = "+Cost_value+"Rs,"+cost_train+"Rs. per head");
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(mainticketid, BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            btm = bitmap;
            barcode_view.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_btn:
                loading.setVisibility(View.VISIBLE);

                FirebaseUser user = mauth.getCurrentUser();
                USER_id = mauth.getCurrentUser().getUid();

                DocumentReference documentReference = fbase.collection("Govt_Ticket_detais").document();
                Map<String,Object> usermap = new HashMap<>();
                usermap.put("Ticket-Id",mainticketid);
                usermap.put("User-Id",USER_id);
                usermap.put("Mode-Id",_mode_id);
                usermap.put("Transpo-Id",_transpo_id);
                documentReference.set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Ticket saved successfully",Toast.LENGTH_SHORT).show();
                    }
                });
                DocumentReference documentREF = fbase.collection("users").document(USER_id).collection("Tickets").document(mainticketid);
                Map<String,Object> ticketmap = new HashMap<>();
                ticketmap.put("Mode",_mode_id);
                ticketmap.put("Transport ID",_transpo_id);
                ticketmap.put("From",_from);
                ticketmap.put("To",_to);
                ticketmap.put("Date",_date);
                documentREF.set(ticketmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createpdfdoc();
                        updatecounter();
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Ticket has been saved in your device , check now !",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(ticket_finalisation.this, Dashboard.class);
                                startActivity(i);
                                finish();
                            }
                        }, 2000);
                    }
                });
        }
    }

    private void updatecounter() {
        DocumentReference documentReferenceupdater = fbase.collection("ticket_counter").document(_transpo_id);
        documentReferenceupdater.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("uuu", "DocumentSnapshot data: " + document.getData());
                        Long my_data = (Long) document.getLong("tickets");
                        assert my_data != null;
                        Long z = my_data+_ticket_number;
                        Log.d("uuu", "Total tickets :::"+z+ document.getData());
                        DocumentReference documentReference = fbase.collection("ticket_counter").document(_transpo_id);
                        Map<String,Object> usermap = new HashMap<>();
                        usermap.put("tickets",z);
                        documentReference.set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Counter Updated Successfully !!",Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {
                        Log.d("uuu", "No such document");
                    }
                } else {
                    Log.d("uuu", "get failed with ", task.getException());
                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createpdfdoc() {

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);
        String filepath = Environment.getExternalStorageDirectory()+"/"+mainticketid+".pdf";
        PdfDocument mypdfDocument = new PdfDocument();
        Paint mypaint = new Paint();
        Paint paintHead = new Paint();
        paintHead.setTextAlign(Paint.Align.CENTER);
        paintHead.setTextSize(17);
        paintHead.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        paintHead.setUnderlineText(true);
        Bitmap scaledbitmap = btm.createScaledBitmap(btm,180,180,false);

        PdfDocument.PageInfo mypageinfo1 = new PdfDocument.PageInfo.Builder(320,410,1).create();
        PdfDocument.Page mypage = mypdfDocument.startPage(mypageinfo1);
        Canvas canvas = mypage.getCanvas();
        canvas.drawBitmap(scaledbitmap,40,50,paintHead);
        canvas.drawText("E-Ticket By E-Transpo App" , 105 , 35, paintHead);
        canvas.drawText("User Id : "+ USER_id , 10 , 260, mypaint);
        canvas.drawText("Mode Id : "+ _mode_id, 10 , 280, mypaint);
        canvas.drawText("Transpo Id : "+ _transpo_id , 10 , 300, mypaint);
        canvas.drawText("From : "+ _from , 10 , 320, mypaint);
        canvas.drawText("To : "+ _to , 10 , 340, mypaint);
        canvas.drawText("Date : "+ _date , 10 , 360, mypaint);
        mypdfDocument.finishPage(mypage);
        File file = new File(filepath);
        try {
            mypdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mypdfDocument.close();

    }
}