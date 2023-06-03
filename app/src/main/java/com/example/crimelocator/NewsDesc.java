package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDesc extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descript_intent);

        Intent intent=getIntent();
        String name=intent.getStringExtra("NAME");
        textView=findViewById(R.id.textDesc);
        imageView=findViewById(R.id.imageDesc);
        textView.setText(name);






    }
}