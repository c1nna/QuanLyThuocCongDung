/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.CongDung;
import model.Thuoc;


/**
 *
 * @author Admin
 */
public class DAO{
    public static Connection connection;

    public DAO()  {
        try{
            String url = "jdbc:mysql://localhost:3306/quanlythuoc";
            String username = "root";
            String password = "Quelong.0468";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex);
        }
    }
    public List<CongDung> getAllCongDung(){
        List<CongDung> list = new ArrayList<>();
        String sql = "select * from congdung order by ma";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                CongDung cd = new CongDung(rs.getInt("ma"), 
                        rs.getString("ten_cong_dung"),
                        rs.getString("mo_ta"));
                list.add(cd);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
    public CongDung getCongDungTheoTen(String ten){
        String sql = "select * from congdung where ten_cong_dung = ?";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, ten);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                CongDung cd = new CongDung(rs.getInt("ma"),
                            rs.getString("ten_cong_dung"),
                            rs.getString("mo_ta"));
                return cd;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    public CongDung getCongDungTheoMa(int ma){
        String sql = "select * from congdung where ma = ?";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ma);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                CongDung cd = new CongDung(rs.getInt("ma"),
                            rs.getString("ten_cong_dung"),
                            rs.getString("mo_ta"));
                return cd;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    
    public List<Thuoc> getAllThuoc(){
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT thuoc.ma, thuoc.ten, thuoc.gia_thuoc, thuoc.dang_thuoc, congdung.ten_cong_dung "
                + "FROM thuoc INNER JOIN congdung ON thuoc.cong_dung_ma = congdung.ma order by thuoc.ma";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getInt("ma"));
                thuoc.setTenThuoc(rs.getString("ten"));
                thuoc.setGiaThuoc(rs.getInt("gia_thuoc"));
                thuoc.setDangThuoc(rs.getString("dang_thuoc"));
                CongDung cd = getCongDungTheoTen(rs.getString("ten_cong_dung"));
                thuoc.setCd(cd);
                list.add(thuoc);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
    public String themCD(CongDung cd){
        String sql = "insert into congdung values(?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cd.getMaCongDung());
            ps.setString(2, cd.getTenCongDung());
            ps.setString(3, cd.getMtCongDung());
            ps.executeUpdate();
            return "ok";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }
    
    public String themThuoc(Thuoc thuoc){
        String sql = "insert into thuoc value(?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, thuoc.getMaThuoc());
            ps.setString(2, thuoc.getTenThuoc());
            ps.setInt(3, thuoc.getGiaThuoc());
            ps.setString(4, thuoc.getDangThuoc());
            ps.setInt(5, thuoc.getCd().getMaCongDung());
            ps.executeUpdate();
            return "ok";
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return "failed";
    }
    
    public String suaCD(CongDung cd){
        String sql = "update congdung set ten_cong_dung = ?, mo_ta = ? where ma = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cd.getTenCongDung());
            ps.setString(2, cd.getMtCongDung());
            ps.setInt(3, cd.getMaCongDung());
            ps.executeUpdate();
            return "ok";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }
    public String suaThuoc(Thuoc thuoc){
        String sql = "UPDATE thuoc SET ten = ?, gia_thuoc = ?, dang_thuoc = ?, cong_dung_ma = ? WHERE ma = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, thuoc.getTenThuoc());
            ps.setInt(2, thuoc.getGiaThuoc());
            ps.setString(3, thuoc.getDangThuoc());
            ps.setInt(4, thuoc.getCd().getMaCongDung());
            ps.setInt(5, thuoc.getMaThuoc());
            ps.executeUpdate();
            return "ok";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }
    
    public String xoaCD(CongDung cd){
        String sql = "DELETE FROM congdung WHERE ma = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cd.getMaCongDung());
            ps.executeUpdate();
            return "ok";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }
    
    public String xoaThuoc(Thuoc thuoc){
        String sql = "delete from thuoc where ma = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, thuoc.getMaThuoc());
            ps.executeUpdate();
            return "ok";
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return "failed";
    }
    
    public List<CongDung> getCongDungByKey(CongDung cd){
        List<CongDung> list = new ArrayList<>();
        String sql = "SELECT * FROM congdung c WHERE c.ten_cong_dung LIKE '%" + cd.getTenCongDung() +"%'";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                CongDung e = new CongDung(rs.getInt("ma"), 
                        rs.getString("ten_cong_dung"),
                        rs.getString("mo_ta"));
                list.add(e);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
    
    public List<Thuoc> getThuocByKey(String key){
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM thuoc t WHERE t.ten like '%" + key + "%'";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Thuoc e = new Thuoc();
                e.setMaThuoc(rs.getInt("ma"));
                e.setTenThuoc(rs.getString("ten"));
                e.setGiaThuoc(rs.getInt("gia_thuoc"));
                e.setDangThuoc(rs.getString("dang_thuoc"));
                CongDung cd = getCongDungTheoMa(rs.getInt("cong_dung_ma"));
                e.setCd(cd);
                list.add(e);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
    
    public List<Thuoc> getThuocByDang(String key){
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM thuoc t WHERE t.dang_thuoc = '" + key + "'";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Thuoc e = new Thuoc();
                e.setMaThuoc(rs.getInt("ma"));
                e.setTenThuoc(rs.getString("ten"));
                e.setGiaThuoc(rs.getInt("gia_thuoc"));
                e.setDangThuoc(rs.getString("dang_thuoc"));
                CongDung cd = getCongDungTheoMa(rs.getInt("cong_dung_ma"));
                e.setCd(cd);
                list.add(e);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
    
    public List<Thuoc> getThuocByCD(String key){
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM thuoc t WHERE t.cong_dung_ma = '" + key + "'";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Thuoc e = new Thuoc();
                e.setMaThuoc(rs.getInt("ma"));
                e.setTenThuoc(rs.getString("ten"));
                e.setGiaThuoc(rs.getInt("gia_thuoc"));
                e.setDangThuoc(rs.getString("dang_thuoc"));
                CongDung cd = getCongDungTheoMa(rs.getInt("cong_dung_ma"));
                e.setCd(cd);
                list.add(e);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
    
    public List<Thuoc> getThuocByGia(List<Integer> gia){
        List<Thuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM thuoc t WHERE t.gia_thuoc >= " + gia.get(0) 
                + " AND t.gia_thuoc <= " + gia.get(1);
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Thuoc e = new Thuoc();
                e.setMaThuoc(rs.getInt("ma"));
                e.setTenThuoc(rs.getString("ten"));
                e.setGiaThuoc(rs.getInt("gia_thuoc"));
                e.setDangThuoc(rs.getString("dang_thuoc"));
                CongDung cd = getCongDungTheoMa(rs.getInt("cong_dung_ma"));
                e.setCd(cd);
                list.add(e);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return list;
    }
//    public List<CongDung> getTimCD(CongDung cd) {
//        List<CongDung> list = new ArrayList<>();
//        String sql = "select * from congdung where ma = '" + cd.getMaCongDung() + "' or ten_cong_dung = '" 
//                + cd.getTenCongDung() + "' or mo_ta = '" + cd.getMtCongDung() + "'";
//        try{
//            PreparedStatement st = connection.prepareStatement(sql);
//            ResultSet rs = st.executeQuery();
//            while(rs.next()){
//                CongDung cd1 = new CongDung();
//                cd1.setMaCongDung(rs.getInt("ma"));
//                cd1.setTenCongDung(rs.getString("ten_cong_dung"));
//                cd1.setMtCongDung(rs.getString("mo_ta"));
//                list.add(cd1);
//            }
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }
//        return list;
//    }
//    public static void main(String[] args) {
//        new DAO();
//        Scanner sc = new Scanner(System.in);
//        CongDung cd = new CongDung(Integer.parseInt(sc.nextLine()), sc.nextLine(), sc.nextLine());
//        List<CongDung> list = getTimCD(cd);
//        for(CongDung c : list){
//            System.out.println(c.toString());
//        }
//        
//    }
}
