package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class L09EmpServiceImp implements L09EmpService{
    //private L05EmpDaoImp empDaoImp;
    private L05EmpDao empDao; //DIP : 의존성 역전의원칙 (서비스가 다른 dao 구현체를 사용할 수 있는 유연성=>재사용)
    private L07DeptDao deptDao;

    private Connection conn;//사용자가 생성해서 전달
    public L09EmpServiceImp(Connection conn){
        this.conn=conn;
        this.empDao=new L05EmpDaoImp(conn);
        this.deptDao=new L07DeptDaoImp(conn);
    }

    @Override
    public boolean register(L05EmpDto emp) throws SQLException, IllegalArgumentException {
        //사번,이름이 있는지
        //이름은 길이가 10이하인지 검사
        //급여와 커미션이 있으면 0보다큰지
        //사번이 이미 등록되어 있는지 (대표키)
        //부서번호가 있다면 부서번호가 있는지(참조의 무결성)
        //상사번호(mgr)가 없으면 오류!(참조의 무결성!!)
        //등록
        //EmpDto.empno int 없으면 0
        if(emp.getEmpno()<=0) throw new IllegalArgumentException("사번을 입력하세요!");
        //String.isBlank() : "", "   "
        if(emp.getEname()==null || emp.getEname().isBlank()) throw new IllegalArgumentException("이름을 입력하세요");

        if(emp.getSal()!=null && emp.getSal()<0) throw new IllegalArgumentException("급여는 0보다 커야합니다.");
        if(emp.getComm()!=null && emp.getComm()<0) throw new IllegalArgumentException("커미션은 0보다 커야합니다.");

        L05EmpDto existEmp=empDao.findByEmpno(emp.getEmpno()); //이미 등록된 사번이 있나?
        if(existEmp!=null) throw new IllegalArgumentException("이미 등록된 사번입니다.");

        L07DeptDto existDept=deptDao.findByDeptno(emp.getDeptno());//등록하려는 부서가 존재하나
        List<L07DeptDto> depts=deptDao.findAll();
        List<Integer> deptnos=new ArrayList<>();
        for (L07DeptDto dept :depts){
            deptnos.add(dept.getDeptno());
        }
        if(existDept==null) throw new IllegalArgumentException("존재하는 부서번호를 입력하세요!"+deptnos);

        int insert=empDao.insertOne(emp);
        return insert==0;
    }

    @Override
    public boolean modify(L05EmpDto emp) throws SQLException, IllegalArgumentException {
        return false;
    }
    //service : dao=1:1 (서비스가 단순한 경우)
    @Override
    public boolean remove(int empno) throws SQLException {
        return (empDao.deleteOne(empno)==1);
    }
    @Override
    public List<L05EmpDto> readAll() throws SQLException {
        return empDao.findAll();
    }

    @Override
    public List<L05EmpDto> readByEname(String ename) throws SQLException {
        return empDao.findByEname(ename);
    }
    @Override
    public L05EmpDto readOne(int empno) throws SQLException {
        return empDao.findByEmpno(empno);
    }
}
