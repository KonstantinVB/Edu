package ru.otus.hwork12.cache;


class MyCacheElement<V> {
    private final V value;
    private final long creationTime;
    private long lastAccessTime;


    MyCacheElement(V value) {
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    V getValue() {
        return value;
    }

    long getCreationTime() {
        return creationTime;
    }

    long getLastAccessTime() {
        return lastAccessTime;
    }

    void setAccessed() {
        lastAccessTime = getCurrentTime();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
