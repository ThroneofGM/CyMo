package com.notice_center.cymo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class SearchFragmentFour extends Fragment {

    RecyclerView recyclerView;
    adaptGen gen;
    androidx.appcompat.widget.SearchView searchView;



    public SearchFragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.pdfRec2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<fileinfo> options =
                new FirebaseRecyclerOptions.Builder<fileinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bsc CS").orderByChild("spinner").equalTo("Fourth"), fileinfo.class)
                        .build();

        gen =new adaptGen(options);
        recyclerView.setAdapter(gen);

        searchView = view.findViewById(R.id.searchBar);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dataSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dataSearch(newText);
                return false;
            }
        });
        return view;
    }

    private void dataSearch(String query) {
        FirebaseRecyclerOptions<fileinfo> options =
                new FirebaseRecyclerOptions.Builder<fileinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bsc CS").orderByChild("filename").startAt(query).endAt(query+"\uf8ff"), fileinfo.class)
                        .build();
        gen =new adaptGen(options);
        gen.startListening();
        recyclerView.setAdapter(gen);

    }

    @Override
    public void onStart() {
        super.onStart();
        gen.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        gen.stopListening();
    }

}