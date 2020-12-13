package com.example.quanlytaisanapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quanlytaisanapp.model.Phong;
import com.example.quanlytaisanapp.model.TaiSan;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quanlytaisandb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PHONG = "phong";
    private static final String PHONG_ID = "id";
    private static final String PHONG_TEN = "ten";
    private static final String PHONG_MO_TA = "mo_ta";

    private static final String TABLE_TAI_SAN = "tai_san";
    private static final String TAI_SAN_ID = "id";
    private static final String TAI_SAN_TEN = "ten";
    private static final String TAI_SAN_VI_TRI = "vi_tri";
    private static final String TAI_SAN_LOAI = "loai";
    private static final String TAI_SAN_GIA_TRI = "gia_tri";


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_phong = String.format("" +
                        "CREATE TABLE \"%s\" (\n" +
                        "\t\"%s\"\tINTEGER ,\n" +
                        "\t\"%s\"\tTEXT,\n" +
                        "\t\"%s\"\tTEXT,\n" +
                        "\tPRIMARY KEY(\"%s\" AUTOINCREMENT)\n" +
                        ")",
                TABLE_PHONG, PHONG_ID, PHONG_TEN, PHONG_MO_TA, PHONG_ID);
        String create_table_tai_san = String.format(
                "CREATE TABLE \"%s\" (\n" +
                        "\t\"%s\"\tINTEGER ,\n" +
                        "\t\"%s\"\tTEXT,\n" +
                        "\t\"%s\"\tTEXT,\n" +
                        "\t\"%s\"\tINTEGER,\n" +
                        "\t\"%s\"\tREAL,\n" +
                        "\tPRIMARY KEY(\"%s\" AUTOINCREMENT )" +
                        " FOREIGN KEY (" + TAI_SAN_VI_TRI + ") REFERENCES " + TABLE_PHONG + "(" + PHONG_ID + ")\n" +
                        ");",
                TABLE_TAI_SAN,
                TAI_SAN_ID,
                TAI_SAN_TEN,
                TAI_SAN_LOAI,
                TAI_SAN_VI_TRI,
                TAI_SAN_GIA_TRI,
                TAI_SAN_ID);
        db.execSQL(create_table_phong);
        db.execSQL(create_table_tai_san);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_phong_table = String.format("DROP TABLE IF EXISTS %s", TABLE_PHONG);
        String drop_tai_san_table = String.format("DROP TABLE IF EXISTS %s", TABLE_TAI_SAN);
        db.execSQL(drop_phong_table);
        db.execSQL(drop_tai_san_table);
        onCreate(db);
    }

    //phòng ----------------------------------
    public void addPhong(Phong phong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PHONG_TEN, phong.getTen());
        values.put(PHONG_MO_TA, phong.getMoTa());

        db.insert(TABLE_PHONG, null, values);
        db.close();
    }

    public List<Phong> getAllPhong() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PHONG;
        List<Phong> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Phong nhom = new Phong(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            data.add(nhom);
            cursor.moveToNext();
        }
        db.close();
        return data;
    }

    public Phong getPhongById(String phongId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PHONG + " WHERE " + PHONG_ID + " = " + phongId;
        List<Phong> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Phong nhom = new Phong(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            data.add(nhom);
            cursor.moveToNext();
        }
        db.close();
        return data.get(0);
    }


    public boolean updatePhong(Phong phong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PHONG_ID, phong.getMa());
        values.put(PHONG_TEN, phong.getTen());
        values.put(PHONG_MO_TA, phong.getMoTa());

        int kq = db.update(TABLE_PHONG, values, PHONG_ID + "=" + phong.getMa(), null);
        db.close();
        return kq > 0;
    }

    public boolean deletePhong(int idphong) {
        SQLiteDatabase db = this.getWritableDatabase();
        int kq = db.delete(TABLE_PHONG, PHONG_ID + "=" + idphong, null);
        db.close();
        return kq > 0;
    }


    //tai sản ----------------------------------
    public void addTaiSan(TaiSan taiSan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TAI_SAN_TEN, taiSan.getTen());
        values.put(TAI_SAN_LOAI, taiSan.getLoai());
        values.put(TAI_SAN_VI_TRI, taiSan.getViTri());
        values.put(TAI_SAN_GIA_TRI, taiSan.getGiaTri());

        db.insert(TABLE_TAI_SAN, null, values);
        db.close();
    }

    public List<TaiSan> getAllTaiSan() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TAI_SAN;
        List<TaiSan> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return getListTaiSan(cursor);
    }

    public List<TaiSan> getTaiSanTrongPhong(int idPhong) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM  " + TABLE_TAI_SAN + " WHERE "
                + TAI_SAN_VI_TRI + " = " + idPhong;

        Cursor cursor = db.rawQuery(query, null);
        return getListTaiSan(cursor);
    }

    public List<TaiSan> getTaiSanHon10Cu(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM  " + TABLE_TAI_SAN + " WHERE "
                + TAI_SAN_GIA_TRI + " >= " + 10000000 +" AND "+TAI_SAN_VI_TRI +" = " + id ;
        Cursor cursor = db.rawQuery(query, null);
        return getListTaiSan(cursor);
    }


    public boolean deleteTaiSan(int idTaiSan) {
        SQLiteDatabase db = this.getWritableDatabase();
        int kq = db.delete(TABLE_TAI_SAN, TAI_SAN_ID + "=" + idTaiSan, null);
        db.close();
        return kq > 0;
    }

    public boolean updateTaiSan(TaiSan taiSan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TAI_SAN_ID, taiSan.getMa());
        values.put(TAI_SAN_TEN, taiSan.getTen());
        values.put(TAI_SAN_LOAI, taiSan.getLoai());
        values.put(TAI_SAN_VI_TRI, taiSan.getViTri());
        values.put(TAI_SAN_GIA_TRI, taiSan.getGiaTri());

        int kq = db.update(TABLE_TAI_SAN, values, TAI_SAN_ID + "=" + taiSan.getMa(), null);
        db.close();
        return kq > 0;
    }

    public boolean updateTaiSanTrongPhong(TaiSan taiSan, int idPhong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TAI_SAN_ID, taiSan.getMa());
        values.put(TAI_SAN_TEN, taiSan.getTen());
        values.put(TAI_SAN_LOAI, taiSan.getLoai());
        values.put(TAI_SAN_VI_TRI, idPhong);
        values.put(TAI_SAN_GIA_TRI, taiSan.getGiaTri());

        int kq = db.update(TABLE_PHONG, values, PHONG_ID + "=" + taiSan.getMa(), null);
        db.close();
        return kq > 0;
    }

    public List<TaiSan> getListTaiSan(Cursor cursor) {
        List<TaiSan> data = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TaiSan taiSan = new TaiSan(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4)
            );
            data.add(taiSan);
            cursor.moveToNext();
        }
        return data;
    }
    public TaiSan getTaiSanById(String taiSanID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TAI_SAN + " WHERE " + TAI_SAN_ID + " = " + taiSanID;
        List<TaiSan> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TaiSan taiSan = new TaiSan(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4)
            );
            data.add(taiSan);
            cursor.moveToNext();
        }
        db.close();
        return data.get(0);
    }

}
