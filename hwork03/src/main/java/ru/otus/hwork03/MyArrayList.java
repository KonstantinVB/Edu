package ru.otus.hwork03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;

/**
 * Создаем ArrayList
 */
public final class MyArrayList<E> implements List<E>
{
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;
    private Object[] elements;
    private int size;
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > this.elements.length) {
            this.grow(minCapacity);
        }
    }

/**
* {@inheritDoc}
*/

    @Override
    public int size() {
        return this.size;
    }
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size)
            return (T[]) Arrays.copyOf(this.elements, this.size, a.getClass());
        System.arraycopy(this.elements, 0, a, 0, this.size);
        if (a.length > this.size)
            a[size] = null;

        return a;
    }
    @Override
    public boolean add(E e) {
        this.ensureCapacity(size + 1);
        this.elements[size++] = e;
        return true;
    }
    @Override
    public boolean addAll(Collection<? extends E> c) {
        this.ensureCapacity(size + c.size());
        for (E element : c) {
            this.elements[size++] = element;
        }
        return true;
    }
    private void grow(int minCapacity) {
        long newCapacity = Math.max(minCapacity, ((this.elements.length * 3L) / 2 + 1));
        if (newCapacity > MAX_CAPACITY) {
            newCapacity = MAX_CAPACITY;
        }

        Object[] copy = new Object[(int) newCapacity];
        System.arraycopy(this.elements, 0, copy, 0, this.size);

        this.elements = copy;
    }
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

}

