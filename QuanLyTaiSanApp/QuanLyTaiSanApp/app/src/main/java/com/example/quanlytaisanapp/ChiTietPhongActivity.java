package com.example.quanlytaisanapp;

import android.content.Intent;
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

public class ChiTietPhongActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText edtRoomName, edtRoomId, edtRoomDes;
    private TextInputLayout tilRoomName, tilRoomId, tilRoomDes;
    String type = "";
    int roomId;
    private String roomName, roomDes;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phong);
        roomId = getRoomId();
        init();

        getSupportActionBar().setTitle("Chi tiết Phòng");
        btnSubmit.setText("Cập nhật phòng");
        edtRoomId.setText(roomId);
    }

    private void init() {
        edtRoomId = findViewById(R.id.edt_phong_id);
        edtRoomId.setFocusable(false);
        edtRoomId.setEnabled(false);
        edtRoomName = findViewById(R.id.edt_name_room);
        edtRoomDes = findViewById(R.id.edt_des_room);
        tilRoomId = findViewById(R.id.til_phong_id);
        tilRoomName = findViewById(R.id.til_name_room);
        tilRoomDes = findViewById(R.id.til_des_room);
        toolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btn_submit);
        setToolbar();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRoom();
            }
        });
    }

    public int getRoomId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("roomId");
        return id;
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void editRoom() {
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
        tilRoomId.setError(null);
        tilRoomName.setError(null);
    }

}