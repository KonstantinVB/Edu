package ru.otus.hwork05;

/*
 * this class will be tested.
 */
@SuppressWarnings("unused")
public class MyTestClass {
    private int a = 0;
    private String s = "";

    public MyTestClass() {
    }

    public MyTestClass(Integer a) {
        this.a = a;
    }

    public MyTestClass(int a, String s) {
        this.a = a;
        this.s = s;
    }

    int getA() {
        return a;
    }

    String getS() {
        return s;
    }

    private void setDefault(){
        a = 0;
        s = "";
    }
}
