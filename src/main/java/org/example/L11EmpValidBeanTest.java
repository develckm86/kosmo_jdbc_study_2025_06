package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class L11EmpValidBeanTest {
    public static void main(String[] args) {
        L11EmpValidBean empValidBean=new L11EmpValidBean();
//        empValidBean.setEname("     ");
//        empValidBean.setEname("김수암무거북이와두루미");
        empValidBean.setEmpno(5555);
        empValidBean.setEname("최경민");
        empValidBean.setMgr(7788);
        empValidBean.setDeptno(10);
        //empValidBean.setSal(-90.0);//오류
        empValidBean.setSal(100.0);
        empValidBean.setComm(10.0);

        try(Connection conn=L03DBFactory.getConn()){
            L09EmpService empService=new L09EmpServiceImp(conn);

            System.out.println("삭제 성공 :"+empService.remove(5555));

            System.out.println("등록 성공 :"+empService.register(empValidBean));

            System.out.println(empService.readOne(5555));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
