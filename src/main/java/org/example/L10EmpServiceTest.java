package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class L10EmpServiceTest {
    public static void main(String[] args) {

        try(Connection conn=L03DBFactory.getConn();){
            L09EmpService empService=new L09EmpServiceImp(conn);
            System.out.println("readAll 테스트");
            System.out.println(empService.readAll());
            System.out.println("readlByEname 테스트");
            System.out.println(empService.readByEname("scott"));

            L05EmpDto emp=new L05EmpDto();
            emp.setEmpno(6666);
            emp.setEname("테스트");
            emp.setSal(500.0);
            emp.setComm(99.0);
            emp.setMgr(1234);
            emp.setDeptno(40); //참조의무결성 위반!!!
            System.out.println("등록 성공:"+empService.register(emp));
            System.out.println("readOne 테스트 6666");
            System.out.println(empService.readOne(6666));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
