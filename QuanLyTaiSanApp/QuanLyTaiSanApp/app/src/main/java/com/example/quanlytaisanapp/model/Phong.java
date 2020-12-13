package com.example.quanlytaisanapp.model;

import java.io.Serializable;

public class Phong implements Serializable {
    private int ma;
    private String ten;
    private String moTa;

    public Phong(int ma, String ten, String moTa) {
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
    }

    public Phong(String ten, String moTa) {
        this.ten = ten;
        this.moTa = moTa;
    }

    public Phong() {
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
