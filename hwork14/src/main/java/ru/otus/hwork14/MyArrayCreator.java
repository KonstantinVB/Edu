package ru.otus.hwork14;

import java.util.Random;

public class MyArrayCreator implements MyWorker{
    private int[] inArray;
    private int threadCount;

    public MyArrayCreator (int[] myArray, int threadCount){
        this.inArray=myArray;
        this.threadCount=threadCount;
    }

    public void run(int i) {
        int arrLength = inArray.length;
        int startPos = (arrLength / threadCount) * i;
        int procLen;
        if ((i + 1) == threadCount) {
            procLen = arrLength - startPos;
        } else {
            procLen = arrLength / threadCount;
        }
        fillWorker(startPos,procLen);
        System.out.println("Originated by "+Thread.currentThread().getName());
    }

    private void fillWorker(Integer startPos, Integer procLen) {
        final Random random = new Random();
        for (int i = startPos; i < startPos+procLen; i++) {
            inArray[i] = random.nextInt(inArray.length);
        }
    }

}
