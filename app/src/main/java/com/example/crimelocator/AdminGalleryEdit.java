package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AdminGalleryEdit extends AppCompatActivity {

    String desc;
    ImageView adminGalleyCoverImage;
    Button adminGalleySelectImage,adminGalleryCreateNews;
    EditText adminGalleryDesc;
    ProgressBar adminGalleryProgBar;
    Uri coverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gallery_edit);
        getSupportActionBar().hide();

        adminGalleyCoverImage = findViewById(R.id.adminGalleyCoverImage);
        adminGalleySelectImage = findViewById(R.id.adminGalleySelectImage);
        adminGalleryCreateNews = findViewById(R.id.adminGalleryCreateNews);
        adminGalleryDesc = findViewById(R.id.adminGalleryDesc);
        adminGalleryProgBar = findViewById(R.id.adminGalleryProgBar);

        adminGalleySelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
                intent.setType("image/*");
                startActivityForResult(intent,10);  //reqCode
            }
        });

        adminGalleryCreateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                desc = adminGalleryDesc.getText().toString();
                if(desc.isEmpty())  //coverImage==null will come here after creating coverImage.
                {
                    Toast.makeText(AdminGalleryEdit.this, "image and description not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}