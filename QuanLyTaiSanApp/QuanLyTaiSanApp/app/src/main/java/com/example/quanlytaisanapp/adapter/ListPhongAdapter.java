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

import java.util.List;

public class ListPhongAdapter extends BaseAdapter {
    List<Phong> data;
    Context context;
    LayoutInflater layoutInflater;

    public ListPhongAdapter(List<Phong> data, Context context) {
        this.data = data;
        this.context = context;
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
            convertView = layoutInflater.inflate(R.layout.item_phong, null);
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.tv_room);
            holder.imgDelete = convertView.findViewById(R.id.img_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Phong phong = data.get(position);

        holder.txtName.setText(phong.getTen());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Xác nhận xóa");
                builder1.setMessage("bạn có muốn xóa " + phong.getTen() + " ?");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler databaseHelper = new DatabaseHandler(context);
                                databaseHelper.deletePhong(phong.getMa());
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

    public static class ViewHolder {
        TextView txtName;
        ImageView imgDelete;
    }
}
