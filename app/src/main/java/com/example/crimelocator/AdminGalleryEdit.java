package com.example.crimelocator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminGalleryEdit extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;

    String desc;
    ImageView adminGalleyImage;
    Button adminGalleySelectImage, adminGallerySetImage;
    EditText adminGalleryImageDesc;
    ProgressBar adminGalleryProgBar;
    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gallery_edit);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        String dbPath = "News/"+id+"/gallery";
        String storagePath = "News/" + id + "/gallery/";

        adminGalleyImage = findViewById(R.id.adminGalleyImage);
        adminGalleySelectImage = findViewById(R.id.adminGalleySelectImage);
        adminGallerySetImage = findViewById(R.id.adminGallerySetImage);
        adminGalleryImageDesc = findViewById(R.id.adminGalleryImageDesc);
        adminGalleryProgBar = findViewById(R.id.adminGalleryProgBar);

        adminGalleySelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
                intent.setType("image/*");
                startActivityForResult(intent,10);  //reqCode
            }
        });

        adminGallerySetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminGalleryProgBar.setVisibility(View.VISIBLE);

                desc = adminGalleryImageDesc.getText().toString();
                if(desc.isEmpty() || image == null)  //coverImage==null will come here after creating coverImage.
                {
                    Toast.makeText(AdminGalleryEdit.this, "image and description not be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Date date = new Date();
                    String imgName = date.toString();
                    Map<String, Object> m = new HashMap<>();
                    m.put("desc", desc);
                    m.put("name", imgName);
                    ref = storage.getReference(storagePath + imgName+".jpg");
                    ref.putFile(image)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(AdminGalleryEdit.this, "Image Uploaded.. Editing Database", Toast.LENGTH_SHORT).show();
                                    db.collection(dbPath)
                                            .document(imgName)
                                            .set(m)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(AdminGalleryEdit.this, "Gallery Image Set", Toast.LENGTH_SHORT).show();
                                                    adminGalleryProgBar.setVisibility(View.GONE);
                                                }
                                            });
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                image = data.getData();
                adminGalleyImage.setImageURI(image);
            }
        }
    }
}