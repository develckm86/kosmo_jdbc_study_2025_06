package org.example;

import java.sql.SQLException;
import java.time.LocalDate;

public class L06EmpDaoTest {
    public static void main(String[] args) throws SQLException {
        //사원전체 조회
        L05EmpDao empDao=new L05EmpDaoImp(L03DBFactory.getConn());
        System.out.println("전체조회");
        System.out.println(empDao.findAll());
        System.out.println("SCOTT 이름 검색");
        System.out.println(empDao.findByEname("king"));

        System.out.println("9999사원 삭제");
        int del=empDao.deleteOne(9999);
        if(del==1) {
            System.out.println("삭제성공");
        }else{
            System.out.println("삭제할 레코드가 없음");
        }

        L05EmpDto emp=new L05EmpDto();
        emp.setEmpno(9999);
        emp.setEname("최경민");//10
        emp.setJob("DEVELOPER");
        emp.setDeptno(10); //없는 14 부서를 참조 => 오류 (참조의 무결성)
        emp.setMgr(null); //없는 상사를 참조=>참조무결성( FK 지정을 안해서 오류 안뜸)
        emp.setSal(10000.11);
        emp.setComm(100.22);
        emp.setHiredate(LocalDate.of(2000,10,10));
        empDao.insertOne(emp);


        System.out.println("9999 사번 조회");
        System.out.println(empDao.findByEmpno(9999));
        //findByLikeEname

        emp.setEname("최경만");//10
        emp.setJob("BACKEND");
        emp.setDeptno(20); //없는 14 부서를 참조 => 오류 (참조의 무결성)
        emp.setMgr(7843); //없는 상사를 참조=>참조무결성( FK 지정을 안해서 오류 안뜸)
        emp.setSal(1234.11);
        emp.setComm(123.22);
        emp.setHiredate(LocalDate.of(2022,2,22));
        System.out.println("수정하기위한 dto : "+emp);
        int update=empDao.updateOne(emp);
        if(update>0){
            System.out.println("9999 사원수정 성공");
        }else{
            System.out.println("9999 사원수정 실패");
        }

        System.out.println(empDao.findByEmpno(9999));



        del=empDao.deleteOne(9999);
        if(del==1) {
            System.out.println("삭제성공");
        }else{
            System.out.println("삭제할 레코드가 없음");
        }

    }
}
