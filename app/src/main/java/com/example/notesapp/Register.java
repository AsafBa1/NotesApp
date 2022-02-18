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

import com.example.notesapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editname, editpass, editEmail;
    private Button registerUser;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();


        registerUser = findViewById(R.id.register_user);
        registerUser.setOnClickListener(this);

        editname = findViewById(R.id.sign_name);
        editEmail = findViewById(R.id.sign_mail);
        editpass = findViewById(R.id.sign_password);

        progressBar = findViewById(R.id.sign_prog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_user:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String name = editname.getText().toString().trim();
        String password = editpass.getText().toString().trim();

        if (name.isEmpty()) {
            editname.setError("Full Name is Required");
            editname.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editEmail.setError("Email is Required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please Insert a Valid Email");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editpass.setError("Password is Required");
            editpass.requestFocus();
            return;
        }
        if(password.length() < 6){
            editpass.setError("Password Needs To Be Longer then 6 Characters ");
            editpass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name,email);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Register.this, "User Has Been Registered Successfully!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                //redirect to login Layout
                                startActivity(new Intent(Register.this,Login.class));
                            }else{
                                Toast.makeText(Register.this,"Failed to Register Try again!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this,"Failed to Register Try again!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}