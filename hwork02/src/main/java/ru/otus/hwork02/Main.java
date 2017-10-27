package ru.otus.hwork02;

import java.util.function.Supplier;

/**
 * VM options -Xmx512m -Xms512m
 */

//        Определяем размер пустой строки и пустых контейнеров.
//        Определяем рост размера контейнера от количества элементов в нем.

public class Main {
    public static void main(String... args) throws InterruptedException {
        MemCounter memcnt = new MemCounter();
        calculateMemory(memcnt,"empty array container",()-> new Boolean[0]);
        calculateMemory(memcnt,"empty string",()-> new String(new char[0]));
        calculateMemory(memcnt,"string with 1 char",()-> new String(new char[1]));
        calculateMemory(memcnt,"string with 10 char",()-> new String(new char[10]));
        calculateMemory(memcnt,"string with 100 char",()-> new String(new char[100]));
    }

    private static void calculateMemory(MemCounter memcnt, String OName, Supplier OBuilder){
        System.out.print("Size of " + OName + "... ");
        try {
            long size = memcnt.calculate(OBuilder);
            System.out.println(size + " bytes");
        }catch(Exception ex){
            System.out.print("calculation was wrong: ");
            System.out.println(ex.getMessage());
        }
    }
}
