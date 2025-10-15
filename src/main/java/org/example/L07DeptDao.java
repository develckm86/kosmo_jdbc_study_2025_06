package org.example;

public interface L07DeptDao {
}
@FunctionalInterface //람다식으로 익명클래스를 대체
interface B{//모든 함수는 자동으로 public(오픈된 기능), abstract(추상화)
    //public static final int a=10; 클래스상수만 정의가능
    int b=20; //자동으로 클래스상수가됨
    //public void a(){};
    void e(); //abstract public void e()
}
abstract class Aable implements B{
    int t=20;
    public void d(){};
    //추상=> 기능(함수)을 추상화 =>재사용 빈도를 높인다.
    abstract public void c(); //{}바디를 생략 =>추상화
}
class A extends Aable{
    int a=10; //데이터
    public void b(){} //기능
    @Override
    public void c() {}
    @Override
    public void e() {
    }
} //객체가될 수 있는 설계도

//class 1 implement B{ e(){} } => 익명클래스
class InstanceTest{
    public static void main(String[] args) {
        //Type : class, abstract class, interface
        //객체지향 문법의 추상화!!=> 타입을 재사용(**)
        A a=new A();
//        Aable aable=new Aable(); //추상함수를 포함하는 미완성 설계도는 객체가 될수 없다.

        B b=new B(){
            @Override
            public void e() {}
        };

        B b2=()->{}; //==e(){}
    }
}
