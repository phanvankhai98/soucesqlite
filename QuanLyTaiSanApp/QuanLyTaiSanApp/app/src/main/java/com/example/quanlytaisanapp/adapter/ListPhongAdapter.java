package com.example.quanlytaisanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlytaisanapp.R;
import com.example.quanlytaisanapp.model.Phong;

import java.util.List;

public class ListPhongAdapter extends BaseAdapter {
    List<Phong> data;
    Context context;

    public ListPhongAdapter(List<Phong> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return  data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_phong,parent,false);
        TextView tvTenPhong;

        tvTenPhong = convertView.findViewById(R.id.tv_room);
        Phong phong = data.get(position);

        tvTenPhong.setText(phong.getTen());


        return convertView;
    }
}
