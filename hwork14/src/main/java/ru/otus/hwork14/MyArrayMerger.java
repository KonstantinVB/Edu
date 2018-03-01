package ru.otus.hwork14;

import java.util.Arrays;

import static ru.otus.hwork14.Main.myArray;
import static ru.otus.hwork14.Main.THREAD_COUNT;
import static ru.otus.hwork14.Main.nextElements;
import static ru.otus.hwork14.Main.counter;

public class MyArrayMerger implements MyWorker {
    int[] inArray;

    public MyArrayMerger(int[] myArray){
        this.inArray=myArray;
    }

    @Override
    public void run(int i) {
        int arrLength = inArray.length;
        int startPos = (arrLength / THREAD_COUNT) * i;
        int procLen;
        if ((i + 1) == THREAD_COUNT) {
            procLen = arrLength - startPos;
        } else {
            procLen = arrLength / THREAD_COUNT;
        }
        int[] partArray = Arrays.copyOfRange(inArray,startPos,startPos+procLen);
        for (int j = 0; j < partArray.length; j++) synchronized (nextElements) {
            nextElements[i] = partArray[j];
            while (minIndex()!=i) {
                try {
                    nextElements.notifyAll();
                    nextElements.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (minIndex()==i) {
                myArray[counter.getAndIncrement()] = partArray[j];
            }
            if (j==partArray.length-1) {
                nextElements[i] = inArray.length+1;
            }
            nextElements.notifyAll();
        }
        System.out.println("Merged by "+Thread.currentThread().getName());
    }

    private int minIndex() {
        int indexOfMin = 0;
        boolean hasNull=false;
        for (int i = 1; i < nextElements.length; i++) {
            if (nextElements[i]!=null&&nextElements[indexOfMin]!=null) {
                if (nextElements[i] < nextElements[indexOfMin]) {
                    indexOfMin = i;
                }
            } else hasNull=true;
        }
        if (hasNull) {return nextElements.length;} else {return indexOfMin;}
    }

}
