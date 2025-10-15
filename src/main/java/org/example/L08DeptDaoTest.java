package org.example;

import java.sql.Connection;
import java.util.List;

public class L08DeptDaoTest {
    public static void main(String[] args) {
        try (Connection conn=L03DBFactory.getConn();){

            L07DeptDao deptDao=new L07DeptDaoImp(conn);
            List<L07DeptDto> depts=deptDao.findAll();
            System.out.println(depts);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
