package com.bogdan.sorting;

import com.bogdan.list.SimpleList;

import java.util.Comparator;

public interface Sorter {
    <T> void sort(SimpleList<T> list, Comparator<T> comparator);
}
