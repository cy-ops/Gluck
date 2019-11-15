package com.example.gluck;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText emailreset;
    private Button btnReset;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reset);

        firebaseAuth = FirebaseAuth.getInstance();

        emailreset = (EditText) findViewById(R.id.emailreset);
        btnReset = (Button) findViewById(R.id.btnReset);
        progressDialog = new ProgressDialog(this);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resetpassword();
            }
        });

    }

    public void Resetpassword(){

        String email = emailreset.getText().toString();


        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(!(activeNetwork!=null && activeNetwork.isConnectedOrConnecting())) {
            Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }


        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please wait");
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            AlertDialog alertDialog = new AlertDialog.Builder(ResetPassword.this)
                                    .setMessage("Check your email for reset link")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            finish();
                                            startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                                        }
                                    })
                                    .show();

                        }else{
                            AlertDialog alertDialog = new AlertDialog.Builder(ResetPassword.this)
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

}
