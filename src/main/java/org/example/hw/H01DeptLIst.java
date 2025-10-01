package org.example.hw;
import java.sql.*;

public class H01DeptLIst {
//  접속=> dept 출력!
    public static void main(String[] args) {
        String url="jdbc:oracle:thin:@//localhost:1521/XEPDB1";
        String user="scott";
        String pw="tiger";
        String sql="SELECT * FROM DEPT";
        try{
            Connection conn=DriverManager.getConnection(url,user,pw);
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            System.out.println("번호\t|\t부서이름\t|\t부서위치");
            while (rs.next()){
                int deptno=rs.getInt("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                System.out.println(deptno+"\t|\t"+dname+"\t|\t"+loc+"\t|\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
