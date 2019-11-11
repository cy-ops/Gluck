package com.example.gluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class BarDetailActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference bar;
    TextView offer;
    TextView about;
    TextView brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_detail);

        setUpTextViews();
    }

    private void setUpTextViews() {
        offer = findViewById(R.id.textViewBarOfferDetail);
        about = findViewById(R.id.textViewBarAboutDetail);
        brand = findViewById(R.id.textViewBarBrand);

        bar = db.collection("Bars")
                .document(getIntent().getStringExtra("bar"));

        bar.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        offer.setText(document.getString("offer"));
                        about.setText(document.getString("about"));
                        brand.setText(document.getString("brand"));
                    }
                }
            }
        });
    }
}
