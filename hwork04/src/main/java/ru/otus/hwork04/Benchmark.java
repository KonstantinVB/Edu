package ru.otus.hwork04;

import java.util.ArrayList;

/**
 * Создаем объекты и заполняем память
 */
public class Benchmark {
    private int size = 0;

    public Benchmark(int size) {
        this.size = size;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    void run() throws InterruptedException {
        final ArrayList<String> list = new ArrayList<>(this.size);
        while (true) {
            for (int i = 0; i < this.size; i++) {
                list.add(new String(new char[500]));
            }
            for (int i = 0; i < this.size / 2; i++) {
                list.remove(i);
            }

            Thread.sleep(100);
        }
    }
}
