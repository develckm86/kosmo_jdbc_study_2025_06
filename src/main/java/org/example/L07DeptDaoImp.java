package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return null;
    }

    @Override
    public int insertOne(L07DeptDto deptDto) throws SQLException {
        return 0;
    }

    @Override
    public int updateOne(L07DeptDto deptDto) throws SQLException {
        return 0;
    }

    @Override
    public int deleteOne(int deptno) throws SQLException {
        return 0;
    }

}
