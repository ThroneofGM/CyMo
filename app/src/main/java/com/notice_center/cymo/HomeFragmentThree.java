package com.notice_center.cymo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragmentThree extends Fragment {

    RecyclerView recview;
    adaptGen adapt;
    FloatingActionButton fab;

    public HomeFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_one, container, false);
        fab = view.findViewById(R.id.add_fab);
        recview = (RecyclerView) view.findViewById(R.id.student_view);
        recview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<fileinfo> options =
                new FirebaseRecyclerOptions.Builder<fileinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bsc CS").orderByChild("spinner").equalTo("Third"), fileinfo.class)
                        .build();

        adapt=new adaptGen(options);
        recview.setAdapter(adapt);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),StudentAuth.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
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