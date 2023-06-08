package com.example.crimelocator;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminAddNews extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref;

    Integer newsId = -1;
    String title, desc;

    Button adminSelectImage, adminCreateNews;
    TextView adminNewsTitle;
    ImageView adminCoverImageView;
    EditText adminNewsDesc;

    Uri coverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        getSupportActionBar().hide();

        adminSelectImage =findViewById(R.id.adminSelectImage);
        adminNewsTitle = findViewById(R.id.adminNewsTitle);
        adminCreateNews = findViewById(R.id.adminCreateNews);
        adminCoverImageView = findViewById(R.id.adminCoverImageView);
        adminNewsDesc = findViewById(R.id.adminDesc);

        adminSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //ACTION_GET_CONTENT
                intent.setType("image/*");
                startActivityForResult(intent,10);  //reqCode
            }
        });

        adminCreateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = adminNewsTitle.getText().toString();
                desc = adminNewsDesc.getText().toString();
                Date date = new Date();


                Toast.makeText(AdminAddNews.this, "Creating News", Toast.LENGTH_SHORT).show();
                db.collection("NewsId")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot snapshot: queryDocumentSnapshots){
                                    Toast.makeText(AdminAddNews.this, "Generating News ID", Toast.LENGTH_SHORT).show();
                                    newsId = Integer.parseInt(snapshot.getData().get("id").toString());
                                    Log.d(TAG, "onSuccess: Generated Id number "+ newsId);
                                    Toast.makeText(AdminAddNews.this, "Generated News ID Please Wait...", Toast.LENGTH_SHORT).show();

                                    ref = storage.getReference("News/"+newsId+"/cover.jpg");

                                    Map<String, Object> m = new HashMap<>();
                                    m.put("title", title);
                                    m.put("id", newsId);
                                    m.put("desc", desc);
                                    m.put("date_created", date);

                                    db.collection("News")
                                            .document(newsId.toString())
                                            .set(m)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    ref.putFile(coverImage);
//                                                    Map<String, Object> id = new HashMap<>();
//                                                    id.put("id", newsId+1);
//                                                    db.collection("NewsId")
//                                                                    .document("NewsId")
//                                                                            .set(id)
//                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                @Override
//                                                                public void onSuccess(Void unused) {
//                                                                    Toast.makeText(AdminAddNews.this, "News Created", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            });
                                                    Toast.makeText(AdminAddNews.this, "News Created", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e);
                            }
                        });
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                coverImage = data.getData();
                adminCoverImageView.setImageURI(coverImage);
            }
        }
    }
}