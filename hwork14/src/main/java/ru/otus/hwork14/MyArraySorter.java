package ru.otus.hwork14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyArraySorter {
    private Integer ARRAY_SIZE;
    private Integer THREAD_COUNT;
    private AtomicBoolean NEED_SORT = new AtomicBoolean(true);
    private int[] myArray;

    public MyArraySorter(Integer array_size, Integer thread_count){
        this.ARRAY_SIZE=array_size;
        this.THREAD_COUNT=thread_count;
        this.myArray = new int[ARRAY_SIZE];
    }

    public void runThreads(Integer code) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i =0; i < THREAD_COUNT; i++) {
            Integer startPos = (ARRAY_SIZE/THREAD_COUNT)*i;
            Integer procLen;
            if ((i+1)==THREAD_COUNT) {
                procLen = ARRAY_SIZE-startPos;
            } else {
                procLen = ARRAY_SIZE/THREAD_COUNT;
            }
            threadList.add(i,new Thread(() -> {
                switch (code) {
                    case 1: fillArray(startPos,procLen); break;
                    case 2: sortWorker(); break;
                }
            }));
            threadList.get(i).start();
        }
        for (int i =0; i < THREAD_COUNT; i++) {
            threadList.get(i).join();
        }
        System.out.println(Arrays.toString(myArray));
    }

    private void fillArray(Integer startPos, Integer procLen) {
        final Random random = new Random();
        for (int i = startPos; i < startPos+procLen; i++) {
            myArray[i] = random.nextInt(ARRAY_SIZE);
        }
        System.out.println("Originated by "+Thread.currentThread().getName());
    }

    private void sortWorker() {
        while (NEED_SORT.get()) {
            Integer hi = null;
            for (Integer counter=0;counter<ARRAY_SIZE-1;counter++) {
                if (myArray[counter] > myArray[counter + 1]) {
                    hi = swap(counter);
                }
            }
            NEED_SORT.set(hi!=null);
        }
        System.out.println("Sorted by "+Thread.currentThread().getName());
    }

    private Integer swap(Integer counter) {
        Integer big = myArray[counter];
        myArray[counter] = myArray[counter + 1];
        myArray[counter + 1] = big;
        return big;
    }

}

