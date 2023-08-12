/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import model.CongDung;

/**
 *
 * @author Admin
 */
public class Request implements Serializable{
    private int code;
    private Object o;
    public Request(int code, Object o) {
        this.code = code;
        this.o = o;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
    
//    public static Request getAllCongDungRequest(){
//        return new Request(1, "");
//    }
//    public static Request getAllThuocRequest(){
//        return new Request(2, "");
//    }
//    public static Request getTimCD(CongDung o){
//        return new Request(3, o);
//    }
}
