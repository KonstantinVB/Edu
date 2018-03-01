package ru.otus.hwork14;

import static ru.otus.hwork14.Main.THREAD_COUNT;

public class MyArraySorter implements MyWorker {
    private int[] inArray;

    public MyArraySorter(int[] myArray){
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
        sortWorker(startPos,procLen);
        System.out.println("Sorted by "+Thread.currentThread().getName());
    }

    private void sortWorker(int startPos, int procLen) {
        Boolean NEED_SORT = true;
        while (NEED_SORT) {
            Integer hi = null;
            for (int i = startPos; i < startPos+procLen-1; i++) {
                if (inArray[i] > inArray[i + 1]) {
                    hi = swap(i);
                }
            }
            NEED_SORT=(hi!=null);
        }
    }

    private Integer swap(Integer counter) {
        Integer big = inArray[counter];
        inArray[counter] = inArray[counter + 1];
        inArray[counter + 1] = big;
        return big;
    }

}

