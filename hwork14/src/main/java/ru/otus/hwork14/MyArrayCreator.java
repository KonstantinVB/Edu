package ru.otus.hwork14;

import java.util.Random;

import static ru.otus.hwork14.Main.THREAD_COUNT;

public class MyArrayCreator implements MyWorker{
    private int[] inArray;

    public MyArrayCreator (int[] myArray){
        this.inArray=myArray;
    }

    public void run(int i) {
        int arrLength = inArray.length;
        int startPos = (arrLength / THREAD_COUNT) * i;
        int procLen;
        if ((i + 1) == THREAD_COUNT) {
            procLen = arrLength - startPos;
        } else {
            procLen = arrLength / THREAD_COUNT;
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
