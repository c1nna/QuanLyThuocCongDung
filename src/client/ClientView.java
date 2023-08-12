/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CongDung;
import model.Thuoc;

/**
 *
 * @author Admin
 */
public class ClientView extends javax.swing.JFrame {
    //implements CustomEventListener
    private static List<CongDung> listCD;
    private static List<Thuoc> listThuoc;
    private DefaultTableModel tmCongDung, tmThuoc;
    
    private void docCD(){
        tmCongDung.setRowCount(0);
        cbCongDung.removeAllItems();
        cbCongDung.addItem("0");
        for(CongDung i : listCD){
            tmCongDung.addRow(i.toObject());
            cbCongDung.addItem(i.getMaCongDung() + "");
        }
    }
    private void docThuoc(){
        tmThuoc.setRowCount(0);
        Set<String> se = new HashSet<>();
        for(Thuoc i : listThuoc){
            tmThuoc.addRow(i.toObject());
        }
    }
    public ClientView() {
        initComponents();
        ClientControl ctr = new ClientControl(); //this
        ctr.start();
        while(!ctr.isConnected()){
            System.out.println(ctr.isConnected());
        }
        this.setLocationRelativeTo(null);
        tmCongDung = (DefaultTableModel) tbCongDung.getModel();
        tmThuoc = (DefaultTableModel) tbThuoc.getModel();
        listCD = ctr.getListCD();   //ctr.sendReqGetListCD();
        listThuoc = ctr.getListThuoc();
        
        docCD();
        docThuoc();
   
        btnLoadCD.addActionListener((e) -> {
            listCD = ctr.getListCD();
            docCD();        //ctr.sendReqGetListCD();
            txtMaCD.setText("");
            txtTenCD.setText("");
            txtMoTa.setText("");
        });
        btnLoadThuoc.addActionListener((e) -> {
            listThuoc = ctr.getListThuoc();
            docThuoc();
            txtMaThuoc.setText("");
            txtTenThuoc.setText("");
            txtGiaThuoc.setText("");
            txtGiaThuoc1.setText("");
            cbCongDung.setSelectedIndex(0);
            cbDangThuoc.setSelectedIndex(0);
        });
        
        btnTimCD.addActionListener((e) -> {
            tmCongDung.setRowCount(0);
//            for (CongDung i : listCD) {
//                if (String.valueOf(i.getMaCongDung()).equals(txtMaCD.getText())
//                        || i.getTenCongDung().equalsIgnoreCase(txtTenCD.getText())
//                        || i.getMtCongDung().equalsIgnoreCase(txtMoTa.getText())) {
//                    tmCongDung.addRow(i.toObject());
//                }
//            }
            List<CongDung> listTimCD = ctr.getListCDByKey(
                    new CongDung(1, txtTenCD.getText(), txtMoTa.getText()));
            for(CongDung i : listTimCD){
                tmCongDung.addRow(i.toObject());
            }
        });
        
        btnTimThuoc.addActionListener((e) -> {
            tmThuoc.setRowCount(0);
//            for (Thuoc i : listThuoc) {
//                if (String.valueOf(i.getMaThuoc()).equals(txtMaThuoc.getText())
//                        || i.getTenThuoc().equalsIgnoreCase(txtTenThuoc.getText())
//                        || String.valueOf(i.getGiaThuoc()).equals(txtGiaThuoc.getText())
//                        || i.getDangThuoc().equals(cbDangThuoc.getSelectedItem())
//                        || String.valueOf(i.getCd().getMaCongDung()).equals(cbCongDung.getSelectedItem())) {
//                    tmThuoc.addRow(i.toObject());
//                }
//            }
            List<Thuoc> listTimThuoc = ctr.getListThuocByKey(txtTenThuoc.getText());
            for(Thuoc i : listTimThuoc){
                tmThuoc.addRow(i.toObject());
            }
        });
        
        btnThemCD.addActionListener((e) -> {
            CongDung cd = new CongDung();
            if(!txtMaCD.getText().equals("") && !txtTenCD.getText().equals("") && !txtMoTa.getText().equals("")){
                cd.setMaCongDung(Integer.parseInt(txtMaCD.getText()));
                cd.setTenCongDung(txtTenCD.getText());
                cd.setMtCongDung(txtMoTa.getText());
                
                String res = ctr.themCD(cd);
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Them thanh cong!!!");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Them that bai!!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Them that bai!!!");
            }
        });
        
        btnThemThuoc.addActionListener((e) -> {
            Thuoc thuoc = new Thuoc();
            if(!txtMaThuoc.getText().equals("") && !txtTenThuoc.getText().equals("") && !txtGiaThuoc.getText().equals("")
                    &&!cbDangThuoc.getSelectedItem().equals(" ") && !cbCongDung.getSelectedItem().equals(" ")){
                thuoc.setMaThuoc(Integer.parseInt(txtMaThuoc.getText()));
                thuoc.setTenThuoc(txtTenThuoc.getText());
                thuoc.setGiaThuoc(Integer.parseInt(txtGiaThuoc.getText()));
                thuoc.setDangThuoc(cbDangThuoc.getSelectedItem().toString());
                thuoc.setCd(getCongDung(Integer.parseInt(cbCongDung.getSelectedItem().toString())));
                
                String res = ctr.themThuoc(thuoc);
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Them thanh cong!!!");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Them that bai!!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Them that bai!!!");
            }
        });
        
        btnSuaCD.addActionListener((e) -> {
            CongDung cd = new CongDung();
            if(!txtMaCD.getText().equals("") && !txtTenCD.getText().equals("") && !txtMoTa.getText().equals("")){
                cd.setMaCongDung(Integer.parseInt(txtMaCD.getText()));
                cd.setTenCongDung(txtTenCD.getText());
                cd.setMtCongDung(txtMoTa.getText());
                
                String res = ctr.suaCD(cd);
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Cap nhat thanh cong!!!");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Cap nhat that bai!!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Cap nhat that bai!!!");
            }
        });
        
        btnSuaThuoc.addActionListener((e) -> {
            Thuoc thuoc = new Thuoc();
            if(!txtMaThuoc.getText().equals("") && !txtTenThuoc.getText().equals("") && !txtGiaThuoc.getText().equals("")
                    &&!cbDangThuoc.getSelectedItem().equals(" ") && !cbCongDung.getSelectedItem().equals(" ")){
                thuoc.setMaThuoc(Integer.parseInt(txtMaThuoc.getText()));
                thuoc.setTenThuoc(txtTenThuoc.getText());
                thuoc.setGiaThuoc(Integer.parseInt(txtGiaThuoc.getText()));
                thuoc.setDangThuoc(cbDangThuoc.getSelectedItem().toString());
                thuoc.setCd(getCongDung(Integer.parseInt(cbCongDung.getSelectedItem().toString())));
                
                String res = ctr.suaThuoc(thuoc);
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Cap nhat thanh cong!!!");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Cap nhat that bai!!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Cap nhat that bai!!!");
            }
        });
        
        btnXoaCD.addActionListener((e) -> {
            int resp = JOptionPane.showConfirmDialog(this, "Delete this item???", "Select an Option" ,JOptionPane.YES_NO_CANCEL_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                CongDung cd = new CongDung();
                if (!txtMaCD.getText().equals("") && !txtTenCD.getText().equals("") && !txtMoTa.getText().equals("")) {
                    cd.setMaCongDung(Integer.parseInt(txtMaCD.getText()));
                    cd.setTenCongDung(txtTenCD.getText());
                    cd.setMtCongDung(txtMoTa.getText());

                    String res = ctr.xoaCD(cd);
                    if (res.equals("ok")) {
                        JOptionPane.showMessageDialog(rootPane, "Xoa thanh cong!!!");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Xoa that bai!!!");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Xoa that bai!!!");
                }
            }
            else{
                
            }
        });
        
        btnXoaThuoc.addActionListener((e) -> {
            int resp = JOptionPane.showConfirmDialog(this, "Delete this item???", "Select an Option" ,JOptionPane.YES_NO_CANCEL_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                Thuoc thuoc = new Thuoc();
                if (!txtMaThuoc.getText().equals("") && !txtTenThuoc.getText().equals("") && !txtGiaThuoc.getText().equals("")
                        && !cbDangThuoc.getSelectedItem().equals(" ") && !cbCongDung.getSelectedItem().equals(" ")) {
                    thuoc.setMaThuoc(Integer.parseInt(txtMaThuoc.getText()));
                    thuoc.setTenThuoc(txtTenThuoc.getText());
                    thuoc.setGiaThuoc(Integer.parseInt(txtGiaThuoc.getText()));
                    thuoc.setDangThuoc(cbDangThuoc.getSelectedItem().toString());
                    thuoc.setCd(getCongDung(Integer.parseInt(cbCongDung.getSelectedItem().toString())));

                    String res = ctr.xoaThuoc(thuoc);
                    if (res.equals("ok")) {
                        JOptionPane.showMessageDialog(rootPane, "Xoa thanh cong!!!");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Xoa that bai!!!");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Xoa that bai!!!");
                }
            }
            else{
                
            }
        });
        btnTimGia.addActionListener((e) -> {
            tmThuoc.setRowCount(0);
            List<Integer> gia = new ArrayList<>();
            int gia1 = 1, gia2 = 1;
            try{
                gia1 = Integer.parseInt(txtGiaThuoc.getText());
                gia2 = Integer.parseInt(txtGiaThuoc1.getText());
               
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(rootPane, "Vui long nhap gia");
            }
            gia.add(gia1);
            gia.add(gia2);
            List<Thuoc> listTimThuoc = ctr.getListThuocByGia(gia);
            for(Thuoc i : listTimThuoc){
                tmThuoc.addRow(i.toObject());
            }
        });
//        cbDangThuoc.addItemListener((e) -> {
//            tmThuoc.setRowCount(0);
//            String select = (String) cbDangThuoc.getSelectedItem();
//            List<Thuoc> listTimThuocTheoDang = ctr.getListThuocByDang(select);
//            for (Thuoc i : listTimThuocTheoDang) {
//                tmThuoc.addRow(i.toObject());
//            }
//        });
        
//        cbCongDung.addActionListener((e) -> {
//            tmThuoc.setRowCount(0);
//            String select = (String) cbCongDung.getSelectedItem();
//            List<Thuoc> listTimThuocTheoDang = ctr.getListThuocByCD(select);
//            for (Thuoc i : listTimThuocTheoDang) {
//                tmThuoc.addRow(i.toObject());
//            }
//        });
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCongDung = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        txtTenCD = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        btnThemCD = new javax.swing.JButton();
        btnSuaCD = new javax.swing.JButton();
        btnXoaCD = new javax.swing.JButton();
        btnTimCD = new javax.swing.JButton();
        btnLoadCD = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbThuoc = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaThuoc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenThuoc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGiaThuoc = new javax.swing.JTextField();
        btnThemThuoc = new javax.swing.JButton();
        btnSuaThuoc = new javax.swing.JButton();
        btnXoaThuoc = new javax.swing.JButton();
        btnTimThuoc = new javax.swing.JButton();
        btnLoadThuoc = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbDangThuoc = new javax.swing.JComboBox<>();
        cbCongDung = new javax.swing.JComboBox<>();
        txtGiaThuoc1 = new javax.swing.JTextField();
        btnTimGia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbCongDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Ma cong dung", "Ten cong dung", "Mo ta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCongDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCongDungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCongDung);

        jLabel1.setText("Ma cong dung");

        jLabel2.setText("Ten cong dung");

        jLabel3.setText("Mo ta");

        btnThemCD.setText("Them");

        btnSuaCD.setText("Sua");

        btnXoaCD.setText("Xoa");

        btnTimCD.setText("Tim Kiem");

        btnLoadCD.setText("Load");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnThemCD))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSuaCD)
                        .addGap(31, 31, 31)
                        .addComponent(btnXoaCD)
                        .addGap(28, 28, 28)
                        .addComponent(btnTimCD)
                        .addGap(26, 26, 26)
                        .addComponent(btnLoadCD)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtMaCD)
                    .addComponent(txtTenCD)
                    .addComponent(txtMoTa))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemCD)
                    .addComponent(btnSuaCD)
                    .addComponent(btnXoaCD)
                    .addComponent(btnTimCD)
                    .addComponent(btnLoadCD))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Cong dung", jPanel2);

        tbThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Ma thuoc", "Ten thuoc", "Gia thuoc", "Dang thuoc", "Cong dung"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbThuocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbThuoc);

        jLabel4.setText("Ma thuoc");

        jLabel5.setText("Ten thuoc");

        jLabel6.setText("Gia thuoc");

        btnThemThuoc.setText("Them");

        btnSuaThuoc.setText("Sua");

        btnXoaThuoc.setText("Xoa");

        btnTimThuoc.setText("Tim Kiem");

        btnLoadThuoc.setText("Load");

        jLabel7.setText("Dang thuoc");

        jLabel8.setText("Cong dung");

        cbDangThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Viên", "Bột", "Nước" }));
        cbDangThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbDangThuocMouseClicked(evt);
            }
        });

        cbCongDung.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbCongDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbCongDungMouseClicked(evt);
            }
        });

        btnTimGia.setText("Tim Theo Gia");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnThemThuoc))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSuaThuoc)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaThuoc)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoadThuoc)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtGiaThuoc)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaThuoc1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTenThuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addComponent(txtMaThuoc, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(57, 57, 57)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbDangThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCongDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnTimGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cbDangThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTenThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(cbCongDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimGia)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtGiaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGiaThuoc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemThuoc)
                    .addComponent(btnSuaThuoc)
                    .addComponent(btnXoaThuoc)
                    .addComponent(btnLoadThuoc)
                    .addComponent(btnTimThuoc))
                .addContainerGap(119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Thuoc", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbCongDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCongDungMouseClicked
        // TODO add your handling code here:
        int row = tbCongDung.getSelectedRow();
        if(row >= 0 && row < tbCongDung.getRowCount()){
            txtMaCD.setText(tmCongDung.getValueAt(row, 0).toString());
            txtTenCD.setText(tmCongDung.getValueAt(row, 1).toString());
            txtMoTa.setText(tmCongDung.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_tbCongDungMouseClicked

    private void tbThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbThuocMouseClicked
        // TODO add your handling code here:
        int row = tbThuoc.getSelectedRow();
        if(row >= 0 && row < tbThuoc.getRowCount()){
            txtMaThuoc.setText(tmThuoc.getValueAt(row, 0).toString());
            txtTenThuoc.setText(tmThuoc.getValueAt(row, 1).toString());
            txtGiaThuoc.setText(tmThuoc.getValueAt(row, 2).toString());
            for (int i = 0; i < cbDangThuoc.getItemCount(); i++) {
                if(cbDangThuoc.getItemAt(i).equals(tmThuoc.getValueAt(row, 3).toString())){
                    cbDangThuoc.setSelectedIndex(i);
                }
            }
            for (int i = 1; i < cbCongDung.getItemCount(); i++) {
                if(Integer.parseInt(cbCongDung.getItemAt(i)) == (getMaCD(tmThuoc.getValueAt(row, 4).toString()))){
                    cbCongDung.setSelectedIndex(i);
                }
            }
        }
    }//GEN-LAST:event_tbThuocMouseClicked

    private void cbDangThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbDangThuocMouseClicked
        // TODO add your handling code here:
        tmThuoc.setRowCount(0);
        String select = (String) cbDangThuoc.getSelectedItem();
        for (Thuoc i : listThuoc) {
            if(i.getDangThuoc().equals(select))
            tmThuoc.addRow(i.toObject());
        }
    }//GEN-LAST:event_cbDangThuocMouseClicked

    private void cbCongDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCongDungMouseClicked
        // TODO add your handling code here:
        tmThuoc.setRowCount(0);
        String select = (String) cbCongDung.getSelectedItem();
        for (Thuoc i : listThuoc) {
            if(String.valueOf(i.getCd().getMaCongDung()).equals(select))
            tmThuoc.addRow(i.toObject());
        }
    }//GEN-LAST:event_cbCongDungMouseClicked

    
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientView().setVisible(true);
            }
        });
    }
    private int getMaCD(String s){
        for(CongDung i : listCD){
            if(i.getTenCongDung().equals(s)){
                return i.getMaCongDung();
            }
        }
        return 0;
    }
    private CongDung getCongDung(int ma){
        for(CongDung i : listCD){
            if(i.getMaCongDung() == ma){
                return i;
            }
        }
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadCD;
    private javax.swing.JButton btnLoadThuoc;
    private javax.swing.JButton btnSuaCD;
    private javax.swing.JButton btnSuaThuoc;
    private javax.swing.JButton btnThemCD;
    private javax.swing.JButton btnThemThuoc;
    private javax.swing.JButton btnTimCD;
    private javax.swing.JButton btnTimGia;
    private javax.swing.JButton btnTimThuoc;
    private javax.swing.JButton btnXoaCD;
    private javax.swing.JButton btnXoaThuoc;
    private javax.swing.JComboBox<String> cbCongDung;
    private javax.swing.JComboBox<String> cbDangThuoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tbCongDung;
    private javax.swing.JTable tbThuoc;
    private javax.swing.JTextField txtGiaThuoc;
    private javax.swing.JTextField txtGiaThuoc1;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtTenCD;
    private javax.swing.JTextField txtTenThuoc;
    // End of variables declaration//GEN-END:variables

//    @Override
//    public void setCongDung(List<CongDung> listCD) {
//        this.listCD = listCD;
//        docCD();
//    }
}
