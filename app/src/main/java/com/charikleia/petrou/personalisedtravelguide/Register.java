package com.charikleia.petrou.personalisedtravelguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    //initialize needed variables for UI and functionally
    private TextView textView4;
    private EditText username, age, mail, pass;
    private Button regbtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
//    private ScrollView scrollView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //set values to the variables
        username = findViewById(R.id.editTextTextPersonName2);
        age = findViewById(R.id.editTextNumber);
        mail = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextTextPassword2);
        regbtn = findViewById(R.id.button3);
//        scrollView2 = findViewById(R.id.scrollView2);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

//        scrollView2.setVisibility(View.VISIBLE);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    //register User function
    private  void registerUser(){
        //initialize input variables for register and check
        String name = username.getText().toString();
        String Age = age.getText().toString().trim();
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString();

        // input check
        if (name.isEmpty()){
            username.setError("Full name is required!");
            username.requestFocus();
            return;

        }

        if (Age.isEmpty()){
            age.setError("Age is required!");
            age.requestFocus();
            return;

        }

        if (email.isEmpty()){
            mail.setError("Mail is required!");
            mail.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Please provide a valid mail!");
            mail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            pass.setError("Password is required!");
            pass.requestFocus();
            return;

        }

        if (password.length()<5){
            pass.setError("Min password length is 5 characters");
            pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user in firebase with extra info
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //set values to firebase from user class
                            User user= new User(name, Age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        //successful registration
                                        Toast.makeText(Register.this, "Successful Registration", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
//                                        scrollView2.setVisibility(View.GONE);

                                        //go back to login activity
                                        Intent intent = new Intent(Register.this, MainMenu.class);
                                        //intent.putExtra("","");
                                        startActivity(intent);

                                    }else{
                                        //failed registration
                                        Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
//                                        scrollView2.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }else{
                            //failed registration
                            Toast.makeText(Register.this, "Registration failed, user already exist", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
//                            scrollView2.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }
}