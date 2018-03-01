package ru.otus.hwork14;

import java.util.ArrayList;
import java.util.List;

public class MyThreadsFactory {

    public MyThreadsFactory () {

    }

    public List<Thread> getThreads(MyWorker worker, int threadCount) {
        List<Thread> threadList = new ArrayList<>();
        for (int i =0; i < threadCount; i++) {
            int threadNum = i;
            threadList.add(i,new Thread(() -> {
                worker.run(threadNum);
            }));
        }
        return threadList;
    }

}
