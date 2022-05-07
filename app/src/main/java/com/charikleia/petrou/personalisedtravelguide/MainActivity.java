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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView, textView2, textView3;
    private EditText mail, pass;
    private Button login, signup;
    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
    ProgressBar progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        mail= findViewById(R.id.editTextTextEmailAddress2);
        pass= findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.button);
        signup = findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressBar2 = findViewById(R.id.progressBar2);


        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();


        //log in
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();

            }
        });


        //sign up
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                intent.putExtra("","");
                startActivity(intent);
            }
        });




    }

    private void userLogin(){
        System.out.println("0");

        String email = mail.getText().toString().trim();
        String password = pass.getText().toString();

        if (email.isEmpty()){
            mail.setError("Email is required!");
            mail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Please enter a valid email!");
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
        System.out.println("1");
        progressBar2.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("2");
                if (task.isSuccessful()){
                    System.out.println("3");
                    //retrieve data from firebase
                    FirebaseUser currentUser = mAuth.getCurrentUser();


                    //msg to inform user for successful login
                    Toast.makeText(MainActivity.this, "Successful Log In! Please check your credentials!", Toast.LENGTH_SHORT).show();
                    //successful login leads to the next activity
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    intent.putExtra("uid",currentUser.getUid());
                    startActivity(intent);
                }else{
                    System.out.println("4");
                    progressBar2.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed Log In! Please check your credentials!", Toast.LENGTH_SHORT).show();
                }
                System.out.println("5");
            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();
        progressBar2.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar2.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {

    }
}