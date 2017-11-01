package ru.otus.hwork03;

//import java.util.List;
import java.util.Collections;

public class Main {
    public static void main(String... args) {
        MyArrayList<String> arrlist = new MyArrayList<String>();
        arrlist.add("A");
        arrlist.add("B");
        arrlist.add("C");
        arrlist.add("from java2s.com");
        System.out.println(arrlist);
        // add values to this collection
        boolean b = Collections.addAll(arrlist, "1","2","3");
        System.out.println(arrlist);

    }
}
