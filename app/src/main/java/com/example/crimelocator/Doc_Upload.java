package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Doc_Upload extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;

    Uri imageUri;

    Button chooseBtn, uploadBtn;
    TextView fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);

        Intent intent = getIntent();

        Log.d(TAG, "onCreate: "+ intent.getStringExtra("EMAIL"));

        chooseBtn = findViewById(R.id.chooseButton);
        uploadBtn = findViewById(R.id.uploadButton);
        fileName = findViewById(R.id.fileName);

        ActivityCompat.requestPermissions(Doc_Upload.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);


           chooseBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                   Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
                   intent.setType("*/*");
                   startActivityForResult(intent,10);  //reqcode
               }
           });
           
           uploadBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(imageUri == null){
                       Toast.makeText(Doc_Upload.this, "Choose an Image", Toast.LENGTH_SHORT).show();
                   }
                   else{
                       if(fileName.getText() == null){
                           Toast.makeText(Doc_Upload.this, "Enter a file name", Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(Doc_Upload.this, "File Upload Ready", Toast.LENGTH_SHORT).show();
                           ref = storage.getReference("News/1/userHelps/user/demo.jpg");
                           ref.putFile(imageUri)
                                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                           Toast.makeText(Doc_Upload.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(Doc_Upload.this, "Error while uploading file", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                       }
                   }
               }
           });
    }

    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                }
                break;
        }
    }
}