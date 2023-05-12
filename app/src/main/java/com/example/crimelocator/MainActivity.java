package com.example.crimelocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView signUpBtn, forgotPasswordBtn;
    Button signInBtn, adminLogin;
    TextInputEditText email, password;
    ProgressBar progressBar;

    FirebaseAuth auth;

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if(currentUser != null){
//            Toast.makeText(MainActivity.this,currentUser.toString()+" Signed in",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this,NewsFeed.class));
//            finish();}
//        else
//        {
//            Toast.makeText(MainActivity.this,"No Current user",Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);
       // adminLogin = findViewById(R.id.adminLogin);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
//        progressBar = findViewById(R.id.progBar);
         auth=FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpAct.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(MainActivity.this.email.getText());
                password = String.valueOf(MainActivity.this.password.getText());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Enter Email and password", Toast.LENGTH_SHORT).show();
                    return;
               }
                else{
                    firebaseSignin(email, password);


                }
            }
        });

//        adminLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  startActivity(new Intent(MainActivity.this, adminlogin.class));
//            }
//        });//ADMIN LOGIN
    }
    public void firebaseSignin(String email, String password){


        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(MainActivity.this,user.toString()+" Signed in Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, NewsFeed.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}