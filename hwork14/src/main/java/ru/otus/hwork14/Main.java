package ru.otus.hwork14;

/*
Написать приложение, которое сортирует массив чисел в 4 потоках
 с использованием библиотеки или без нее.
*/


import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Main {
    public static Queue <Integer> sortedArray = new PriorityQueue<>();
    public static Integer ARRAY_SIZE = 100_000;
    public static Integer THREAD_COUNT = 4;
    public static Long spentTime = System.currentTimeMillis();
    public static int[] unsortedArray = new int[ARRAY_SIZE];

    public static void main(String... args) throws InterruptedException {
        fillArray(sortedArray);
        List <Thread> threadList = new ArrayList<>();
        for (int i =0; i < THREAD_COUNT; i++) {
            Integer startPos = (ARRAY_SIZE/THREAD_COUNT)*i;
            Integer procLen;
            if ((i+1)==THREAD_COUNT) {
                procLen = ARRAY_SIZE-startPos;
            } else {
                procLen = ARRAY_SIZE/THREAD_COUNT;
            }
            threadList.add(i,new Thread(() -> {
                sortWorker(startPos,procLen);
            }));
            threadList.get(i).start();
            threadList.get(i).join();
        }
        fillArray(sortedArray);
    }

    private static void fillArray(Queue <Integer> sortedArray) {
        final Random random = new Random();
        String text = "Total "+String.valueOf(System.currentTimeMillis()-spentTime)+" ms ";;
        if (sortedArray.isEmpty()) {
            for (int i = 0; i < ARRAY_SIZE; i++) {
                unsortedArray[i] = random.nextInt(ARRAY_SIZE);
            }
            text = text+unsortedArray.length+" unsorted elements";
        } else {
            for (int i = 0; i < ARRAY_SIZE; i++) {
                unsortedArray[i] = sortedArray.poll();
            }
            text = text+unsortedArray.length+" sorted elements";
        }
        System.out.println(Arrays.toString(unsortedArray)+"/"+text);
    }

    private static void sortWorker(Integer startPos, Integer procLen) {
        for (int i = startPos; i<startPos+procLen; i++) {
            sortedArray.add(unsortedArray[i]);
        }
    }

}