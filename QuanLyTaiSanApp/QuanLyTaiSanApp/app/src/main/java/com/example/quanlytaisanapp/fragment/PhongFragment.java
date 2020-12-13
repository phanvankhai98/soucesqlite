package com.example.quanlytaisanapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlytaisanapp.ChiTietPhongActivity;
import com.example.quanlytaisanapp.R;
import com.example.quanlytaisanapp.adapter.ListPhongAdapter;
import com.example.quanlytaisanapp.database.DatabaseHandler;
import com.example.quanlytaisanapp.model.Phong;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PhongFragment extends Fragment {

    DatabaseHandler db;
    FrameLayout frameLayout;
    List<Phong> data;
    ListView listView;
    FloatingActionButton floatButton;
    public PhongFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_phong, container, false);
        init(view);
        handleView();
        handleEvent();
        return view;
    }

    private void init(View view) {
        db = new  DatabaseHandler(getContext());
        data = db.getAllPhong();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);

        //listview
        listView = view.findViewById(R.id.listview);
        ListPhongAdapter listAdapter = new ListPhongAdapter(data,getContext());
        listView.setAdapter(listAdapter);
    }

    private void handleView() {
        if (data.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else
            frameLayout.setVisibility(View.INVISIBLE);
    }


    private void handleEvent() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietPhongActivity.class);
                startActivity(intent);
            }
        });
    }
}