package com.example.quanlytaisanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.quanlytaisanapp.database.DatabaseHandler;
import com.example.quanlytaisanapp.fragment.PhongFragment;
import com.example.quanlytaisanapp.fragment.TaiSanFragment;
import com.example.quanlytaisanapp.model.Phong;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
//     db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleView();
        handleEvent();

    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }


    private void handleView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_view,new PhongFragment(), null)
                .commit();
    }

    private void handleEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        replaceFragment(new PhongFragment());
                        break;
                    case R.id.page_2:
                        replaceFragment(new TaiSanFragment());
                        break;
                }
                return true;
            }
        });
    }



    void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, fragment, null)
                .commit();
    }
}