package com.bogdan.list;

public interface SimpleList<T> extends Iterable<T> {
    boolean add(T element);

    void add(int index, T element);

    T set(int index, T element);

    T get(int index);

    boolean remove(T element);

    T remove(int index);

    void clear();

    boolean isEmpty();

    int size();

    void trimToSize();
}