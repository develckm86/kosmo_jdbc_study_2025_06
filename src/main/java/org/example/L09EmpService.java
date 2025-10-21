package org.example;

import java.sql.SQLException;
import java.util.List;

public interface L09EmpService {
    //  사원등록 register(EmpDto emp)  boolean or EmpDto
    //  사원수정 modify(EmpDto emp) boolean or EmpDto
    //  상원삭제 remove(empno) boolean
    //  사원전체조회 readAll() List<EmpDto>
    //  사원이름조회 readByEname List<EmpDto>
    //  상세조회    readOne EmpDto
    //서비스가 간단할수록 dao 와 service가 거의 유사
    //작은기업 service 를 구현하지 않는 경우도 존재!!
    //IllegalArgumentException : 입력오류(사원의이름이 없거나 길이가 넘거나, 급여가 잘못등록됨...)
    boolean register(L05EmpDto emp) throws SQLException,IllegalArgumentException;
    boolean modify(L05EmpDto emp) throws SQLException,IllegalArgumentException;

    boolean register(L11EmpValidBean emp) throws SQLException,IllegalArgumentException;
    boolean modify(L11EmpValidBean emp) throws SQLException,IllegalArgumentException;

    boolean remove(int empno) throws SQLException;

    List<L05EmpDto> readAll() throws SQLException;
    List<L05EmpDto> readByEname(String ename) throws SQLException;
    L05EmpDto readOne(int empno) throws SQLException;
}
