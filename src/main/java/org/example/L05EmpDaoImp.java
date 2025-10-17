package org.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        int insertOne=0;
        String sql="INSERT INTO EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO) " +
                " VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1,emp.getEmpno());
            ps.setString(2,emp.getEname());
            ps.setString(3,emp.getJob());
//            ps.setInt(4,null);
            ps.setObject(4,emp.getMgr());
//            ps.setDate(5,emp.getHiredate());
            //java.sql.Date->LocalDate => "20025-01-05" => oracle Date로 바로변환!!
            //LocalDate->java.sql.Date
            java.sql.Date hiredate=null;
            if(emp.getHiredate()!=null)hiredate=java.sql.Date.valueOf(emp.getHiredate());
            ps.setDate(5,hiredate);
            //ps.setString(5,emp.getHiredate().toString());
            ps.setObject(6,emp.getSal());
            ps.setObject(7,emp.getComm());
            ps.setObject(8,emp.getDeptno());
            insertOne=ps.executeUpdate();
            //쿼리실행못하고 멈춤!!
        }
        return insertOne;
    }

    //updateSal :"UPDATE EMP SET SAL=? WHERE EMPNO=?"
    @Override
    public int updateOne(L05EmpDto emp) throws SQLException {
        int updateOne=0;
        String sql="UPDATE EMP SET ENAME=?, JOB=?, MGR=?, HIREDATE=?, SAL=?, COMM=?, DEPTNO=? WHERE EMPNO=?";
        try(PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1,emp.getEname());
            ps.setString(2,emp.getJob());
            ps.setObject(3,emp.getMgr());
            ps.setString(4,(emp.getHiredate()!=null) ? emp.getHiredate().toString() : null );
            ps.setObject(5,emp.getSal());
            ps.setObject(6,emp.getComm());
            ps.setObject(7,emp.getDeptno());
            ps.setInt(8,emp.getEmpno());
            updateOne= ps.executeUpdate();
        }
        return updateOne;
    }

    @Override
    public int deleteOne(int empno) throws SQLException {
        int deleteOne=0;
        String sql="DELETE FROM EMP WHERE empno=?";
        try (PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1,empno);
            deleteOne=ps.executeUpdate();
        }
        return deleteOne;
    }

    @Override
    public List<L05EmpDto> findAll() throws SQLException {
        List<L05EmpDto> empList=null;

        String sql="SELECT * FROM EMP";
        pstmt=conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        empList=new ArrayList<>();
        while (rs.next()){
            //comm,sal,hiredate,job,mgr
            int empno=rs.getInt("empno");
            String ename=rs.getString("ename");
            //자바는 기본형이 null을 참조하는 것이 불가능!=> 기본형의 없음===0
            //기본형타입의 자료형 랩퍼클래스(Byte,Short,Integer,Long, Float,Double)
            //int deptno=rs.getInt("deptno"); //null:부서가없은, 0: 부서번호가 0
            //int mgr=rs.getInt("mgr"); //null:상사가없음 ,0: 상사번호가 0
            //new Integer(10)
            BigDecimal deptnoDec=rs.getBigDecimal("deptno");
            Integer deptno= (deptnoDec!=null)? deptnoDec.intValue() : null;
            BigDecimal mgrDec=rs.getBigDecimal("mgr");
            Integer mgr=(mgrDec!=null)?mgrDec.intValue():null;
            BigDecimal commDec=rs.getBigDecimal("comm");
            Double comm=(commDec!=null)?commDec.doubleValue():null;
            BigDecimal salDec=rs.getBigDecimal("sal");
            Double sal=(salDec!=null)?salDec.doubleValue():null;
            //null일수 있는 기본형 데이터 필드는 Object로 가져와서 랩퍼클래스로 변환
            //ResultSet DB에서 가져온 Date를 java.sql.Date or java.util.Date 로 반환
            //요즘 자바에서는 LocalDate* 를 많이 사용
            java.sql.Date hiredate=rs.getDate("hiredate");
            LocalDate hiredateLocal=(hiredate!=null)?hiredate.toLocalDate():null;
            //LocalDate.parse("2025-09-25")
            L05EmpDto emp=new L05EmpDto();
            emp.setEmpno(empno);
            emp.setEname(ename);
            emp.setComm(comm);
            emp.setSal(sal);
            emp.setMgr(mgr);
            emp.setJob(rs.getString("job"));
            emp.setDeptno(deptno);
            emp.setHiredate(hiredateLocal);
            empList.add(emp);
        }
        return empList;
    }
    //findByLikeEname
    // String sql="SELECT * FROM EMP WHERE ename LIKE %?%"; (오류)
    // String sql="SELECT * FROM EMP WHERE ename LIKE %'k'%"; (오류)
    // String sql="SELECT * FROM EMP WHERE ename LIKE %||?||%; (정답)
    // String sql="SELECT * FROM EMP WHERE ename LIKE '%k%'";
    @Override
    public List<L05EmpDto> findByEname(String ename) throws SQLException {
        List<L05EmpDto> emps=null;
        String sql="SELECT * FROM EMP WHERE UPPER(ename)=UPPER(?)"; //대소문자 구분없이 조회
        try (PreparedStatement pstmt=conn.prepareStatement(sql); ){
            pstmt.setString(1,ename);
            try (ResultSet rs=pstmt.executeQuery()){
                emps=new ArrayList<>();
                while (rs.next()){
                    L05EmpDto emp=mapRow(rs);
                    emps.add(emp);
                }
            }
        }
        return emps;
    }

    @Override
    public L05EmpDto findByEmpno(int empno) throws SQLException {
        L05EmpDto emp=null;
        String sql="SELECT * FROM EMP WHERE empno=?";
        try(PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setInt(1,empno);
            try (ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    emp=mapRow(rs);
                }
            }
        }
        return emp;
    }

    public L05EmpDto mapRow(ResultSet rs) throws SQLException{
        L05EmpDto emp=new L05EmpDto();

        emp.setEmpno(rs.getInt("empno"));
        emp.setEname(rs.getString("ename"));
        emp.setJob(rs.getString("job"));
        if(rs.getDate("hiredate")!=null)
            emp.setHiredate(rs.getDate("hiredate").toLocalDate());
        if(rs.getBigDecimal("comm")!=null)
            emp.setComm(rs.getDouble("comm"));
        if(rs.getObject("sal")!=null)
            emp.setSal(rs.getDouble("sal"));
        if(rs.getObject("mgr")!=null)
            emp.setMgr(rs.getInt("mgr"));
        if(rs.getObject("deptno")!=null)
            emp.setDeptno(rs.getInt("deptno"));
        return  emp;
    }


}
