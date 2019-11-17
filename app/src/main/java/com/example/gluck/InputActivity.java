package com.example.gluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InputActivity extends AppCompatActivity {

    private EditText editTextBrand;
    private EditText editTextOffer;
    private EditText editTextAbout;
    private EditText editLocation;
    private Spinner category_spinner;
    private Button submit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        editTextBrand = findViewById(R.id.editTextBrand);
        editTextOffer = findViewById(R.id.editTextOffer);
        editTextAbout = findViewById(R.id.editTextAbout);
        editLocation = findViewById(R.id.editLocation);
        category_spinner = findViewById(R.id.category_spinner);
        submit_content = findViewById(R.id.submit_content);

        String[] category = new String[]{"Restaurant","Bar"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text, category);

        adapter.setDropDownViewResource(R.layout.spinner_text);

        category_spinner.setAdapter(adapter);


    }

    public void addPlace(View view) {
        String category = category_spinner.getSelectedItem().toString();
        String document = editTextBrand.getText().toString();
        DocumentReference place = FirebaseFirestore.getInstance()
                .collection(category).document(document);
        if(category.equals("Restaurant")) {
            final Restaurant restaurant = new Restaurant(
                    editTextAbout.getText().toString(),
                    editTextBrand.getText().toString(),
                    editLocation.getText().toString(),
                    editTextOffer.getText().toString()
            );

            place.set(restaurant).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(InputActivity.this, "Sucessfully added "+restaurant.getBrand()
                    ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InputActivity.this, MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   Toast.makeText(InputActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            final Bar restaurant = new Bar(
                    editTextAbout.getText().toString(),
                    editTextBrand.getText().toString(),
                    editLocation.getText().toString(),
                    editTextOffer.getText().toString(),
                    ""
            );

            place.set(restaurant).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(InputActivity.this, "Sucessfully added "+restaurant.getBrand()
                            ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InputActivity.this, MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InputActivity.this, e.toString(), Toast.LENGTH_SHORT);
                }
            });
        }
    }
}
