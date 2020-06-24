package com.example.e_transpo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class admin_profile extends AppCompatActivity {
    private TextView iduser,nameuser;
    private Button btn_dashboard;
    FirebaseFirestore fbase;
    FirebaseAuth fauth;
    private String UserId ;
    private String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);
        iduser = (TextView)findViewById(R.id.userid_healthissue);
        btn_dashboard = (Button) findViewById(R.id.profile_dashboard_btn);
        nameuser = (TextView)findViewById(R.id.username_title);
        fbase = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();
        UserId = fauth.getCurrentUser().getUid();
        iduser.setText(UserId);
        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(admin_profile.this,administrationphasedashboard.class);
                startActivity(i);
            }
        });
        DocumentReference documentReference = fbase.collection("Admins").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                UserName = (String)documentSnapshot.get("firstname");
                nameuser.setText(UserName);
            }
        });


    }
}