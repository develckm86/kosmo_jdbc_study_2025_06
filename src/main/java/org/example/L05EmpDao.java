package org.example;

import java.sql.SQLException;
import java.util.List;

public interface L05EmpDao {
/*사원관리자 페이지를 만들예정인데 어떤기능이 있을까??
* 사원등록
* 사원수정
* 사원삭제
* 사원전체조회
* 사원이름조회
* 사원상세=>사원번호조회
* */
    //public int insertOne(int empno,String ename ....);
    public int insertOne(L05EmpDto emp) throws SQLException;
    public int updateOne(L05EmpDto emp) throws SQLException;
    public int deleteOne(int empno) throws SQLException;
    //Delete From emp Where empno=?

    //SELECT * FROM EMP;
    public List<L05EmpDto> findAll() throws SQLException;
    public List<L05EmpDto> findByEname(String ename) throws SQLException;
    public L05EmpDto findByEmpno(int empno) throws SQLException; //pk,one,empno

}
