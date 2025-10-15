package org.example;

public class L07DeptDto {
    /*DEPTNO NUMBER(2) => byte short int(*) long
DNAME VARCHAR(14) => String(*), char[14]
LOC VARCHAR(13) => String*/
    private int deptno; //부서번호
    private String dname; //부서이름
    private String loc; //부서위치
    //생성자 : 타입이 객체가 될때 초기값(default)을 정의
    //이름 한개인데 역할이 여러개 => 다형성(객체지향문법)
    //오버로드,오버라이드,타입의 다형성
    public L07DeptDto(){} //생성자를 정의하면 기본생성자가 사라짐
    public L07DeptDto(int deptno,String dname, String loc){
        this.deptno=deptno;
        this.dname=dname;
        this.loc=loc;
    }

    public void setDname(String dname){
        this.dname=dname;
    }
    public String getDname(){
        return this.dname;
    }
    public String getLoc() {
        return this.loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
    // dto : 데이터 저장(set) 및 전송(get)
    public void setDeptno(int deptno){
        this.deptno=deptno;
    }
    public int getDeptno(){
        return this.deptno;
    }
//    @Override
//    public String toString(){
//        String str="";
//        str+="deptno: "+deptno+",";
//        str+="dname: "+dname+",";
//        str+="loc: "+loc+",";
//        return str;
//    }
    @Override
    public String toString() {
        return "{" +
                "deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                "}\n";
    }
}
class T{
    public static void main(String[] args) {
        L07DeptDto deptDto=new L07DeptDto();
        //deptDto.deptno=10;
        deptDto.setDeptno(10);
        deptDto.setDname("리서치");
        deptDto.setLoc("서울");
        System.out.println(deptDto.getDeptno());
        System.out.println(deptDto.getDname());
        System.out.println(deptDto.getLoc());
        System.out.println(deptDto);
        L07DeptDto deptDto2=new L07DeptDto(20,"개발","부산");
        System.out.println(deptDto2);
    }
}