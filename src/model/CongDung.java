package model;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class CongDung implements Serializable{
    private int maCongDung;
    private String tenCongDung, mtCongDung;

    public CongDung() {
    }

    public CongDung(int maCongDung, String tenCongDung, String mtCongDung) {
        this.maCongDung = maCongDung;
        this.tenCongDung = tenCongDung;
        this.mtCongDung = mtCongDung;
    }

    public int getMaCongDung() {
        return maCongDung;
    }

    public void setMaCongDung(int maCongDung) {
        this.maCongDung = maCongDung;
    }

    public String getTenCongDung() {
        return tenCongDung;
    }

    public void setTenCongDung(String tenCongDung) {
        this.tenCongDung = tenCongDung;
    }

    public String getMtCongDung() {
        return mtCongDung;
    }

    public void setMtCongDung(String mtCongDung) {
        this.mtCongDung = mtCongDung;
    }

    @Override
    public String toString() {
        return maCongDung + " " + tenCongDung + " " + mtCongDung;
    }
    public Object[] toObject(){
        return new Object[]{
            maCongDung, tenCongDung, mtCongDung
        };
    }
}
