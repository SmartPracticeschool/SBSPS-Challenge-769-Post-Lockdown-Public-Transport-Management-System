package com.example.e_transpo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class login extends AppCompatActivity implements View.OnClickListener {
    private TextView reg_here;
    private Button login_btn;
    private EditText email,password;
    private ProgressBar loading;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reg_here = (TextView)findViewById(R.id.reg_btn);
        login_btn = (Button) findViewById(R.id.already_reg_login_btn);
        email = (EditText) findViewById(R.id.editTextlogin_email);
        password = (EditText) findViewById(R.id.editTextTextlogin_password);
        loading = (ProgressBar)findViewById(R.id.loader);
        reg_here.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reg_btn:
                finish();
                Intent i = new Intent(login.this, user_activity.class);
                startActivity(i);
                break;
            case R.id.already_reg_login_btn:
                userlogin();
                break;
       }

    }

    private void userlogin() {
        String email_input = email.getText().toString().trim();
        String password_input = password.getText().toString().trim();
        if (email_input.isEmpty()){
            email.setError("Email iS Requirred");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_input).matches()){
            email.setError("Please set a valid Email");
            email.requestFocus();
            return;
        }
        if (password_input.length()<6){
            password.setError("Minimun length of password is 6 letter");
            password.requestFocus();
            return;
        }
        if (password_input.isEmpty()){
            password.setError("Password Is Requirred");
            password.requestFocus();
            return;
        }

        loading.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loading.setVisibility(View.GONE);
                    finish();
                    Intent i = new Intent(login.this, Dashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            finish();
            Intent i = new Intent(this,Dashboard.class);
            startActivity(i);
        }
    }
}