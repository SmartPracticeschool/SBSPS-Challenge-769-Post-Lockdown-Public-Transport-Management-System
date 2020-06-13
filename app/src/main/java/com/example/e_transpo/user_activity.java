package com.example.e_transpo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.regex.Pattern;

public class user_activity extends AppCompatActivity implements View.OnClickListener {
    private Button signup_btn;
    private TextView login_btn;
    private EditText email ,password;
    private ProgressBar loading;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity);
        login_btn = (Button) findViewById(R.id.login_btn);
        signup_btn = (Button) findViewById(R.id.reg_sinup_btn);
        email = (EditText)findViewById(R.id.editTextTextEmail_reg);
        password = (EditText)findViewById(R.id.editTextPassword_reg);
        loading = (ProgressBar)findViewById(R.id.loader);
        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reg_btn:
                Intent i = new Intent(user_activity.this, login.class);
                startActivity(i);
                break;
            case R.id.reg_sinup_btn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
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

       mAuth.createUserWithEmailAndPassword(email_input,password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   loading.setVisibility(View.GONE);
                   Toast.makeText(getApplicationContext(),"User Registed Succesfully!",Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(user_activity.this, Dashboard.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(i);
               }
               else{
                   if(task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                   }
               }

           }
       });


    }

}