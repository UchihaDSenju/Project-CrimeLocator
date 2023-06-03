package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDesc extends AppCompatActivity {

    ImageView coverImage;
    TextView newsTitle, newsDesc, newsDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_desc);

        newsTitle =findViewById(R.id.newsDescTitle);
        newsDesc = findViewById(R.id.newsDesc);
        newsDate = findViewById(R.id.newsDescDate);
        coverImage =findViewById(R.id.imageDesc);

        Intent intent=getIntent();
        String name=intent.getStringExtra("TITLE");
        String desc = intent.getStringExtra("DESC");
        String date = intent.getStringExtra("DATE");
        byte[] coverImg = intent.getByteArrayExtra("COVER");

        newsTitle.setText(name);
        newsDate.setText(date);
        newsDesc.setText(desc);
//        coverImage.setImageBitmap(coverImg);






    }
}