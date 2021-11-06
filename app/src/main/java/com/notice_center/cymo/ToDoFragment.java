package com.notice_center.cymo;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ToDoFragment extends Fragment {
    List<String> toDOList;
    ArrayAdapter<String> stringArrayAdapter;
    ListView listView;
    EditText editText;
    Button addTask;

    public ToDoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        toDOList = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<>(getContext(),R.layout.list_viewlayout,toDOList);
        listView = view.findViewById(R.id.list);
        listView.setAdapter(stringArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }

        });

        editText = view.findViewById(R.id.newTask);

        addTask = view.findViewById(R.id.addTask);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDOList.add(editText.getText().toString());
                stringArrayAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

        return view;
    }

    
}