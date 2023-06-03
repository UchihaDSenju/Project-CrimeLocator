package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class NewsDesc extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;

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
        String id = intent.getStringExtra("ID");

        Log.d(TAG, "onCreate: "+ id);
        Bitmap[] cover = getImage("News/"+id+"/cover.jpg", coverImage);

        newsTitle.setText(name);
        newsDate.setText(date);
        newsDesc.setText(desc);
    }

    public Bitmap[] getImage(String path, ImageView img){
        final Bitmap[] bitmap = new Bitmap[1];
        StorageReference ref = storage.getReference(path);
        try {
            File localFile = File.createTempFile("temp",".jpg");
            ref.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Log.d(TAG, "onSuccess: Image fetched");
                            img.setImageBitmap(bitmap[0]);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Error While fetching" + e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}