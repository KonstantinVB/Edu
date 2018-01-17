package ru.otus.hwork08;


/*public class MyObject{
    private int f1;
    private String f2;

    public MyObject (int v1, String v2) {
        this.f1=v1;
        this.f2=v2;
    }
}*/

class A{
    int f1 = 123;
    String f2 = "abc";
}

class AA{
    int f3 = 456;
    A f4 = new A();
}
class MyObject {
    AA f6 = new AA();
    int f5 = 9;

}


