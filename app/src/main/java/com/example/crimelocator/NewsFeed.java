package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class NewsFeed extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

      getSupportActionBar().hide();
        RecyclerView newsFeedRV=findViewById(R.id.newsFeedRV);
        newsFeedRV.setHasFixedSize(true);
        newsFeedRV.setLayoutManager(new LinearLayoutManager(this));

        NewsData[] newsData=new NewsData[]{
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),
                new NewsData("India won the match","12 hrs ago",R.drawable.bg1),

        };
        NewsAdapter adapter= new NewsAdapter(newsData,NewsFeed.this);
        newsFeedRV.setAdapter(adapter);
    }
}