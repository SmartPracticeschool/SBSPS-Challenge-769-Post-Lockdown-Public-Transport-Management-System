package com.example.e_transpo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ticket_finalisation extends AppCompatActivity {
    Button pay_button;
    TextView transpo_name;
    ImageView barcode_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_finalisation);
        pay_button = (Button)findViewById(R.id.pay_btn);
        transpo_name = (TextView) findViewById(R.id.transpo_name);
        barcode_view = (ImageView)findViewById(R.id.bcode_img) ;
        barcode_generate();

    }

    private void barcode_generate() {
        String _transpo_id = getIntent().getStringExtra("passed_transpoid");
        String _mode_id = getIntent().getStringExtra("passed_modeid");
        String _user_id = getIntent().getStringExtra("passed_userid");
        String _no_ticket = getIntent().getStringExtra("passed_ticket");
        String _to = getIntent().getStringExtra("passed_to");
        String _from = getIntent().getStringExtra("passed_from");
        String _date = getIntent().getStringExtra("passed_date");
        String text = "TRANSPPORT ID -- "+_transpo_id+" "+"MODE ID -- "+_mode_id+"USER ID -- "+ _user_id+" FROM -- "+_from +" TO -- "+_to+" DATE -- "+_date+" NO OF TICKETS -- "+_no_ticket;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,400,400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcode_view.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}