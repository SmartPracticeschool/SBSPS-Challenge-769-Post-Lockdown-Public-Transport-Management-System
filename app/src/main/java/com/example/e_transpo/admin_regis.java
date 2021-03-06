package com.example.e_transpo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class admin_regis extends AppCompatActivity implements View.OnClickListener {

    private Button signup_btn;
    private TextView login_btn;
    private EditText email ,password,name;
    private ProgressBar loading;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fbase;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_regis);
        login_btn = (Button) findViewById(R.id.login_btn);
        signup_btn = (Button) findViewById(R.id.reg_sinup_btn);
        email = (EditText)findViewById(R.id.editTextTextEmail_reg);
        name = (EditText)findViewById(R.id.reg_name);
        password = (EditText)findViewById(R.id.editTextPassword_reg);
        loading = (ProgressBar)findViewById(R.id.loader);
        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        fbase = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                finish();
                Intent i = new Intent(admin_regis.this, Administrationphase.class);
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
        final String name_input = name.getText().toString();
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
        if (name_input.isEmpty()){
            email.setError("Name is Requirred");
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
                    FirebaseUser user = mAuth.getCurrentUser();
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Admin Registed Succesfully!",Toast.LENGTH_SHORT).show();
                    userid = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fbase.collection("Admins").document(userid);
                    Map<String,Object> usermap = new HashMap<>();
                    usermap.put("firstname",name_input);
                    documentReference.set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"Admin Registed Succesfully in datasbase!!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                    Intent i = new Intent(admin_regis.this, administrationphasedashboard.class);
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