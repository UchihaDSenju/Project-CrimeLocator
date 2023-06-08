package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class HelpedUserListGallery extends AppCompatActivity {

    RecyclerView helpedUserGalleyRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helped_user_list_gallery);
        getSupportActionBar().hide();

        helpedUserGalleyRV = findViewById(R.id.helpedUserGalleyRV);
        helpedUserGalleyRV.setHasFixedSize(true);
        helpedUserGalleyRV.setLayoutManager(new LinearLayoutManager(this));
    }
}