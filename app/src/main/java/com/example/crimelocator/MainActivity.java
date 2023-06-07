package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView signUpBtn, forgotPasswordBtn;
    Button signInBtn, adminLogin;
    TextInputEditText email, password;
    ProgressBar mainProgressBar;

    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(MainActivity.this,currentUser+" Signed in",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,NewsFeed.class));
            finish();}

    }

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
        mainProgressBar = findViewById(R.id.mainProgBar);
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
                mainProgressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(MainActivity.this.email.getText());
                password = String.valueOf(MainActivity.this.password.getText());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                   mainProgressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Enter Email and password", Toast.LENGTH_SHORT).show();
                    return;
               }
                else{
                    firebaseSignin(email,password);
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
                            moveToNewsFeed(email);

                        } else {
                            // If sign in fails, display a message to the user.
                            mainProgressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void moveToNewsFeed(String email){
        db.document("Users/"+email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username;
                        username = documentSnapshot.get("username").toString();
                        boolean isAdmin = (boolean)documentSnapshot.get("isAdmin");
                        Log.d(TAG, "onSuccess: Username Retrieved "+username);
                        mainProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Signed In successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, NewsFeed.class);
                        LoggedInUserDetails ud = new LoggedInUserDetails(username, email);
                        intent.putExtra("USERNAME", username);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("ADMIN", isAdmin);
                        startActivity(intent);
                        finish();
                    }
                });

    }
}

//TODO
/*
* Add Progress Bar on login loading*/