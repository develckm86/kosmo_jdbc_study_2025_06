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

    @Override
    public List<L05EmpDto> findByEname(String ename) throws SQLException {
        return List.of();
    }

    @Override
    public L05EmpDto findByEmpno(int empno) throws SQLException {
        return null;
    }
}
