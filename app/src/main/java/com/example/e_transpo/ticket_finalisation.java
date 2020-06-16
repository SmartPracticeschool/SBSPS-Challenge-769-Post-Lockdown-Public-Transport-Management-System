package com.example.e_transpo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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

    private void ticket_generate() {
        Calendar calendar = Calendar.getInstance();
        String _transpo_id = getIntent().getStringExtra("passed_transpoid");
        String _mode_id = getIntent().getStringExtra("passed_modeid");
        String _user_id = getIntent().getStringExtra("passed_userid");
        Integer _no_ticket = Integer.parseInt(getIntent().getStringExtra("passed_ticket"));
        String _to = getIntent().getStringExtra("passed_to");
        String _from = getIntent().getStringExtra("passed_from");
        String _date = getIntent().getStringExtra("passed_date");
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
                usermap.put(USER_id,mainticketid);
                documentReference.set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Ticket saved successfully",Toast.LENGTH_SHORT).show();
                    }
                });


                break;
        }

    }
}