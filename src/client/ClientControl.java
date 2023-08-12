/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CongDung;
import model.Thuoc;
import model.Request;
import model.Response;

/**
 *
 * @author Admin
 */
public class ClientControl extends Thread {
    private int port;
    private String host;
    private Socket mySocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Map<Integer , Object> getList;
    private boolean connected;
    private Thuoc thuoc;
    private CongDung cd;
    private Map<Integer, ArrayList<?>> mp = new HashMap<>();
//    private CustomEventListener event;
    public ClientControl() {    //CustomEventListener event;
        host = "localhost";
        port = 8888;
        getList = new HashMap<>();
//        this.event = event;
    }
    
    @Override
    public void run(){
        try{
        mySocket = new Socket(host, port);
        System.out.println(mySocket + " " + LocalDateTime.now().toString());
        oos = new ObjectOutputStream(mySocket.getOutputStream());
        ois = new ObjectInputStream(mySocket.getInputStream());
        connected = true;
        while(true){
                Response res = (Response) ois.readObject();
                if (res != null) {
                    switch (res.getCode()) {
                        case 1: 
                            getList.put(res.getCode(), (List<CongDung>) res.getRes());
                            noti();
//                            event.setCongDung((List<CongDung>) res.getRes());
                            break;
                        
                        case 2:
                            getList.put(res.getCode(), (List<Thuoc>) res.getRes());
                            noti();
                            break;
                        
                        case 3:
                            getList.put(res.getCode(), (String) res.getRes());
                            noti();
                            break;
                        case 4:
                            getList.put(res.getCode(), (String) res.getRes());
                            noti();
                            break;
                        case 5:
                            getList.put(res.getCode(), (String) res.getRes());
                            noti();
                            break;
                        case 6:
                            getList.put(res.getCode(), (String) res.getRes());
                            noti();
                            break;
                        case 7:
                            getList.put(res.getCode(), (String) res.getRes());
                            noti();
                            break;
                        case 8:
                            getList.put(res.getCode(), (String) res.getRes());
                            noti();
                            break;
                        case 9:
                            getList.put(res.getCode(), (List<CongDung>) res.getRes());
                            noti();
                            break;
                        case 10:
                            getList.put(res.getCode(), (List<Thuoc>) res.getRes());
                            noti();
                            break;
                        case 11:
                            getList.put(res.getCode(), (List<Thuoc>) res.getRes());
                            noti();
                            break;
                        case 12:
                            getList.put(res.getCode(), (List<Thuoc>) res.getRes());
                            noti();
                            break;
                        case 13:
                            getList.put(res.getCode(), (List<Thuoc>) res.getRes());
                            noti();
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void noti(){
        notifyAll();
    }
    
    public synchronized List<CongDung> getListCD(){
        try {
            System.out.println("send");
            cd = new CongDung();
            oos.writeObject(new Request(1, cd));
            oos.flush();
            System.out.println("have send");
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CongDung> res = (List<CongDung>) getList.get(1);
        getList.remove(1);
        return res;
    }
    public synchronized List<Thuoc> getListThuoc() {
        try{
            thuoc = new Thuoc();
            oos.writeObject(new Request(2, thuoc));
            oos.flush();
            this.wait();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        List<Thuoc> res = (List<Thuoc>) getList.get(2);
        getList.remove(2);
        return res;
    }
    
    public synchronized String themCD(CongDung cd){
        try{
            oos.writeObject(new Request(3, cd));
            oos.flush();
            this.wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String res = (String)getList.get(3);
        getList.remove(3);
        return res;
    }
    
    public synchronized String themThuoc(Thuoc thuoc){
        try{
            oos.writeObject(new Request(4, thuoc));
            oos.flush();
            this.wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String res = (String)getList.get(4);
        getList.remove(4);
        return res;
    }
    
    public synchronized String suaCD(CongDung cd){
        try{
            oos.writeObject(new Request(5, cd));
            oos.flush();
            this.wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String res = (String)getList.get(5);
        getList.remove(5);
        return res;
    }
    
    public synchronized String suaThuoc(Thuoc thuoc){
        try{
            oos.writeObject(new Request(6, thuoc));
            oos.flush();
            this.wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String res = (String)getList.get(6);
        getList.remove(6);
        return res;
    }
    
    public synchronized String xoaCD(CongDung cd){
        try{
            oos.writeObject(new Request(7, cd));
            oos.flush();
            this.wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String res = (String)getList.get(7);
        getList.remove(7);
        return res;
    }
    
    public synchronized String xoaThuoc(Thuoc thuoc){
        try{
            oos.writeObject(new Request(8, thuoc));
            oos.flush();
            this.wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String res = (String)getList.get(8);
        getList.remove(8);
        return res;
    }
    
    public synchronized List<CongDung> getListCDByKey(CongDung cd){
        try {
            oos.writeObject(new Request(9, cd));
            oos.flush();
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CongDung> res = (List<CongDung>) getList.get(9);
        getList.remove(9);
        return res;
    }
    public synchronized List<Thuoc> getListThuocByKey(String key){
        try {
            oos.writeObject(new Request(10, key));
            oos.flush();
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Thuoc> res = (List<Thuoc>) getList.get(10);
        getList.remove(10);
        return res;
    }
    
    public synchronized List<Thuoc> getListThuocByDang(String key){
        try {
            oos.writeObject(new Request(11, key));
            oos.flush();
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Thuoc> res = (List<Thuoc>) getList.get(11);
        getList.remove(11);
        return res;
    }
    
    public synchronized List<Thuoc> getListThuocByCD(String key){
        try {
            oos.writeObject(new Request(12, key));
            oos.flush();
            this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Thuoc> res = (List<Thuoc>) getList.get(12);
        getList.remove(12);
        return res;
    }
    
    public synchronized List<Thuoc> getListThuocByGia(List<Integer> gia) {
        try{
            oos.writeObject(new Request(13, gia));
            oos.flush();
            this.wait();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        List<Thuoc> res = (List<Thuoc>) getList.get(13);
        getList.remove(13);
        return res;
    }
    public boolean isConnected(){
        return connected;
    }
    
    public void closeSocket(){
        try{
            mySocket.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Socket client = new Socket("localhost", 8888);
//        System.out.println(client + " " + LocalDateTime.now().toString());
//        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
//        ArrayList<CongDung> listCD = (ArrayList<CongDung>) ois.readObject();
//        for(CongDung cd : listCD){
//            System.out.println(cd);
//        }
//        ArrayList<Thuoc> listThuoc = (ArrayList<Thuoc>) ois.readObject();
//        for(Thuoc thuoc : listThuoc){
//            System.out.println(thuoc);
//        }
//    }

    

}
