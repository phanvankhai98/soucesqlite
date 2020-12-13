package com.example.quanlytaisanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlytaisanapp.database.DatabaseHandler;
import com.example.quanlytaisanapp.model.Phong;
import com.example.quanlytaisanapp.model.TaiSan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ChiTietTaiSanActivity extends AppCompatActivity {
    List<Phong> phongList = new ArrayList<>();
    DatabaseHandler db;
    Spinner spinner;
    String typeEdit,taiSanID;
    private TextInputEditText edtTenTaiSan, edtGiaTri, edtLoaiTaiSan;
    private TextInputLayout tilTenTaiSan, tilGiaTri, tilLoaiTaiSan;
    private String tenTaiSan, loaiTaiSan,giaTri;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tai_san);

        init();
        handleView();
        handleEvent();

    }

    private void handleEvent() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaiSan();
            }
        });
    }

    private void handleView() {
        List<String> tenPhong = new ArrayList<>();
        for (Phong i : phongList) {
            tenPhong.add("Phòng " + i.getTen());
        }
        setSpinner(tenPhong);
    }

    private void init() {
        db = new DatabaseHandler(ChiTietTaiSanActivity.this);
        typeEdit = getIntent().getStringExtra("type_edit");
        taiSanID = getIntent().getStringExtra("tai_san_id");
        phongList = db.getAllPhong();
        spinner = findViewById(R.id.spinner);
        edtTenTaiSan = findViewById(R.id.edt_ten_tai_san);
        edtLoaiTaiSan = findViewById(R.id.edt_loai_tai_san);
        edtGiaTri = findViewById(R.id.edt_gia_tri);
        tilTenTaiSan = findViewById(R.id.til_ten_tai_san);
        tilLoaiTaiSan = findViewById(R.id.til_loai_tai_san);
        tilGiaTri = findViewById(R.id.til_gia_tri);
        btnSubmit = findViewById(R.id.btn_submit);
        if(typeEdit=="1"){
            btnSubmit.setText("Thêm");
        }
        if(taiSanID!= null){

        }
    }
    public void getDataInput() {
        tenTaiSan = edtTenTaiSan.getText().toString().trim();
        loaiTaiSan = edtLoaiTaiSan.getText().toString().trim();
        giaTri = edtGiaTri.getText().toString().trim();

    }
    public void setDefaultError() {
        tilTenTaiSan.setError(null);
        tilLoaiTaiSan.setError(null);
        tilGiaTri.setError(null);
    }
    public void addTaiSan() {
        getDataInput();
        setDefaultError();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(tenTaiSan)) {
            tilTenTaiSan.setError("Không để trống");
            focusView = tilTenTaiSan;
            cancel = true;
        } else if (TextUtils.isEmpty(loaiTaiSan)) {
            tilLoaiTaiSan.setError("Không để trống");
            focusView = tilLoaiTaiSan;
            cancel = true;
        }else if(TextUtils.isEmpty(giaTri)){
            tilGiaTri.setError("Không để trống");
            focusView = tilGiaTri;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            TaiSan taiSan = new TaiSan(
                    tenTaiSan,
                    loaiTaiSan,
                    phongList.get(spinner.getSelectedItemPosition()).getMa(),
                    Double.parseDouble(giaTri));
            DatabaseHandler databaseHelper = new DatabaseHandler(getBaseContext());
            databaseHelper.addTaiSan(taiSan);
            Toast.makeText(getBaseContext(), "Thêm tài sản thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    void setSpinner(List<String> strings) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapter);
    }
}