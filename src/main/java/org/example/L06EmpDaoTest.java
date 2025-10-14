package org.example;

import java.sql.SQLException;

public class L06EmpDaoTest {
    public static void main(String[] args) throws SQLException {
        //사원전체 조회
        L05EmpDao empDao=new L05EmpDaoImp(L03DBFactory.getConn());
        System.out.println(empDao.findAll());
    }
}
