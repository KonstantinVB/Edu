package ru.otus.hwork14;

public class MyArraySorter implements MyWorker {
    private int[] inArray;
    private int threadCount;

    public MyArraySorter(int[] myArray, int threadCount){
        this.inArray=myArray;
        this.threadCount=threadCount;
    }

    @Override
    public void run(int i) {
        int arrLength = inArray.length;
        int startPos = (arrLength / threadCount) * i;
        int procLen;
        if ((i + 1) == threadCount) {
            procLen = arrLength - startPos;
        } else {
            procLen = arrLength / threadCount;
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

