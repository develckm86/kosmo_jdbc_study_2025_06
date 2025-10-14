package org.example;

import java.time.LocalDate;

public class L05EmpDto {
/*EMPNO
ENAME
JOB
MGR
HIREDATE
SAL NUMBER(7,2)
COMM
DEPTNO*/
    private int empno;
    private String ename;
    private String job;
    private int mgr;
//   private  java.util.Date hiredate;
    private LocalDate hiredate;
    private double sal;
    private double comm;
    private int deptno;

    @Override
    public String toString() {
        return "L05EmpDto{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", hiredate=" + hiredate +
                ", sal=" + sal +
                ", comm=" + comm +
                ", deptno=" + deptno +
                "}\n";
    }

    //getter setter 캡슐화
    public void setEmpno(int empno){
        this.empno=empno;
    }
    public int getEmpno(){
        return this.empno;
    }
    public void setEname(String ename){
        this.ename=ename;
    }
    public String getEname(){
        return this.ename;
    }
    //거의 대부분의 개발툴은 get set 자동완성 재공
    //lombok : 컴파일시 자동완성하는 라이브러리 => spring
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public LocalDate getHiredate() {
        return hiredate;
    }
    public void setHiredate(LocalDate hiredate) {
        this.hiredate = hiredate;
    }
    public double getComm() {
        return comm;
    }
    public void setComm(double comm) {
        this.comm = comm;
    }
    public int getDeptno() {
        return deptno;
    }
    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }
    public double getSal() {
        return sal;
    }
    public void setSal(double sal) {
        this.sal = sal;
    }
    public int getMgr() {
        return mgr;
    }
    public void setMgr(int mgr) {
        this.mgr = mgr;
    }
}
