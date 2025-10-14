package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class L04DBFactoryTest {
    public static void main(String[] args) {
        String sql="SELECT * FROM DEPT";
        try(//Connection conn=L03DBFactory.conn;
            Connection conn=L03DBFactory.getConn();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql)){
            while (rs.next()){
                System.out.println(rs.getString("dname"));
            }
        } catch (SQLException e) {e.printStackTrace();}
        System.out.println("/////////////////close 이후 한번더 호출///////////////////");
        sql="SELECT dname FROM DEPT";
        try(//Connection conn=L03DBFactory.conn;
            Connection conn=L03DBFactory.getConn();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql)){
            while (rs.next()){
                System.out.println(rs.getString("dname"));
            }
        } catch (SQLException e) {e.printStackTrace();}

    }
}
