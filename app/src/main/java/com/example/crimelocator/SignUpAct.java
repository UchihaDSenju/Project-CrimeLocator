package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpAct extends AppCompatActivity {
    Button register;
    TextView txtsignin;
   // FirebaseAuth mAuth;
    ProgressBar progressBar;
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            startActivity(new Intent(SignUpAct.this,demo.class));
//            finish();}}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        // mAuth=FirebaseAuth.getInstance();
        TextInputLayout usernameLayout =findViewById(R.id.usernameLayout);
        TextInputLayout emailLayout=findViewById(R.id.emailLayout);
        TextInputLayout passwordLayout=findViewById(R.id.passwordLayout);
        TextInputLayout confirmPasswordLayout =findViewById(R.id.confirmPasswordLayout);
        TextInputLayout numberLayout=findViewById(R.id.numberLayout);
        TextInputEditText confirmPassword =findViewById(R.id.confirmPassword);  //confirm password
        TextInputEditText password=findViewById(R.id.password);   //password
        TextInputEditText number=findViewById(R.id.number);   // mobile
        TextInputEditText email = findViewById(R.id.email);    // Email
        TextInputEditText username =findViewById(R.id.username); // username
        register=findViewById(R.id.register);
        txtsignin=findViewById(R.id.textsignin);
        progressBar=findViewById(R.id.progBar);
        String user = username.getText().toString();

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass = number.getText().toString();
                if(pass.length()>10){
                    number.setError("Enter Less than 10 numbers");//red
                    number.setText("");
                }
                else if (pass.length()==10){
                    numberLayout.setHelperText("verified");//color
                }
                else {
                    numberLayout.setError("Enter 10 numbers");}}
            @Override
            public void afterTextChanged(Editable editable) {}});

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass=charSequence.toString();
                if(pass.length() >=8 && pass.length()<=10){
                    Pattern pattern=Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher=pattern.matcher(pass);
                    boolean ispswcntnspchar=matcher.find();
                    if(ispswcntnspchar){
                        passwordLayout.setHelperText("Strong Password");
                        passwordLayout.setError("");}
                    else {
                        passwordLayout.setHelperText("");
                        passwordLayout.setError("Weak Password.Include 1 special character(eg:#@*)");}}
                else if(pass.length() < 8){
                    passwordLayout.setHelperText("");
                    passwordLayout.setError("Minimum 8 to 10 characters");}
                else if(pass.length()>10){
                    password.setText("");}}
            @Override
            public void afterTextChanged(Editable editable) {}});
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = password.getText().toString();
                String m =confirmPassword.getText().toString();
                if(m.length() >=8 && m.length()<=10){
                    Pattern pattern=Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher=pattern.matcher(m);
                    boolean ispswcntnspchar=matcher.find();
                    if(ispswcntnspchar && m.equals(s)){
                        confirmPasswordLayout.setHelperText("Confirm Password Match with Password");}
                    else if(!ispswcntnspchar) {
                        confirmPasswordLayout.setHelperText("");
                        confirmPasswordLayout.setError("Weak Password and password not match");}
                    else {
                        confirmPasswordLayout.setError("Confirm Password not Match with Password");}}
                else if(m.length() < 8) {
                    confirmPasswordLayout.setError("Confirm Password not Match with Password and minimum 8 char");}
                else if(m.length() >10) {
                    confirmPassword.setText(" ");}
                else {
                    confirmPasswordLayout.setError("Confirm Password not Match with Password");}}
            @Override
            public void afterTextChanged(Editable editable) {}});

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = username.getText().toString();
                if (user.matches("[a-zA-z0-9]+")&& user.length() > 6) {
                    usernameLayout.setHelperText("Matched");}
                else if (user.length() <= 6) {
                    usernameLayout.setError("enter more than 6 char");
                }
                else if(!user.matches("[a-zA-z0-9]+") && user.length() >6){
                    usernameLayout.setError("include only alphabetical char");}}
            @Override
            public void afterTextChanged(Editable s) {}});

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = password.getText().toString();
                String m = confirmPassword.getText().toString();
                String n = number.getText().toString();
                String e = email.getText().toString();
                String u = username.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                String sEmail;
                String sPassword;
                sEmail= String.valueOf(email.getText());
                sPassword= String.valueOf(password.getText());

                if(TextUtils.isEmpty(sPassword) || TextUtils.isEmpty(sEmail)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpAct.this,"Invalid.Fill all fields",Toast.LENGTH_SHORT).show();
                    return;}
                else if (u.length() <= 6) {
                    Toast.makeText(getApplicationContext(),"Invalid Username.Less than 6 char",Toast.LENGTH_SHORT).show();}
                // mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                //         .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                //           @Override
                //          public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(s.length() >=8 && s.length()<=10 && m.length() >=8 && m.length() <=10 && n.length()==10 && u.length() > 6) {
                    //  if (task.isSuccessful() && Patterns.EMAIL_ADDRESS.matcher(sEmail).matches() && s.equals(m) && u.matches("[a-zA-z0-9]+")) {
                    Toast.makeText(SignUpAct.this, "Account created.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpAct.this, MainActivity.class));
                    finish();
                    createNotify();}
                else if(!u.matches("[a-zA-z0-9]+")){
                    Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_SHORT).show();}
                else if( u.length() >=6 && !u.matches("[a-zA-z0-9]+") && !Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
                    Toast.makeText(getApplicationContext(),"Invalid Username and sEmail",Toast.LENGTH_SHORT).show();}
                else {
                    Toast.makeText(SignUpAct.this, "invalid Email id",
                            Toast.LENGTH_SHORT).show();};
                if(!s.equals(m)){
                    password.setText("");
                    confirmPassword.setText("");
                    passwordLayout.setError("Re Enter Password with Minimum 8 to 10 Char");
                    confirmPasswordLayout.setError("Enter Same Password for Confirm Password");
                    Toast.makeText(getApplicationContext(),"Password not matched.Re Enter sPassword",Toast.LENGTH_SHORT).show();}
                else if( m.isEmpty() || n.isEmpty() || u.isEmpty() )  {
                    Toast.makeText(getApplicationContext(),"Invalid.Fill all fields",Toast.LENGTH_SHORT).show();}
                else if(s.length() <8 || m.length() <8) {
                    Toast.makeText(getApplicationContext(),"invalid with less sPassword char",Toast.LENGTH_SHORT).show();}
                if(n.length() <10){
                    Toast.makeText(getApplicationContext(),"less than 10 numbers",Toast.LENGTH_SHORT).show();}}});

        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpAct.this,MainActivity.class));
                finish();}});}

    private void createNotify()
    {
        String id="Crime Locate";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if(channel==null) {
                channel=new NotificationChannel(id,"Crime Locate",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Crime Locate and ");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100,1000,200,340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);}}
        Intent intent = new Intent(this,demo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent=null;
        if(android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.S){
            pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
        }else{
            pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,id)
               // .setSmallIcon(R.drawable.crimelogo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.back))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.back))
                        .bigLargeIcon(null))
                .setContentTitle("Crime Locate")
                .setContentText("Hello from Crime Locate.You have Signed up and logged in")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{100,1000,200,340})
                .setAutoCancel(false) //if true tap to close.but false swipe to close
                .setTicker("Notification");
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat m=NotificationManagerCompat.from(getApplicationContext());
        m.notify(new Random().nextInt(),builder.build()); //new Random.nextInt(),..for multiple notification
    }}