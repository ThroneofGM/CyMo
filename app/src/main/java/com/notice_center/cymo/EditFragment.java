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


public class EditFragment extends Fragment {

    RecyclerView recview;
    rowAdapt adapt;

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        recview = (RecyclerView) view.findViewById(R.id.pdfRec);
        recview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<fileinfo> options =
                new FirebaseRecyclerOptions.Builder<fileinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bsc CS"), fileinfo.class)
                        .build();

        adapt=new rowAdapt(options);
        recview.setAdapter(adapt);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapt.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapt.stopListening();
    }

}