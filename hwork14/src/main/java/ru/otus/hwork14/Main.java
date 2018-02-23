package ru.otus.hwork14;

/*
Написать приложение, которое сортирует массив чисел в 4 потоках
 с использованием библиотеки или без нее.
*/

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static Long spentTime = System.currentTimeMillis();
    private static Integer ARRAY_SIZE = 100_000;
    private static Integer THREAD_COUNT=4;

    public static void main(String... args) throws InterruptedException, NoSuchMethodException {
        MyArraySorter sorter = new MyArraySorter(ARRAY_SIZE,THREAD_COUNT);
        sorter.runThreads(1);
        System.out.println("Total "+String.valueOf(System.currentTimeMillis()-spentTime)+" ms ");
        sorter.runThreads(2);
        System.out.println("Total "+String.valueOf(System.currentTimeMillis()-spentTime)+" ms ");
    }

}