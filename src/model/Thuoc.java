/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Thuoc implements Serializable{
    private int maThuoc, giaThuoc;
    private String tenThuoc, dangThuoc;
    private CongDung cd;

    public Thuoc() {
    }

    public Thuoc(int maThuoc, String tenThuoc, int giaThuoc, String dangThuoc, CongDung cd) {
        this.maThuoc = maThuoc;
        this.giaThuoc = giaThuoc;
        this.tenThuoc = tenThuoc;
        this.dangThuoc = dangThuoc;
        this.cd = cd;
    }

    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public int getGiaThuoc() {
        return giaThuoc;
    }

    public void setGiaThuoc(int giaThuoc) {
        this.giaThuoc = giaThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDangThuoc() {
        return dangThuoc;
    }

    public void setDangThuoc(String dangThuoc) {
        this.dangThuoc = dangThuoc;
    }

    public CongDung getCd() {
        return cd;
    }

    public void setCd(CongDung cd) {
        this.cd = cd;
    }

    @Override
    public String toString() {
        return maThuoc + " " + tenThuoc + " " + giaThuoc + " " + dangThuoc + " " + cd.getTenCongDung();
    }
    public Object[] toObject(){
        return new Object[]{
            maThuoc, tenThuoc, giaThuoc, dangThuoc, cd.getTenCongDung()
        };
    }
}
