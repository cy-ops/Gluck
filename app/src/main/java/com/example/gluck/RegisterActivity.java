package com.example.gluck;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstnamereg;
    private EditText lastnamereg;
    private EditText emailreg;
    private EditText passwordreg;
    private Button btnactualreg;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register);

        firstnamereg = findViewById(R.id.firstnamereg);
        lastnamereg = findViewById(R.id.lastnamereg);
        emailreg = findViewById(R.id.emailreg);
        passwordreg = findViewById(R.id.passwordreg);
        btnactualreg = findViewById(R.id.btnactualreg);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        btnactualreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });
    }

    public void SignUp(){
        final String firstname = firstnamereg.getText().toString().trim();
        final String lastname = lastnamereg.getText().toString().trim();
        final String email = emailreg.getText().toString().trim();
        final String password = passwordreg.getText().toString().trim();

        if(TextUtils.isEmpty(firstname)){
            //firstname is empty
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(lastname)){
            //lastname is empty
            Toast.makeText(this, "Please enter your Last Name", Toast.LENGTH_SHORT).show();
            return;
        }


        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "PLease enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please provide a password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(this, "Minimum password length is 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            firebaseAuth.signInWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                UserData userData = new UserData(firstname, lastname);
                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                databaseReference.child(user.getUid()).setValue(userData);

                                                firebaseAuth.getCurrentUser().sendEmailVerification()
                                                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    progressDialog.dismiss();
                                                                    firebaseAuth.signOut();
                                                                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                                                                            .setMessage("Registration successful. Check email for verification")
                                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                                    finish();
                                                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                                                }
                                                                            })
                                                                            .show();
                                                                }else{
                                                                    progressDialog.dismiss();
                                                                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                                                                            .setMessage(task.getException().getMessage())
                                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                                    finish();
                                                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                                                }
                                                                            })
                                                                            .show();

                                                                }
                                                            }
                                                        });

                                            }else{
                                                progressDialog.dismiss();
                                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                                                        .setMessage(task.getException().getMessage())
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                finish();
                                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                            }
                                                        })
                                                        .show();

                                            }
                                        }
                                    });

                        }else{
                            progressDialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Toast.makeText(RegisterActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }


}
