package com.example.quanlytaisanapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.quanlytaisanapp.ChiTietPhongActivity;
import com.example.quanlytaisanapp.ChiTietTaiSanActivity;
import com.example.quanlytaisanapp.R;
import com.example.quanlytaisanapp.adapter.ListPhongAdapter;
import com.example.quanlytaisanapp.adapter.ListTaiSanAdapter;
import com.example.quanlytaisanapp.database.DatabaseHandler;
import com.example.quanlytaisanapp.model.Phong;
import com.example.quanlytaisanapp.model.TaiSan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaiSanFragment extends Fragment {


    DatabaseHandler db;
    FrameLayout frameLayout;
    List<TaiSan> taiSanList = new ArrayList<>();
    ListView listView;
    FloatingActionButton floatButton;
    ListTaiSanAdapter listAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_san, container, false);
        init(view);
        handleView();
        handleEvent();
        return view;
    }

    private void handleView() {
        if (taiSanList.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else
            frameLayout.setVisibility(View.INVISIBLE);
    }
    private void handleEvent() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietTaiSanActivity.class);
                intent.putExtra("type_room", "1");
                startActivityForResult(intent, 1);
            }
        });
    }
    private void init(View view) {
        listView = view.findViewById(R.id.listview);
        db = new DatabaseHandler(getContext());
        taiSanList = db.getAllTaiSan();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);

        //listview
        listView = view.findViewById(R.id.listview);
        listAdapter = new ListTaiSanAdapter(taiSanList, getContext());
        listView.setAdapter(listAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                int roomId = taiSanList.get(i).getMa();
////                goToDetail(roomId);
//            }
//        });
    }
}