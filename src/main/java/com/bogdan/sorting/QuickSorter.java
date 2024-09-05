package com.bogdan.sorting;

import com.bogdan.list.SimpleList;

import java.util.Comparator;

/**
 * Класс предоставляющий реалитзующий интерфейс Sorter и его метод сортировки.
 * В классе представлен метод быстрой сортировки для передаваемого списка.
 */
public class QuickSorter implements Sorter {

    /**
     * Метод быстрой сортировки передаваемого списка. В качестве параметров список для сортировки,
     * первый и последний индекс списка. Сначала мы проверяем индексы и продолжаем только в том случае,
     * если есть еще элементы для сортировки. Мы получаем индекс отсортированного опорного элемента
     * и используем его для рекурсивного вызова метода partition() с теми же параметрами, что и у метода quickSort(),
     * но с другими индексами. Метод partition() для простоты берет последний элемент в качестве опорного.
     * Затем проверяет каждый элемент и меняет его перед опорным, если его значение меньше. К концу разбиения
     * все элементы, меньшие опорного элемента, находятся слева от него, а все элементы, большие опорного
     * элемента, находятся справа от него. Опорный элемент находится в своей окончательной
     * отсортированной позиции, и функция возвращает эту позицию
     * @param list передаваемый список для сортировки
     * @param comparator тип компаратора для выбора поля сортировки
     * @param <T> тип элемента который передается в метод сортировки.
     */
    @Override
    public <T> void sort(SimpleList<T> list, Comparator<T> comparator){
        quickSort(list,0,list.size() - 1,comparator);
    }

    private <T> void quickSort(SimpleList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);

            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private <T> int partition(SimpleList<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    private <T> void swap(SimpleList<T> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
