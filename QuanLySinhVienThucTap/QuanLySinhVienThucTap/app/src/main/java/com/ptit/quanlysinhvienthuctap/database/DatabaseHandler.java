package com.ptit.quanlysinhvienthuctap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ptit.quanlysinhvienthuctap.model.GiangVien;
import com.ptit.quanlysinhvienthuctap.model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quanlysinhviendb";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    //table student
    public static final String STUDENT_TABLE_NAME = "STUDENT";
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_NAME = "student_name";
    private static final String STUDENT_BOD = "student_bod";
    private static final String STUDENT_ADDRESS = "student_address";
    private static final String STUDENT_TEACHER_ID = "student_teacher_id";

    //table class
    public static final String CLASS_TABLE_NAME = "class";
    private static final String CLASS_ID = "class_id";
    private static final String CLASS_NAME = "class_name";
    private static final String CLASS_DESCRIPTION = "class_description";

    //table teacher
    public static final String TEACHER_TABLE_NAME = "teacher";
    private static final String TEACHER_ID = "teacher_id";
    private static final String TEACHER_NAME = "teacher_name";
    private static final String TEACHER_BOD = "teacher_bod";
    private static final String TEACHER_EXP = "teacher_exp";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //createTableStudent
        String createTableStudent = "CREATE TABLE " + STUDENT_TABLE_NAME + " (" +
                STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_NAME + " TEXT, " +
                STUDENT_BOD + " TEXT, " +
                STUDENT_ADDRESS + " TEXT, " +
                STUDENT_TEACHER_ID + "INTEGER, " +
                " FOREIGN KEY ( " + STUDENT_TEACHER_ID + ") REFERENCES " + TEACHER_TABLE_NAME + " ( " + TEACHER_ID + " ) ON DELETE CASCADE, " +
                " ) ";
        db.execSQL(createTableStudent);

        //create table book
        String createTableClass = "CREATE TABLE " + CLASS_TABLE_NAME + " (" +
                CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLASS_NAME + " TEXT, " +
                CLASS_DESCRIPTION + " TEXT " +
                ")";

        //createTableTeacher
        String createTableTeacher = "CREATE TABLE " + TEACHER_TABLE_NAME + " (" +
                TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TEACHER_NAME + " TEXT, " +
                TEACHER_BOD + " TEXT, " +
                TEACHER_BOD + " TEXT, " +
                TEACHER_EXP + " INTEGER " +

                ")";
        db.execSQL(createTableTeacher);
        Toast.makeText(context, "Create Database successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEACHER_TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();
    }

    //sinh vien ----------------------------------
    public void addSinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, sinhVien.getName());
        values.put(STUDENT_BOD, sinhVien.getName());
        values.put(STUDENT_ADDRESS, sinhVien.getName());
        db.insert(STUDENT_TABLE_NAME, null, values);
        db.close();
    }

    public List<SinhVien> getAllSinhVien() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + STUDENT_TABLE_NAME;
        List<SinhVien> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SinhVien nhom = new SinhVien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            data.add(nhom);
            cursor.moveToNext();
        }
        db.close();
        return data;
    }

    public SinhVien getSinhVienById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + STUDENT_ID + " = " + id;
        List<SinhVien> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SinhVien sinhVien = new SinhVien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            data.add(sinhVien);
            cursor.moveToNext();
        }
        db.close();
        return data.get(0);
    }


    public boolean updateSinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STUDENT_ID, sinhVien.getId());
        values.put(STUDENT_NAME, sinhVien.getName());
        values.put(STUDENT_BOD, sinhVien.getName());
        values.put(STUDENT_ADDRESS, sinhVien.getName());

        int kq = db.update(STUDENT_TABLE_NAME, values, STUDENT_ID + " = " + sinhVien.getId(), null);
        db.close();
        return kq > 0;
    }
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int kq = db.delete(STUDENT_TABLE_NAME, STUDENT_ID + "=" + id, null);
        db.close();
        return kq > 0;
    }

    //Giang Vien ----------------------------------
    public void addGiangVien(GiangVien giangVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEACHER_NAME, giangVien.getName());
        values.put(TEACHER_BOD, giangVien.getName());
        values.put(TEACHER_EXP, giangVien.getExp());
        db.insert(STUDENT_TABLE_NAME, null, values);
        db.close();
    }

    public List<GiangVien> getAllGiangVien() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TEACHER_TABLE_NAME;
        List<GiangVien> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GiangVien giangVien = new GiangVien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3)
            );
            data.add(giangVien);
            cursor.moveToNext();
        }
        db.close();
        return data;
    }

    public GiangVien getGiangVienById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TEACHER_TABLE_NAME + " WHERE " + TEACHER_ID + " = " + id;
        List<GiangVien> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GiangVien giangVien = new GiangVien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3)
            );
            data.add(giangVien);
            cursor.moveToNext();
        }
        db.close();
        return data.get(0);
    }


    public boolean updateGiangVien(GiangVien giangVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TEACHER_ID, giangVien.getId());
        values.put(TEACHER_NAME, giangVien.getName());
        values.put(TEACHER_BOD, giangVien.getName());
        values.put(TEACHER_EXP, giangVien.getExp());

        int kq = db.update(TEACHER_TABLE_NAME, values, TEACHER_ID + "=" + giangVien.getId(), null);
        db.close();
        return kq > 0;
    }

}
