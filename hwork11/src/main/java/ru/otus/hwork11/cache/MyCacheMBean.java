package ru.otus.hwork11.cache;

public interface MyCacheMBean<K, V> {

    void setParams(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal);

    void put(K key, V element);

    V get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

}
