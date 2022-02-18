package com.example.notesapp;

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

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText logEmail,logPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.login_register);
        register.setOnClickListener(this);

        signIn = findViewById(R.id.login_btn);
        signIn.setOnClickListener(this);

        logEmail = findViewById(R.id.login_email);
        logPassword = findViewById(R.id.login_password);

        progressBar = findViewById(R.id.log_prog);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.login_btn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = logEmail.getText().toString().trim();
        String password = logPassword.getText().toString().trim();
        if(email.isEmpty()){
            logEmail.setError("Email is required!");
            logEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            logEmail.setError("Please Enter A Valid Email!");
            logEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            logPassword.setError("Password is required!");
            logPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            logPassword.setError("Minimum length of Password is 6 characters!");
            logPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to NotesApp
                    startActivity(new Intent(Login.this,MainActivity.class));
                    Toast.makeText(Login.this,"Welcome To NotesApp!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Login.this,"Failed to log in!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}