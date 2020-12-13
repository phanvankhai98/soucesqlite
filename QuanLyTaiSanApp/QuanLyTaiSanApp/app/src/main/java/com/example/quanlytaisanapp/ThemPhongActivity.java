package com.example.quanlytaisanapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlytaisanapp.database.DatabaseHandler;
import com.example.quanlytaisanapp.model.Phong;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ThemPhongActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText edtRoomName, edtRoomId, edtRoomDes;
    private TextInputLayout tilRoomName, tilRoomId, tilRoomDes;
    private String roomName, roomDes;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phong);
        init();
    }

    private void init() {
        edtRoomName = findViewById(R.id.edt_name_room);
        edtRoomDes = findViewById(R.id.edt_des_room);
        tilRoomName = findViewById(R.id.til_name_room);
        tilRoomDes = findViewById(R.id.til_des_room);
        toolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btn_submit);
        setToolbar();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRoom();
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Thêm Phòng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addRoom() {
        getDataInput();
        setDefaultError();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(roomName)) {
            tilRoomName.setError("Không để trống");
            focusView = tilRoomName;
            cancel = true;
        } else if (TextUtils.isEmpty(roomName)) {
            tilRoomDes.setError("Không để trống");
            focusView = tilRoomDes;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Phong phong = new Phong(roomName, roomDes);
            DatabaseHandler databaseHelper = new DatabaseHandler(getBaseContext());
            databaseHelper.addPhong(phong);
            Toast.makeText(getBaseContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void getDataInput() {
        roomName = edtRoomName.getText().toString().trim();
        roomDes = edtRoomDes.getText().toString().trim();
    }

    public void setDefaultError() {
        tilRoomDes.setError(null);
        tilRoomName.setError(null);
    }

}