package com.example.quanlytaisanapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlytaisanapp.R;
import com.example.quanlytaisanapp.database.DatabaseHandler;
import com.example.quanlytaisanapp.model.Phong;
import com.example.quanlytaisanapp.model.TaiSan;

import java.util.List;

public class ListTaiSanAdapter extends BaseAdapter {
    List<TaiSan> data;
    List<Phong> listPhong;
    Context context;
    LayoutInflater layoutInflater;

    public ListTaiSanAdapter(List<TaiSan> data, Context context, List<Phong> listPhong) {
        this.data = data;
        this.context = context;
        this.listPhong = listPhong;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_tai_san, null);
            holder = new ViewHolder();
            holder.txtTenTaiSan = convertView.findViewById(R.id.tv_ten);
            holder.txtGia = convertView.findViewById(R.id.tv_gia);
            holder.txtPhong = convertView.findViewById(R.id.tv_phong);
            holder.imgDelete = convertView.findViewById(R.id.img_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TaiSan taiSan = data.get(position);
//
        holder.txtTenTaiSan.setText(taiSan.getTen());
        holder.txtGia.setText(taiSan.getGiaTri() + "đ");
        holder.txtPhong.setText(taiSan.getViTri() == 0
                ? "Chưa có vị trí"
                : ("Phòng " + getTenPhong(taiSan.getViTri())));
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Xác nhận xóa");
                builder1.setMessage("bạn có muốn xóa " + taiSan.getTen() + " ?");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler databaseHelper = new DatabaseHandler(context);
                                databaseHelper.deleteTaiSan(taiSan.getMa());
                                notifyDataSetChanged();
                            }
                        });
                builder1.setPositiveButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return convertView;
    }

    String getTenPhong(int id) {
        for (Phong i : listPhong) {
            if (i.getMa() == id){
                return i.getTen();
            }
        }
        return "";
    }

    public static class ViewHolder {
        TextView txtTenTaiSan, txtGia, txtPhong;
        ImageView imgDelete;
    }

    public void updateReceiptsList(List<TaiSan> newlist) {
        data.clear();
        data.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
