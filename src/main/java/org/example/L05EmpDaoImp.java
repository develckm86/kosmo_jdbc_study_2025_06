package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class L05EmpDaoImp implements L05EmpDao{
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    //db 접속객체 없이는 객체가 될수 없게 강제함
    public L05EmpDaoImp(Connection conn){
        this.conn=conn;
    }
    @Override
    public int insertOne(L05EmpDto emp) throws SQLException {
        return 0;
    }

    @Override
    public int updateOne(L05EmpDto emp) throws SQLException {
        return 0;
    }

    @Override
    public int deleteOne(int empno) throws SQLException {
        return 0;
    }

    @Override
    public List<L05EmpDto> findAll() throws SQLException {
        List<L05EmpDto> empList=null;

        String sql="SELECT * FROM EMP";
        pstmt=conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        empList=new ArrayList<>();
        while (rs.next()){
            L05EmpDto emp=new L05EmpDto();
            emp.setEmpno(rs.getInt("empno"));
            emp.setEname(rs.getString("ename"));
            emp.setDeptno(rs.getInt("deptno"));
            empList.add(emp);
        }
        return empList;
    }

    @Override
    public List<L05EmpDto> findByEname(String ename) throws SQLException {
        return List.of();
    }

    @Override
    public L05EmpDto findByEmpno(int empno) throws SQLException {
        return null;
    }
}
