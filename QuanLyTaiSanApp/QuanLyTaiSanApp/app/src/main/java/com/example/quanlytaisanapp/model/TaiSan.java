package com.example.quanlytaisanapp.model;

import java.io.Serializable;

public class TaiSan implements Serializable {
    private int ma;
    private String ten;
    private String loai;
    private int viTri;
    private double giaTri;

    public TaiSan(int ma, String ten, String loai, int viTri, double giaTri) {
        this.ma = ma;
        this.ten = ten;
        this.loai = loai;
        this.viTri = viTri;
        this.giaTri = giaTri;
    }

    public TaiSan(String ten, String loai, int viTri, double giaTri) {
        this.ten = ten;
        this.loai = loai;
        this.viTri = viTri;
        this.giaTri = giaTri;
    }

    public TaiSan() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getViTri() {
        return viTri;
    }

    public void setViTri(int viTri) {
        this.viTri = viTri;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }
}
