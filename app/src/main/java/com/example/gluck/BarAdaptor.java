package com.example.gluck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class BarAdaptor extends FirestoreRecyclerAdapter<Bar, BarAdaptor.BarHolder> {

    private OnItemClickListener listener;

    public BarAdaptor(@NonNull FirestoreRecyclerOptions<Bar> options) {
        super(options);
    }

    public class BarHolder extends RecyclerView.ViewHolder {

        private TextView textViewLocation;
        private TextView textViewBrand;
        private TextView textViewOffer;

        public BarHolder(@NonNull View itemView) {
            super(itemView);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewOffer = itemView.findViewById(R.id.textViewOffer);

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
    protected void onBindViewHolder(@NonNull BarAdaptor.BarHolder barHolder, int i, @NonNull Bar bar) {
        barHolder.textViewOffer.setText(bar.getOffer());
        barHolder.textViewLocation.setText(bar.getLocation());
        barHolder.textViewBrand.setText(bar.getBrand());
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
