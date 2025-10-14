package org.example.hw;

import java.sql.*;

public class H02PSTMT {
    public static void main(String[] args) {
        String url="jdbc:oracle:thin:@//localhost:1521/XEPDB1";
        String user="scott";
        String pw="tiger";
        try (Connection conn = DriverManager.getConnection(url,user,pw)){
            try (PreparedStatement pstmt=conn.prepareStatement("DELETE FROM EMP WHERE empno IN (?,?)")){
                pstmt.setInt(1,9001);
                pstmt.setInt(2,9002);
                int del=pstmt.executeUpdate();
                if(del==1){
                    System.out.println("9001 삭제 성공");
                }
            }

            try(Statement stmt=conn.createStatement()){
               String sql="INSERT INTO EMP (empno,ename,job,sal,deptno) " +
                       "VALUES (9001,'ALLEN2','SALESMAN',1600.00,30)";
               int insert=stmt.executeUpdate(sql);
               if(insert==1){
                   System.out.println("ALLEN2 등록성공");
               }
               //statement 는 일회용
            }
            String sql="INSERT INTO EMP (empno,ename,job,sal,deptno) VALUES (?,?,?,?,?)";

            try (PreparedStatement psmt=conn.prepareStatement(sql)){
                psmt.setInt(1,9002);
                psmt.setString(2,"SMITH2");
                psmt.setString(3,"CLERK");
                psmt.setInt(4,800);
                psmt.setInt(5,20);
                int insert=psmt.executeUpdate();
                if(insert>0){
                    System.out.println("SMITH2 등록성공");
                }

            }


            try(Statement stmt=conn.createStatement()){
                ResultSet rs=stmt.executeQuery("SELECT * FROM EMP");
                //String str="";
                StringBuilder sb=new StringBuilder();
                while (rs.next()){
                    int empno=rs.getInt("empno");
                    String ename=rs.getString("ename");
                    String job=rs.getString("job");
                    double sal=rs.getDouble("sal");
                    int deptno=rs.getInt("deptno");
                    //str+=empno+" | "+ename+" | "+ job+" | "+sal+" | "+deptno;
                    //문자열 더하기는 객체를 많이 생성 => 메모리 많이 사용
                    sb.append(empno);
                    sb.append(" | ");
                    sb.append(ename);
                    sb.append(" | ");
                    sb.append(job);
                    sb.append(" | ");
                    sb.append(sal);
                    sb.append(" | ");
                    sb.append(deptno);
                    sb.append(" \n ");
                }
                //System.out.println(str);
                System.out.println(sb.toString());
            }
            sql="SELECT e.*, d.dname, e.ename 이름, e.sal 급여 FROM EMP e LEFT JOIN DEPT d ON e.deptno=d.deptno";
//            sql="SELECT e.*,(SELECT dname FROM dept WHERE deptno=e.deptno) dname FROM EMP e";
            try (Statement stmt= conn.createStatement();
                 ResultSet rs=stmt.executeQuery(sql);){
                StringBuilder sb=new StringBuilder();
                while (rs.next()){
                    int empno=rs.getInt("empno");
                    int deptno=rs.getInt("deptno");
                    int sal=rs.getInt("급여");
                    int comm=rs.getInt("comm");
                    String ename=rs.getString("이름");
                    String dname=rs.getString("dname");
                    sb.append(empno+" | "+deptno+" | "+sal+" | "+comm+" | "+ename+" | "+dname+"\n");
                }
                System.out.println(sb.toString());
            }
            sql="SELECT deptno, AVG(sal) \"부서별 평균 급여\" FROM EMP GROUP BY deptno";
            try(Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery(sql)){
                StringBuilder sb=new StringBuilder();
                while (rs.next()){
                    int deptno=rs.getInt("deptno");
                    double salAvg=rs.getDouble("부서별 평균 급여");
                    sb.append(deptno+" | "+salAvg+"\n");
                }
                System.out.println(sb);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
