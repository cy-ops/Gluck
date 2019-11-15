package com.example.gluck;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BarAdaptor extends FirestoreRecyclerAdapter<Bar, BarAdaptor.BarHolder> {

    private OnItemClickListener listener;

    public BarAdaptor(@NonNull FirestoreRecyclerOptions<Bar> options) {
        super(options);
    }

    public class BarHolder extends RecyclerView.ViewHolder {

        private TextView textViewLocation;
        private TextView textViewBrand;
        private TextView textViewOffer;
        private ImageView placeitemimage;

        public BarHolder(@NonNull View itemView) {
            super(itemView);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewOffer = itemView.findViewById(R.id.textViewOffer);
            placeitemimage = itemView.findViewById(R.id.placeitemimage);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull final BarAdaptor.BarHolder barHolder, int i, @NonNull final Bar bar) {
        barHolder.textViewOffer.setText(bar.getOffer());
        barHolder.textViewLocation.setText(bar.getLocation());
        barHolder.textViewBrand.setText(bar.getBrand());


        final StorageReference storageReference = FirebaseStorage.getInstance().getReference(bar.getImage());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(barHolder.placeitemimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ImgNotFound", e.toString());
            }
        });


    }

    @NonNull
    @Override
    public BarAdaptor.BarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item,
                parent, false);


        return new BarHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
