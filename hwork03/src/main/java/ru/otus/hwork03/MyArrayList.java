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

    public MyArrayList(Collection<? extends E> c) {
        el = c.toArray();
        if ((size = el.length) != 0) {
            if (el.getClass() != Object[].class)
                el = Arrays.copyOf(el, size, Object[].class);
        } else {
            this.el = EMPTY_EL;
        }
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

        for(int i = size; i < el.length; i++) {
            if (el[i] != null) {
                size++;
            }
        }
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
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }
        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            cursor = i + 1;
            return (E) el[lastRet = i];
        }

    }
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }


        public boolean hasNext() {
            return cursor != size;
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
        public E previous() {
            throw new NotImplementedException();
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(el, size);
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
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
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isEmpty() {
        throw new NotImplementedException();
    }
}