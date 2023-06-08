package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        UserAdapter adapter =new UserAdapter(userList, HelpedUserList.this);

        db.collection("News/"+id+"/userHelps")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                            ArrayList<String> users = (ArrayList<String>) snapshot.getData().get("users");
                            for(String user : users){
                                userHelpListProgBar.setVisibility(View.GONE);
                                userList.add(new UserData(user));
                            }
                            helpedUserView.setAdapter(adapter);
                        }
                    }
                });

    }
}