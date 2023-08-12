/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import model.Response;
import model.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.CongDung;
import model.Thuoc;

/**
 *
 * @author Admin
 */
public class ServerControl {
    private int port;
    private String host;
    private DAO dao;
    private ServerSocket myServer;
    private ArrayList<Socket> list;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public ServerControl() {
        port = 8888;
        host = "localhost";
        dao = new DAO();
        openSocket();
        while(true){
            try {
                Socket s = myServer.accept();
                System.out.println("New req: " + s + " " + LocalDateTime.now().toString());
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
                listeningRequest();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
    
    public void listeningRequest() throws IOException, ClassNotFoundException{
        while(true){
            System.out.println("wait");
            Request req = (Request) ois.readObject();
            System.out.println("req: " + req);
            switch(req.getCode()){
                case 1:
                    ArrayList<CongDung> listCD = (ArrayList<CongDung>) dao.getAllCongDung();
                    oos.writeObject(new Response(1, listCD));
                    oos.flush();
                    break;
                case 2:
                    ArrayList<Thuoc> listThuoc = (ArrayList<Thuoc>) dao.getAllThuoc();
                    oos.writeObject(new Response(2, listThuoc));
                    oos.flush();
                    break;
                case 3:
                    String res = dao.themCD((CongDung)req.getO());
                    oos.writeObject(new Response(3, res));
                    oos.flush();
                    break;
                case 4:
                    String res1 = dao.themThuoc((Thuoc)req.getO());
                    oos.writeObject(new Response(4, res1));
                    oos.flush();
                    break;
                case 5:
                    String res2 = dao.suaCD((CongDung)req.getO());
                    oos.writeObject(new Response(5, res2));
                    oos.flush();
                    break;
                case 6:
                    String res3 = dao.suaThuoc((Thuoc)req.getO());
                    oos.writeObject(new Response(6, res3));
                    oos.flush();
                    break;
                case 7:
                    String res4 = dao.xoaCD((CongDung)req.getO());
                    oos.writeObject(new Response(7, res4));
                    oos.flush();
                    break;
                case 8:
                    String res5 = dao.xoaThuoc((Thuoc)req.getO());
                    oos.writeObject(new Response(8, res5));
                    oos.flush();
                    break;
                case 9:
                    ArrayList<CongDung> listTimCD = (ArrayList<CongDung>) dao.getCongDungByKey((CongDung) req.getO());
                    oos.writeObject(new Response(9, listTimCD));
                    oos.flush();
                    break;
                case 10:
                    ArrayList<Thuoc> listTimThuocTheoTen = (ArrayList<Thuoc>) dao.getThuocByKey((String)req.getO());
                    oos.writeObject(new Response(10, listTimThuocTheoTen));
                    oos.flush();
                    break;
                case 11:
                    ArrayList<Thuoc> listTimThuocTheoDang = (ArrayList<Thuoc>) dao.getThuocByDang((String)req.getO());
                    oos.writeObject(new Response(11, listTimThuocTheoDang));
                    oos.flush();
                    break;
                case 12:
                    ArrayList<Thuoc> listTimThuocTheoCD = (ArrayList<Thuoc>) dao.getThuocByCD((String)req.getO());
                    oos.writeObject(new Response(12, listTimThuocTheoCD));
                    oos.flush();
                    break;    
                case 13:
                    ArrayList<Thuoc> listTimThuocTheoGia = (ArrayList<Thuoc>) dao.getThuocByGia((List<Integer>) req.getO());
                    oos.writeObject(new Response(13, listTimThuocTheoGia));
                    oos.flush();
                    break; 
            }
        }
    }
    
    public void openSocket(){
        try {
            myServer = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
