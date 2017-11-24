package ru.otus.hwork03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.*;

/*
 * Создаем ArrayList
 */
public class MyArrayList<E> implements List<E>
{
    private int size = 0;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    transient Object[] el;
    private static final Object[] EMPTY_EL = {};

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.el = new Object[initialCapacity];
            size = el.length;
        } else if (initialCapacity == 0) {
            this.el = EMPTY_EL;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    public MyArrayList() {
        this.el = EMPTY_EL;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("called index not in array size");
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - el.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int newCapacity = el.length;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        el = Arrays.copyOf(el, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean add(E e) {
        ensureExplicitCapacity(size + 1);
        this.el[size++] = e;
        return true;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E) el[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = (E) el[index];
        el[index] = element;
        return oldValue;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        rangeCheck(index);
        return new ListItr(index);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    private class ListItr implements ListIterator<E> {
        int lastRet = -1;
        int cursor;

        ListItr(int index) {
            super();
            cursor = index;
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.el;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

// Not implemented Objects of listIterator
        @Override
        public void add(E e) {
            throw new NotImplementedException();
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }

        @Override
        public int previousIndex() {
            throw new NotImplementedException();
        }

        @Override
        public int nextIndex() {
            throw new NotImplementedException();
        }

        @Override
        public E previous() {
            throw new NotImplementedException();
        }

        @Override
        public boolean hasPrevious() {
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext() {
            throw new NotImplementedException();
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(el, size);
    }

    public String toString() {
        return Arrays.toString(this.el);
    }

// Not implemented Objects of MyArrayList

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new NotImplementedException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public E remove(int index) {
        throw new NotImplementedException();
    }

    @Override
    public void add(int index, E element) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new NotImplementedException();
    }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isEmpty() {
        throw new NotImplementedException();
    }
}