package com.example.quanlytaisanapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    List<Phong> listPhong;

    ListView listView;
    FloatingActionButton floatButton;
    ListTaiSanAdapter listAdapter;
    Spinner spPhong, spTaiSan;
    private String[] item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_san, container, false);
        init(view);
        handleView();
        handleEvent();
        //spinner phong
        setDataSpinnerPhong();
        //spinner tai san
        setDataSpinnerTaiSan();
        return view;
    }

    private void handleView() {
        if (taiSanList.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else
            frameLayout.setVisibility(View.INVISIBLE);
    }

    private void handleEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int taiSanID = taiSanList.get(i).getMa();
                goToDetail(taiSanID);
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietTaiSanActivity.class);
                intent.putExtra("type_edit", "1");
                startActivityForResult(intent, 3);
            }
        });
    }

    private void init(View view) {
        listView = view.findViewById(R.id.listview);
        db = new DatabaseHandler(getContext());
        taiSanList = db.getAllTaiSan();
        listPhong = db.getAllPhong();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);
        spPhong = view.findViewById(R.id.spinner_phong);
        spTaiSan = view.findViewById(R.id.spinner_taisan);
        item = getResources().getStringArray(R.array.taiSanType);
        //listview
        listView = view.findViewById(R.id.listview);
        listAdapter = new ListTaiSanAdapter(taiSanList, getContext(), listPhong);
        listView.setAdapter(listAdapter);


    }

    public void goToDetail(int id) {
        Intent intent = new Intent(getActivity(), ChiTietTaiSanActivity.class);
        intent.putExtra("tai_san_id", id + "");
        startActivityForResult(intent, 4);
    }

    public void setDataSpinnerPhong() {
        listPhong = db.getAllPhong();
        List<String> listnamePhong = new ArrayList<>();
        listnamePhong.add("Tất cả");
        for (Phong phong : listPhong) {
            listnamePhong.add(phong.getTen());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listnamePhong);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spPhong.setAdapter(adapter);
        this.spPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    listAdapter.updateReceiptsList(db.getAllTaiSan());
                } else {
                    Phong phong = listPhong.get(position - 1);
                    listAdapter.updateReceiptsList(db.getTaiSanTrongPhong(phong.getMa()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> adapterTaiSan = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listnamePhong);
        adapterTaiSan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void setDataSpinnerTaiSan() {
        //handle tai san filter
        ArrayAdapter<String> adapterTaiSan = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, item);
        adapterTaiSan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spTaiSan.setAdapter(adapterTaiSan);
        this.spTaiSan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = item[position];
                if (name.equals("Lớn hơn 10 triệu")) {
                    listAdapter.updateReceiptsList(db.getTaiSanHon10Cu(listPhong.get(spPhong.getSelectedItemPosition() > 0 ?
                            spPhong.getSelectedItemPosition()- 1 : 0).getMa()));
                } else {
                    //get all
                    listAdapter.updateReceiptsList(db.getAllTaiSan());
                    System.out.println("name: " + name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 || requestCode == 4) {
            List<TaiSan> list = db.getAllTaiSan();
            listAdapter.updateReceiptsList(list);
            if(list.isEmpty()){
                frameLayout.setVisibility(View.VISIBLE);
            }else frameLayout.setVisibility(View.INVISIBLE);
        }
    }
}
