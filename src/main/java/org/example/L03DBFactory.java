package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class L03DBFactory {
    public int b;
    int a=100;
    protected  int c;
    private  int d;
    static private int e=2000; //클래스변수 (독립적으로 데이터로 존재!!)
    //class 전역에 선언된 변수 => 필드
    //static : 클래스 변수
    //접근지정자 : public(모두) [default](같은패키지) protected(상속) private(class)

    private static String url="jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static String user="scott";
    private static String pw="tiger";


    //public static Connection conn= DriverManager.getConnection(url,user,pw);

    //일반적으로 사용되는 1개의 객체를 자원으로 사용하는 방법 : 문제를 컨트롤하기 힘들다!
    public static Connection conn=null;
    static {
        try {
            conn=DriverManager.getConnection(url,user,pw);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }//jvm이 static을 메소드 영역에 저장할때 실행되는 블럭 (초기실행)

    //싱글톤패턴으로 1개의 자원을 안전하게 공유하는 방법!
    private static Connection beanConn; //1.private으로 바로접근 못하게 하기
    public static Connection getConn() throws SQLException{//2.public으로 안전하게 접근할 함수를 정의
        //3. 안전하게 반환하도록 검사를 진행
        if(beanConn==null || beanConn.isClosed()){
            beanConn=DriverManager.getConnection(url,user,pw);
        }
        return beanConn;
    }

    public static void main(String[] args) throws SQLException {
        conn.close();
        System.out.println(conn); //위험한 상황
        System.out.println(conn.isClosed());

        getConn().close();
        System.out.println(getConn());//close 되어 있다면 매번 새로 만들기 때문에 안전
        System.out.println(getConn().isClosed());


        //System.out.println(a);
        L03DBFactory dbFactory=new L03DBFactory();
        System.out.println(dbFactory.a); //필드가 데이터로 존재하려면 꼭 객체가 되어야함
        System.out.println(e);
    }
}
