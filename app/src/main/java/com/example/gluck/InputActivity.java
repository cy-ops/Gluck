package com.example.gluck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
}
