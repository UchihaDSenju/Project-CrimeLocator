package com.example.crimelocator;

import android.app.ProgressDialog;

import com.google.firebase.auth.FirebaseAuth;

public class MyFirebase {


    FirebaseAuth auth;
    void register(String username, String password, ProgressDialog progressDialog){
        auth=FirebaseAuth.getInstance(); //Instantiate the firebaseAuth


    }
}
