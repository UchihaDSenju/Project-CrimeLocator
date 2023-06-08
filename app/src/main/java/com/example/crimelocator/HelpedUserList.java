package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HelpedUserList extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    RecyclerView helpedUserView;
    ProgressBar userHelpListProgBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helped_user_list);
        getSupportActionBar().hide();



        userHelpListProgBar = findViewById(R.id.userHelpListProgBar);
        userHelpListProgBar.setVisibility(View.VISIBLE);

        String id = getIntent().getStringExtra("ID");

        helpedUserView=findViewById(R.id.helpedUserView);
        helpedUserView.setHasFixedSize(true);
        helpedUserView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<UserData> userList = new ArrayList<>();
        UserAdapter adapter =new UserAdapter(userList, HelpedUserList.this, id);

        db.collection("News/"+id+"/userHelps")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess: Inside");
                        List snap = queryDocumentSnapshots.getDocuments();
                        if(snap.isEmpty()){
                            Toast.makeText(HelpedUserList.this, "No Users Have helped yet", Toast.LENGTH_SHORT).show();
                            userHelpListProgBar.setVisibility(View.GONE);
                        }
                        
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                            ArrayList<String> users = (ArrayList<String>) snapshot.getData().get("users");
                            Log.d(TAG, "onSuccess: inside 2");
                            for(String user : users){
                                userList.add(new UserData(user));
                            }
                            helpedUserView.setAdapter(adapter);
                            userHelpListProgBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}