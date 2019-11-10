package com.example.gluck;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class BarFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("Bars");
    private RecyclerView recyclerView;
    private BarAdaptor adapter;
    View view;


    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bar, container, false);
        recyclerView = view.findViewById(R.id.barRecycler);
        setUpRecycler();
        return view;
    }

    private void setUpRecycler() {
        Query query = ref;
        FirestoreRecyclerOptions<Bar> options = new FirestoreRecyclerOptions.Builder<Bar>()
                .setQuery(query, Bar.class)
                .build();

        adapter = new BarAdaptor(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new BarAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(getContext(), RestaurantDetailActivity.class);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
