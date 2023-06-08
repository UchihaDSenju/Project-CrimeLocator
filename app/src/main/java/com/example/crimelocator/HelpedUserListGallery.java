package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelpedUserListGallery extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;

    RecyclerView helpedUserGalleyRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helped_user_list_gallery);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        String id = intent.getStringExtra("ID");
        Log.d(TAG, "onCreate: " + email + " " + id);

        helpedUserGalleyRV = findViewById(R.id.helpedUserGalleyRV);
        helpedUserGalleyRV.setHasFixedSize(true);
        helpedUserGalleyRV.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<GalleryData> userGallery = new ArrayList<>();

        String storagePath = "News/" + id + "/userHelps/" + email + "/";

        db.collection("News/"+id+"/userDocs/users/"+email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){
                            String imgName = snapshot.getData().get("name").toString();
                            String desc = snapshot.getData().get("desc").toString();
                            ref = storage.getReference(storagePath+imgName+".jpg");
                            final Bitmap[] uploadPhoto = new Bitmap[1];
                            try {
                                File localFile = File.createTempFile("temp", ".jpg");
                                ref.getFile(localFile)
                                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                uploadPhoto[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                userGallery.add(new GalleryData(uploadPhoto[0], desc));
                                                galleryAdapter adapter = new galleryAdapter(userGallery, HelpedUserListGallery.this);
                                                helpedUserGalleyRV.setAdapter(adapter);
//                                                userUploadProgBar.setVisibility(View.GONE);
                                            }
                                        });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onGallery: "+ snapshot.getData().get("name"));
                        }
                    }
                });
    }
}