package org.example;

import java.time.LocalDate;

public class L11EmpValidBean {
    //Dto : 순수 캡슐화 객체 (getter setter만 존재),직렬화,검증이 필요없는 데이터 저장 및 전송
    //Bean : dto + 검증  (직렬화시 오류 발생)
    //Entity : 데이터베이스 전용 dto(데이터베이스 자동맵핑 기술이 포함되어 직렬화시 오류발생 가능성 존재!)
    //EmpDto와 EmpBean 필드는 같을 수도 있고 다를 수도 있다.
    private int empno;
    private String ename;
    private String job;
    private LocalDate hiredate;
    private Double sal;
    private Double comm;
    private Integer mgr; //Emp.empno FK (생성불가)
    private Integer deptno; //DEPT.deptno FK (생성불가)

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public Double getComm() {
        return comm;
    }

    public void setComm(Double comm) {
        if(comm!=null){
            if(comm<0)throw new IllegalArgumentException("커미션은 0보다 커야합니다");
        }
        this.comm = comm;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        //null<0 : 오류
        //sall!=null && sal<0 : sal 이 null 이 아닐때만 비교연산하기 때문에 오류가 발생하지 않음
        if(sal!=null && sal<0) throw new IllegalArgumentException("급여는 0보다 커야 합니다.");
        this.sal = sal;
    }

    public LocalDate getHiredate() {
        return hiredate;
    }

    public void setHiredate(LocalDate hiredate) {
        this.hiredate = hiredate;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        //if(ename==null) return;
        //"" , " "
        if(ename==null || ename.isBlank()) throw new IllegalArgumentException("이름은 꼭 입력해야합니다.");
        //길이가 10이하 "최경민아아아앙아앙아아아"
        if(ename.trim().length()>10) throw new IllegalArgumentException("이름은 10자 이하입니다.");
        //유효성(Valid) 검사 => bean
        this.ename = ename;
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        if(empno<=0)throw new IllegalArgumentException("사번은 0보다 커야합니다.");
        this.empno = empno;
    }
}
