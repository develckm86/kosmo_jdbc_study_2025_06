package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class L07DeptDaoImp implements L07DeptDao{
    private final Connection conn;
    public L07DeptDaoImp(Connection conn){
        this.conn=conn;
    }
    //TDD :테스트주도개발
    @Override
    public List<L07DeptDto> findAll() throws SQLException {
        List<L07DeptDto> depts=null;
        String sql="SELECT * FROM DEPT";
        try(Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql)){
            depts=new ArrayList<>();
            while (rs.next()){
                int deptno=rs.getInt("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                L07DeptDto deptDto=new L07DeptDto(deptno,dname,loc);
                depts.add(deptDto);
            }
        }
        return depts;
    }

    @Override
    public L07DeptDto findByDeptno(int deptno) throws SQLException {
        L07DeptDto detp=null;
        String sql="SELECT * FROM DEPT WHERE DEPTNO=?";
        try(PreparedStatement pstmt=conn.prepareStatement(sql); ){
            pstmt.setInt(1,deptno);
            try(ResultSet rs=pstmt.executeQuery()){
                if (rs.next()){ //결과가 무조건 1개
                    int deptno2=rs.getInt("deptno");
                    String dname=rs.getString("dname");
                    String loc=rs.getString("loc");
                    detp=new L07DeptDto(deptno2,dname,loc);
                }
            }
        }
        return detp;
    }

    @Override
    public int insertOne(L07DeptDto deptDto) throws SQLException {
        int insertOne=0;
        String sql="INSERT INTO DEPT (DEPTNO,DNAME,LOC) VALUES (?,?,?)";
        try(PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1,deptDto.getDeptno());
            pstmt.setString(2,deptDto.getDname());
            pstmt.setString(3,deptDto.getLoc());
            insertOne=pstmt.executeUpdate();//dml => long or int
        }
        return insertOne;
    }

    @Override
    public int updateOne(L07DeptDto deptDto) throws SQLException {
        int updateOne=0;
        String sql="UPDATE DEPT SET DNAME=?, LOC=? WHERE DEPTNO=?";
        try (PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,deptDto.getDname());
            pstmt.setString(2,deptDto.getLoc());
            pstmt.setInt(3,deptDto.getDeptno());
            updateOne=pstmt.executeUpdate();
        }
        return updateOne;
    }

    @Override
    public int deleteOne(int deptno) throws SQLException {
        int deleteOne=0;
        String sql="DELETE FROM DEPT WHERE DEPTNO=?";
        try (PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1,deptno);
            deleteOne=pstmt.executeUpdate();
        }
        return deleteOne;
    }

}
