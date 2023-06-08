package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class NewsDesc extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button helpBtn, adminHelpBtn;
    ImageView coverImage;
    TextView newsTitle, newsDesc, newsDate;
    RecyclerView galleryView;
    ProgressBar galleryProgBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_desc);
        getSupportActionBar().hide();


        Intent dataIntent=getIntent();
        String name=dataIntent.getStringExtra("TITLE");
        String desc = dataIntent.getStringExtra("DESC");
        String date = dataIntent.getStringExtra("DATE");
        String id = dataIntent.getStringExtra("ID");
        String email = dataIntent.getStringExtra("EMAIL");
        boolean isAdmin = dataIntent.getBooleanExtra("ADMIN", false);


        newsTitle =findViewById(R.id.newsDescTitle);
        newsDesc = findViewById(R.id.newsDesc);
        newsDate = findViewById(R.id.newsDescDate);
        coverImage =findViewById(R.id.imageDesc);
        helpBtn =findViewById(R.id.helpButton);
        adminHelpBtn =findViewById(R.id.adminHelpButton);
        galleryProgBar = findViewById(R.id.galleryProgBar);

        galleryView=findViewById(R.id.galleryView);
        galleryView.setHasFixedSize(true);
        galleryView.setLayoutManager(new LinearLayoutManager(this));

        if(isAdmin){
            helpBtn.setVisibility(View.GONE);
            adminHelpBtn.setVisibility(View.VISIBLE);
        }

        ArrayList<GalleryData> gallery = new ArrayList<>();

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NewsDesc.this,Doc_Upload.class);
                intent
                        .putExtra("ID", id)
                        .putExtra("EMAIL", email);
                startActivity(intent);


            }
        });

        adminHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsDesc.this, HelpedUserList.class);
                intent.putExtra("ID", id);
                Toast.makeText(NewsDesc.this, "Fetched id"+id, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });


        Log.d(TAG, "onCreate: "+ id + " " + email);
        setCoverImage("News/"+id+"/cover.jpg", coverImage);
        setGallery(id, gallery);


        newsTitle.setText(name);
        newsDate.setText(date);
        newsDesc.setText(desc);
    }

    public void setCoverImage(String path, ImageView img){
        final Bitmap[] bitmap = new Bitmap[1];
        ref = storage.getReference(path);
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
    }

    public void setGallery(String id, ArrayList<GalleryData> gallery){

        db.collection("News/"+id+"/gallery")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot snapshot:queryDocumentSnapshots.getDocuments()){
                            Map<String, Object> imageData = snapshot.getData();
                            String imageName = imageData.get("name").toString();
                            String desc = imageData.get("desc").toString();
                            Log.d(TAG, "onSuccess: "+imageName);
                            setGalleryImage(id, imageName, gallery, desc);

                        }
                    }
                });
    }
    public void setGalleryImage(String id, String image, ArrayList<GalleryData> gallery, String desc){

        galleryProgBar.setVisibility(View.VISIBLE);
        ref = storage.getReference("News/"+id+"/gallery/"+image+".jpg");
        final Bitmap[] galleryPhoto = new Bitmap[1];
        try {
            File localFile = File.createTempFile("temp",".jpg");

            ref.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.d(TAG, "onSuccess: Fetched Image "+image);
                            galleryPhoto[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            gallery.add(new GalleryData(galleryPhoto[0], desc));
                            Log.d(TAG, "setGalleryImage: Setting up image "+image);
                            galleryAdapter adapter = new galleryAdapter(gallery, NewsDesc.this);
                            galleryView.setAdapter(adapter);
                            galleryProgBar.setVisibility(View.GONE);
                            Log.d(TAG, "onSuccess: Image set up");
                        }
                    });
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}