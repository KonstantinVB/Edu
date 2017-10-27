package ru.otus.hwork02;

import java.util.function.*;

/**
 * Измеряем размер объектов
 */
public class MemCounter {
    private Runtime runtime;

    public MemCounter(){
        this.runtime = Runtime.getRuntime();
    }

    /**
     * Вычисляет размер объекта, конструирумого с помощью OBuilder с заданным количеством прогонов и объектов.
     */
    public long calculate(Supplier OBuilder) throws InterruptedException{
        return this.calculate(OBuilder,1, 500_000);
    }

    public long calculate(Supplier oBuilder, int iCounter, int oCounter) throws InterruptedException {
        long mem1,mem2;
        long objectsSizesSum = 0;
        Object[] objectArray;
        for(int i = 0; i < iCounter; i++) {
            objectArray = null;
            objectArray = new Object[oCounter];
            this.runGC();
            mem1 = getUsedMemory();
            for (int j = 0; j < oCounter; j++) {
                objectArray[j] = oBuilder.get();
            }
            mem2 = getUsedMemory();
            long objectSize = (mem2 - mem1)/oCounter;
            objectsSizesSum += objectSize;
        }
        return objectsSizesSum/iCounter;
    }

    private void runGC() throws InterruptedException {
        System.gc();
        Thread.sleep(50);
    }

    private long getUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
