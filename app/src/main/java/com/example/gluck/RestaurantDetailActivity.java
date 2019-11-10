package com.example.gluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantDetailActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();;
    DocumentReference restaurant;

    TextView about;
    TextView offer;
    TextView brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        setUpTextViews();
    }

    private void setUpTextViews() {
        offer = findViewById(R.id.textViewOfferDetail);
        about = findViewById(R.id.textViewAboutDetail);
        brand = findViewById(R.id.textViewBrand);

        restaurant = db.collection("Restaurants")
                .document(getIntent().getStringExtra("restaurant"));

        restaurant.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
