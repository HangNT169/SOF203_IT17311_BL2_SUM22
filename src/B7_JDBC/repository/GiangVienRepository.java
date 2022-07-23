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
        String query = "SELECT * FROM giang_vien gv  WHERE ma_gv = ?";
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

    public static void main(String[] args) {
        List<GiangVien> lists = new GiangVienRepository().getAll();
        for (GiangVien g : lists) {
            System.out.println(g.toString());
        }
    }

}
