package com.example.crimelocator;

import static androidx.constraintlayout.widget.StateSet.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NewsFeed extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        ArrayList<NewsData> data = new ArrayList<>();
        NewsAdapter adapter= new NewsAdapter(data,NewsFeed.this);

        getSupportActionBar().hide();
        RecyclerView newsFeedRV=findViewById(R.id.newsFeedRV);
        newsFeedRV.setHasFixedSize(true);
        newsFeedRV.setLayoutManager(new LinearLayoutManager(this));

        db.collection("News")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("", "onSuccess: getting Documents");
                        List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot : snapshots){
                            Map<String, Object> newsFeed = snapshot.getData();
                            String day = getDate((Timestamp) newsFeed.get("date_created"));
                            Log.d(TAG, "onSuccess: " + day);
                            data.add(new NewsData(newsFeed.get("title").toString(),day,R.drawable.bg1));
//                            Log.d(TAG, "onSuccess: " + data);
                        }
                        newsFeedRV.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("", "onFailure: ",e);
                    }
                });

    }

    public String getDate(Timestamp t){
        String day;
        long milli = t.getSeconds()*1000;
        Date date = new Date(milli);
        DateFormat f = new SimpleDateFormat("dd/MM");
        return f.format(date);
    }
}

//TODO
/*
* Add a progressBar**
* Page Reload
* */