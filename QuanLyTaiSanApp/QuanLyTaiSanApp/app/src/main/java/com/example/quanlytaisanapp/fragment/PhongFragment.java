package com.example.quanlytaisanapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PhongFragment extends Fragment {

    DatabaseHandler db;
    FrameLayout frameLayout;
    List<Phong> phongList = new ArrayList<>();
    ListView listView;
    FloatingActionButton floatButton;
    ListPhongAdapter listAdapter;

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
        db = new DatabaseHandler(getContext());
        phongList = db.getAllPhong();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);

        //listview
        listView = view.findViewById(R.id.listview);
        listAdapter = new ListPhongAdapter(phongList, getContext());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int roomId = phongList.get(i).getMa();
                goToDetail(roomId);
            }
        });
    }

    public void goToDetail(int id) {
        Intent intent = new Intent(getActivity(), ChiTietPhongActivity.class);
        intent.putExtra("roomId", id);
        startActivityForResult(intent, 2);
    }

    private void handleView() {
        if (phongList.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else
            frameLayout.setVisibility(View.INVISIBLE);
    }


    private void handleEvent() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietPhongActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            phongList.clear();
            phongList = db.getAllPhong();
            listAdapter = new ListPhongAdapter(phongList, getContext());
            listView.setAdapter(listAdapter);
        }
    }
}