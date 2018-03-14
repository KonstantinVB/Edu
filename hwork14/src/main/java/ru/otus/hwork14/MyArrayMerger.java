package ru.otus.hwork14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyArrayMerger {
    private int[] inArray;
    private int threadCount;
    private int arraySize;

    public MyArrayMerger(int[] myArray, int threadCount, int arraySize){
        this.inArray=myArray;
        this.threadCount=threadCount;
        this.arraySize=arraySize;
    }

    public void run() {
        Integer[] nextElements = new Integer[threadCount];
        List<int[]> partArray = new ArrayList<>(threadCount);
        for (int i=0; i < threadCount; i++) {
            int startPos = (arraySize / threadCount) * i;
            int procLen;
            if ((i + 1) == threadCount) {
                procLen = arraySize - startPos;
            } else {
                procLen = arraySize / threadCount;
            }
            nextElements[i]=inArray[startPos];
            partArray.add(i, Arrays.copyOfRange(inArray, startPos+1, startPos + procLen));
        }

        for (int i=0; i<arraySize; i++) {
            int pointer = minIndex(nextElements);
            inArray[i]=nextElements[pointer];
            if (partArray.get(pointer).length > 0) {
                nextElements[pointer]=partArray.get(pointer)[0];
                partArray.set(pointer,Arrays.copyOfRange(partArray.get(pointer),1,partArray.get(pointer).length));
            } else {
                nextElements[pointer]=null;
            }
        }
        System.out.println("Merged");
    }

    private int minIndex(Integer[] nextElements) {
        int indexOfMin=0;
        for (int i = 1; i < nextElements.length; i++) {
            if (nextElements[indexOfMin] == null) {
                indexOfMin=i;
            } else if (nextElements[i] != null) {
                if (nextElements[i] < nextElements[indexOfMin]) {
                    indexOfMin = i;
                }
            }
        }
        return indexOfMin;
    }

}
