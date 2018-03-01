package ru.otus.hwork14;

/*
Написать приложение, которое сортирует массив чисел в 4 потоках
 с использованием библиотеки или без нее.
*/

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static int ARRAY_SIZE = 100;
    public static int THREAD_COUNT = 4;
    public static Integer[] nextElements = new Integer[THREAD_COUNT];
    public static int[] myArray = new int[ARRAY_SIZE];
    public static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String... args) throws InterruptedException {
        Long spentTime = System.currentTimeMillis();
        List<Thread> threadList;
//Fill new array
        threadList = new MyThreadsFactory().getThreads(new MyArrayCreator(myArray),THREAD_COUNT);
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).start();
        }
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).join();
        }
        System.out.println(Arrays.toString(myArray));
        System.out.println("Total time "+String.valueOf(System.currentTimeMillis()-spentTime)+" ms ");
//Sort array partitions
        threadList = new MyThreadsFactory().getThreads(new MyArraySorter(myArray),THREAD_COUNT);
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).start();
        }
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).join();
        }
        System.out.println(Arrays.toString(myArray));
        System.out.println("Total time "+String.valueOf(System.currentTimeMillis()-spentTime)+" ms ");
//Merge sorted partitions and write them into recreated array
        threadList = new MyThreadsFactory().getThreads(new MyArrayMerger(myArray),THREAD_COUNT);
        myArray = new int[ARRAY_SIZE];
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).start();
        }
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).join();
        }
        System.out.println(Arrays.toString(myArray));
        System.out.println("Total time "+String.valueOf(System.currentTimeMillis()-spentTime)+" ms ");
    }

}