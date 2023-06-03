package com.example.crimelocator;

import static androidx.constraintlayout.widget.StateSet.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
    TextView textLogout;
    ProgressBar progressBar;
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        progressBar=findViewById(R.id.progBar);
        ArrayList<NewsData> data = new ArrayList<>();
        NewsAdapter adapter= new NewsAdapter(data,NewsFeed.this);

        getSupportActionBar().hide();
        RecyclerView newsFeedRV=findViewById(R.id.newsFeedRV);
        newsFeedRV.setHasFixedSize(true);
        newsFeedRV.setLayoutManager(new LinearLayoutManager(this));

        textLogout=findViewById(R.id.textLogout);
        textLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Logout");
                alert.setMessage("do you want to logout");

                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(NewsFeed.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(NewsFeed.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        dialog.cancel();

            }
        });
                AlertDialog alertDialog= alert.create();
                alertDialog.show();
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        db.collection("News")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("", "onSuccess: getting Documents");
                        List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot : snapshots){
                            Map<String, Object> newsFeed = snapshot.getData();
                            String day = getDate((Timestamp) newsFeed.get("date_created"));
                            Log.d(TAG, "onSuccess: " + day);
                            data.add(new NewsData(newsFeed.get("title").toString(),day,R.drawable.bg1,newsFeed.get("desc").toString()));
                            Log.d(TAG, "onSuccess: " + newsFeed.get("desc").toString());
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