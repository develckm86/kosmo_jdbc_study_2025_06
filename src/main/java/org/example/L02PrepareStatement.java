package org.example;
//import oracle.jdbc.OracleDriver;

import java.sql.*;

public class L02PrepareStatement {
    public static void main(String[] args) {
        //job="SALESMAN" 인 사람을 조회
        String param="SALESMAN";

        String url="jdbc:oracle:thin:@//localhost:1521/XEPDB1";
        String user="scott";
        String pw="tiger";
//        String sql="SELECT * FROM EMP WHERE job='"+param+"'";
        String sql="SELECT * FROM EMP WHERE job=? AND sal>?";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn= DriverManager.getConnection(url,user,pw);
            //Statement stmt= conn.createStatement();
            //ResultSet rs=stmt.executeQuery(sql);
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,param);
            pstmt.setDouble(2,1300.0);
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()){
                int empno=rs.getInt("empno");
                double sal=rs.getDouble("sal");
                String hiredate=rs.getString("hiredate");
                String ename=rs.getString("ename");
                String job=rs.getString("job");
                System.out.println(empno+" / "+ename+" / "+sal+" / "+job+" / "+hiredate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
