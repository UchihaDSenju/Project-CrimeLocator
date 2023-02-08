package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextView signUpBtn, forgotPasswordBtn;
    Button signInBtn, adminLogin;
    TextInputEditText email, password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);
        adminLogin = findViewById(R.id.adminLogin);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progBar);
        //  mAuth=FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpAct.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(MainActivity.this.email.getText());
                password = String.valueOf(MainActivity.this.password.getText());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Enter Email and password", Toast.LENGTH_SHORT).show();
                    return;
                }}});
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(MainActivity.this, adminlogin.class));
            }
        });//ADMIN LOGIN
    }}