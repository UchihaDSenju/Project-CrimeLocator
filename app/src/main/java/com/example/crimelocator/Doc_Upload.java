package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Doc_Upload extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;
    RecyclerView userUploadView;
    ArrayList<String> users = new ArrayList<>();
    ArrayList<GalleryData> userGallery = new ArrayList<>();


    Uri imageUri;

    Button chooseBtn, uploadBtn;
    TextView fileName;
    EditText descEditText;
    ProgressBar uploadProgBar,userUploadProgBar;

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
        String storagePath = "News/" + id + "/userHelps/" + email + "/";

        Log.d(TAG, "onCreate: "+ intent.getStringExtra("EMAIL"));

        chooseBtn = findViewById(R.id.chooseButton);
        uploadBtn = findViewById(R.id.uploadButton);
        fileName = findViewById(R.id.fileName);
        descEditText = findViewById(R.id.descEditText);
        uploadProgBar = findViewById(R.id.uploadProgBar);
        userUploadProgBar = findViewById(R.id.userUploadProgBar);

        userUploadView=findViewById(R.id.userUploadView);
        userUploadView.setHasFixedSize(true);
        userUploadView.setLayoutManager(new LinearLayoutManager(this));



           chooseBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
                   intent.setType("image/*");
                   startActivityForResult(intent,10);  //reqCode

               }
           });

        db.collection(dbPath)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                            users = (ArrayList<String>) snapshot.getData().get("users");
                            Log.d(TAG, "onSuccess: "+ snapshot.getData().get("users"));
                        }
                        if(users.contains(email)) {
                            userUploadProgBar.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onSuccess: Already Helped");
                            setDocsInGallery("News/"+id+"/userDocs/users/"+email, storagePath, userGallery);
                        }
                    }
                });
           
           uploadBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   uploadProgBar.setVisibility(View.VISIBLE);
                   if(imageUri == null){
                       Toast.makeText(Doc_Upload.this, "Choose an Image", Toast.LENGTH_SHORT).show();
                       uploadProgBar.setVisibility(View.GONE);
                   }
                   else{
                       if(fileName.getText() == null){
                           Toast.makeText(Doc_Upload.this, "Enter a file name", Toast.LENGTH_SHORT).show();
                           uploadProgBar.setVisibility(View.GONE);
                       }
                       else{
                           Toast.makeText(Doc_Upload.this, "File Upload Ready", Toast.LENGTH_SHORT).show();
                           String docName = fileName.getText().toString();
                           String docDesc = descEditText.getText().toString();
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
                                                           if(users.contains(email)) {
                                                               Log.d(TAG, "onSuccess: Already Helped");
                                                               setFirestoreEntry(db, "News/" + id + "/userDocs/users/"+email, docName, docDesc);
                                                           }
                                                           else{
                                                               users.add(email);
                                                               Map<String, ArrayList<String>> usersList = new HashMap<>();
                                                               usersList.put("users",users);
                                                               db.collection(dbPath)
                                                                       .document("userHelps")
                                                                       .set(usersList);
                                                               setFirestoreEntry(db, "News/" + id + "/userDocs/users/"+email, docName, docDesc);
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
                                           userUploadProgBar.setVisibility(View.GONE);
                                       }
                                   });
                       }
                   }
               }
           });
    }

    private void setFirestoreEntry(FirebaseFirestore db ,String dbPath, String docName, String docDesc) {
        Map<String, Object> m = new HashMap<>();
        m.put("name", docName);
        m.put("desc", docDesc);
        db.collection(dbPath)
                .document(docName)
                .set(m)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Doc_Upload.this, "Firebase Storage updated Successfully", Toast.LENGTH_SHORT).show();
                        uploadProgBar.setVisibility(View.GONE);
                    }
                });
    }

    private void setDocsInGallery(String dbPath, String storagePath, ArrayList<GalleryData> userGallery) {
        db.collection(dbPath)
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
                                                galleryAdapter adapter = new galleryAdapter(userGallery, Doc_Upload.this);
                                                userUploadView.setAdapter(adapter);
                                                userUploadProgBar.setVisibility(View.GONE);
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