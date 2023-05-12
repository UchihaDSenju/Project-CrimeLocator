package com.example.crimelocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpAct extends AppCompatActivity {
    Button registerBtn;
    TextView signInBtn;
    Boolean usernameDone=Boolean.FALSE, emailDone=Boolean.FALSE, numberDone=Boolean.FALSE,co, passwordDone=Boolean.FALSE;
    ProgressBar progressBar;

    FirebaseAuth auth;

    String Email = "", Password = "",usernameFieldText=" ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();

        TextInputLayout usernameLayout =findViewById(R.id.usernameLayout);
        TextInputLayout emailLayout=findViewById(R.id.emailLayout);
        TextInputLayout passwordLayout=findViewById(R.id.passwordLayout);
        TextInputLayout confirmPasswordLayout =findViewById(R.id.confirmPasswordLayout);
    //    TextInputLayout numberLayout=findViewById(R.id.numberLayout);

        TextInputEditText usernameField =findViewById(R.id.username); // usernameField
        TextInputEditText emailField = findViewById(R.id.email);    // Email
        TextInputEditText passwordField=findViewById(R.id.password);   //passwordField
        TextInputEditText confirmPasswordField =findViewById(R.id.confirmPassword);  //confirm passwordField
    //    TextInputEditText numberField =findViewById(R.id.number);   // mobile



        registerBtn =findViewById(R.id.register);
        signInBtn =findViewById(R.id.textsignin);
        progressBar=findViewById(R.id.progBar);


        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailChecker;
                emailChecker = String.valueOf(emailField.getText());
                if( Patterns.EMAIL_ADDRESS.matcher(emailChecker).matches()) {
                    Email = emailChecker;
                    emailLayout.setHelperText("Valid");
                    if (Email!=" ") {
                        firebaseEmailCheck(Email, emailLayout);
                    }
                }
                else{
                    Email = "";
                    emailLayout.setError("Not Valid");
                    emailDone=Boolean.FALSE;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                    Password = confirmPasswordFieldText;
                }
                else if(!passwordDone){
                    Password = "";
                    confirmPasswordLayout.setError("Enter password according to the given standards");
                }
                else {
                    Password = "";
                    confirmPasswordLayout.setError("Confirm Password not Match with Password");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 usernameFieldText = usernameField.getText().toString();
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

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                if(Email == "" || Password == "" || usernameFieldText==" "){
                    Toast.makeText(SignUpAct.this, "Fill All Fields Properly", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

                else if(!usernameDone || !emailDone || !passwordDone)
                {
                    Toast.makeText(SignUpAct.this, "Fill All Fields Properly", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    firebaseRegister(Email, Password);
                    startActivity(new Intent(SignUpAct.this,MainActivity.class));
                    finish();
                }

//
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpAct.this,MainActivity.class));
                finish();
            }
        });
    }

    public void firebaseEmailCheck(String email, TextInputLayout emailLayout){
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean check= !task.getResult().getSignInMethods().isEmpty();
                        if(check){
                            emailDone=Boolean.FALSE;
                            emailLayout.setError("Email Already Exists");
                            Toast.makeText(SignUpAct.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            emailDone=Boolean.TRUE;
                        }
                    }
                });
    }

    public void firebaseRegister(String Email, String Password){
      //  Toast.makeText(this, "Please Wait while we are Registering you...", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        auth= FirebaseAuth.getInstance(); //Instantiate the firebaseAuth

        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpAct.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpAct.this,MainActivity.class));
                    finish();
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpAct.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


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


