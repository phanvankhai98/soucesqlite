package com.ptit.quanlysinhvienthuctap.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ptit.quanlysinhvienthuctap.R;
import com.ptit.quanlysinhvienthuctap.database.DatabaseHandler;
import com.ptit.quanlysinhvienthuctap.model.GiangVien;

import java.util.ArrayList;
import java.util.List;


public class GiangVienFragment extends Fragment {

    public GiangVienFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_giang_vien, container, false);
//    }
    DatabaseHandler db;
    FrameLayout frameLayout;
    List<GiangVien> listGiangVien = new ArrayList<>();
    ListView listView;
    FloatingActionButton floatButton;
    ListPhongAdapter giangVienAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_giang_vien, container, false);
        init(view);
        handleView();
        handleEvent();
        return view;
    }

    private void init(View view) {
        db = new DatabaseHandler(getContext());
        listGiangVien = db.getAllGiangVien();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);

        //listview
        listView = view.findViewById(R.id.listview);
        giangVienAdapter = new ListPhongAdapter(listGiangVien, getContext());
        listView.setAdapter(giangVienAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int roomId = listGiangVien.get(i).getId();
                goToDetail(roomId);
            }
        });
    }

    public void goToDetail(int id) {
        Intent intent = new Intent(getActivity(), ChiTietPhongActivity.class);
        intent.putExtra("roomId", id + "");
        startActivityForResult(intent, 2);
    }

    private void handleView() {
        if (listGiangVien.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else
            frameLayout.setVisibility(View.INVISIBLE);
    }


    private void handleEvent() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemPhongActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            listGiangVien.clear();
            listGiangVien = db.getAllGiangVien();
            giangVienAdapter = new ListPhongAdapter(listGiangVien, getContext());
            listView.setAdapter(giangVienAdapter);
        } else if (requestCode == 2) {
            listGiangVien.clear();
            listGiangVien = db.getAllPhong();
            giangVienAdapter = new ListPhongAdapter(listGiangVien, getContext());
            listView.setAdapter(giangVienAdapter);
        }
    }
}