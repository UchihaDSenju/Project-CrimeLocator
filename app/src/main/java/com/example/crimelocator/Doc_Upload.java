package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Doc_Upload extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;
    RecyclerView userUploadView;
    ArrayList<String> users = new ArrayList<>();


    Uri imageUri;

    Button chooseBtn, uploadBtn;
    TextView fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);
        getSupportActionBar().hide();


        Log.d(TAG, "onCreate: in app" + users);


        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        String id = intent.getStringExtra("ID");

        String dbPath = "News/"+id+"/userHelps";

        Log.d(TAG, "onCreate: "+ intent.getStringExtra("EMAIL"));

        chooseBtn = findViewById(R.id.chooseButton);
        uploadBtn = findViewById(R.id.uploadButton);
        fileName = findViewById(R.id.fileName);
        userUploadView=findViewById(R.id.userUploadView);

        ActivityCompat.requestPermissions(Doc_Upload.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);


           chooseBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                   Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
                   intent.setType("*/*");
                   startActivityForResult(intent,10);  //reqCode
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
                           String docName = fileName.getText().toString();
                           ref = storage.getReference("News/" + id + "/userHelps/" + email + "/"+docName+".jpg");
                           ref.putFile(imageUri)
                                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                           db.collection(dbPath)
                                                   .get()
                                                   .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                       @Override
                                                       public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                           for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                                                               users = (ArrayList<String>) snapshot.getData().get("users");
                                                               Log.d(TAG, "onSuccess: "+ users);
                                                           }
                                                           if(users.contains(email))
                                                               Log.d(TAG, "onSuccess: Already Helped");
                                                           else{
                                                               users.add(email);
                                                               Map<String, ArrayList<String>> usersList = new HashMap<>();
                                                               usersList.put("users",users);
                                                               db.collection(dbPath)
                                                                       .document("userHelps")
                                                                       .set(usersList);
                                                           }
                                                       }
                                                   });
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