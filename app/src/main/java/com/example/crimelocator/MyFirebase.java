package com.example.crimelocator;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MyFirebase {


    FirebaseAuth auth;

    void register(String username, String password, ProgressDialog progressDialog){

        progressDialog.setMessage("Please wait while we register you");
        progressDialog.setTitle("Registering");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth=FirebaseAuth.getInstance(); //Instantiate the firebaseAuth

        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                }
                else{
                    progressDialog.setMessage("Error " + task.getException());
                    progressDialog.setCanceledOnTouchOutside(true);
                }
            }
        });


    }
}
