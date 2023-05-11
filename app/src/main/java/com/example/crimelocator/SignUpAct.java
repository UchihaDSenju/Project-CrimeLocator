package com.example.crimelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpAct extends AppCompatActivity {
    Button register;
    TextView singInBtn;
    Boolean usernameDone=Boolean.FALSE, emailDone=Boolean.FALSE, numberDone=Boolean.FALSE,co, passwordDone=Boolean.FALSE;
    ProgressBar progressBar;

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
    //    TextInputLayout numberLayout=findViewById(R.id.numberLayout);
        TextInputEditText confirmPasswordField =findViewById(R.id.confirmPassword);  //confirm passwordField
        TextInputEditText passwordField=findViewById(R.id.password);   //passwordField
    //    TextInputEditText numberField =findViewById(R.id.number);   // mobile
        TextInputEditText emailField = findViewById(R.id.email);    // Email
        TextInputEditText usernameField =findViewById(R.id.username); // usernameField
        register=findViewById(R.id.register);
        singInBtn =findViewById(R.id.textsignin);
        progressBar=findViewById(R.id.progBar);


//        numberField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String numberFieldText = numberField.getText().toString();
//                if(numberFieldText.length()>10){
//                    numberField.setError("Enter Less than 10 numbers");//red
//                    numberField.setText("");
//                }
//                else if (numberFieldText.length()==10){
//                    numberLayout.setHelperText("verified");//color
//                    numberDone=Boolean.TRUE;
//                }
//                else {
//                    numberLayout.setError("Enter 10 numbers");
//                    numberDone=Boolean.FALSE;
//                }}
//            @Override
//            public void afterTextChanged(Editable editable) {}});

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Changes Before the TextField has been changed
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String passwordFieldText =charSequence.toString();
                if(passwordFieldText.length() >=8 && passwordFieldText.length()<=10){
                    Pattern pattern=Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher=pattern.matcher(passwordFieldText);
                    boolean passwordOkay=matcher.find();
                    if(passwordOkay){
                        passwordLayout.setHelperText("Strong Password");
                        passwordLayout.setError("");
                        passwordDone=Boolean.TRUE;
                    }
                    else {
                        passwordLayout.setHelperText("");
                        passwordLayout.setError("Weak Password.Include 1 special character(eg:#@*)");
                        passwordDone=Boolean.FALSE;
                    }
                }
                else if(passwordFieldText.length() < 8){
                    passwordLayout.setHelperText("");
                    passwordLayout.setError("Minimum 8 to 10 characters");
                    passwordDone=Boolean.FALSE;
                }
                else if(passwordFieldText.length()>10){
                    passwordField.setText("");//mustRemove This
                }
            }
            @Override
            public void afterTextChanged(Editable editable){
                //Changes after TextField has been changed
            }
        });
        confirmPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String passwordFieldText = passwordField.getText().toString();
                String confirmPasswordFieldText = confirmPasswordField.getText().toString();
                if(confirmPasswordFieldText.equals(passwordFieldText) && passwordDone){
                    confirmPasswordLayout.setHelperText("Password Matches");
                }
                else if(!passwordDone){
                    confirmPasswordLayout.setError("Enter password according to the given standards");
                }
                else {
                    confirmPasswordLayout.setError("Confirm Password not Match with Password");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Email;
                Email= String.valueOf(emailField.getText());
                if( Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    emailLayout.setHelperText("Valid");
                    emailDone=Boolean.TRUE;
                }
                else{
                    emailLayout.setError("Not Valid");
                    emailDone=Boolean.FALSE;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usernameFieldText = usernameField.getText().toString();
                if (usernameFieldText.matches("[a-zA-z0-9]+")&& usernameFieldText.length() > 6) {
                    usernameLayout.setHelperText("Matched");
                    usernameDone=Boolean.TRUE;
                }
                else if (usernameFieldText.length() <= 6) {
                    usernameDone=Boolean.FALSE;
                    usernameLayout.setError("enter more than 6 char");
                }
                else if(!usernameFieldText.matches("[a-zA-z0-9]+") && usernameFieldText.length() >6){
                    usernameLayout.setError("include only alphabetical char");}}
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if(usernameDone && passwordDone && emailDone ){
                    Toast.makeText(SignUpAct.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(SignUpAct.this, "Fill All Fields Properly", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        singInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpAct.this,MainActivity.class));
                finish();}});}}


//    private void createNotify()
//    {
//        String id="Crime Locate";
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = manager.getNotificationChannel(id);
//            if(channel==null) {
//                channel=new NotificationChannel(id,"Crime Locate",NotificationManager.IMPORTANCE_HIGH);
//                channel.setDescription("Crime Locate and ");
//                channel.enableVibration(true);
//                channel.setVibrationPattern(new long[]{100,1000,200,340});
//                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//                manager.createNotificationChannel(channel);}}
//        Intent intent = new Intent(this,demo.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent=null;
//        if(android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.S){
//            pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
//        }else{
//            pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//        }
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,id)
//                .setSmallIcon(R.drawable.crimelogo)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.back))
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.back))
//                        .bigLargeIcon(null))
//                .setContentTitle("Crime Locate")
//                .setContentText("Hello from Crime Locate.You have Signed up and logged in")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setVibrate(new long[]{100,1000,200,340})
//                .setAutoCancel(false) //if true tap to close.but false swipe to close
//                .setTicker("Notification");
//        builder.setContentIntent(pendingIntent);
//        NotificationManagerCompat m=NotificationManagerCompat.from(getApplicationContext());
//        m.notify(new Random().nextInt(),builder.build()); //new Random.nextInt(),..for multiple notification
//    }