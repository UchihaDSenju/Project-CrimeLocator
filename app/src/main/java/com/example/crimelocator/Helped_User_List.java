package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Helped_User_List extends AppCompatActivity {

    RecyclerView helpedUserView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helped_user_list);
        getSupportActionBar().hide();

        helpedUserView=findViewById(R.id.helpedUserView);
        helpedUserView.setHasFixedSize(true);
        helpedUserView.setLayoutManager(new LinearLayoutManager(this));

        UserData[] userData =new UserData[]{
                new UserData("Besanto@gmail.com"),
                new UserData("Besanto@gmail.com"),
                new UserData("Besanto@gmail.com"),
                new UserData("Besanto@gmail.com"),
                new UserData("Besanto@gmail.com"),
                new UserData("Besanto@gmail.com"),

        };

        UserAdapter userAdapter =new UserAdapter(userData,Helped_User_List.this);
        helpedUserView.setAdapter(userAdapter);
    }
}