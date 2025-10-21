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
    //트랜잭션: 데이터 수정이 2번이상 일어날때 문제가 생기면 세이브포인트로 되돌림
    //register : 2번이상 수정(DML insert,update,delete)하지 않기 때문에 트랜잭션관리 필요 없음
    //수업이니까 작성함!!!

    @Override
    public boolean register(L05EmpDto emp) throws SQLException, IllegalArgumentException {
        try{
            //jdbc conn 의 설정  autoCommit true
            conn.setAutoCommit(false);
            conn.commit(); // save point
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

            //Emp.deptno null 허용
            if(emp.getDeptno()!=null){
                L07DeptDto existDept=deptDao.findByDeptno(emp.getDeptno());//등록하려는 부서가 존재하나
                if(existDept==null){
                    List<L07DeptDto> depts=deptDao.findAll();
                    List<Integer> deptnos=new ArrayList<>();
                    for (L07DeptDto dept :depts){
                        deptnos.add(dept.getDeptno());
                    }
                    throw new IllegalArgumentException("존재하는 부서번호를 입력하세요!"+deptnos);
                }
            }
            //Emp.mrg (상사의 사번) null 허용
            if(emp.getMgr()!=null){
                L05EmpDto existMrg=empDao.findByEmpno(emp.getMgr());
                if(existMrg==null) throw new IllegalArgumentException("상사가 존재하지 않습니다.mgr을 확인");
            }
            int insert=empDao.insertOne(emp);
            return insert==1;
        } catch (Exception e) {
            conn.rollback();
            throw e; //오류가 뜨면 오류를 바로 위임
        }
    }

    @Override
    public boolean modify(L05EmpDto emp) throws SQLException, IllegalArgumentException {
        //트랜잭션 연습!!!
        //사번 이름 꼭 존재
        //급여와 커미션 > 0
        //수정하려는 사원이 없다=> 존재하지 않느 사원은 수정불가
        //수정하려는 부서번호가 없다=> 존재하지 않는 부서번호!
        if(emp.getEmpno()==0)throw new IllegalArgumentException("수정할 사번을 입력하세요!");
        if(emp.getEname()==null || emp.getEname().isBlank()) throw new IllegalArgumentException("이름은 꼭 입력하세요!");
        if(emp.getSal()!=null && emp.getSal()<0) throw new IllegalArgumentException("급여는 0보다 큽니다.");
        if(emp.getComm()!=null && emp.getComm()<0) throw new IllegalArgumentException("커미션는 0보다 큽니다.");
        //유효성(Valid, Validation)검사
        L05EmpDto existEmp=empDao.findByEmpno(emp.getEmpno());
        if(existEmp==null)throw new IllegalArgumentException("수정할 사원이 없습니다.(이미 삭제된 사원입니다. 등록하고 수정하세요.)");
        if(emp.getDeptno()!=null){
            L07DeptDto existDept=deptDao.findByDeptno(emp.getDeptno());
            if(existDept==null) throw  new IllegalArgumentException("존재하지 않는 부서번호입니다. 확인하세요!");
        }
        if(emp.getMgr()!=null){
            L05EmpDto existMgr=empDao.findByEmpno(emp.getMgr());
            if(existMgr==null) throw new IllegalArgumentException("존재하지 않는 상사번호입니다. 확인하세요!");
        }

        return empDao.updateOne(emp)==1;
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
