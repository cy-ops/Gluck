package com.example.gluck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class RestaurantDetailActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();;
    DocumentReference restaurant;

    TextView about;
    TextView offer;
    TextView brand;
    ImageView brandimage;

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
        brandimage = findViewById(R.id.ImageViewImage);

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



                        final StorageReference storageReference = FirebaseStorage.getInstance().getReference(Objects.requireNonNull(document.getString("image")));
                        final long ONE_MEGABYTE = 1024 * 1024;
                        storageReference.getBytes(ONE_MEGABYTE)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {


                                        Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                        DisplayMetrics dm = new DisplayMetrics();
                                        getWindowManager().getDefaultDisplay().getMetrics(dm);


                                        brandimage.setMinimumHeight(dm.heightPixels);
                                        brandimage.setMinimumWidth(dm.widthPixels);
                                        brandimage.setImageBitmap(bm);

                                        //Glide.with(getApplicationContext()).load(storageReference).into(brandimage);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(RestaurantDetailActivity.this, "Image not found", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                }
            }
        });
    }
}
