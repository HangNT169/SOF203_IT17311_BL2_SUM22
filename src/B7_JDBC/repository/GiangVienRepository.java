/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package B7_JDBC.repository;

import B7_JDBC.model.GiangVien;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author hangnt
 */
public class GiangVienRepository {

    public List<GiangVien> getAll() {
        //B1: Viet query 
        String query = "SELECT * FROM giang_vien gv ";
        // B2: Mo cong ket noi
        // Connection: La 1 inteface cung cap cac dang de co the ket noi vs Database
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            // Statement: Cac inteface JDBCStatement , PreparedStatement ... => Cac phuong thuc cho phep gui cac SQL toi DB 
            ResultSet rs = ps.executeQuery();
            // Result Set: dat dien cho tap hop cac ban ghi ( record) co duoc sau khi thuc hien cau query
            // B3: Tao 1 list
            List<GiangVien> listGiangVien = new ArrayList<>();
            // B4: Doc du lieu vao list
            while (rs.next()) {
                GiangVien gv = new GiangVien(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
                listGiangVien.add(gv);
            }
            return listGiangVien;
        } catch (SQLException e) { // Bat tat ca cac loi lien quan toi connect DB
            e.printStackTrace(System.out);
        }
        return null;
    }

    public GiangVien getOne(String maGV) {
        String query = "SELECT * FROM giang_vien gv  WHERE ma_gv = ? ";
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, maGV);
            // Statement: Cac inteface JDBCStatement , PreparedStatement ... => Cac phuong thuc cho phep gui cac SQL toi DB 
            ResultSet rs = ps.executeQuery();
            // Result Set: dat dien cho tap hop cac ban ghi ( record) co duoc sau khi thuc hien cau query
            // B4: Doc du lieu vao list
            if (rs.next()) {
                GiangVien gv = new GiangVien(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
                return gv;
            }
        } catch (SQLException e) { // Bat tat ca cac loi lien quan toi connect DB
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean add(GiangVien gv) {
        String query = "INSERT INTO B7_TrenLop.dbo.giang_vien "
                + "(ma_gv, ten_gv, tuoi, bac, loai, gioi_tinh) "
                + "VALUES(?,?,?,?,?,?)";
        int check = 0;
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, gv.getMaGV());
            ps.setObject(2, gv.getTenGV());
            ps.setObject(3, gv.getTuoi());
            ps.setObject(4, gv.getBac());
            ps.setObject(5, gv.getLoai());
            ps.setObject(6, gv.isGioiTinh());
            check = ps.executeUpdate();
        } catch (SQLException e) { // Bat tat ca cac loi lien quan toi connect DB
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(String maGV) {
        String query = "DELETE FROM giang_vien "
                + "WHERE ma_gv = ?";
        int check = 0;
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, maGV);
            check = ps.executeUpdate();
        } catch (SQLException e) { // Bat tat ca cac loi lien quan toi connect DB
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean update(GiangVien gv, String maGV) {
        String query = "UPDATE B7_TrenLop.dbo.giang_vien "
                + "SET ten_gv= ? , tuoi=?, bac=?, loai=?, gioi_tinh=? "
                + "WHERE ma_gv= ?";
        int check = 0;
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, gv.getTenGV());
            ps.setObject(2, gv.getTuoi());
            ps.setObject(3, gv.getBac());
            ps.setObject(4, gv.getLoai());
            ps.setObject(5, gv.isGioiTinh());
            ps.setObject(6, maGV);
            check = ps.executeUpdate();
        } catch (SQLException e) { // Bat tat ca cac loi lien quan toi connect DB
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public static void main(String[] args) {
//        List<GiangVien> lists = new GiangVienRepository().getAll();
//        for (GiangVien g : lists) {
//            System.out.println(g.toString());
//        }
        GiangVien gv = new GiangVien("ma_test_01", "ten01", 20, "5", "part_time", true);
        boolean add = new GiangVienRepository().delete("ma_test_01");
        System.out.println(add);
    }

}
